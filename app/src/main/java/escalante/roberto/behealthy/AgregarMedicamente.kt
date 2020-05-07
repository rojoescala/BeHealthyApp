package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_medicamente.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AgregarMedicamente : AppCompatActivity() {

    var jsonFile: JSONFile? = null
    var name = "medicamento"
    var day = "lunes"
    var hora = "10:30 a.m."
    var data : Boolean = false
    var lista = ArrayList<medicinas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_medicamente)

        var intent = Intent(this, Medicamentos::class.java)

        //jsonFile = JSONFile()

        //fetchingData()

        agregarbutton.setOnClickListener{
            guardar()
            startActivity(intent)
        }

    }




    fun guardar(){

        var jsonArray = JSONArray()
        var o : Int = 0
        for(i in lista){
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("nombre", i.nombre)
            j.put("dias", i.dias)
            j.put("hora", i.hora)


            jsonArray.put(o, j)
            o++

        }

        jsonFile?.saveData(this,jsonArray.toString())

        Toast.makeText(this,"Datos guardados", Toast.LENGTH_SHORT).show()

    }


}
