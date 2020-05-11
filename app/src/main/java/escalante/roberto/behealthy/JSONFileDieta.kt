package escalante.roberto.behealthy

import android.content.Context
import android.util.Log
import java.io.IOException

class JSONFileDieta{

    val comidas = "data3.json"

    constructor(){



    }

    fun saveData(context: Context, json:String){

        try {

            context.openFileOutput(comidas, Context.MODE_PRIVATE).use {
                it.write((json.toByteArray()))
            }
        }catch (e: IOException){
            Log.e("Guardar","Error in Writting: " + e.localizedMessage)

        }

    }

    fun getData(context: Context): String{

        try {
            return context.openFileInput(comidas).bufferedReader().readLine()
        }
        catch (e: IOException){
            Log.e("OBTENER","Error in fetching data: " + e.localizedMessage)
            return ""
        }

    }


}