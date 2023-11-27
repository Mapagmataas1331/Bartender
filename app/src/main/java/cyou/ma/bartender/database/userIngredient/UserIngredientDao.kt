package cyou.ma.bartender.database.userIngredient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserIngredientDao {
    @Query("SELECT * FROM USERINGREDIENT")
    fun getUserIngredients(): Flow<List<UserIngredient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserIngredient(userIngredient: UserIngredient)

    @Query("DELETE FROM USERINGREDIENT WHERE name= :name")
    suspend fun removeUserIngredientByName(name: String)
}