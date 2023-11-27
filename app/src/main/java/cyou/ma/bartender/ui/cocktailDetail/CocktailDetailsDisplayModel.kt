package cyou.ma.bartender.ui.cocktailDetail

import cyou.ma.bartender.api.model.Drink

data class Ingredient(
    val name: String,
    val quantity: String,
) {
    override fun toString(): String {
        return listOf(quantity, name)
            .filter { it.isNotBlank() }
            .joinToString("")
    }
}

data class CocktailDetailsDisplayModel(
    val id: String,
    val name: String,
    val altName: String?,
    val glassware: String?,
    val ingredients: List<Ingredient>,
    val instructions: String,
    val image: String?,
    val tags: List<String>,
) {
    companion object {
        fun fromDrink(drink: Drink): CocktailDetailsDisplayModel {
            val ingredientTuples = listOf(
                drink.ingredient1 to drink.strMeasure1,
                drink.ingredient2 to drink.strMeasure2,
                drink.ingredient3 to drink.strMeasure3,
                drink.ingredient4 to drink.strMeasure4,
                drink.ingredient5 to drink.strMeasure5,
                drink.ingredient6 to drink.strMeasure6,
                drink.ingredient7 to drink.strMeasure7,
                drink.ingredient8 to drink.strMeasure8,
                drink.ingredient9 to drink.strMeasure9,
                drink.ingredient10 to drink.strMeasure10,
                drink.ingredient11 to drink.strMeasure11,
                drink.ingredient12 to drink.strMeasure12,
                drink.ingredient13 to drink.strMeasure13,
                drink.ingredient14 to drink.strMeasure14,
                drink.ingredient15 to drink.strMeasure15,
            )

            val ingredients = ingredientTuples
                .filter { it.first != null } // We only care about ingredient values
                .map { Ingredient(it.first!!, it.second ?: "") }

            val chips = listOfNotNull(
                drink.category,
                drink.alcoholic,
                drink.iba,
            )

            return CocktailDetailsDisplayModel(
                drink.idDrink,
                drink.drink,
                drink.drinkAlternate,
                drink.glass,
                ingredients,
                drink.instructions,
                drink.drinkThumb,
                chips,
            )
        }
    }
}


