package john.kingsley.currencyconverter.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import john.kingsley.currencyconverter.data.Quote
import john.kingsley.currencyconverter.data.Rate

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote")
    fun getAll(): List<Quote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<Quote>)

    @Query("SELECT distinct c.name, c.code, b.updatedAt, ((select quote.quote from quote where quote.code =  c.code limit 1)/(select quote.quote from quote where quote.code = :str) * :amount) as rate from quote b inner join currency c")
    fun getRates(str: String, amount: Double): List<Rate>
}