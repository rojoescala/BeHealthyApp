package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // No se si es necesario hacer muchos itentos

        var intent2 = Intent(this, Alerta::class.java)
        var intent3 = Intent(this, Dieta::class.java)
        var intent4 = Intent(this, pantallaGlucosa::class.java)
        var intent5 = Intent(this, plan::class.java)
        var intent6 = Intent(this, Ejercicio::class.java)




        var botonAlerta: TextView = findViewById(R.id.btn_alerta) as TextView
        var botonDieta: LinearLayout = findViewById(R.id.btn_dieta) as LinearLayout
        var botonGlucosa: LinearLayout = findViewById(R.id.btn_glucosa) as LinearLayout
        var botonPlan: LinearLayout = findViewById(R.id.btn_plan) as LinearLayout
        var botonEjercicio: LinearLayout = findViewById(R.id.btn_ejercicio) as LinearLayout






        botonAlerta.setOnClickListener{
            startActivity(intent2)
        }

        botonDieta.setOnClickListener{
            startActivity(intent3)
        }

        botonGlucosa.setOnClickListener{
            startActivity(intent4)
        }

        botonPlan.setOnClickListener{
            startActivity(intent5)
        }

        botonEjercicio.setOnClickListener{
            startActivity(intent6)
        }



    }
}
