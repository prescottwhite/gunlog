package com.pgwhite.gunlog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addNewGun(view: View) {
        val intent = Intent(this, AddActivity::class.java)

        startActivity(intent)
    }

    fun viewGuns(view: View) {
        val intent = Intent(this, ViewActivity::class.java)

        startActivity(intent)
    }
}