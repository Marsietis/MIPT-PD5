package com.example.mipt_pd5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.SimpleAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val dataLoader = DataLoader()
    private val currencyList = ArrayList<HashMap<String, String>>()
    private lateinit var currencyAdapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.Toolbar))

        val currencyListView = findViewById<ListView>(R.id.CurrencyListView)

        // Set an adapter to the ListView
        currencyAdapter = SimpleAdapter(
            this,
            currencyList,
            R.layout.list,
            arrayOf("currencyAndRate"),
            intArrayOf(R.id.currencyRateTextView)
        )
        currencyListView.adapter = currencyAdapter

        // Load data using DataLoader class
        CoroutineScope(Dispatchers.Main).launch {
            val loadedData = dataLoader.loadData()
            currencyList.clear()
            for (item in loadedData) {
                // Combine currency and rate into a single string
                val currency = item["currency"]
                val rate = item["rate"]
                item["currencyAndRate"] = "$currency — Rate: $rate"
                currencyList.add(item)
            }
            // Update the adapter with the new data
            currencyAdapter.notifyDataSetChanged()
        }
    }
}
