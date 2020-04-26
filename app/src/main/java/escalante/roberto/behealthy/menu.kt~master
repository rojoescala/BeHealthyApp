package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var intento: Intent = Intent(this,Medicamentos::class.java)

        var boton: TextView = findViewById(R.id.btn_medicamentos) as TextView

        boton.setOnClickListener{
            startActivity(intento)
        }
    }
}
