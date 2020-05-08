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
import kotlinx.android.synthetic.main.activity_ejercicio.*
import kotlinx.android.synthetic.main.activity_menu.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList


class Menu : AppCompatActivity() {
    var jsonFile: JSONFile? = null
    var dieta = 0.0F
    var hidratacion = 0.0F
    var ejercicio = 0.0F
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
        //var botonTerminado: Button = findViewById(R.id.buttonTerminado) as Button

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
            ejercicio = 1.8F
            actualizarGrafica()
            startActivity(intent6)

        }
        /*
        estado = intent.getBooleanExtra("estado",false)
        if (estado == true){
            ejercicio = 1.8F
            actualizarGrafica()
            guardar()
        }

         */



        /*botonTerminado.setOnClickListener{
            ejercicio = 1.8F
            actualizarGrafica()
        }

         */



    }

    fun fetchingData(){
        try {
            var json : String = jsonFile?.getData(this) ?:""
            if (json != ""){
                this.data = true
                var jsonArray : JSONArray = JSONArray(json)

                this.lista = parseJson(jsonArray)

                for (i in lista){
                    when(i.nombre){
                        "Dieta" -> dieta = i.total
                        "Hidratacion" -> hidratacion = i.total
                        "Ejercicio" -> ejercicio = i.total
                    }
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
                val nombre = jsonArray.getJSONObject(i).getString("nombre")
                val porcentaje = jsonArray.getJSONObject(i).getDouble("porcentaje").toFloat()
                val color = jsonArray.getJSONObject(i).getInt("color")
                val total = jsonArray.getJSONObject(i).getDouble("total").toFloat()
                var porce = Porcentaje (nombre, porcentaje, color, total)
                lista.add(porce)
            } catch (exception: JSONException){
                exception.printStackTrace()
            }
        }
        return lista
    }



    fun actualizarGrafica(){
        val total = 9

        var pD: Float = (dieta * 100 / total).toFloat()
        var pH: Float = (hidratacion * 100 / total).toFloat()
        var pE: Float = (ejercicio * 100 / total).toFloat()

        var porcentaje: Int = pD.toInt()+pH.toInt()+pE.toInt()

        Porciento.setText(""+porcentaje)




        lista.clear()
        lista.add(Porcentaje("Dieta", pD, R.color.fondoAzul, dieta))
        lista.add(Porcentaje("Hidratacion", pH, R.color.fondoAzul, hidratacion))
        lista.add(Porcentaje("Ejercicio", pE, R.color.fondoAzul, ejercicio))

        val fondo = CustomCircleDrawable(this,lista)
       graph.background = fondo
    }



    fun guardar(){
        var jsonArray = JSONArray()
        var o : Int = 0
        for (i in lista){
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("nombre", i.nombre)
            j.put("porcentaje", i.porcentaje)
            j.put("color", i.color)
            j.put("total",i.total)

            jsonArray.put(o,j)
            o++
        }
        jsonFile?.saveData(this, jsonArray.toString())
        Toast.makeText(this,"Datos guardados", Toast.LENGTH_SHORT).show()
    }


}
