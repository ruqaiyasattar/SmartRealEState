package ha.ecz.com.subscriberpanel.Models

import kotlin.math.cos
import kotlin.math.pow

class MyBounceInterpolator : android.view.animation.Interpolator {

    private var mAmplitude: Double = 1.0
    private var mFrequency: Double = 10.0

    override fun getInterpolation(time: Float): Float {
        return (-1 * Math.E.pow(-time / mAmplitude) *
                cos(mFrequency * time) + 1).toFloat()
    }



}