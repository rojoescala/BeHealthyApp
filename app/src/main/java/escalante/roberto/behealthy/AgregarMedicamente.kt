package escalante.roberto.behealthy

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import escalante.roberto.behealthy.utilies.JSONFileMedicina
import escalante.roberto.behealthy.utilies.ReminderBroadcaster
import escalante.roberto.behealthy.utilies.medicinas
import kotlinx.android.synthetic.main.activity_agregar_medicamente.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AgregarMedicamente : AppCompatActivity() {

    var jsonFile: JSONFileMedicina? = null
    var data : Boolean = false
    var lista = ArrayList<medicinas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_medicamente)

        createNotificationCannel()


        jsonFile = JSONFileMedicina()
        fetchingData()
        //var intent = Intent(this, Medicamentos::class.java)




        agregarbutton.setOnClickListener{
            var estado = false

                var nombreMedicamento= nombre.text.toString()

            if (nombreMedicamento == ""){
                nombre.setError("Ingrese un nombre")
            }
            else{
                estado = true
            }

                var horaMedicamento = hora.text.toString()
                var dias: String = ""

                if (checkLunes.isChecked)
                    dias = "Lunes, "
                if(checkMartes.isChecked)
                    dias = dias+"Martes, "
                if (checkMiercoles.isChecked)
                    dias = dias+"Miercoles, "
                if (checkJueves.isChecked)
                    dias = dias+"Jueves, "
                if (checkViernes.isChecked)
                    dias = dias+"Viernes, "
                if (checkSabado.isChecked)
                    dias = dias+"Sabado, "
                if (checkDomingo.isChecked)
                    dias = dias+"Domingo"


                var medi = medicinas(
                    nombreMedicamento,
                    dias,
                    horaMedicamento
                )


            var intent = Intent(this,  Medicamentos::class.java)
/*


            if (lista.isEmpty()){
                intent=Intent(this, ReminderBroadcaster::class.java)
                var pendingIntent:PendingIntent= PendingIntent.getBroadcast(this,0,intent,0)

                var alarmManager:AlarmManager= getSystemService(Context.ALARM_SERVICE) as AlarmManager
                var timeAtButtonClick= SystemClock.elapsedRealtime()
                var hour=AlarmManager.INTERVAL_HALF_DAY

                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,timeAtButtonClick+hour,hour,pendingIntent)
                startActivity(intent)
            }
            else
            {
                startActivity(intent)
            }
            */
            startActivity(intent)
                lista.add(medi)
                guardar()



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

    private fun createNotificationCannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            var name:CharSequence="TomaMedicinaChannel"
            var description:String="Channel toma medicina"

            var importante:Int= NotificationManager.IMPORTANCE_DEFAULT
            var channel:NotificationChannel= NotificationChannel("tomaMed",name,importante)
            channel.setDescription(description)

            var noManagger:NotificationManager=getSystemService(NotificationManager::class.java)
            noManagger.createNotificationChannel(channel)
        }

    }



}
