package dev.cisnux.mytablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Selain itu, di sini juga terdapat constructor yang
 * diperlukan yaitu AppCompatActivity karena kita menggunakan
 * Activity. Apabila Anda menerapkannya di Fragment,
 * gunakanFragmentActivity.
 * */
/**
 * Perlu diketahui juga, sebenarnya Anda dapat menggunakan
 * RecyclerView.Adapter sebagai adapter. Hal ini karena
 * pada dasarnya ViewPager2 dibuat menggunakan RecyclerView.
 * Jadi, Anda pun bisa menggunakannya. Menarik ya!
 * */
class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // with different fragments
//        val fragment: Fragment = when (position) {
//            0 -> HomeFragment()
//            else -> ProfileFragment()
//        }
        // with same fragment
        val fragment = HomeFragment()
        fragment.arguments = Bundle().apply {
            putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }
}