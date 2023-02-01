package com.example.a7minworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import com.example.a7minworkoutapp.databinding.ActivityFinishBinding
import java.util.*

class FinishActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityFinishBinding? = null
    private var ttsf: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        ttsf = TextToSpeech(this,this)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBar)
        if(supportActionBar!=null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolBar?.title = "Finish"
        binding?.toolBar?.setNavigationOnClickListener {
            onBackPressed()
        }
        speakOut("Congrats! You've Completed all the exercises")
        binding?.finsihBtn?.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = ttsf!!.setLanguage(Locale.UK)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language specified is not supported")
            } else {
                Log.e("TTS","Initialization failed")
            }
        }
    }
    private fun speakOut(text:String){
        ttsf?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}