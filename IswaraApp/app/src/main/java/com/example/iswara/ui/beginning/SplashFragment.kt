package com.example.iswara.ui.beginning
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.iswara.R
import com.example.iswara.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _splashFragmentBinding : FragmentSplashBinding? = null
    private val splashFragmentBinding get() = _splashFragmentBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_splash, container, false)

        _splashFragmentBinding = FragmentSplashBinding.inflate(inflater,container,false)
        val view = splashFragmentBinding.root


        Handler().postDelayed({

            if(onBoardingCheckFinish()) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerOnBoardingFragment)
            }


        },400)



        return view

    }

    //check on boarding
    private fun onBoardingCheckFinish(): Boolean{
        val onBoardingSharedPref = requireActivity().getSharedPreferences("onBoardingScreen", Context.MODE_PRIVATE)
        return onBoardingSharedPref.getBoolean("Finished",false)
    }



    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _splashFragmentBinding = null
    }

}