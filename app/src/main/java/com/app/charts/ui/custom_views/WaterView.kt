package com.app.charts.ui.custom_views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.app.charts.R
import com.app.charts.helpers.Utilities

class WaterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var wavePhase = 0f
    private var currentWaterLevel = 0f // Default to 0% full
    private val waveAmplitude = 15f
    private val cornerRadius: Float

    init {
        paint.color = ContextCompat.getColor(context, R.color.blue)
        paint.style = Paint.Style.FILL
        cornerRadius = Utilities.dpToPx(30f, context)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val clipPath = Path()
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)
        canvas.clipPath(clipPath)

        if (currentWaterLevel == 0f) {
            return
        }

        if (currentWaterLevel == 1f) {
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        } else {
            drawWaterWave(canvas)
        }
    }

    private fun drawWaterWave(canvas: Canvas) {
        val path = Path()

        // Calculate water height based on the current level
        val waterHeight = height * (1 - currentWaterLevel)

        // Start drawing from the left
        path.moveTo(0f, waterHeight)

        // Define sine wave properties
        val waveLength = width.toFloat() // A single sine wave spans the entire width

        // Create a smooth sine wave
        for (x in 0..width) {
            val y = (waveAmplitude * Math.sin((2 * Math.PI * x / waveLength) + wavePhase)).toFloat()
            path.lineTo(x.toFloat(), waterHeight + y)
        }

        // Complete the path by closing it to the bottom of the view
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()

        // Draw the water wave path
        canvas.drawPath(path, paint)

        // Update the wave phase for animation
        wavePhase += 0.03f // Adjust this for faster/slower wave movement
        if (wavePhase > 2 * Math.PI) {
            wavePhase = 0f
        }

        invalidate() // Force re-draw for animation effect
    }

    // This method updates the water level
    fun setWaterLevel(level: Float) {
        // Ensure the target level is within 0 and 1
        val targetLevel = level.coerceIn(0f, 1f)

        // Create the ValueAnimator to animate the water level
        val animator = ValueAnimator.ofFloat(currentWaterLevel, targetLevel)
        animator.duration = 1500 // Set duration based on the input parameter
        animator.addUpdateListener { animation ->
            // Update the water level as the animation progresses
            currentWaterLevel = animation.animatedValue as Float
            invalidate() // Redraw the view
        }

        // Start the animation
        animator.start()
    }

}
