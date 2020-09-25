package com.example.innovateapprecruitmenttest.view.content

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.innovateapprecruitmenttest.R
import kotlinx.android.synthetic.main.activity_splashscreen.*
import kotlinx.android.synthetic.main.layout_progressbar.*

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        setContentView(R.layout.activity_splashscreen)
        startAnim()
    }

    private fun startAnim() {
        progressbar.show()
        val ivInnovateAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val ivAppAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        iv_innovate_text.startAnimation(ivInnovateAnim)
        iv_app_text.startAnimation(ivAppAnim)
    }

    private fun makeFullScreen() {
        // Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Make Fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Hide the toolbar
        supportActionBar?.hide()
    }
}