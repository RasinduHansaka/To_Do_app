package com.example.to_do_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.content.Intent

class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)



        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this@LogoActivity, MainActivity::class.java))
            finish()
        }, 4000)

    }
}