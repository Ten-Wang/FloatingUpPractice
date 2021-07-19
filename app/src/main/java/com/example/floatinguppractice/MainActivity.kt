package com.example.floatinguppractice

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.PointF
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var original: PointF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.button).setOnClickListener {
            val pointP0 = PointF() //p0

            pointP0.x = it.x
            pointP0.y = it.y
            original = pointP0

            val pointP3 = PointF() //p3

            pointP3.x = pointP0.x
            pointP3.y = pointP0.y - 1000

            val pointP1 = PointF() //p1

            pointP1.x = pointP0.x - 500
            pointP1.y = pointP0.y - 200

            val pointP2 = PointF() //p2

            pointP2.x = pointP0.x + 500
            pointP2.y = pointP0.y - 500

            // 初始化一个贝塞尔计算器- - 传入
            val evaluator = BezierEvaluator(
                pointP1,
                pointP2
            )
            val animator = ValueAnimator.ofObject(
                evaluator, pointP0,
                pointP3
            )
            animator.addUpdateListener(BezierListener(it as ImageView, original))
            animator.setTarget(this)
            animator.duration = 1300
            animator.start()
        }
    }

    private class BezierListener(private val target: ImageView, val original: PointF) : AnimatorUpdateListener {
        override fun onAnimationUpdate(animation: ValueAnimator) {
            //更新值之后更新UI
            val pointF = animation.animatedValue as PointF
            target.x = pointF.x
            target.y = pointF.y
            // 这里顺便做一个alpha动画
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