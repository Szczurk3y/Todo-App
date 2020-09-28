package com.example.innovateapprecruitmenttest.view.content

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.innovateapprecruitmenttest.R
import com.example.innovateapprecruitmenttest.model.RawTodo
import com.example.innovateapprecruitmenttest.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splashscreen.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: AppCompatActivity(R.layout.activity_splashscreen) {

    private val viewmodel: SplashViewModel by viewModel<SplashViewModel>() // Injecting view model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAnim()

        viewmodel.todosLiveData.observe(this, Observer { todos ->
            Handler().postDelayed({
                val intent = Intent(this, AllTodosActivity::class.java)
                intent.putParcelableArrayListExtra(RawTodo.TODO_KEY, ArrayList(todos))
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }, 1000)
        })

        viewmodel.getTodos()
    }

    private fun startAnim() {
        progressbar.show()
        val ivInnovateAnim = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val ivAppAnim = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        iv_innovate_text.startAnimation(ivInnovateAnim)
        iv_app_text.startAnimation(ivAppAnim)
    }
}