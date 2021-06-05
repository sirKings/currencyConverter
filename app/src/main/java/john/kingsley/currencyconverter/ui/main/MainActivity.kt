package john.kingsley.currencyconverter.ui.main

import android.icu.util.TimeUnit
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import john.kingsley.currencyconverter.R
import john.kingsley.currencyconverter.data.Currency
import john.kingsley.currencyconverter.data.Rate
import john.kingsley.currencyconverter.data.util.FetchDataWorker
import john.kingsley.currencyconverter.databinding.ActivityMainBinding
import john.kingsley.currencyconverter.ui.main.adapter.RecyclerViewAdapter
import john.kingsley.currencyconverter.ui.main.adapter.SpinnerAdapter
import kotlinx.coroutines.Delay
import java.util.concurrent.Delayed

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var rateAdapter: RecyclerViewAdapter? = null
    private val rates = arrayListOf<Rate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        rateAdapter = RecyclerViewAdapter(rates, this)
        viewModel = MainViewModel(this)

        viewModel.fetchCurrencyList()

        binding.rates.layoutManager = LinearLayoutManager(this)
        binding.rates.adapter = rateAdapter

        //observe currency live data to populate spinner
        viewModel.currencyLiveData.observe(this, Observer {
            val adapter =
                SpinnerAdapter(
                    this,
                    it
                )
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter

            binding.spinner.onItemSelectedListener = this
        })

        //observer rates livedata to populate recyclerview
        viewModel.ratesLiveData.observe(this, Observer {
            rates.clear()
            rates.addAll(it)
            rateAdapter?.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
        })


        ///Work manager to fetch data every 30 mins
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
        val work = PeriodicWorkRequestBuilder<FetchDataWorker>(30, java.util.concurrent.TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        val workManager = WorkManager.getInstance(this.applicationContext)
        workManager.enqueue(work)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item = parent?.getItemAtPosition(position) as Currency
        if(binding.amountEditTxt.text.toString().isNotEmpty()){
            val amount = binding.amountEditTxt.text.toString().toDoubleOrNull()
            amount?.let {
                Log.e("Amount", it.toString())
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getRates(it,item.code)
            }
        }else{
            Toast.makeText(this,getString(R.string.enter_amount_warning), Toast.LENGTH_SHORT).show()
        }
    }
}