package com.app.charts.ui.custom_views

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.app.charts.R
import com.app.charts.helpers.Utilities

class SleepProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(35f, context)  // Keep this same as progress bar
        color = ContextCompat.getColor(context, R.color.green)
        strokeCap = Paint.Cap.ROUND
    }

    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(35f, context)
        color = ContextCompat.getColor(context, R.color.f0_stroke)
        strokeCap = Paint.Cap.ROUND
    }

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL  // Filled circle
        color = ContextCompat.getColor(context, R.color.green)
    }

    private val circleStrokePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = Utilities.dpToPx(2f, context)  // Thin stroke for the circle outline
        color = ContextCompat.getColor(context, R.color.white)
    }

    private var progress = 0f
    private val maxProgress = 24 * 60f  // 24 hours in minutes (1440 minutes)

    @SuppressLint("DrawAllocation")
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
        val sweepAngle = (360f * progress / maxProgress)
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)

        // Draw start and end circles if progress is not full
        if (progress < maxProgress) {
            val startAngle = Math.toRadians((-90).toDouble())
            val endAngle = Math.toRadians((-90 + sweepAngle).toDouble())

            val startX = (width / 2 + (rectF.width() / 2) * Math.cos(startAngle)).toFloat()
            val startY = (height / 2 + (rectF.height() / 2) * Math.sin(startAngle)).toFloat()

            val endX = (width / 2 + (rectF.width() / 2) * Math.cos(endAngle)).toFloat()
            val endY = (height / 2 + (rectF.height() / 2) * Math.sin(endAngle)).toFloat()

            val circleRadius = progressPaint.strokeWidth / 2

            // Draw start circle
            canvas.drawCircle(startX, startY, circleRadius, circlePaint)
            canvas.drawCircle(startX, startY, circleRadius, circleStrokePaint)

            // Draw end circle
            canvas.drawCircle(endX, endY, circleRadius, circlePaint)
            canvas.drawCircle(endX, endY, circleRadius, circleStrokePaint)
        }
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
