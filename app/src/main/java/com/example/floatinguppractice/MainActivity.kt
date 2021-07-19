package com.example.floatinguppractice

import android.animation.ValueAnimator
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.button).setOnClickListener {
            floatingUpAnimation(it)
        }
    }

    private fun floatingUpAnimation(it: View) {
        val pointP0 = PointF() //p0

        pointP0.x = it.x
        pointP0.y = it.y

        val pointP1 = PointF() //p1

        pointP1.x = pointP0.x - 500
        pointP1.y = pointP0.y - 200

        val pointP2 = PointF() //p2

        pointP2.x = pointP0.x + 500
        pointP2.y = pointP0.y - 500

        val pointP3 = PointF() //p3

        pointP3.x = pointP0.x
        pointP3.y = pointP0.y - 1000

        // init BezierEvaluator
        val evaluator = BezierEvaluator(
            pointP1,
            pointP2
        )
        val animator = ValueAnimator.ofObject(
            evaluator, pointP0,
            pointP3
        )
        animator.addUpdateListener(BezierEvaluator.BezierListener(it as ImageView, pointP0))
        animator.setTarget(this)
        animator.duration = 1300
        animator.start()
    }
}