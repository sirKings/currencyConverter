package john.kingsley.currencyconverter.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import john.kingsley.currencyconverter.data.Currency

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency")
    fun getAll(): List<Currency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<Currency>)

}