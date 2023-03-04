package dev.cisnux.myactionbar

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import dev.cisnux.myactionbar.databinding.ActivityMainBinding


/**
 *
 * ifRoom, akan menampilkan action ketika ada ruangan pada action bar.
 * withText, akan menampilkan actionitem beserta judulnya.
 * never, tidak akan pernah ditampilkan pada action bar dan hanya akan ditampilkan pada overflow menu.
 * always, akan selalu tampil pada action bar.
 * collapseActionView, berhubungan dengan komponen collapsible .
 * */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        /**
         * Untuk mengambil komponen searchview yang sebelumnya sudah di inflate,
         * kita menggunakan fungsi berikut menu.findItem().getActionView().
         * */
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                /**
                 * Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
                 * */
                override fun onQueryTextChange(newText: String?): Boolean = false

                /**
                 * Gunakan method ini ketika search selesai atau OK
                 * */
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                    /**
                     * Pada kasus kali ini, Anda menampilkan teks yang dimasukkan
                     * ke dalam sebuah Toast. Selain itu, juga ada fungsi clearFocus
                     * yang dipanggil supaya tidak ada duplikasi dalam pemanggilan
                     * fungsi onQueryTextSubmit dan untuk meng-hide keyboard.
                     * [clearFocus()] is same as searchView.clearFocus()
                     * */
                    searchView.clearFocus()
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu1 -> {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MenuFragment())
                .addToBackStack(null)
                .commit()
            true
        }
        R.id.menu2 -> {
            val i = Intent(this, MenuActivity::class.java)
            startActivity(i)
            true
        }
        else -> true
    }
}