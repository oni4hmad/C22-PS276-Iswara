package com.example.iswara.ui.beginning.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.iswara.R
import com.example.iswara.databinding.FragmentSecondOnBoardingBinding


class SecondOnBoardingFragment : Fragment() {

    private var _secondOnBoardingBinding: FragmentSecondOnBoardingBinding?= null
    private val secondOnBoardingBinding get() = _secondOnBoardingBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _secondOnBoardingBinding = FragmentSecondOnBoardingBinding.inflate(inflater,container,false)
        val view = secondOnBoardingBinding.root


        val viewPager = activity?.findViewById<ViewPager2>(R.id.vp_onboarding)

        secondOnBoardingBinding.tvNextSecondFragment.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _secondOnBoardingBinding = null
    }

}