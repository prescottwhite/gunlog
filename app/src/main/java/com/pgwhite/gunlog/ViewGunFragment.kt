package com.pgwhite.gunlog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_view_gun.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_POS = "param1"
private const val ARG_MFR = "param2"
private const val ARG_MODEL = "param3"
private const val ARG_TOTAL_ROUNDS = "param4"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewGunFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewGunFragment : Fragment() {
    private lateinit var gunViewModel: GunViewModel

    private var currentGun: Gun? = null

    private var pos: Int? = null
    private var mfr: String? = null
    private var model: String? = null
    private var totalRounds: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pos = it.getInt(ARG_POS)
            mfr = it.getString(ARG_MFR)
            model = it.getString(ARG_MODEL)
            totalRounds = it.getInt(ARG_TOTAL_ROUNDS)
        }

        gunViewModel = (activity as ViewActivity).getViewModelInstance()

        currentGun = gunViewModel.allGuns.value?.get(pos!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_gun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewFragMfr.text = this.mfr
        textViewFragModel.text = this.model
        textViewFragTotalRounds.text = this.totalRounds.toString()

        buttonCloseFragment.setOnClickListener {
            it.hideKeyboard()
            activity?.onBackPressed()
        }

        buttonUpdateRounds.setOnClickListener {
            try {
                var newRoundCount: Int = editTextNewRoundCount.text.toString().toInt()
                if (newRoundCount > 0) {
                    val updateGun = Gun(currentGun!!.id, currentGun!!.mfr, currentGun!!.model, currentGun!!.rounds_total + newRoundCount)
                    gunViewModel.update(updateGun)
                    currentGun = updateGun

                    textViewFragMfr.text = currentGun!!.mfr
                    textViewFragModel.text = currentGun!!.model
                    textViewFragTotalRounds.text = currentGun!!.rounds_total.toString()
                }
                editTextNewRoundCount.text = null
                it.hideKeyboard()
            } catch (e: Exception) {
                editTextNewRoundCount.text = null
            }
        }

        buttonDeleteGun.setOnClickListener {
            linearLayoutUpdateRounds.visibility = View.INVISIBLE
            buttonCloseFragment.visibility = View.GONE

            buttonDeleteGun.visibility = View.INVISIBLE
            buttonDeleteYes.visibility = View.VISIBLE
            buttonDeleteNo.visibility = View.VISIBLE

            textViewDeleteConfirmation.visibility = View.VISIBLE
        }

        buttonDeleteYes.setOnClickListener {
            gunViewModel.delete(currentGun!!)
            it.hideKeyboard()
            activity?.onBackPressed()
        }

        buttonDeleteNo.setOnClickListener {
            linearLayoutUpdateRounds.visibility = View.VISIBLE
            buttonCloseFragment.visibility = View.VISIBLE

            buttonDeleteGun.visibility = View.VISIBLE
            buttonDeleteYes.visibility = View.INVISIBLE
            buttonDeleteNo.visibility = View.INVISIBLE

            textViewDeleteConfirmation.visibility = View.GONE
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param mfr Parameter 1.
         * @param model Parameter 2.
         * @return A new instance of fragment ViewGunFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(pos: Int, mfr: String, model: String, totalRounds: Int) =
            ViewGunFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POS, pos)
                    putString(ARG_MFR, mfr)
                    putString(ARG_MODEL, model)
                    putInt(ARG_TOTAL_ROUNDS, totalRounds)
                }
            }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}