package com.pgwhite.gunlog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    private lateinit var gunViewModel: GunViewModel

    private lateinit var gun: Gun

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        gunViewModel = ViewModelProvider(this).get(GunViewModel::class.java)

        checkBoxRecoilSpring.setOnClickListener {
            if (checkBoxRecoilSpring.isChecked) {
                editTextRecoilRounds.visibility = View.VISIBLE
            }
            else {
                editTextRecoilRounds.visibility = View.INVISIBLE
            }
        }

        buttonSave.setOnClickListener {
            // text
            val mfrText = editTextManufacturer.text.toString()
            val modelText = editTextModel.text.toString()

            // ints
            var roundsInt = -1
            var recoilInt = -1
            if (editTextTotalRounds.text.isNotEmpty()) {
                roundsInt = editTextTotalRounds.text.toString().toInt()
            }
            if (checkBoxRecoilSpring.isChecked) {
                if (editTextRecoilRounds.text.isNotEmpty()) {
                    recoilInt = editTextRecoilRounds.text.toString().toInt()
                }
            }

            if (mfrText.isEmpty() || modelText.isEmpty() || roundsInt == -1 || ((checkBoxRecoilSpring.isChecked) && (recoilInt == -1))) {
                Toast.makeText(this, R.string.toast_missing_fields, Toast.LENGTH_SHORT).show()
            }

            else {
                gun = Gun(0, mfrText, modelText, roundsInt, checkBoxRecoilSpring.isChecked, recoilInt)
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