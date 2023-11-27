package cyou.ma.bartender.database.localIngredient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cyou.ma.bartender.database.userIngredient.UserIngredient

@Entity
data class LocalIngredient(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String
) {
    fun toUserIngredient() = UserIngredient(name = this.name)
}
