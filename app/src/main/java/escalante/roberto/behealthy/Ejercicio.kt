package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import escalante.roberto.behealthy.utilies.CustomCircleDrawable
import escalante.roberto.behealthy.utilies.JSONFile
import escalante.roberto.behealthy.utilies.Porcentaje
import kotlinx.android.synthetic.main.activity_ejercicio.*
import kotlinx.android.synthetic.main.activity_medicamentos.*
import kotlinx.android.synthetic.main.activity_medicamentos.textoDia
import kotlinx.android.synthetic.main.activity_menu.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Ejercicio : AppCompatActivity() {
    //var stringKey = "skey"
    //var ejercicio: Boolean = false
    var jsonFile: JSONFile? = null
    var comida = 0
    var aguita = 0
    var movimiento = false
    var data: Boolean = false
    var lista = ArrayList<Porcentaje>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio)




        jsonFile = JSONFile()

        fetchingData()

        var sCalendar = Calendar.getInstance()
        var dayLongName: String? = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        textoDia.setText(dayLongName)

        var intent = Intent(this, Menu::class.java)
        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView

        buttonTerminado.setOnClickListener{
            if(movimiento == false){
                movimiento = true
                lista.add(Porcentaje(aguita,movimiento,comida,"Sabado"))
                guardar()
                buttonTerminado.setBackgroundResource(R.drawable.button_ejercicio_acabado)
            } else{
                Toast.makeText(this,"Ya hiciste ejercicio CRACK!!",Toast.LENGTH_SHORT).show()
            }

        }

        botonAtras.setOnClickListener{
            startActivity(intent)
        }


    }
    fun fetchingData(){
        try {
            var json : String = jsonFile?.getData(this) ?:""
            if (json != ""){
                this.data = true
                var jsonArray : JSONArray = JSONArray(json)

                this.lista = parseJson(jsonArray)

                for (i in lista){

                    aguita = i.agua
                    comida = i.dieta
                    movimiento = i.ejericio

                }
            } else {
                this.data = false
            }
        } catch (exception: JSONException){
            exception.printStackTrace()
        }
    }


    fun parseJson(jsonArray: JSONArray): ArrayList<Porcentaje>{
        var lista = ArrayList<Porcentaje>()

        for (i in 0..jsonArray.length()){
            try {
                val agua = jsonArray.getJSONObject(i).getInt("agua")
                val dieta = jsonArray.getJSONObject(i).getInt("dieta")
                val ejercicio = jsonArray.getJSONObject(i).getBoolean("ejercicio")
                val fecha = jsonArray.getJSONObject(i).getString("fecha")

                var porce = Porcentaje (agua, ejercicio,dieta , fecha)
                lista.add(porce)
            } catch (exception: JSONException){
                exception.printStackTrace()
            }
        }
        return lista
    }



    fun guardar(){
        var jsonArray = JSONArray()
        var o : Int = 0
        for (i in lista){
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("agua", i.agua)
            j.put("ejercicio", i.ejericio)
            j.put("dieta",i.dieta)
            j.put("fecha",i.fecha)

            jsonArray.put(o,j)
        }
        jsonFile?.saveData(this, jsonArray.toString())
        Toast.makeText(this,"Datos guardados", Toast.LENGTH_SHORT).show()
    }
}
