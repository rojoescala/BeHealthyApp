package escalante.roberto.behealthy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_medicamentos.*
import kotlinx.android.synthetic.main.item_medicamento.view.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class Medicamentos : AppCompatActivity() {




    var jsonFile: JSONFileMedicina? = null
    var name = ""
    var day = ""
    var time = ""
    var data : Boolean = false
    var listaMedicamentos = ArrayList<medicinas>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamentos)

        var sCalendar = Calendar.getInstance()
        var dayLongName: String? = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        textoDia.setText(dayLongName)




        jsonFile = JSONFileMedicina()
        fetchingData()



        var adaptador = AdaptorMedicamentos(this,listaMedicamentos)
        list_medicamentos.adapter =adaptador




        var intent = Intent(this, Menu::class.java)

        var botonAtras: ImageView = findViewById(R.id.atras) as ImageView
        var botonAgregar: ImageView = findViewById(R.id.fab) as ImageView

        botonAtras.setOnClickListener{
            startActivity(intent)
        }

        botonAgregar.setOnClickListener{
            var intent2 = Intent(this, AgregarMedicamente::class.java)
            startActivityForResult(intent2,123)
        }
    }

    fun fetchingData(){
        try {
             var json: String = jsonFile?.getData(this) ?:""
            //var json : String = agregarme.getJSON()

            if (json != ""){
                this.data = true
                var jsonArray : JSONArray = JSONArray(json)

                this.listaMedicamentos = parseJson(jsonArray)

            } else {
                this.data = false
            }
        } catch (exception: JSONException){
            exception.printStackTrace()
        }
    }





    fun parseJson (jsonArray: JSONArray): ArrayList<medicinas> {
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


    private class AdaptorMedicamentos: BaseAdapter {
        var contexto: Context? = null
        var medicinas = ArrayList<medicinas>()


        constructor(contexto: Context, medicinas: ArrayList<medicinas>){
            this.contexto = contexto
            this.medicinas = medicinas
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


            
            var mie = medicinas[position]
            var inflator = LayoutInflater.from(contexto)
            var vista = inflator.inflate(R.layout.item_medicamento, null)

            vista.nombre_medicamento.setText(mie.nombre)
            vista.dias_medicamento.setText(mie.dias)
            vista.hora_medicamento.setText(mie.hora)


            return vista
        }

        override fun getItem(position: Int): Any {
            return medicinas[position]
        }

        override fun getItemId(position: Int): Long {
            return 1
        }

        override fun getCount(): Int {
            return medicinas.size
        }

    }

}
