package escalante.roberto.behealthy.utilities

import android.content.Context
import android.util.Log
import java.io.IOException

class JSONFile{
    val porcentajes="data.json"
    constructor(){}

    fun saveData(context: Context, json:String){
        try{
            context.openFileOutput(porcentajes,Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
        }
        catch (e: IOException){
            Log.e("Guardar","Error in writing"+e.localizedMessage)
        }
    }

    fun getData(context: Context):String{
        try {
            return context.openFileInput(porcentajes).bufferedReader().readLine()
        }
        catch (e:IOException){
            Log.e("OBTENER","Error in fetching data: "+e.localizedMessage)
            return ""
        }
    }
}