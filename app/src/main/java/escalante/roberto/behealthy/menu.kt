package escalante.roberto.behealthy

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_menu.*


class menu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        salud.text = getString(R.string.salud)
        salud.typeface = Typeface.createFromAsset(assets, "fonts/modern.otf")






    }



}
