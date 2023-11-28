package cyou.ma.bartender.repo

import cyou.ma.bartender.api.Category
import cyou.ma.bartender.api.CocktailApi
import cyou.ma.bartender.api.model.Drink
import cyou.ma.bartender.api.model.Ingredient
import cyou.ma.bartender.api.model.CardDrink

interface SearchRepo {
  suspend fun searchCocktails(query: String): Result<List<CardDrink>>
  suspend fun getPopularCocktails(): Result<List<Drink>>
  suspend fun getCategories(): Result<List<Category>>
  suspend fun getDrink(id: String): Result<Drink>
  suspend fun getIngredients(name: String): Result<List<Ingredient>>
  suspend fun getDrinksForMixers(mixers: Set<String>): Result<List<CardDrink>>
}

class SearchRepoImp(
  private val api: CocktailApi
) : SearchRepo {
  override suspend fun searchCocktails(query: String): Result<List<CardDrink>> {
    return try {
      api.getSearchResults(query = query).mapToResult { it.drinks }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun getPopularCocktails(): Result<List<Drink>> {
    return try {
      api.getPopularCocktails().mapToResult { it.drinks }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun getCategories(): Result<List<Category>> {
    return try {
      api.getCategories().mapToResult { it.categories }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun getDrink(id: String): Result<Drink> {
    return try {
      api.getCocktail(id = id).mapToResult {
        it.drinks.first()
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun getIngredients(name: String): Result<List<Ingredient>> {
    return try {
      Result.success(emptyList())
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun getDrinksForMixers(mixers: Set<String>): Result<List<CardDrink>> {
    return try {
      if (mixers.isEmpty()) {
        Result.failure(IllegalArgumentException("No mixers provided"))
      } else {
        val split = mixers.joinToString(",")
        val sanitized = split.replace(" ", "_")
        api.getDrinksWithMixers(mixers = sanitized).mapToResult {
          it.drinks
        }
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}
