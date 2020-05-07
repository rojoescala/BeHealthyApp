package escalante.roberto.behealthy.utilies

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import escalante.roberto.behealthy.R
import java.util.ArrayList

class CustomCircleDrawable: Drawable {
    var coordernadas: RectF? = null
    var anguloBarrido: Float = 0.0F
    var anguloInicio: Float = 0.0F
    var grosorFondo: Int = 0
    var grosorMetrica: Int = 0
    var context: Context? = null
    var porcentajes = ArrayList<Porcentaje>()

    override fun draw(p0: Canvas) {
        val fondo: Paint = Paint();
        fondo.style = Paint.Style.STROKE
        fondo.strokeWidth = (this.grosorFondo).toFloat()
        fondo.isAntiAlias = true
        fondo.strokeCap = Paint.Cap.ROUND
        fondo.color = context?.resources?.getColor(R.color.blanco) ?: R.color.blanco
        val ancho: Float = (p0.width -25).toFloat()
        val alto : Float = (p0.height -25).toFloat()

        coordernadas = RectF(25.0F,25.0F,ancho,alto)

        p0.drawArc(coordernadas!!, 0.0F, 360.0F, false, fondo)
        if (porcentajes.size != 0){
            for(e in porcentajes){
                val degree: Float = (e.porcentaje*360)/100
                this.anguloBarrido = degree

                var seccion: Paint = Paint()
                seccion.style = Paint.Style.STROKE
                seccion.isAntiAlias = true
                seccion.strokeWidth = (this.grosorMetrica).toFloat()
                seccion.strokeCap = Paint.Cap.SQUARE
                seccion.color = ContextCompat.getColor(this.context!!, e.color)

                p0.drawArc(coordernadas!!, this.anguloInicio, this.anguloBarrido,false,seccion)
                this.anguloInicio += this.anguloBarrido
            }
        }
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    constructor(context: Context, porcentajes: ArrayList<Porcentaje>){
        this.context = context
        grosorMetrica = context.resources.getDimensionPixelSize(R.dimen.graphBackground)
        grosorFondo = context.resources.getDimensionPixelSize(R.dimen.grapwith)
        this.porcentajes = porcentajes
    }

}