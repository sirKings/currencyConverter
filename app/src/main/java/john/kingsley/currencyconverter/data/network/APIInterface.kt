package john.kingsley.currencyconverter.data.network

import john.kingsley.currencyconverter.data.CurrencyResponse
import john.kingsley.currencyconverter.data.QuoteResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {
    @GET("/live")
    suspend fun getQuote(@Query("access_key") accessKey: String): QuoteResponse

    @GET("/list")
    suspend fun getCurrency(@Query("access_key") accessKey: String): CurrencyResponse

}