package john.kingsley.currencyconverter.data

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import org.json.JSONObject

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey val code: String,
    val name: String
)

@Entity(tableName = "quote")
data class Quote(
    @PrimaryKey val code: String,
    val quote: Double,
    val updatedAt: Long
)

data class Rate(
        val code: String,
        val rate: Double,
        val updatedAt: Long,
        val name: String
)

data class CurrencyResponse(
    val currencies: Map<String, String>,
    val success: Boolean,
    val terms: String,
    val privacy: String
){
    fun toCurrencyList(): List<Currency>{
        val items = arrayListOf<Currency>()
        this.currencies.forEach {
            items.add(Currency(code = it.key, name = it.value))
        }
        return items
    }
}

data class QuoteResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes:  Map<String, Double>
){
    fun toQuoteList(): List<Quote>{
        val items = arrayListOf<Quote>()
        this.quotes.forEach {
            items.add(Quote(code = it.key.drop(3), quote = it.value, updatedAt = timestamp))
        }
        return items
    }
}