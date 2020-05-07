package escalante.roberto.behealthy.utilies

import android.content.Context
import android.util.Log
import java.io.IOException

class JSONFile{
    val PORCENTAJE = "data.json"

    constructor(){

    }

    fun saveData(context: Context, json: String){
        try{
            context.openFileOutput(PORCENTAJE, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
        } catch (e: IOException){
            Log.e("GUARDAR","Error in writing: " + e.localizedMessage)
        }
    }

    fun getData(context: Context):String{
        try {
            return context.openFileInput(PORCENTAJE).bufferedReader().readLine()
        } catch (e: IOException){
            Log.e("OBTENER","Error in fetching data: " + e.localizedMessage)
            return ""
        }
    }
}