package escalante.roberto.behealthy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import escalante.roberto.behealthy.utilies.*
import kotlinx.android.synthetic.main.activity_agregar_medicamente.*
import kotlinx.android.synthetic.main.activity_dieta.*
import kotlinx.android.synthetic.main.activity_ejercicio.*
import kotlinx.android.synthetic.main.activity_menu.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class Menu : AppCompatActivity() {
    var jsonFile: JSONFile? = null
    var comida = 0
    var aguita = 0
    var movimiento = false
    var data: Boolean = false
    var lista = ArrayList<Porcentaje>()
    var fechaJSON: String? = null
    var listaFecha = ArrayList<Reinicio>()
    var jsonFileReinicio: JSONFileReinicio? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        jsonFileReinicio = JSONFileReinicio()
        fetchingDataDia()



        jsonFile = JSONFile()
        fetchingData()

        val anotherCurDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        var fecha = formatter.format(anotherCurDate)


        if(fecha != fechaJSON) {
            listaFecha.add(Reinicio(fecha))
            guardarFecha()
            comida = 0
            aguita = 0
            movimiento = false
            lista.add(Porcentaje(aguita,movimiento,comida,"sabado"))
            guardar()

        }


        // No se si es necesario hacer muchos itentos

        var intent2 = Intent(this, Alerta::class.java)
        var intent3 = Intent(this, Dieta::class.java)
        var intent4 = Intent(this, pantallaGlucosa::class.java)
        var intent5 = Intent(this, plan::class.java)
        var intent6 = Intent(this, Ejercicio::class.java)


        var botonMedicamentos: TextView = findViewById(R.id.btn_medicamentos) as TextView
        var botonAlerta: TextView = findViewById(R.id.btn_alerta) as TextView
        var botonDieta: LinearLayout = findViewById(R.id.btn_dieta) as LinearLayout
        var botonGlucosa: LinearLayout = findViewById(R.id.btn_glucosa) as LinearLayout
        var botonPlan: LinearLayout = findViewById(R.id.btn_plan) as LinearLayout
        var botonEjercicio: LinearLayout = findViewById(R.id.btn_ejercicio) as LinearLayout



        if (!data){
            var porcentajes = ArrayList<Porcentaje>()
            val fondo = CustomCircleDrawable(this, porcentajes)
            graph.background = fondo
        } else{
            actualizarGrafica()
        }








        botonMedicamentos.setOnClickListener {
            var intent = Intent(this,  Medicamentos::class.java)
            intent.putExtra("Type", "medicamentos")
            startActivity(intent)
        }


        botonAlerta.setOnClickListener {
            startActivity(intent2)
        }

        botonDieta.setOnClickListener {
            startActivity(intent3)
        }

        botonGlucosa.setOnClickListener {
            startActivity(intent4)
        }

        botonPlan.setOnClickListener {
            startActivity(intent5)
        }

        botonEjercicio.setOnClickListener {
            actualizarGrafica()
            startActivity(intent6)

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



    fun actualizarGrafica(){

        var pH: Float = (aguita.toFloat()*30)/8
        var pE = 0.0F

        if (movimiento){
            pE = 20.0F
        }
        var pD: Float = (comida.toFloat()*50)/5



        var porcentaje: Int = pD.toInt()+pH.toInt()+pE.toInt()

        Porciento.setText(""+porcentaje)




        lista.clear()
        lista.add(Porcentaje(aguita,movimiento,comida,"Sabado"))


        val fondo = CustomCircleDrawable(this,lista)
       graph.background = fondo
    }
    fun guardarFecha(){var jsonArray = JSONArray()
        var o : Int = 0
        for (i in listaFecha){
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("dia", i.dia)


            jsonArray.put(o,j)
            o++
        }
        jsonFileReinicio?.saveData(this, jsonArray.toString())
    }

    fun fetchingDataDia(){
        try {
            var json : String = jsonFileReinicio?.getData(this) ?:""
            if (json != ""){
                this.data = true
                var jsonArray : JSONArray = JSONArray(json)

                this.listaFecha = parseJsonDia(jsonArray)
                for (i in listaFecha){

                    fechaJSON = i.dia
                }
            } else {
                this.data = false
            }
        } catch (exception: JSONException){
            exception.printStackTrace()
        }
    }


    fun parseJsonDia(jsonArray: JSONArray): ArrayList<Reinicio>{
        var lista = ArrayList<Reinicio>()

        for (i in 0..jsonArray.length()){
            try {
                val dia = jsonArray.getJSONObject(i).getString("dia")
                var reinicio = Reinicio (dia)
                lista.add(reinicio)
            } catch (exception: JSONException){
                exception.printStackTrace()
            }
        }
        return lista
    }


}
