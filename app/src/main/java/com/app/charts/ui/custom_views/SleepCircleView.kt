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

class SleepCircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(35f, context)
        color = ContextCompat.getColor(context, R.color.green)
        strokeCap = Paint.Cap.ROUND
    }

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(35f, context)
        color = ContextCompat.getColor(context, R.color.white)
        strokeCap = Paint.Cap.ROUND
    }

    private var progress = 0f
    private val maxProgress = 24 * 60f  // 24 hours in minutes (1440 minutes)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val padding = Utilities.dpToPx(15f, context)
        val left = padding + (progressPaint.strokeWidth / 2)
        val top = padding + (progressPaint.strokeWidth / 2)
        val right = width - padding - (progressPaint.strokeWidth / 2)
        val bottom = height - padding - (progressPaint.strokeWidth / 2)

        val rectF = RectF(left, top, right, bottom)

        // Draw background circle
        canvas.drawArc(rectF, 0f, 360f, false, backgroundPaint)

        // Draw progress arc based on sleep hours
        // The progress is now relative to 1440 minutes (24 hours)
        val sweepAngle = (360f * progress / maxProgress)
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)
    }

    fun setSleepProgress(sleepDurationInMinutes: Float) {
        // Animate the progress change
        val animator = ValueAnimator.ofFloat(progress, sleepDurationInMinutes)
        animator.duration = 500  // Animation duration in milliseconds
        animator.addUpdateListener { animation ->
            progress = animation.animatedValue as Float
            invalidate()  // Redraw the view
        }
        animator.start()
    }

}

