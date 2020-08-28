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
// 1
//        if (savedInstanceState == null) {
//            // 2
//            supportFragmentManager
//                // 3
//                .beginTransaction()
//                // 4
//                .add(R.id.constraint_layout, ViewGunFragment.newInstance("Mfr", "Model"), "gunFragment")
//                // 5
//                .commit()
//        }
    }

    fun openGunEntry(view : View) {
        val position = recyclerView.getChildAdapterPosition(view)
        val mfrString = gunViewModel.allGuns.value?.get(position)?.mfr!!
        val modelString = gunViewModel.allGuns.value?.get(position)?.model!!

        supportFragmentManager
            .beginTransaction()
            .add(R.id.constraint_layout, ViewGunFragment.newInstance(mfrString, modelString),"gunFragment")
            .addToBackStack("gunFragment")
            .commit()

//        val intent = Intent(this@ViewActivity, ViewGunActivity::class.java)
//        intent.putExtra("gunPosExtra", position)
//        startActivity(intent)
    }
}