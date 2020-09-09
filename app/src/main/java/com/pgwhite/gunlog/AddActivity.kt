package com.pgwhite.gunlog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class AddActivity : AppCompatActivity() {

    private lateinit var editMfrView: EditText
    private lateinit var editModelView: EditText
    private lateinit var editTotalRoundsView: EditText

    private lateinit var gunViewModel: GunViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editMfrView = findViewById(R.id.editTextManufacturer)
        editModelView = findViewById(R.id.editTextModel)
        editTotalRoundsView = findViewById(R.id.editTextTotalRounds)

        gunViewModel = ViewModelProvider(this).get(GunViewModel::class.java)

        val button = findViewById<Button>(R.id.buttonSave)
        button.setOnClickListener {
            val gun = Gun(0, editMfrView.text.toString(), editModelView.text.toString(), editTotalRoundsView.text.toString().toInt())
            gunViewModel.insert(gun)
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}