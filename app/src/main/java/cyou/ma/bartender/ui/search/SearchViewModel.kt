package cyou.ma.bartender.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cyou.ma.bartender.api.model.CardDrink
import cyou.ma.bartender.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepo: SearchRepo) :
  ViewModel() {

  private val _searchState = MutableStateFlow(SearchScreenSearchState.Initial)

  private val _cocktails = MutableStateFlow<List<CardDrink>>(emptyList())
  val cocktails: StateFlow<List<CardDrink>> = _cocktails

  fun searchCocktails(query: String) {
//    Log.i("search", "searchCocktails: $query")
    viewModelScope.launch {
      _searchState.value = SearchScreenSearchState.Loading
      _cocktails.value = emptyList()
      val results = searchRepo.searchCocktails(query)
      if (results.isSuccess) {
        _cocktails.value = results.getOrDefault(emptyList())
        _searchState.value = if (_cocktails.value.isEmpty()) {
          SearchScreenSearchState.Empty
        } else {
          SearchScreenSearchState.Success
        }
      } else {
        _searchState.value = SearchScreenSearchState.Fail
      }
    }
  }
}

