package dev.cisnux.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    private lateinit var rvHeroes: RecyclerView
    private val list = ArrayList<Hero>()
    private val showSelectedHero: OnItemClickCallback<Hero> = { hero ->
        Toast.makeText(this, "Kamu memilih " + hero.name, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvHeroes = findViewById(R.id.rv_heroes)
        rvHeroes.setHasFixedSize(true)

        list.addAll(getListHeroes())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        with(rvHeroes) {
            when (item.itemId) {
                R.id.action_list -> layoutManager = LinearLayoutManager(this@MainActivity)
                R.id.action_grid -> {
                    layoutManager = GridLayoutManager(this@MainActivity, 2)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListHeroes(): ArrayList<Hero> = with(resources) {
        val dataName = getStringArray(R.array.data_name)
        val dataDescription = getStringArray(R.array.data_description)
        val dataPhoto = obtainTypedArray(R.array.data_photo)
        val listHero = ArrayList<Hero>()
        for (i in dataName.indices) {
            val hero = Hero(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listHero.add(hero)
        }
        dataPhoto.recycle()
        listHero
    }

    private fun showRecyclerList() {
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListHeroAdapter(list, showSelectedHero)
        rvHeroes.adapter = listHeroAdapter
    }
}