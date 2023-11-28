package cyou.ma.bartender.ui.ingredients

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cyou.ma.bartender.api.model.MixerDrink
import cyou.ma.bartender.database.userIngredient.UserIngredient
import cyou.ma.bartender.repo.IngredientsRepo
import cyou.ma.bartender.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(
  private val ingredientsRepo: IngredientsRepo,
  private val searchRepo: SearchRepo
) : ViewModel() {

  val screenState = mutableStateOf<IngredientsListScreenState>(IngredientsListScreenState.Loading)
  val selectedMixers = mutableStateOf(mutableSetOf<String>())
  val foundMixers = mutableStateOf(listOf<MixerDrink>())

  init {
    viewModelScope.launch {
      ingredientsRepo.userIngredientsFlow
        .distinctUntilChanged()
        .collect {
          val state =
            if (it.isEmpty()) IngredientsListScreenState.Empty else IngredientsListScreenState.Success(
              it
            )
          screenState.value = state
        }
    }
  }

  fun removeIngredient(removeIngredient: UserIngredient) = viewModelScope.launch {
    ingredientsRepo.deleteIngredient(removeIngredient)
  }

  fun addMixer(ingredient: UserIngredient) {
    val updated = selectedMixers.value.toMutableSet().apply {
      add(ingredient.name)
    }
    selectedMixers.value = updated
    getDrinksForMixers()
  }

  fun removeMixer(mixer: String) {
    val updated = selectedMixers.value.toMutableSet().apply {
      remove(mixer)
    }
    selectedMixers.value = updated
    // clear out
    if (updated.isEmpty()) {
      foundMixers.value = emptyList()
    } else {
      getDrinksForMixers()
    }
  }

  fun getDrinksForMixers() = viewModelScope.launch {
    searchRepo.getDrinksForMixers(selectedMixers.value).fold(
      {
        foundMixers.value = it
      },
      {
        it.printStackTrace()
      }
    )
  }

  fun clearMixers() {
    selectedMixers.value = mutableSetOf()
    foundMixers.value = mutableListOf()
  }
}
