package john.kingsley.currencyconverter.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import john.kingsley.currencyconverter.data.Currency
import john.kingsley.currencyconverter.data.Rate
import john.kingsley.currencyconverter.data.repository.CurrencyRepository
import john.kingsley.currencyconverter.data.util.handleError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(context: Context): ViewModel(){

   private val repo = CurrencyRepository(context)
    val ratesLiveData = MutableLiveData<List<Rate>>()
    val currencyLiveData = MutableLiveData<List<Currency>>()

    fun getCurrencyDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrency()
                .handleError {
                    //handle error
                }.collect {
                        //success
                    }
        }
    }

    fun getQuotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getQuotes().handleError {
                //handle error
            }.collect {
                //success
            }
        }
    }

    fun getRates(amount: Double, currency: String){

        viewModelScope.launch(Dispatchers.IO) {

            val it = repo.getRates(amount,currency)
            if(it.isNotEmpty()){
                ratesLiveData.postValue(it)
            }else{
                getQuotes()
                getCurrencyDetails()
            }

        }
    }

    fun fetchCurrencyList(){
        viewModelScope.launch(Dispatchers.IO) {
            val it = repo.fetchCurrencyListLocal()
                if(it.isNotEmpty()){
                    currencyLiveData.postValue(it)
                }else{
                    getQuotes()
                    getCurrencyDetails()
                }
        }

    }
}