package escalante.roberto.behealthy

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.FragmentActivity
//import androidx.test.orchestrator.junit.BundleJUnitUtils.getResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import escalante.roberto.behealthy.utilies.ReminderBroadcaster
import kotlinx.android.synthetic.main.activity_sign_in.*

const val RC_SIGN_IN = 343

class Sign_in : AppCompatActivity() {
    lateinit var mGoogleSignInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        createNotificationCannel()

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        var intent = Intent(this, Menu::class.java)
        var boton: Button = findViewById(R.id.btn_signin) as Button

        sign_in_button.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        boton.setOnClickListener{
           startActivity(intent)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) { // The Task returned from this call is always completed, no need to attach
// a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason.
// Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(FragmentActivity.TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null){
            val intent = Intent(this, Menu::class.java)
            val intent2=Intent(this, ReminderBroadcaster::class.java)
            var pendingIntent:PendingIntent= PendingIntent.getBroadcast(this,0,intent2,0)

            var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var timeAtButtonClick= SystemClock.elapsedRealtime()
            var hour=AlarmManager.INTERVAL_HOUR

            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,timeAtButtonClick+hour,hour,pendingIntent)

            startActivity(intent)
        }
    }



    private fun createNotificationCannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            var name:CharSequence="TomaAguaChannel"
            var description:String="Channel toma agua"

            var importante:Int= NotificationManager.IMPORTANCE_DEFAULT
            var channel: NotificationChannel =NotificationChannel("tomaAgua",name,importante)
            channel.setDescription(description)

            var noManagger:NotificationManager=getSystemService(NotificationManager::class.java)
            noManagger.createNotificationChannel(channel)
        }
    }
}
