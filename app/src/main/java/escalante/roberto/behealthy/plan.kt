package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class plan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan1)
        var intent = Intent(this, Menu::class.java)
        var intent2 = Intent(this, plan2::class.java)

        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView
        var botonSiguiente: ImageView = findViewById(R.id.siguiente) as ImageView

        botonAtras.setOnClickListener{
            startActivity(intent)
        }
        botonSiguiente.setOnClickListener{
            startActivity(intent2)
        }
    }
}
