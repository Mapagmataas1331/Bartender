package cyou.ma.bartender.ui.ingredients

import cyou.ma.bartender.database.userIngredient.UserIngredient

sealed class IngredientsListScreenState {
    object Loading : IngredientsListScreenState()
    data class Success(val items: List<UserIngredient>) : IngredientsListScreenState()
    object Empty : IngredientsListScreenState()
}
