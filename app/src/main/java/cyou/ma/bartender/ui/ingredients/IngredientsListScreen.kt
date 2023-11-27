package cyou.ma.bartender.ui.ingredients

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cyou.ma.bartender.R
import cyou.ma.bartender.api.model.MixerDrink
import cyou.ma.bartender.database.userIngredient.UserIngredient
import cyou.ma.bartender.ui.addIngredient.AddIngredientScreen
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@FlowPreview
@ExperimentalMaterialApi
@Composable
fun IngredientsListScreen(
    onDrinkClicked: (drinkId: String) -> (Unit),
    viewModel: IngredientsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_stored_ingredients),
                        fontWeight = FontWeight.Black,
                        fontSize = 24.sp
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        scaffoldState = scaffoldState,
        sheetContent = {
            AddIngredientScreen(
                scaffoldState = scaffoldState
            )
        },
        sheetShape = RoundedCornerShape(12.dp)
    ) {
        UserIngredientsListScreen(
            viewModel,
            it,
            onAddNewIngredient = {
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            },
            onNavigateToDrinkDetail = {
                onDrinkClicked(it.id)
            }
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun UserIngredientsListScreen(
    viewModel: IngredientsViewModel,
    paddingValues: PaddingValues,
    onAddNewIngredient: () -> Unit,
    onNavigateToDrinkDetail: (MixerDrink) -> Unit
) {
    val state = viewModel.screenState
    val mixerItems = viewModel.selectedMixers
    val foundMixers = viewModel.foundMixers

    when (val screenState = state.value) {
        is IngredientsListScreenState.Success -> {

            Column {

                MixerChipGroup(
                    mixers = mixerItems.value,
                    onRemoveMixer = {
                        viewModel.removeMixer(it)
                    }
                ) {
                    viewModel.clearMixers()
                }
                InlineMixerDrinks(foundMixers.value, mixerItems.value, onNavigateToDrinkDetail)

                LazyColumn(Modifier.padding(paddingValues)) {
                    items(screenState.items) {
                        UserIngredientItem(
                            it,
                            onAddMixer = {
                                viewModel.addMixer(it)
                            },
                            onRemoveIngredient = {
                                viewModel.removeIngredient(it)
                            }
                        )
                    }
                }
            }

        }
        IngredientsListScreenState.Empty -> {
            UserIngredientEmptyState(onAddNewIngredient)
        }
        IngredientsListScreenState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun UserIngredientEmptyState(onAddNewIngredient: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedButton(
            onClick = onAddNewIngredient
        ) {
            Icon(
                imageVector = Icons.Outlined.AddShoppingCart,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp),
                tint = MaterialTheme.colors.onSecondary
            )
            Text(
                text = stringResource(id = R.string.add_some_ingredients),
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserIngredientItem(
    ingredient: UserIngredient,
    onAddMixer: (UserIngredient) -> Unit,
    onRemoveIngredient: (UserIngredient) -> Unit
) {

    Column(
        Modifier.combinedClickable(
            onClick = {

            },
            onLongClick = {
                onRemoveIngredient(ingredient)
            }
        )
    ) {
        Row(
            Modifier.padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ingredient.name,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    onAddMixer(ingredient)
                },
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlaylistAdd,
                    contentDescription = "Add to mixer"
                )
            }
        }
        Divider()
    }
}

