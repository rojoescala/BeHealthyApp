package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Sign_in : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)




        var intent = Intent(this, Menu::class.java)
        var boton: Button = findViewById(R.id.btn_signin) as Button
        boton.setOnClickListener{
           startActivity(intent)
        }

    }
}
