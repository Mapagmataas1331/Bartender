package cyou.ma.bartender.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cyou.ma.bartender.database.favorite.FavoriteDrink
import cyou.ma.bartender.database.favorite.FavoriteDrinkDao
import cyou.ma.bartender.database.localIngredient.LocalIngredient
import cyou.ma.bartender.database.localIngredient.LocalIngredientDao
import cyou.ma.bartender.database.userIngredient.UserIngredient
import cyou.ma.bartender.database.userIngredient.UserIngredientDao

@Database(
  entities = [FavoriteDrink::class, LocalIngredient::class, UserIngredient::class],
  version = 4,
  exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun getFavoriteDrinksDao(): FavoriteDrinkDao
  abstract fun getLocalIngredientsDao(): LocalIngredientDao
  abstract fun getUserIngredientsDao(): UserIngredientDao
}
