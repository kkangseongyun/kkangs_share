package com.example.app_13

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RuntimeShader
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi


private const val COLOR_SHADER_SRC =
    """uniform float2 iResolution;
   half4 main(float2 fragCoord) {
      float2 scaled = fragCoord/iResolution.xy;
      return half4(scaled, 0, 1);
   }"""
@RequiresApi(33)
class MyShaderView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val fixedColorShader = RuntimeShader(COLOR_SHADER_SRC)
        val paint = Paint()
        paint.shader = fixedColorShader
        fixedColorShader.setFloatUniform("iResolution", width.toFloat(), height.toFloat())
        canvas?.drawPaint(paint)
    }
}

