package com.himochi.fif.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.himochi.fif.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setUpListeners()
        requestSmsPermission()
    }

    private fun setUpListeners(){
        tv_start.setOnClickListener{
            startActivity(Intent(this, SenderActivity::class.java))
        }
    }

    private fun initView() {
        val fadeInTitle = AlphaAnimation(0f,1f)
        fadeInTitle.interpolator = DecelerateInterpolator()
        fadeInTitle.duration = 2000
        tv_title.animation = fadeInTitle
        tv_start.animation = fadeInTitle

        val fadeInSubtitle = AlphaAnimation(0f,1f)
        fadeInSubtitle.interpolator = DecelerateInterpolator()
        fadeInSubtitle.duration = 4000
        tv_description.animation = fadeInSubtitle
    }

    private fun requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1)
        } else {
            startActivity(Intent(this, SenderActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Awesome! \ud83d\ude01 We aware sending SMS may cost you some money", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No SMS will be sent, but you still can see the result", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
