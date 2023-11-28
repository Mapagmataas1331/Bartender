package cyou.ma.bartender.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cyou.ma.bartender.api.Category
import cyou.ma.bartender.api.model.Drink
import cyou.ma.bartender.database.favorite.FavoriteDrinkDao
import cyou.ma.bartender.repo.SearchRepo
import cyou.ma.bartender.ui.utils.AsyncState
import cyou.ma.bartender.ui.utils.Fail
import cyou.ma.bartender.ui.utils.Loading
import cyou.ma.bartender.ui.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val searchRepo: SearchRepo,
  favoriteDrinkDao: FavoriteDrinkDao
) : ViewModel() {
  val popularItems = mutableStateOf(emptyList<Drink>())
  val popularItemsState = mutableStateOf<AsyncState<Unit>>(Loading)

  val drinkCategories = mutableStateOf(emptyList<Category>())

  val favoriteDrinks = favoriteDrinkDao.getFavorites().distinctUntilChanged()

  init {
    getPopularDrinks()
    getCategories()
  }

  fun getPopularDrinks() = viewModelScope.launch {
    searchRepo.getPopularCocktails().fold(
      {
        popularItems.value = it
        popularItemsState.value = Success(Unit)
      },
      {
        popularItemsState.value = Fail(it)
      }
    )
  }

  fun getCategories() = viewModelScope.launch {
    searchRepo.getCategories().onSuccess {
      drinkCategories.value = it
    }.onFailure {
      it.printStackTrace()
    }
  }

}
