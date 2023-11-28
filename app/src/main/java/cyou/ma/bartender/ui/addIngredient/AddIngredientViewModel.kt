package cyou.ma.bartender.ui.addIngredient

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cyou.ma.bartender.database.localIngredient.LocalIngredient
import cyou.ma.bartender.repo.IngredientsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class AddIngredientViewModel @Inject constructor(private val ingredientsRepo: IngredientsRepo) :
  ViewModel() {
  val screenState = mutableStateOf(AddIngredientSearchState.Initial)
  val query = MutableStateFlow("")
  val results = mutableStateOf<List<LocalIngredient>>(emptyList())

  private val allIngredients = ingredientsRepo.allIngredients.map {
    screenState.value = AddIngredientSearchState.Success
    it
  }

  init {
    refreshIngredients()
    viewModelScope.launch {
      observeQuery()
    }
  }

  private suspend fun observeQuery() {
    query.debounce(300)
      .distinctUntilChanged()
      .collect {
        filterForQuery(it)
      }
  }

  private fun refreshIngredients() = viewModelScope.launch {
    ingredientsRepo.saveIngredients()
  }

  private fun filterForQuery(query: String) = viewModelScope.launch {
    val start = allIngredients.first()
    if (query.isEmpty()) {
      results.value = start
      return@launch
    }

    results.value = start.filter { it.name.contains(query, ignoreCase = true) }
  }

  fun addIngredient(ingredient: LocalIngredient) = viewModelScope.launch {
    ingredientsRepo.addIngredient(ingredient)
  }

  fun removeIngredient(removeIngredient: LocalIngredient) = viewModelScope.launch {
    ingredientsRepo.deleteIngredient(removeIngredient)
  }

}
