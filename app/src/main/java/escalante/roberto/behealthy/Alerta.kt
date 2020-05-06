package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import kotlin.math.roundToInt

class Alerta : AppCompatActivity() {
    var progressState:Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerta)

        var intent = Intent(this, Menu::class.java)
        val btnagua:Button = findViewById(R.id.btnagua) as Button
        val botonAtras: ImageView = findViewById(R.id.atras) as ImageView
        val progress: ProgressBar =findViewById(R.id.progresswater) as ProgressBar;

        botonAtras.setOnClickListener{
            startActivity(intent)
        }

        btnagua.setOnClickListener{
            progressState+=12.5
            progress.setSecondaryProgress(progressState.roundToInt())
        }
    }
}
