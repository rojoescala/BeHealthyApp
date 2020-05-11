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
import escalante.roberto.behealthy.utilies.CustomCircleDrawable
import escalante.roberto.behealthy.utilies.JSONFile
import escalante.roberto.behealthy.utilies.Porcentaje
import kotlinx.android.synthetic.main.activity_agregar_medicamente.*
import kotlinx.android.synthetic.main.activity_ejercicio.*
import kotlinx.android.synthetic.main.activity_menu.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList


class Menu : AppCompatActivity() {
    var jsonFile: JSONFile? = null
    var comida = 0
    var aguita = 0
    var movimiento = false
    var data: Boolean = false
    var lista = ArrayList<Porcentaje>()
    var porcentaje: Porcentaje? =  null
    var estado: Boolean = false
    // var s: String = getIntent().getStringExtra()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)



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


        jsonFile = JSONFile()

        fetchingData()
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





}
