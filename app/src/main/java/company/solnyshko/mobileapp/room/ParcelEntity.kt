package company.solnyshko.mobileapp.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ParcelEntity(
    @PrimaryKey(autoGenerate = true)
    val icon : Int? = null,
    val type : String = "",
    val isChecked : Boolean)