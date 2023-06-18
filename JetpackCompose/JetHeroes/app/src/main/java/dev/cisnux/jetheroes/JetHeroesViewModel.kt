package dev.cisnux.jetheroes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.cisnux.jetheroes.data.HeroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class JetHeroesViewModel(private val repository: HeroRepository) : ViewModel() {
    private val _heroes = MutableStateFlow(
        repository.heroes
    )
    val groupedHeroes get() = _heroes.map { heroes ->
        heroes.sortedBy { hero ->
            hero.name
        }
            .groupBy { hero ->
                hero.name.first()
            }
    }

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    fun search(query: String){
        _query.value = query
        _heroes.value = repository.searchHeroes(query)
    }
}

class ViewModelFactory(private val repository: HeroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetHeroesViewModel::class.java)) {
            return JetHeroesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
