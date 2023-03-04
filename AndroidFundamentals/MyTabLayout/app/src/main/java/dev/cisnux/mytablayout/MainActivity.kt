package dev.cisnux.mytablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayoutMediator
import dev.cisnux.mytablayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            /**
             *  Anda juga bisa menghubungkan ViewPager2 dengan
             *  TabLayout dengan menggunakan TabLayoutMediator.
             *  Dengan menerapkan TabLayoutMediator, maka Fragment
             *  yang tampil pada ViewPager2 akan sesuai dengan posisi
             *  yang dipilih pada tab. Selain itu, di sini Anda juga
             *  menentukan judul dari masing-masing Tab dengan
             *  menggunakan TAB_TITLE yang diambil sesuai dengan urutan
             *  posisinya.
             * */
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        // to configure elevation effect or shadow effect behind action bar
        supportActionBar?.elevation = 0f
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}