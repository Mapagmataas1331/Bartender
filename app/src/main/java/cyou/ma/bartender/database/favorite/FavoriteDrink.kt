package cyou.ma.bartender.database.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import cyou.ma.bartender.api.model.Drink

@Entity
data class FavoriteDrink(
  @PrimaryKey
  val id: String,
  val isAlcoholic: Boolean,
  val category: String,
  val name: String,
  val imageThumbnail: String?,
  val glass: String?,
  val iba: String?,
  val ingredients: String,
  val instructions: String? = null,
  val measurements: String? = null
) {
  companion object {
    fun fromNetwork(drink: Drink) = FavoriteDrink(
      id = drink.idDrink,
      isAlcoholic = drink.alcoholic == "Alcoholic",
      category = drink.category,
      name = drink.drink,
      imageThumbnail = drink.drinkThumb,
      glass = drink.glass,
      iba = drink.iba,
      ingredients = drink.ingredient1,
      instructions = drink.instructions,
      measurements = drink.strMeasure1
    )
  }
}

