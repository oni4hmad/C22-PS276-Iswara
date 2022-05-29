package com.example.iswara.ui.beginning.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iswara.R
import com.example.iswara.databinding.FragmentViewPagerOnBoardingBinding


class ViewPagerOnBoardingFragment : Fragment() {

    private var _viewPagerOnBoardingBinding: FragmentViewPagerOnBoardingBinding? = null
    private val viewPagerOnBoardingBinding get() = _viewPagerOnBoardingBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _viewPagerOnBoardingBinding = FragmentViewPagerOnBoardingBinding.inflate(inflater,container,false)
        val view = viewPagerOnBoardingBinding.root

        val fragmentList = arrayListOf<Fragment>(
            FirstOnBoardingFragment(),
            SecondOnBoardingFragment(),
            ThirdOnBoardingFragment()
        )

        val adapterOnBoarding = ViewPagerOnBoardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        viewPagerOnBoardingBinding.vpOnboarding.adapter = adapterOnBoarding

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _viewPagerOnBoardingBinding = null
    }


}