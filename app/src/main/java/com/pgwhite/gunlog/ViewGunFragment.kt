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

/**
 * A simple [Fragment] subclass.
 * Use the [ViewGunFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewGunFragment : Fragment() {
    private lateinit var gunViewModel: GunViewModel

    private var currentGun: Gun? = null

    private var pos: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pos = it.getInt(ARG_POS)
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

        textViewFragMfr.text = currentGun!!.mfr
        textViewFragModel.text = currentGun!!.model
        textViewFragTotalRounds.text = currentGun!!.rounds_total.toString()

        if (currentGun!!.recoil_spring_bool) {
            showViews(listOf(linearLayoutRecoilSpring, linearLayoutResetRecoil))
            textViewFragRecoilRounds.text = currentGun!!.recoil_spring_rounds.toString()
        }

        buttonCloseFragment.setOnClickListener {
            it.hideKeyboard()
            activity?.onBackPressed()
        }

        buttonUpdateRounds.setOnClickListener {
            try {
                val newRoundCount: Int = editTextNewRoundCount.text.toString().toInt()
                var newRecoilCount: Int = 0

                if (newRoundCount > 0) {
                    // check if gun has recoil spring
                    if (currentGun!!.recoil_spring_bool) {
                        newRecoilCount = currentGun!!.recoil_spring_rounds + newRoundCount
                    }

                    val updateGun = Gun(currentGun!!.id, currentGun!!.mfr, currentGun!!.model, currentGun!!.rounds_total + newRoundCount, currentGun!!.recoil_spring_bool, newRecoilCount)
                    gunViewModel.update(updateGun)
                    currentGun = updateGun

                    textViewFragMfr.text = currentGun!!.mfr
                    textViewFragModel.text = currentGun!!.model
                    textViewFragTotalRounds.text = currentGun!!.rounds_total.toString()
                    textViewFragRecoilRounds.text = currentGun!!.recoil_spring_rounds.toString()
                }
                editTextNewRoundCount.text = null
                it.hideKeyboard()
            } catch (e: Exception) {
                editTextNewRoundCount.text = null
            }
        }

        // resetting recoil spring
        buttonResetRecoil.setOnClickListener {
            showViews(listOf(buttonResetRecoilYes, buttonResetRecoilNo))
            hideViews(listOf(buttonResetRecoil, linearLayoutUpdateRounds, linearLayoutDelete, buttonCloseFragment))
        }

        buttonResetRecoilYes.setOnClickListener {
            val updateGun = Gun(currentGun!!.id, currentGun!!.mfr, currentGun!!.model, currentGun!!.rounds_total, currentGun!!.recoil_spring_bool, 0)
            gunViewModel.update(updateGun)
            currentGun = updateGun

            textViewFragRecoilRounds.text = currentGun!!.recoil_spring_rounds.toString()

            showViews(listOf(buttonResetRecoil, linearLayoutUpdateRounds, linearLayoutDelete, buttonCloseFragment))
            hideViews(listOf(buttonResetRecoilYes, buttonResetRecoilNo))
        }

        buttonResetRecoilNo.setOnClickListener {
            showViews(listOf(buttonResetRecoil, linearLayoutUpdateRounds, linearLayoutDelete, buttonCloseFragment))
            hideViews(listOf(buttonResetRecoilYes, buttonResetRecoilNo))
        }

        // deleting a gun
        buttonDeleteGun.setOnClickListener {
            if (currentGun!!.recoil_spring_bool) {
                goneViews(listOf(linearLayoutResetRecoil))
            }
            showViews(listOf(buttonDeleteYes, buttonDeleteNo, textViewDeleteConfirmation))
            hideViews(listOf(linearLayoutUpdateRounds, buttonDeleteGun))
            goneViews(listOf(buttonCloseFragment))
        }

        buttonDeleteYes.setOnClickListener {
            gunViewModel.delete(currentGun!!)
            it.hideKeyboard()
            activity?.onBackPressed()
        }

        buttonDeleteNo.setOnClickListener {
            showViews(listOf(linearLayoutUpdateRounds, buttonDeleteGun, buttonCloseFragment))
            hideViews(listOf(buttonDeleteYes, buttonDeleteNo))
            if (currentGun!!.recoil_spring_bool) {
                showViews(listOf(linearLayoutResetRecoil))
            }

            goneViews(listOf(textViewDeleteConfirmation))
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
        fun newInstance(pos: Int) =
            ViewGunFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POS, pos)
                }
            }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun showViews(views: List<View>) {
        for (i in views) {
            i.visibility = View.VISIBLE
        }
    }

    fun hideViews(views: List<View>) {
        for (i in views) {
            i.visibility = View.INVISIBLE
        }
    }

    fun goneViews(views: List<View>) {
        for (i in views) {
            i.visibility = View.GONE
        }
    }
}