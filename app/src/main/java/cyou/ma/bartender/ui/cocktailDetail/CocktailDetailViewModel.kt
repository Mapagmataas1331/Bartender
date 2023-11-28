package cyou.ma.bartender.ui.cocktailDetail

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cyou.ma.bartender.api.model.Drink
import cyou.ma.bartender.database.favorite.FavoriteDrink
import cyou.ma.bartender.database.favorite.FavoriteDrinkDao
import cyou.ma.bartender.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class CocktailDetailsViewState {
  data object Loading : CocktailDetailsViewState()
  data class Content(val data: CocktailDetailsDisplayModel) : CocktailDetailsViewState()
  data class Error(val message: String) :
    CocktailDetailsViewState() {
    companion object {
      operator fun invoke(message: String? = null) =
        Error(message ?: "An Unknown Error Has Occurred")
    }
  }
}

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
  private val searchRepo: SearchRepo,
  private val favoriteDrinkDao: FavoriteDrinkDao
) : ViewModel() {
  val drink = mutableStateOf<Drink?>(null)
  val isFavorite = mutableStateOf(false)

  private val _displayDrink =
    mutableStateOf<CocktailDetailsViewState>(CocktailDetailsViewState.Loading)
  val displayDrink = derivedStateOf { _displayDrink.value }
  val pageTitle = derivedStateOf {
    when (val state = displayDrink.value) {
      is CocktailDetailsViewState.Content -> state.data.name
      is CocktailDetailsViewState.Error -> "Error"
      CocktailDetailsViewState.Loading -> "Loading..."
    }
  }


  var drinkId: String? = null
    set(value) {
      if (value != null) {
        getDrink(value)
        checkFavorite(value)
        field = value
      }
    }

  private fun checkFavorite(value: String) = viewModelScope.launch {
    favoriteDrinkDao.isFavorite(value).distinctUntilChanged().collect {
      isFavorite.value = it
    }
  }

  private fun getDrink(id: String) {
    viewModelScope.launch {
      searchRepo.getDrink(id)
        .onSuccess {
          drink.value = it
          _displayDrink.value = CocktailDetailsViewState.Content(
            CocktailDetailsDisplayModel.fromDrink(it)
          )
        }.onFailure {
          _displayDrink.value = CocktailDetailsViewState.Error(it.message)
        }
    }
  }

  fun toggleFavorite(checked: Boolean) = viewModelScope.launch {
    val favoriteDrink = drink.value ?: return@launch

    if (!checked) {
      favoriteDrinkDao.deleteFavorite(FavoriteDrink.fromNetwork(favoriteDrink))
    } else {
      favoriteDrinkDao.insertFavorite(FavoriteDrink.fromNetwork(favoriteDrink))
    }
  }
}

