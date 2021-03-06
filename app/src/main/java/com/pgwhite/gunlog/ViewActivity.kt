package com.pgwhite.gunlog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewActivity : AppCompatActivity() {
    private lateinit var gunViewModel: GunViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = GunListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        gunViewModel = ViewModelProvider(this).get(GunViewModel::class.java)

        gunViewModel.allGuns.observe(this, Observer { guns ->
            // Update the cached copy of the guns in the adapter.
            guns?.let { adapter.setGuns(it) }
        })
    }

    fun openGunEntry(view : View) {
        val position = recyclerView.getChildAdapterPosition(view)

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in,
                                R.anim.fade_out, R.anim.slide_in, R.anim.slide_out)
            .add(R.id.constraintLayout, ViewGunFragment.newInstance(position),"gunFragment")
            .addToBackStack("gunFragment")
            .commit()
    }

    fun getViewModelInstance():GunViewModel = gunViewModel
}