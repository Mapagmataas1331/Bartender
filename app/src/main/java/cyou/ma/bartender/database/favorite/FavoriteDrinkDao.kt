package cyou.ma.bartender.database.favorite

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDrinkDao {
  @Query("SELECT * FROM FAVORITEDRINK")
  fun getFavorites(): Flow<List<FavoriteDrink>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFavorite(favoriteDrink: FavoriteDrink)

  @Delete
  suspend fun deleteFavorite(favoriteDrink: FavoriteDrink)

  @Query("SELECT EXISTS (SELECT * FROM FAVORITEDRINK WHERE id = :drinkId LIMIT 1)")
  fun isFavorite(drinkId: String): Flow<Boolean>
}