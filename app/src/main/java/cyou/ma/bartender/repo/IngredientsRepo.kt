package cyou.ma.bartender.repo

import cyou.ma.bartender.api.CocktailApi
import cyou.ma.bartender.database.localIngredient.LocalIngredient
import cyou.ma.bartender.database.localIngredient.LocalIngredientDao
import cyou.ma.bartender.database.userIngredient.UserIngredient
import cyou.ma.bartender.database.userIngredient.UserIngredientDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

interface IngredientsRepo {
  suspend fun saveIngredients(): Result<Boolean>
  suspend fun addIngredient(ingredient: LocalIngredient): Result<Unit>
  suspend fun deleteIngredient(removeIngredient: LocalIngredient): Result<Unit>
  suspend fun deleteIngredient(removeIngredient: UserIngredient): Result<Unit>

  val userIngredientsFlow: Flow<List<UserIngredient>>

  val allIngredients: Flow<List<LocalIngredient>>
}

class IngredientsRepoImp(
  private val api: CocktailApi,
  private val ingredientsDao: LocalIngredientDao,
  private val userIngredientsDao: UserIngredientDao
) : IngredientsRepo {

  override val allIngredients = ingredientsDao.getIngredients().distinctUntilChanged()

  private val ingredientsFailure = Exception("Unable to get ingredients")

  override val userIngredientsFlow: Flow<List<UserIngredient>> =
    userIngredientsDao.getUserIngredients()

  override suspend fun saveIngredients(): Result<Boolean> {
    return try {
      val results = api.getAllIngredients().mapToResult()

      if (results.isSuccess) {
        val ingredients = results.getOrNull()?.ingredients?.map {
          LocalIngredient(it.name)
        } ?: throw ingredientsFailure

        ingredientsDao.insertAllIngredients(ingredients)

        Result.success(true)
      } else {
        Result.failure(ingredientsFailure)
      }
    } catch (e: Exception) {
      Result.failure(e)
    }

  }

  override suspend fun addIngredient(ingredient: LocalIngredient): Result<Unit> {
    return try {
      userIngredientsDao.addUserIngredient(ingredient.toUserIngredient())
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun deleteIngredient(removeIngredient: LocalIngredient): Result<Unit> {
    return try {
      userIngredientsDao.removeUserIngredientByName(removeIngredient.name)
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun deleteIngredient(removeIngredient: UserIngredient): Result<Unit> {
    return try {
      userIngredientsDao.removeUserIngredientByName(removeIngredient.name)
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}
