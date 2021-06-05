package john.kingsley.currencyconverter.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import john.kingsley.currencyconverter.R
import john.kingsley.currencyconverter.data.Currency
import john.kingsley.currencyconverter.data.Rate
import john.kingsley.currencyconverter.databinding.ActivityMainBinding
import john.kingsley.currencyconverter.ui.main.adapter.RecyclerViewAdapter
import john.kingsley.currencyconverter.ui.main.adapter.SpinnerAdapter

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

        viewModel.ratesLiveData.observe(this, Observer {
            rates.clear()
            rates.addAll(it)
            rateAdapter?.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE
        })

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