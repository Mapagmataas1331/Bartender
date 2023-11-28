package cyou.ma.bartender.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cyou.ma.bartender.R
import cyou.ma.bartender.api.model.CardDrink

@Composable
fun SearchScreenListing(onDrinkClicked: (String) -> Unit, viewModel: SearchViewModel = hiltViewModel()) {
  LocalContext.current
  val focusManager = LocalFocusManager.current

  val cocktailsState by viewModel.cocktails.collectAsState(emptyList())
  val cocktails: List<CardDrink> = cocktailsState
  val queryState = remember { mutableStateOf("") }

  Column {
    Text(
      text = stringResource(id = R.string.title_search),
      style = MaterialTheme.typography.h5,
      modifier = Modifier.padding(16.dp)
    )

    OutlinedTextField(
      value = queryState.value,
      onValueChange = { query ->
        queryState.value = query
        viewModel.searchCocktails(query)
      },
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth(),
      keyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Search
      ),
      keyboardActions = KeyboardActions(
        onSearch = {
          focusManager.clearFocus()
          viewModel.searchCocktails(queryState.value)
        }
      ),
      label = { Text(stringResource(id = R.string.type_to_filter)) }
    )

    CocktailList(cocktails = cocktails, onDrinkClicked = onDrinkClicked)
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CocktailList(cocktails: List<CardDrink>, onDrinkClicked: (String) -> Unit) {
  LazyColumn {
    items(cocktails) { cocktail ->
      ListItem(
        text = { Text(text = cocktail.name) },
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onDrinkClicked(cocktail.id) }
          .padding(horizontal = 16.dp, vertical = 8.dp)
      )
    }
  }
}
