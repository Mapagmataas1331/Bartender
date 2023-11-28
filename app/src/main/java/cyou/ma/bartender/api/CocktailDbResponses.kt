package cyou.ma.bartender.api

import cyou.ma.bartender.api.model.Drink
import cyou.ma.bartender.api.model.Ingredient
import cyou.ma.bartender.api.model.CardDrink
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksResponse(
  @Json(name = "drinks")
  val drinks: List<Drink>
)

@JsonClass(generateAdapter = true)
data class IngredientsResponse(
  @Json(name = "drinks")
  val ingredients: List<Ingredient>
)

@JsonClass(generateAdapter = true)
data class CardDrinkResponse(
  @Json(name = "drinks")
  val drinks: List<CardDrink>
)