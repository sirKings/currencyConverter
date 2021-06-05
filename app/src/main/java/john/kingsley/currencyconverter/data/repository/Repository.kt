package john.kingsley.currencyconverter.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import john.kingsley.currencyconverter.data.Currency
import john.kingsley.currencyconverter.data.Quote
import john.kingsley.currencyconverter.data.Rate
import john.kingsley.currencyconverter.data.local.AppDatabase
import john.kingsley.currencyconverter.data.network.APIClient
import john.kingsley.currencyconverter.data.network.APIInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrencyRepository(context: Context) {

    ///Access key hard code because of no login process
    private val accesskey = "b83dde9dc8c9cb316a0b3dc9ea042bdf"

    private val api = APIClient.client.create(APIInterface::class.java)
    private val db = AppDatabase.getInstance(context)

    suspend fun getCurrency(): Flow<List<Currency>> {
        return flow {
            val response = api.getCurrency(accesskey)
            if (response.success){
                val list = response.toCurrencyList()
                db.currencyDao().insertAll(list)
                emit(list)
            }else{
                throw Throwable("An error occurred")
            }
        }
    }

    suspend fun getQuotes(): Flow<List<Quote>> {
        return flow {
            val response = api.getQuote(accesskey)
            if (response.success){
                val list = response.toQuoteList()
                db.quoteDao().insertAll(list)
                emit(list)
            }else{
                throw Throwable("An error occurred")
            }
        }
    }

    fun getRates(amount: Double, currency: String): List<Rate> {
         return db.quoteDao().getRates(currency,amount)
    }

    fun fetchCurrencyListLocal(): List<Currency>{
        return db.currencyDao().getAll()
    }
}