package com.example.a7minworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minworkoutapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private  var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setSupportActionBar(binding?.toolBarHistory)
        if(supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.toolBarHistory?.title = "HISTORY"
        binding?.toolBarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}