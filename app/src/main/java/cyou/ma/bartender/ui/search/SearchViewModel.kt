package cyou.ma.bartender.ui.search

import androidx.lifecycle.ViewModel
import cyou.ma.bartender.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepo: SearchRepo) : ViewModel() {


}
