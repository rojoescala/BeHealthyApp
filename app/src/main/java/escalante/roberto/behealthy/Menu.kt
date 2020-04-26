package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        var intent = Intent(this, Medicamentos::class.java)
        var boton: TextView = findViewById(R.id.medicamentos) as TextView
        boton.setOnClickListener{
            startActivity(intent)
        }
    }
}
