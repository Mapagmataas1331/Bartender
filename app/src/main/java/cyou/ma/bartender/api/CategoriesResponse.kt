package cyou.ma.bartender.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesResponse(
    @Json(name = "drinks")
    val categories: List<Category>
)

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "strCategory")
    val name: String
)
