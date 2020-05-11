package escalante.roberto.behealthy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_dieta.*

import kotlinx.android.synthetic.main.activity_medicamentos.textoDia

import kotlinx.android.synthetic.main.item_comidas.view.*

import org.json.JSONArray
import org.json.JSONException
import java.util.*

import android.widget.*
import androidx.core.view.get
import escalante.roberto.behealthy.utilies.JSONFile
import escalante.roberto.behealthy.utilies.Porcentaje
import org.json.JSONObject
import kotlin.collections.ArrayList


class Dieta : AppCompatActivity(){
    var jsonFile: JSONFileDieta? = null
    var data : Boolean = false
    var comida = 0
    var aguita = 0
    var movimiento = false
    var listaDieta = ArrayList<Comidas>()
    var jsonFilePorcentajae: JSONFile? = null
    var listaPorcentaje = ArrayList<Porcentaje>()
    var dataPorcentaje: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dieta)





        var intent = Intent(this, Menu::class.java)
        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView

        botonAtras.setOnClickListener{
            startActivity(intent)
        }

        var sCalendar = Calendar.getInstance()
        var dayLongName: String? = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        textoDia.setText(dayLongName)


        jsonFile = JSONFileDieta()
        fetchingData()



        jsonFilePorcentajae = JSONFile()
        fetchingDataPorcentaje()




        var adaptador = AdaptorMedicamentos(this,listaDieta)
        lista_Dieta.adapter =adaptador

        lista_Dieta.setOnItemClickListener { parent, view, position, id ->

            if (position == 0) {


                if (listaDieta[0].estado == false) {

                    lista_Dieta[0].palomita.setBackgroundResource(R.drawable.palomita)
                    var comidita = Comidas(listaDieta[0].comida, listaDieta[0].descripcion, true)
                    listaDieta.set(0, comidita)
                    guardarDieta()
                    comida++

                }


            }
            if (position == 1) {


                if (listaDieta[1].estado == false) {
                    lista_Dieta[1].palomita.setBackgroundResource(R.drawable.palomita)
                    var comidita = Comidas(listaDieta[1].comida, listaDieta[1].descripcion, true)
                    listaDieta.set(1, comidita)
                    guardarDieta()
                    comida++
                }


            }
            if (position == 2) {


                if (listaDieta[2].estado == false) {
                    lista_Dieta[2].palomita.setBackgroundResource(R.drawable.palomita)
                    var comidita = Comidas(listaDieta[2].comida, listaDieta[2].descripcion, true)
                    listaDieta.set(2, comidita)
                    guardarDieta()
                    comida++
                }


            }
            if (position == 3) {


                if (listaDieta[3].estado == false) {
                    lista_Dieta[3].palomita.setBackgroundResource(R.drawable.palomita)
                    var comidita = Comidas(listaDieta[3].comida, listaDieta[3].descripcion, true)
                    listaDieta.set(3, comidita)
                    guardarDieta()
                    comida++
                }


            }
            if (position == 4) {


                if (listaDieta[4].estado == false) {
                    lista_Dieta[4].palomita.setBackgroundResource(R.drawable.palomita)
                    var comidita = Comidas(listaDieta[4].comida, listaDieta[4].descripcion, true)
                    listaDieta.set(4, comidita)
                    guardarDieta()
                    comida++
                }


            }


            listaPorcentaje.add(Porcentaje(aguita, movimiento, comida, "Sabado"))
            guardarPorcentaje()
        }

    }

    fun fetchingData(){
        try {
            var json: String = jsonFile?.getData(this) ?:""
            if (json != ""){
                this.data = true
                var jsonArray : JSONArray = JSONArray(json)

                this.listaDieta = parseJson(jsonArray)

            } else {
                this.data = false
            }
        } catch (exception: JSONException){
            exception.printStackTrace()
        }
    }





    fun parseJson (jsonArray: JSONArray): ArrayList<Comidas> {
        var lista = ArrayList<Comidas>()
        for (i in 0..jsonArray.length()) {
            try {
                val nombre = jsonArray.getJSONObject(i).getString("comida")
                val descripcion = jsonArray.getJSONObject(i).getString("descripcion")
                val estado = jsonArray.getJSONObject(i).getBoolean("estado")
                var com = Comidas(nombre, descripcion, estado)
                lista.add(com)
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
        }
        return lista
    }


    private class AdaptorMedicamentos: BaseAdapter {
        var contexto: Context? = null
        var Comidas = ArrayList<Comidas>()


        constructor(contexto: Context, Comidas: ArrayList<Comidas>){
            this.contexto = contexto
            this.Comidas = Comidas
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var com = Comidas[position]
            var inflator = LayoutInflater.from(contexto)
            var vista = inflator.inflate(R.layout.item_comidas, null)

            vista.nombre_comida.setText(com.comida)
            vista.descripcion_comida.setText(com.descripcion)

            if (com.estado){
                vista.palomita.setBackgroundResource(R.drawable.palomita)
            }

            return vista
        }

        override fun getItem(position: Int): Any {
            return Comidas[position]
        }

        override fun getItemId(position: Int): Long {
            return 1
        }

        override fun getCount(): Int {
            return Comidas.size
        }
    }
    fun guardarDieta(){
        var jsonArray = JSONArray()
        var o : Int = 0
        for (i in listaDieta){
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("comida", i.comida)
            j.put("descripcion", i.descripcion)
            j.put("estado",i.estado)


            jsonArray.put(o,j)
            o++
        }
        jsonFile?.saveData(this, jsonArray.toString())
    }

















    fun fetchingDataPorcentaje(){
        try {
            var json : String = jsonFilePorcentajae?.getData(this) ?:""
            if (json != ""){
                this.data = true
                var jsonArray : JSONArray = JSONArray(json)

                this.listaPorcentaje = parseJsonPorcentaje(jsonArray)

                for (i in listaPorcentaje){

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


    fun parseJsonPorcentaje(jsonArray: JSONArray): ArrayList<Porcentaje>{
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
    fun guardarPorcentaje(){
        var jsonArray = JSONArray()
        var o : Int = 0
        for (i in listaPorcentaje){
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("agua", i.agua)
            j.put("ejercicio", i.ejericio)
            j.put("dieta",i.dieta)
            j.put("fecha",i.fecha)

            jsonArray.put(o,j)
        }
        jsonFilePorcentajae?.saveData(this, jsonArray.toString())
    }

    /*
    fun actualizarPalomitas(){
        if ()
    }

     */
}







