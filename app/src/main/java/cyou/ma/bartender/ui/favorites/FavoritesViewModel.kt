package cyou.ma.bartender.ui.favorites

import androidx.lifecycle.ViewModel
import cyou.ma.bartender.database.favorite.FavoriteDrinkDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val favoriteDrinkDao: FavoriteDrinkDao) :
    ViewModel() {
    val favoriteDrinks = favoriteDrinkDao.getFavorites().distinctUntilChanged()
}
