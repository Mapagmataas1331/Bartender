package cyou.ma.bartender.database.localIngredient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalIngredientDao {
  @Query("SELECT * FROM LOCALINGREDIENT ORDER BY name")
  fun getIngredients(): Flow<List<LocalIngredient>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAllIngredients(localIngredients: List<LocalIngredient>)

  @Query("DELETE FROM LOCALINGREDIENT")
  suspend fun deleteAllIngredients()
}
