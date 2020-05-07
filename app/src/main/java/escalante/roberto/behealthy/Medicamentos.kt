package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_medicamentos.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Medicamentos : AppCompatActivity() {

    var jsonFile: JSONFile? = null
    var name = "medicamento"
    var day = "lunes"
    var hora = "10:30 a.m."
    var data : Boolean = false
    var lista = ArrayList<medicinas>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamentos)




        jsonFile = JSONFile()

        fetchingData()


        var intent = Intent(this, Menu::class.java)

        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView

        botonAtras.setOnClickListener{
            startActivity(intent)
        }
    }

    fun  fetchingData(){
        //leer el archivo json


        //var txtdia: TextView = findViewById(R.id.textoDia) as TextView

        try {
            var json: String = jsonFile?.getData(this)?: ""
            if(json!= ""){

                this.data=true
                var jsonArray: JSONArray = JSONArray(json)

                this.lista = parseJson(jsonArray)


                for (i in lista){

                    //txtdia.setText(i.nombre)
                    //textoMedicamento.setText(i.nombre)


                    //when(i.nombre){
                    //    "lunes" -> name = i.nombre
                    //    "martes" -> day = i.dias
                    //    "miercoles" -> hora = i.hora

                    //}
                }

            } else{
                this.data = false
            }
        }catch (exception: JSONException){
            exception.printStackTrace()
        }

    }





    fun parseJson (jsonArray: JSONArray): ArrayList<medicinas> {
        //convierte el arreglo json en la lista de objetos tipo medicamentos

        var lista = ArrayList<medicinas>()

        for (i in 0..jsonArray.length()) {
            try {
                val nombre = jsonArray.getJSONObject(i).getString("nombre")
                val dias = jsonArray.getJSONObject(i).getString("dias")
                val hora = jsonArray.getJSONObject(i).getString("hora")
                var med = medicinas(nombre, dias, hora)
                lista.add(med)
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
        }


        return lista
    }

}
