package cyou.ma.bartender.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredient(
  @Json(name = "strIngredient1")
  val name: String
)