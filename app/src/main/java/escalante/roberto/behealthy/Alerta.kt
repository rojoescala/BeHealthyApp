package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import escalante.roberto.behealthy.utilities.JSONFile
import escalante.roberto.behealthy.utilities.PorcentajeAgua
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.lang.Exception
import kotlin.math.roundToInt

class Alerta : AppCompatActivity() {
    var jsonFile: JSONFile? = null
    var progressState: Double = 0.0
    var data: Boolean = false
    var lista = ArrayList<PorcentajeAgua>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerta)

        var intent = Intent(this, Menu::class.java)
        val btnagua: Button = findViewById(R.id.btnagua) as Button
        val progress: ProgressBar = findViewById(R.id.progresswater) as ProgressBar;
        val botonAtras: ImageView = findViewById(R.id.atras) as ImageView

        botonAtras.setOnClickListener {
            startActivity(intent)
        }

        btnagua.setOnClickListener {
            progressState += 12.5
            progress.setSecondaryProgress(progressState.roundToInt())
            guardar()
        }
    }

    fun guardar() {
        var j: JSONObject = JSONObject()
        j.put("porcentajeAgua", progressState)
        saveJson(j.toString())
    }

    fun saveJson(jsonString: String) {
        try {
            var fo=FileWriter("json.json")
            fo.write(jsonString)
            fo.close()
        }catch (ex:Exception){
            print(ex.message)
        }
    }
}
