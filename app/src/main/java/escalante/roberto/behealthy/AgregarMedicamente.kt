package escalante.roberto.behealthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import escalante.roberto.behealthy.utilies.JSONFile
import kotlinx.android.synthetic.main.activity_agregar_medicamente.*
import org.json.JSONArray
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
    fun getJSON(): String {
        var json3 : String = jsonFile?.getData(this) ?:""
        return json3
    }


}
