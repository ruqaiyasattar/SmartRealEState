package ha.ecz.com.subscriberpanel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout

import com.daimajia.numberprogressbar.NumberProgressBar
import com.daimajia.numberprogressbar.OnProgressBarListener

import java.util.Timer
import java.util.TimerTask

import ha.ecz.com.agentportal.R
import ha.ecz.com.subscriberpanel.Utils.Utilities


/**
 * Created by eczpk0062 on 8/3/2015.
 */

class SplashScreenActivity : Activity(), OnProgressBarListener {

    private var timer: Timer? = null

    private var bnp: NumberProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        startAnimations()
        bnp = findViewById<View>(R.id.numberbar3) as NumberProgressBar
        bnp!!.setOnProgressBarListener(this)
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { bnp!!.incrementProgressBy(1) }
            }
        }, 600, 10)

    }

    private fun startAnimations() {
        var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        anim.reset()
        val l = findViewById<View>(R.id.layout_splash) as LinearLayout
        l.clearAnimation()
        l.startAnimation(anim)

        anim = AnimationUtils.loadAnimation(this, R.anim.translate)
        anim.reset()
        val iv = findViewById<View>(R.id.img_splash) as ImageView
        iv.clearAnimation()
        iv.startAnimation(anim)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
        }
    }

    override fun onProgressChange(current: Int, max: Int) {
        if (current == max) {
            timer!!.cancel()
            bnp!!.progress = 100
            val admindata = Utilities.getPreference(this, "adminObj", null)
            if (admindata != null) {
                startActivity(Intent(this@SplashScreenActivity, DashboardActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }

            finish()

        }
    }
}
