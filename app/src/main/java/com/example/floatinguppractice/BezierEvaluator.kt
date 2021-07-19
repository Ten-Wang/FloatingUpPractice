package com.example.floatinguppractice

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.widget.ImageView

class BezierEvaluator(private val pointF1: PointF, private val pointF2: PointF) :
    TypeEvaluator<PointF> {
    override fun evaluate(
        time: Float, startValue: PointF,
        endValue: PointF
    ): PointF {
        val timeLeft = 1.0f - time
        val resultPoint = PointF() //result
        resultPoint.x = timeLeft * timeLeft * timeLeft * startValue.x +
                    3 * timeLeft * timeLeft * time * pointF1.x +
                    3 * timeLeft * time * time * pointF2.x +
                time * time * time * endValue.x
        resultPoint.y = timeLeft * timeLeft * timeLeft * startValue.y +
                3 * timeLeft * timeLeft * time * pointF1.y +
                3 * timeLeft * time * time * pointF2.y +
                time * time * time * endValue.y
        return resultPoint
    }

    public class BezierListener(private val target: ImageView, val original: PointF) :
        ValueAnimator.AnimatorUpdateListener {
        override fun onAnimationUpdate(animation: ValueAnimator) {
            // update UI
            val pointF = animation.animatedValue as PointF
            target.x = pointF.x
            target.y = pointF.y
            // animate
            target.alpha = 1 - animation.animatedFraction
            target.scaleX = 1 + animation.animatedFraction
            target.scaleY = 1 + animation.animatedFraction
            if (animation.animatedFraction == 1f) {
                target.x = original.x
                target.y = original.y
                target.alpha = 1.0f
                target.scaleX = 1F
                target.scaleY = 1F
            }
        }
    }
}