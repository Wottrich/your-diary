package wottrich.github.io.yourdiary.extensions

import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.animation.LinearInterpolator
import android.support.v4.view.ViewCompat.animate
import android.R.attr.translationX
import android.R.attr.translationY
import android.view.View
import android.view.ViewPropertyAnimator


abstract class AnimationMin : Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) = Unit
    abstract override fun onAnimationEnd(animation: Animation?)
    override fun onAnimationStart(animation: Animation?) = Unit
}

fun defaultAnimation(dismiss: Boolean): Animation {
    val anim = if (!dismiss) toTranslateBottom() else toTranslateTop()
    anim.duration = 300
    anim.repeatCount = 0
    return anim
}

fun toTranslateBottomToTop() = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 1f,
        Animation.RELATIVE_TO_SELF, 0f)

fun toTranslateTopToBottom() = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 1f)

fun toTranslateTop() = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, -1f,
        Animation.RELATIVE_TO_SELF, 0f)

fun toTranslateBottom() = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, -1f)