package escalante.roberto.behealthy

import android.content.Context
import android.util.Log
import java.io.IOException

class JSONFileMedicina{

    val medicinas = "data2.json"

    constructor(){



    }

    fun saveData(context: Context, json:String){

        try {

            context.openFileOutput(medicinas, Context.MODE_PRIVATE).use {
                it.write((json.toByteArray()))
            }
        }catch (e: IOException){
            Log.e("Guardar","Error in Writting: " + e.localizedMessage)

        }

    }

    fun getData(context: Context): String{

        try {
            return context.openFileInput(medicinas).bufferedReader().readLine()
        }
        catch (e: IOException){
            Log.e("OBTENER","Error in fetching data: " + e.localizedMessage)
            return ""
        }

    }


}