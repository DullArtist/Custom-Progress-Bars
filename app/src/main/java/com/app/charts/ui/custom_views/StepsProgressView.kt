package com.app.charts.ui.custom_views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.app.charts.R
import com.app.charts.helpers.Utilities

class StepsProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(30f,context)
        color = ContextCompat.getColor(context, R.color.brown)
        strokeCap = Paint.Cap.ROUND
    }

    private var backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(30f,context)
        color = ContextCompat.getColor(context, R.color.white)
        strokeCap = Paint.Cap.ROUND
    }

    var max = 10000
    private var currentProgress = 0

    private var rectF = RectF()
    var progress = 0
        set(value) {
            animateProgressChange(value)
        }


    private fun animateProgressChange(newProgress: Int) {
        val animator = ValueAnimator.ofInt(currentProgress, newProgress)
        animator.duration = 2000L
        animator.addUpdateListener { animation ->
            currentProgress = animation.animatedValue as Int
            invalidate()
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val padding = Utilities.dpToPx(30f,context)
        val right = width - padding
        val bottom = height - padding
        rectF.set(padding, padding, right, bottom)

        // Draw background arc (75% of the circle)
        canvas.drawArc(rectF, 135f, 270f, false, backgroundPaint)

        // Draw progress arc based on the animated current progress
        val sweepAngle = (270f * currentProgress) / max
        canvas.drawArc(rectF, 135f, sweepAngle, false, progressPaint)
    }

}
