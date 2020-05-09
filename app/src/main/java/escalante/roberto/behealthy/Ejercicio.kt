package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_ejercicio.*
import kotlinx.android.synthetic.main.activity_medicamentos.*
import kotlinx.android.synthetic.main.activity_medicamentos.textoDia
import java.util.*

class Ejercicio : AppCompatActivity() {
    var stringKey = "skey"
    var ejercicio: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio)






        var sCalendar = Calendar.getInstance()
        var dayLongName: String? = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        textoDia.setText(dayLongName)

        var intent = Intent(this, Menu::class.java)
        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView

        buttonTerminado.setOnClickListener{
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("estado",true)
            startActivity(intent)
            finish()

        }

        botonAtras.setOnClickListener{
            startActivity(intent)
        }


    }


    fun estado(estadito: Boolean){

        intent.putExtra(stringKey,estadito)
    }
}
