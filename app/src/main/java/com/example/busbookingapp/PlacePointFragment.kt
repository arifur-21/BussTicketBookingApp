package com.example.busbookingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.busbookingapp.databinding.FragmentPlacePointBinding


class PlacePointFragment : Fragment() {

    private lateinit var binding: FragmentPlacePointBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlacePointBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val adapter = fragmentManager?.let { myView(it) }
        adapter?.addFragment(StartPointTbFragment(), "Start Point")
        adapter?.addFragment(EndPointTbFragment(), "End Point")
        binding.viewpager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewpager)

    }

    class myView(manager: FragmentManager) : FragmentPagerAdapter(manager){
        val fragmentList : MutableList<Fragment> = ArrayList()
        val titleList : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return titleList.size
        }
        fun addFragment(fragment: Fragment, title: String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }

}