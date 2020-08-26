package com.pgwhite.gunlog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewActivity : AppCompatActivity() {
    private lateinit var gunViewModel: GunViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = GunListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        gunViewModel = ViewModelProvider(this).get(GunViewModel::class.java)

        gunViewModel.allGuns.observe(this, Observer { guns ->
            // Update the cached copy of the words in the adapter.
            guns?.let { adapter.setGuns(it) }
        })
    }
}