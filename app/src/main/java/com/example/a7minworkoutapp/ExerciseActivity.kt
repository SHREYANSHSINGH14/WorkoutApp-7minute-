package com.example.a7minworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkoutapp.databinding.ActivityExcersieBinding
import com.example.a7minworkoutapp.databinding.BackDialogboxBinding
import java.net.URI
import java.util.*
import java.util.logging.Level.parse
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExcersieBinding? = null
    private var countDownTimerRest: CountDownTimer? = null
    private var timerDurationRest: Long = 2000
    private var pauseOffsetRest: Long = 0

    private var countDownTimerExercise: CountDownTimer? = null
    private var timerDurationExercise: Long = 2000
    private var pauseOffsetExercise: Long = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var exerciseNum = -1
    private var size: Int? = null

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exerciseList = Constants.defaultExerciseList()
        size = exerciseList!!.size
        binding = ActivityExcersieBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBar)
        tts = TextToSpeech(this,this)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolBar?.title = "7 Min Workout App"
        binding?.toolBar?.setNavigationOnClickListener {
            customDialogBox()
        }
        startRest()
        setUpRecycleView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(countDownTimerExercise!= null){
            countDownTimerExercise!!.cancel()
        }
        if(countDownTimerRest!= null){
            countDownTimerRest!!.cancel()
        }
    }
    private fun setUpRecycleView() {
        binding?.tvRecycleView?.adapter = ExerciseAdapter(exerciseList!!)
        binding?.tvRecycleView?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun startRest() {
        binding?.tvImageView?.visibility = View.GONE
        binding?.flprogressbar?.visibility = View.VISIBLE
        binding?.getReadyText?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        if(exerciseNum!= (exerciseList?.size)?.minus(2)){
            binding?.tvExerciseName?.text = exerciseList?.get(exerciseNum+1)?.getName()
        }else{
            countDownTimerRest!!.cancel()
        }
        binding?.tvTextView?.text = (timerDurationRest / 1000).toString()
        binding?.tvProgressBar?.max = (timerDurationRest / 1000).toInt()
        binding?.tvProgressBarBg?.max = (timerDurationRest / 1000).toInt()
        binding?.tvProgressBarBg?.progress = (timerDurationRest / 1000).toInt()
        startFuncRest(pauseOffsetRest)
    }

    private fun startExercise() {
        Log.i("Exercise Number","$exerciseNum")
        binding?.tvImageView?.visibility = View.VISIBLE
        binding?.tvExerciseNameExercise?.visibility = View.VISIBLE
        var exerciseName = exerciseList?.get(exerciseNum)?.getName()
        binding?.tvExerciseNameExercise?.text = exerciseName
        if (exerciseName != null) {
            speakOut(exerciseName)
        }
        exerciseList?.get(exerciseNum)?.getImg()?.let{
            binding?.tvImageView?.setImageResource(it)
        }
        exerciseList?.get(exerciseNum)?.setIsSelected(true)
        isSelected()
        binding?.flprogressbarExercise?.visibility = View.VISIBLE
        binding?.tvTextViewExercise?.text = (timerDurationExercise / 1000).toString()
        binding?.tvProgressBarExercise?.max = (timerDurationExercise / 1000).toInt()
        binding?.tvProgressBarBgExercise?.max = (timerDurationExercise / 1000).toInt()
        binding?.tvProgressBarBgExercise?.progress = (timerDurationExercise / 1000).toInt()
        startFuncExercise(pauseOffsetExercise)
    }

    private fun startFuncRest(pauseOffset: Long){
        try {
            val soundURI = Uri.parse("android.resource://com.example.a7minworkoutapp/"+R.raw.press_start)
            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e:Exception){
            e.printStackTrace()
        }
        countDownTimerRest = object : CountDownTimer(timerDurationRest-pauseOffset,1000){
            override fun onTick(millisUntilFinished: Long) {
                this@ExerciseActivity.pauseOffsetRest = timerDurationRest-millisUntilFinished
                binding?.tvProgressBar?.progress = ((millisUntilFinished/1000).toInt())
                binding?.tvTextView?.text = "${(millisUntilFinished/1000)}"
            }

            override fun onFinish() {
//                Toast.makeText(this@ExcersieActivity,"Time's Up",Toast.LENGTH_SHORT).show()
                binding?.flprogressbar?.visibility = View.GONE
                binding?.getReadyText?.visibility = View.GONE
                binding?.tvExerciseName?.visibility = View.GONE
                exerciseNum++
                startExercise()
                resetFuncRest()
            }
        }.start()
    }

    private fun startFuncExercise(pauseOffset: Long){
        countDownTimerExercise = object : CountDownTimer(timerDurationExercise-pauseOffset,1000){
            override fun onTick(millisUntilFinished: Long) {
                this@ExerciseActivity.pauseOffsetExercise = timerDurationExercise-millisUntilFinished
                binding?.tvProgressBarExercise?.progress = ((millisUntilFinished/1000).toInt())
                binding?.tvTextViewExercise?.text = "${(millisUntilFinished/1000)}"
            }

            override fun onFinish() {
//                Toast.makeText(this@ExcersieActivity,"Time's Up",Toast.LENGTH_SHORT).show()
                resetFuncExercise()
                exerciseList?.get(exerciseNum)?.setIsSelected(false)
                isSelected()
                exerciseList?.get(exerciseNum)?.setIsCompleted(true)
                isCompleted()
                Log.i("Exercisesize","${exerciseList!!.size-1}")
                if(exerciseList?.get(exerciseNum)?.getId() == exerciseList?.size){
                    countDownTimerExercise!!.cancel()
                    onFinishActivity()
                }else{
                    binding?.flprogressbarExercise?.visibility = View.GONE
                    binding?.tvImageView?.visibility = View.GONE
                    binding?.tvExerciseNameExercise?.visibility = View.GONE
                    startRest()
                }
            }
        }.start()
    }

    private fun resetFuncRest(){
        timerDurationRest = 5000
        pauseOffsetRest = 0
        if(countDownTimerRest!= null){
            countDownTimerRest!!.cancel()
        }
        binding?.tvTextView?.text = (timerDurationRest/1000).toString()
        binding?.tvProgressBar?.progress = 5
    }

    private fun resetFuncExercise(){
        timerDurationExercise = 5000
        pauseOffsetExercise = 0
        if(countDownTimerExercise!= null){
            countDownTimerExercise!!.cancel()
        }
        binding?.tvTextViewExercise?.text = (timerDurationRest/1000).toString()
        binding?.tvProgressBarExercise?.progress = 5
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.UK)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language specified is not supported")
            } else {
                Log.e("TTS","Initialization failed")
            }
        }
    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun isSelected(){
        if(exerciseList?.get(exerciseNum)?.getIsSelected() == true){
            binding?.tvRecycleView?.get(exerciseNum)?.background =
                AppCompatResources.getDrawable(this,R.drawable.recycleitem_bg_selected)
        }else{
            binding?.tvRecycleView?.get(exerciseNum)?.background =
                AppCompatResources.getDrawable(this,R.drawable.recycleitem_bg)
        }
    }

    private fun isCompleted(){
        if(exerciseList?.get(exerciseNum)?.getIsCompleted() == true){
            binding?.tvRecycleView?.get(exerciseNum)?.background = AppCompatResources.getDrawable(this,R.drawable.recycleitem_bg_completed)
        }
    }

    private fun onFinishActivity(){
        val intent = Intent(this,FinishActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun customDialogBox(){
        if(binding!!.tvProgressBar.visibility == View.VISIBLE){
            if(isVisibleRest()){
                Log.e("Dialog","Stopped Rest")
                countDownTimerRest!!.cancel()
            }
        }
        if(isVisibleExercise()){
            if(countDownTimerExercise!= null){
                Log.e("Dialog","Stopped Exercise")
                countDownTimerExercise!!.cancel()
            }
        }
        val dialog = Dialog(this)
        val dialogboxBinding = BackDialogboxBinding.inflate(layoutInflater)
        dialog.setContentView(dialogboxBinding.root)
        dialogboxBinding.stayBtn.setOnClickListener{
            if(isVisibleRest()){
                Log.e("Dialog","Started rest")
                startFuncRest(pauseOffsetRest)
            }
            if(isVisibleExercise()){
                Log.e("Dialog","started Exercise")
                startFuncExercise(pauseOffsetExercise)
            }
            dialog.dismiss()
        }
        dialogboxBinding.leaveBtn.setOnClickListener{
            this@ExerciseActivity.finish()
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun isVisibleRest(): Boolean{
        Log.e("Dialog","Inside isVisibleRest")
        return binding!!.flprogressbar.isVisible
    }
    private fun isVisibleExercise(): Boolean{
        Log.e("Dialog","Inside isVisibleExercise")
        return binding!!.flprogressbarExercise.isVisible
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.toolBar?.title = ""
        if(tts != null){
            tts?.stop()
        }
        if(player != null){
            player!!.stop()
        }
        binding = null
    }
}