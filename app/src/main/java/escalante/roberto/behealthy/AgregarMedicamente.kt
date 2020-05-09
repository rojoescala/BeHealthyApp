package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import escalante.roberto.behealthy.utilies.JSONFile
import kotlinx.android.synthetic.main.activity_agregar_medicamente.*
import kotlinx.android.synthetic.main.activity_menu.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import escalante.roberto.behealthy.Medicamentos as Medicamentos

class AgregarMedicamente : AppCompatActivity() {

    var jsonFile: JSONFileMedicina? = null
    var data : Boolean = false
    var lista = ArrayList<medicinas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_medicamente)




        jsonFile = JSONFileMedicina()
        fetchingData()
        var intent = Intent(this, Medicamentos::class.java)




        agregarbutton.setOnClickListener{
                var nombreMedicamento= nombre.text.toString()
                var horaMedicamento = hora.text.toString()
                var dias: String = ""

                if (checkLunes.isChecked)
                    dias = "Lunes, "
                if(checkMartes.isChecked)
                    dias = dias+"Martes"
                if (checkMiercoles.isChecked)
                    dias = dias+"Miercoles"
                if (checkJueves.isChecked)
                    dias = dias+"Jueves"
                if (checkViernes.isChecked)
                    dias = dias+"Viernes"
                if (checkSabado.isChecked)
                    dias = dias+"Sabado"
                if (checkDomingo.isChecked)
                    dias = dias+"Domingo,"


                var medi = medicinas (nombreMedicamento, dias, horaMedicamento)

                lista.add(medi)




                guardar()
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

            } else {
                this.data = false
            }
        } catch (exception: JSONException){
            exception.printStackTrace()
        }
    }
    fun parseJson(jsonArray: JSONArray): java.util.ArrayList<medicinas> {
        var lista = java.util.ArrayList<medicinas>()

        for (i in 0..jsonArray.length()){
            try {
                val nombre = jsonArray.getJSONObject(i).getString("nombre")
                val dias = jsonArray.getJSONObject(i).getString("dias")
                val hora = jsonArray.getJSONObject(i).getString("hora")
                var med = medicinas(nombre, dias, hora)
                lista.add(med)
            } catch (exception: JSONException){
                exception.printStackTrace()
            }
        }
        return lista
    }


    fun guardar(){


        var jsonArray = JSONArray()
        var o : Int = 0
        for(i in lista){

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
