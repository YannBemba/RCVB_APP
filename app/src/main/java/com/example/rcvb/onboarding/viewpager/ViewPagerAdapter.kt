package com.example.rcvb.onboarding.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    list: List<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fm, lifecycle) {

    private val fragmentList = list

    // Le nombre de fragments stockés dans la liste

    override fun getItemCount(): Int{
        return fragmentList.size
    }

    //La position du fragment

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }


}