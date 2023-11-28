package cyou.ma.bartender.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cyou.ma.bartender.R
import cyou.ma.bartender.api.Category
import cyou.ma.bartender.api.model.Drink
import cyou.ma.bartender.ui.favorites.FavoriteDrinkListItem
import cyou.ma.bartender.ui.favorites.FavoritesEmptyState
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun HomeScreen(onDrinkClicked: (drinkId: String) -> Unit, onFavoritesClicked: () -> (Unit)) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.Black,
            fontSize = 24.sp
          )
        },
        backgroundColor = MaterialTheme.colors.primary
      )
    }
  ) {
    HomeList(
      onDrinkClicked = onDrinkClicked,
      onFavoritesClicked = onFavoritesClicked
    )
  }
}

@Composable
fun HomeList(
  onDrinkClicked: (drinkId: String) -> Unit,
  onFavoritesClicked: () -> (Unit),
  viewModel: HomeViewModel = hiltViewModel()
) {
  val popularItems = viewModel.popularItems
  val categories = viewModel.drinkCategories
  val favorites = viewModel.favoriteDrinks.collectAsState(initial = emptyList())

  LazyColumn {

    item {
      Column(Modifier.fillMaxWidth()) {
        HomeHeaderText(stringResource = R.string.title_popular_drinks)
        LazyRow {
          items(popularItems.value) { item ->
            MediumDrinkCard(drink = item, onClick = { drink ->
              onDrinkClicked(drink.idDrink)
            })
          }
        }
      }
    }

    item {
      HomeHeaderText(stringResource = R.string.title_favorite_drinks)
    }

    if (favorites.value.isEmpty()) {
      item {
        FavoritesEmptyState(Modifier.fillMaxWidth())
      }
    } else {
      items(favorites.value.take(4)) {
        FavoriteDrinkListItem(drink = it) {
          onDrinkClicked(it.id)
        }
      }

      item {
        TextButton(
          onClick = {
            onFavoritesClicked()
          },
          modifier = Modifier.padding(12.dp)
        ) {
          Text(stringResource(id = R.string.more))
        }
      }
    }

    item {
      HomeHeaderText(stringResource = R.string.title_drink_categories)
    }

    items(categories.value.chunked(2)) {
      ChunkedCategories(it)
    }
  }
}

@Composable
fun HomeHeaderText(stringResource: Int? = null, string: String? = null) {
  val text = if (stringResource != null) stringResource(id = stringResource) else string.orEmpty()
  Text(
    text = text,
    fontWeight = FontWeight.Black,
    fontSize = 20.sp,
    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
  )
}

@Composable
fun ChunkedCategories(categories: List<Category>) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
  ) {
    categories.forEach {

      OutlinedButton(
        modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp),
        onClick = {
          // prefilled search for category
        }
      ) {
        Text(it.name)
      }
    }
  }
}

@Composable
fun MediumDrinkCard(drink: Drink, onClick: ((Drink) -> (Unit))) {

  val painter = rememberCoilPainter(
    request = drink.drinkThumb ?: R.drawable.ic_baseline_local_drink_24
  )

  Card(
    modifier = Modifier
      .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
      .height(200.dp)
      .width(180.dp)
      .clickable {
        onClick(drink)
      },
    shape = RoundedCornerShape(8.dp)
  ) {
    Column(Modifier.fillMaxSize()) {
      Image(
        painter = painter,
        contentDescription = "${drink.drink} image",
        modifier = Modifier.weight(0.8f),
        contentScale = ContentScale.Crop
      )
      when (painter.loadState) {
        ImageLoadState.Empty -> {}
        is ImageLoadState.Loading -> {
          Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
          }
        }
        is ImageLoadState.Error, is ImageLoadState.Success -> {}
      }
      Text(
        drink.drink, modifier = Modifier
          .padding(8.dp)
          .weight(0.2f)
      )
    }

  }
}
