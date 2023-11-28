package cyou.ma.bartender.ui.cocktailDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cyou.ma.bartender.R
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun CocktailDetailScreen(
  onBackPressed: () -> (Unit),
  drinkId: String?,
  viewModel: CocktailDetailViewModel = hiltViewModel()
) {
  viewModel.drinkId = drinkId
  val isFavorite = viewModel.isFavorite.value
  val viewState = viewModel.displayDrink.value
  val title = viewModel.pageTitle.value

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
          IconButton(onClick = onBackPressed) {
            Icon(
              imageVector = Icons.Default.ArrowBack,
              contentDescription = "Back"
            )
          }
        },
        actions = {
          IconToggleButton(checked = isFavorite, onCheckedChange = {
            viewModel.toggleFavorite(it)
          }) {
            Icon(
              imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = "Favorite Button"
            )
          }
        }
      )
    }
  ) { padding ->
    when (viewState) {
      is CocktailDetailsViewState.Content -> {
        CocktailDetailContent(drink = viewState.data)
      }
      is CocktailDetailsViewState.Error -> {
        Text(text = viewState.message)
      }
      CocktailDetailsViewState.Loading -> {
        CircularProgressIndicator()
      }
    }
  }
}

@Composable
fun CocktailDetailContentSpacer() {
  Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CocktailDetailsSectionHeader(text: String) {
  Text(text = text, style = MaterialTheme.typography.h5)
}

@Composable
fun TagChip(text: String, modifier: Modifier = Modifier) {
  Surface(
    shape = MaterialTheme.shapes.small,
    color = Color.Transparent,
    modifier = modifier
      .border(
        width = 1.dp,
        color = MaterialTheme.colors.onSurface
      )
      .padding(8.dp, 4.dp)
  ) {
    Text(
      text = text
    )
  }
}

@Composable
fun CocktailDetailContent(drink: CocktailDetailsDisplayModel) {
  val painter = rememberCoilPainter(drink.image ?: R.drawable.ic_baseline_local_drink_24)

  LazyColumn {
    item {
      Image(
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = "${drink.name} image",
        modifier = Modifier
          .fillMaxWidth()
          .height(300.dp)
      )
    }

    item {
      Column(modifier = Modifier.padding(16.dp)) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
          items(drink.tags) {
            TagChip(it)
          }
        }

        CocktailDetailContentSpacer()

        if (drink.altName != null) {
          CocktailDetailsSectionHeader(text = "A.K.A.")
          Text(
            text = drink.altName,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
          )

          CocktailDetailContentSpacer()
        }


        CocktailDetailsSectionHeader(text = "Glassware")
        Text(
          text = drink.glassware ?: "Bartender's Choice",
          style = MaterialTheme.typography.body1,
          modifier = Modifier.padding(8.dp)
        )

        CocktailDetailContentSpacer()

        CocktailDetailsSectionHeader(text = "Ingredients")
        Column(modifier = Modifier.padding(8.dp)) {
          drink.ingredients.map {
            Text(text = it.toString(), style = MaterialTheme.typography.body1)
          }
        }

        CocktailDetailContentSpacer()

        CocktailDetailsSectionHeader(text = "Instructions")

        Text(
          text = drink.instructions,
          style = MaterialTheme.typography.body1,
          modifier = Modifier.padding(8.dp)
        )
      }
    }
  }
}

