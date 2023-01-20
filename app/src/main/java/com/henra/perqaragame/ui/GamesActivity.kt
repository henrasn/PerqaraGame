package com.henra.perqaragame.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.henra.perqaragame.R
import com.henra.perqaragame.databinding.ActivityMoviesBinding
import com.henra.perqaragame.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMoviesBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.pager.adapter = GamesPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            if (position == 0) {
                binding.materialToolbar.title = "Games For You"
                tab.text = "Home"
                tab.setIcon(R.drawable.ic_home)
            } else {
                binding.materialToolbar.title = "Favorite Games"
                tab.text = "Favorite"
                tab.setIcon(R.drawable.ic_favorite)
            }
        }.attach()
    }

    override fun onStart() {
        super.onStart()
        binding.pager.registerOnPageChangeCallback(tabChanges)
    }

    override fun onStop() {
        binding.pager.unregisterOnPageChangeCallback(tabChanges)
        super.onStop()
    }

    private val tabChanges = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (position == 0) {
                binding.materialToolbar.title = "Games For You"
            } else {
                binding.materialToolbar.title = "Favorite Games"
            }
        }
    }

}

class GamesPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        GamesFragment.newInstance(isHomeView = position == 0)
}