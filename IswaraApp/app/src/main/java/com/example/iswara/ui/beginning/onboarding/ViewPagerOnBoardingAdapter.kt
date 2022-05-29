package com.example.iswara.ui.beginning.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerOnBoardingAdapter(
    listFragmentOnBoarding : ArrayList<Fragment> ,
    fm: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fm,lifecycle) {

    val fragmentList = listFragmentOnBoarding

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}