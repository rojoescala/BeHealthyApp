package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Medicamentos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamentos)


        var intent = Intent(this, Menu::class.java)

        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView

        botonAtras.setOnClickListener{
            startActivity(intent)
        }
    }
}
