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

class WeightProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(30f, context)
        color = ContextCompat.getColor(context, R.color.blue) // Adjust the color as needed
        strokeCap = Paint.Cap.ROUND
    }

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(30f, context)
        color = ContextCompat.getColor(context, R.color.eb_stroke) // Adjust the background color as needed
        strokeCap = Paint.Cap.ROUND
    }

    private var progress = 0f
    private var maxProgress = 100f // Maximum progress (e.g., 100%)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val padding = Utilities.dpToPx(12f, context)
        val left = padding + (progressPaint.strokeWidth / 2)
        val top = padding + (progressPaint.strokeWidth / 2)
        val right = width - padding - (progressPaint.strokeWidth / 2)
        val bottom = height - padding - (progressPaint.strokeWidth / 2)

        val rectF = RectF(left, top, right, bottom)

        // Draw background half-circle
        canvas.drawArc(rectF, 180f, 180f, false, backgroundPaint)

        // Draw progress arc for weight
        canvas.drawArc(rectF, 180f, (180f * progress / maxProgress), false, progressPaint)
    }

    fun setWeightProgress(progressValue: Float) {
        // Animate the progress change
        val animator = ValueAnimator.ofFloat(progress, progressValue)
        animator.duration = 1000  // Animation duration in milliseconds
        animator.addUpdateListener { animation ->
            progress = animation.animatedValue as Float
            invalidate()  // Redraw the view
        }
        animator.start()
    }

}
