package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_medicamentos.*
import java.util.*
import kotlin.math.roundToInt

class Alerta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerta)
        val btnagua: Button = findViewById(R.id.btnagua) as Button
        val progress: ProgressBar = findViewById(R.id.progresswater) as ProgressBar;
        var progressState: Double = 0.0
        btnagua.setOnClickListener {
            progressState += 12.5
            progress.setSecondaryProgress(progressState.roundToInt())
        }
        var sCalendar = Calendar.getInstance()
        var dayLongName: String? = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        textoDia.setText(dayLongName)

        var intent = Intent(this, Menu::class.java)

        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView

        botonAtras.setOnClickListener{
            startActivity(intent)
        }
    }
}
