package com.example.iswara.ui.beginning.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iswara.R
import com.example.iswara.databinding.FragmentThirdOnBoardingBinding


class ThirdOnBoardingFragment : Fragment() {


    private var _thirdOnBoardingBinding: FragmentThirdOnBoardingBinding? = null
    private val thirdOnBoardingBinding get() = _thirdOnBoardingBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _thirdOnBoardingBinding = FragmentThirdOnBoardingBinding.inflate(inflater,container,false)
        val view = thirdOnBoardingBinding.root

        thirdOnBoardingBinding.tvNextThirdFragment.setOnClickListener {
            onBoardingPageDone()
            findNavController().navigate(R.id.action_viewPagerOnBoardingFragment_to_loginFragment2)

        }

        return view

    }


    private fun onBoardingPageDone() {
        val onBoardingSharedPref = requireActivity().getSharedPreferences("onBoardingScreen", Context.MODE_PRIVATE)
        val onBoardingEditor = onBoardingSharedPref.edit()
        onBoardingEditor.putBoolean("Finished", true)
        onBoardingEditor.apply()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _thirdOnBoardingBinding = null
    }

}