package com.example.jasstaxi.helper

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.jasstaxi.utils.MyButtonClickListener
import android.graphics.Bitmap.createBitmap as createBitmap1

class MyButton(
    private val context: Context,
    private val txt: String,
    private val txtSize: Int,
    private val imageResId: Int,
    private val color: Int,
    private val listener: MyButtonClickListener

) {
    private var position: Int = 0
    private var clickRegion: RectF? = null
    private var resource: Resources

    init {
        resource = context.resources
    }

    fun onClick(x: Float, y: Float): Boolean {
        if (clickRegion != null && clickRegion!!.contains(x, y)) {
            listener.onClick(position)
            return true
        }
        return false
    }

    fun onDraw(c: Canvas, rectF: RectF, position: Int) {
        val p = Paint()
        p.color = color
        c.drawRect(rectF, p)
        p.color = Color.WHITE
        p.textSize = txtSize.toFloat()
        val r = Rect()
        val cHeight = rectF.height()
        val cWidth = rectF.width()
        p.textAlign = Paint.Align.LEFT
        p.getTextBounds(
            txt, 0, txt.length, r
        )
        var x = 0f
        var y = 0f
        if (imageResId == 0) {
            x = cWidth / 2f - r.width() / 2f - r.left.toFloat()
            y = cHeight / 2f + r.height() / 2f - r.bottom.toFloat()
            c.drawText(txt, rectF.left + x, rectF.top + y, p)

        } else {
            val d = ContextCompat.getDrawable(context, imageResId)
           // val bitmap = drawableToBitmap(d)
          //  c.drawBitmap(bitmap, (rectF.left + rectF.right) / 2, (rectF.top + rectF.bottom) / 2, p)
        }
    }

//  private fun drawableToBitmap(d: Drawable?): Bitmap {
////        //if (d is BitmapDrawable) return d.bitmap
////
////
////        //val bitmap = createBitmap1()
////
//  }

}