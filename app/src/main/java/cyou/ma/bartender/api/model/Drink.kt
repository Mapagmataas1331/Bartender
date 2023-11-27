package cyou.ma.bartender.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Drink(
    @Json(name = "dateModified")
    val dateModified: String,
    @Json(name = "idDrink")
    val idDrink: String,
    @Json(name = "strAlcoholic")
    val alcoholic: String,
    @Json(name = "strCategory")
    val category: String,
    @Json(name = "strCreativeCommonsConfirmed")
    val creativeCommonsConfirmed: String,
    @Json(name = "strDrink")
    val drink: String,
    @Json(name = "strDrinkAlternate")
    val drinkAlternate: String?,
    @Json(name = "strDrinkThumb")
    val drinkThumb: String?,
    @Json(name = "strGlass")
    val glass: String?,
    @Json(name = "strIBA")
    val iba: String?,
    @Json(name = "strImageAttribution")
    val imageAttribution: String?,
    @Json(name = "strImageSource")
    val imageSource: String?,
    @Json(name = "strIngredient1")
    val ingredient1: String,
    @Json(name = "strIngredient10")
    val ingredient10: String?,
    @Json(name = "strIngredient11")
    val ingredient11: String?,
    @Json(name = "strIngredient12")
    val ingredient12: String?,
    @Json(name = "strIngredient13")
    val ingredient13: String?,
    @Json(name = "strIngredient14")
    val ingredient14: String?,
    @Json(name = "strIngredient15")
    val ingredient15: String?,
    @Json(name = "strIngredient2")
    val ingredient2: String?,
    @Json(name = "strIngredient3")
    val ingredient3: String?,
    @Json(name = "strIngredient4")
    val ingredient4: String?,
    @Json(name = "strIngredient5")
    val ingredient5: String?,
    @Json(name = "strIngredient6")
    val ingredient6: String?,
    @Json(name = "strIngredient7")
    val ingredient7: String?,
    @Json(name = "strIngredient8")
    val ingredient8: String?,
    @Json(name = "strIngredient9")
    val ingredient9: String?,
    @Json(name = "strInstructions")
    val instructions: String,
    @Json(name = "strMeasure1")
    val strMeasure1: String?,
    @Json(name = "strMeasure10")
    val strMeasure10: String?,
    @Json(name = "strMeasure11")
    val strMeasure11: String?,
    @Json(name = "strMeasure12")
    val strMeasure12: String?,
    @Json(name = "strMeasure13")
    val strMeasure13: String?,
    @Json(name = "strMeasure14")
    val strMeasure14: String?,
    @Json(name = "strMeasure15")
    val strMeasure15: String?,
    @Json(name = "strMeasure2")
    val strMeasure2: String?,
    @Json(name = "strMeasure3")
    val strMeasure3: String?,
    @Json(name = "strMeasure4")
    val strMeasure4: String?,
    @Json(name = "strMeasure5")
    val strMeasure5: String?,
    @Json(name = "strMeasure6")
    val strMeasure6: String?,
    @Json(name = "strMeasure7")
    val strMeasure7: String?,
    @Json(name = "strMeasure8")
    val strMeasure8: String?,
    @Json(name = "strMeasure9")
    val strMeasure9: String?,
    @Json(name = "strTags")
    val strTags: String?,
    @Json(name = "strVideo")
    val strVideo: String?
)