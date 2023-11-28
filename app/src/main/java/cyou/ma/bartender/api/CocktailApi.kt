package cyou.ma.bartender.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
  @GET("api/json/v2/1/popular.php")
  suspend fun getPopularCocktails(): Response<DrinksResponse>

  @GET("api/json/v2/1/search.php")
  suspend fun getSearchResults(
    @Query("s") query: String
  ): Response<CardDrinkResponse>

  @GET("api/json/v2/1/list.php?c=list")
  suspend fun getCategories(): Response<CategoriesResponse>

  @GET("api/json/v2/1/lookup.php")
  suspend fun getCocktail(
    @Query("i") id: String
  ): Response<DrinksResponse>

  @GET("api/json/v2/1/list.php?i=list")
  suspend fun getAllIngredients(): Response<IngredientsResponse>

  @GET("api/json/v2/1/filter.php?i=Dry_Vermouth,Gin,Anis")
  suspend fun getDrinksWithMixers(
    @Query("i") mixers: String
  ): Response<CardDrinkResponse>
}
