package com.pgwhite.gunlog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
            var roundsInt = -1
            val mfrText = editMfrView.text.toString()
            val modelText = editModelView.text.toString()
            if (editTotalRoundsView.text.isNotEmpty()) {
                roundsInt = editTotalRoundsView.text.toString().toInt()
            }

            if (mfrText.isEmpty() || modelText.isEmpty() || roundsInt == -1) {
                Toast.makeText(this, R.string.toast_missing_fields, Toast.LENGTH_SHORT).show()
            }

            else {
                val gun = Gun(0, mfrText, modelText, roundsInt)
                gunViewModel.insert(gun)
                Toast.makeText(this, R.string.toast_added_gun, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}