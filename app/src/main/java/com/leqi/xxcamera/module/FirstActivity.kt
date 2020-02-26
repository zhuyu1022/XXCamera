package com.leqi.xxcamera.module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leqi.xxcamera.R
import kotlinx.android.synthetic.main.activity_first.*
import org.jetbrains.anko.startActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        btn.setOnClickListener{
            startActivity<MainActivity>()
        }

    }
}
