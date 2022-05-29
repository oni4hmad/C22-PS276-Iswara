package com.example.iswara.ui.beginning.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.iswara.R
import com.example.iswara.databinding.FragmentFirstOnBoardingBinding


class FirstOnBoardingFragment : Fragment() {

    private var _firstOnBoardingFragment : FragmentFirstOnBoardingBinding?= null
    private val firstOnBoardingFragment get() = _firstOnBoardingFragment!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _firstOnBoardingFragment = FragmentFirstOnBoardingBinding.inflate(inflater,container,false)
        val view = firstOnBoardingFragment.root

        val viewPager = activity?.findViewById<ViewPager2>(R.id.vp_onboarding)

        firstOnBoardingFragment.tvNextFirstFragment.setOnClickListener {
            viewPager?.currentItem = 1
        }



        return view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _firstOnBoardingFragment = null
    }

}