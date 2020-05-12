package escalante.roberto.behealthy

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import escalante.roberto.behealthy.utilies.Glucosa
import escalante.roberto.behealthy.utilies.JSONFileGlucosa
import kotlinx.android.synthetic.main.activity_medicamentos.*
import kotlinx.android.synthetic.main.activity_pantalla_glucosa.*
import kotlinx.android.synthetic.main.activity_pantalla_glucosa.textoDia
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_pantalla_glucosa.view.*
import kotlinx.android.synthetic.main.item_glucosa.view.*
import java.text.SimpleDateFormat
import kotlin.math.roundToInt


class pantallaGlucosa : AppCompatActivity() {
    var jsonFile: JSONFileGlucosa? = null
    var data: Boolean = false
    var lista = ArrayList<Glucosa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_glucosa)




        var sCalendar = Calendar.getInstance()
        var dayLongName: String? = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        textoDia.setText(dayLongName)

        jsonFile = JSONFileGlucosa()
        fetchingData()
        var intent = Intent(this, Menu::class.java)

        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView

        botonAtras.setOnClickListener{
            startActivity(intent)
        }
        btn_guardarGlucosa.setOnClickListener{

            if (glucosa_text.getText().toString() == ""){
                Toast.makeText(this,"Ingrese un valor",Toast.LENGTH_LONG).show()
            }
            else{
                val anotherCurDate = Date()
                val formatter = SimpleDateFormat("dd/mm/yyyy hh:mm a")
                var fecha = formatter.format(anotherCurDate)
                var nivel = glucosa_text.text.toString().toDouble()
                var glucosita = Glucosa(fecha,nivel)
                lista.add(glucosita)
                guardar()
                glucosa_text.setText("")
            }


        }
        var adaptador = AdaptorGlucosa(this,lista)
        lista_Glocusa.adapter =adaptador



    }
    fun fetchingData(){
        try {
            var json : String = jsonFile?.getData(this) ?:""
            if (json != ""){
                this.data = true
                var jsonArray : JSONArray = JSONArray(json)

                this.lista = parseJson(jsonArray)

            } else {
                this.data = false
            }
        } catch (exception: JSONException){
            exception.printStackTrace()
        }
    }


    fun parseJson(jsonArray: JSONArray): ArrayList<Glucosa>{
        var lista = ArrayList<Glucosa>()

        for (i in 0..jsonArray.length()){
            try {
                val fecha = jsonArray.getJSONObject(i).getString("fecha")
                val glucosa = jsonArray.getJSONObject(i).getDouble("glucosa")


                var gluco = Glucosa (fecha, glucosa)
                lista.add(gluco)
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

            var j: JSONObject = JSONObject()
            j.put("fecha", i.fecha)
            j.put("glucosa", i.glucosa)

            jsonArray.put(o,j)
            o++
        }
        jsonFile?.saveData(this, jsonArray.toString())
        Toast.makeText(this,"Datos guardados", Toast.LENGTH_SHORT).show()
    }

    private class AdaptorGlucosa: BaseAdapter {

        var contexto: Context? = null
        var Glucosas = ArrayList<Glucosa>()


        constructor(contexto: Context, Glucosas: ArrayList<Glucosa>){
            this.contexto = contexto
            this.Glucosas = Glucosas
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var glu = Glucosas[position]
            var inflator = LayoutInflater.from(contexto)
            var vista = inflator.inflate(R.layout.item_glucosa, null)

            vista.fecha_glucosa.setText(glu.fecha)
            vista.nivelglucosa.setText(glu.glucosa.toString())


            var porcentaje = (glu.glucosa.toInt()*100)/300
            vista.progressglucosa.setSecondaryProgress(porcentaje)




            return vista
        }

        override fun getItem(position: Int): Any {
            return Glucosas[position]
        }

        override fun getItemId(position: Int): Long {
            return 1
        }

        override fun getCount(): Int {
            return Glucosas.size
        }
    }
}
