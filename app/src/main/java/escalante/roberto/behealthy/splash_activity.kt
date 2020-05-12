package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.view.WindowManager
import escalante.roberto.behealthy.utilies.Comidas
import escalante.roberto.behealthy.utilies.JSONFileDieta
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class splash_activity : AppCompatActivity() {
    var jsonFile: JSONFileDieta? = null
    var lista = ArrayList<Comidas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_activity)

        jsonFile = JSONFileDieta()
        lista.clear()
        lista.add(
            Comidas(
                "DESAYUNO",
                resources.getString(R.string.desayuno_2),
                false
            )
        )
        lista.add(
            Comidas(
                "COMIDA",
                resources.getString(R.string.comida_2),
                false
            )
        )
        lista.add(
            Comidas(
                "MERIENDA",
                resources.getString(R.string.merienda_2),
                false
            )
        )
        lista.add(
            Comidas(
                "CENA",
                resources.getString(R.string.cena_2),
                false
            )
        )
        lista.add(
            Comidas(
                "RECENA",
                resources.getString(R.string.recena_2),
                false
            )
        )

        guardar()
        Handler().postDelayed({
            startActivity(Intent(this,Sign_in::class.java))
            finish()
        },1000)
    }


    fun guardar(){
        var jsonArray = JSONArray()
        var o : Int = 0
        for (i in lista){
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("comida", i.comida)
            j.put("descripcion", i.descripcion)
            j.put("estado", i.estado)

            jsonArray.put(o,j)
            o++
        }
        jsonFile?.saveData(this, jsonArray.toString())
    }
}