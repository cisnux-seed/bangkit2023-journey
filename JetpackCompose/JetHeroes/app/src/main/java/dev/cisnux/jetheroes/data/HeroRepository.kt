package dev.cisnux.jetheroes.data

import dev.cisnux.jetheroes.model.Hero
import dev.cisnux.jetheroes.model.HeroesData

class HeroRepository {
    val heroes
        get() = HeroesData.heroes

    fun searchHeroes(query: String): List<Hero> = heroes.filter {
        it.name.contains(query, ignoreCase = true)
    }
}