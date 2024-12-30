package com.example.countdowntimer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var titleTextView : TextView
    private lateinit var timerTextView : TextView
    private lateinit var startButton : Button
    private lateinit var resetTextView : TextView
    private lateinit var progressBar: ProgressBar

    private var timer : CountDownTimer? = null
    private var isTimerRunning = false

    private val startTimeInMilliseconds : Long = 25 * 60 * 1000
    private var remainingTime = startTimeInMilliseconds
    private val remainingTimeKey = "Remaining Time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initializeViews()

        startButton.setOnClickListener {
            if(!isTimerRunning){
                startTimer(startTimeInMilliseconds)
                titleTextView.text = resources.getText(R.string.keep_going)
            }
        }

        resetTextView.setOnClickListener {
            resetTimer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(remainingTimeKey, remainingTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedTime= savedInstanceState.getLong(remainingTimeKey)
        if (savedTime != startTimeInMilliseconds){
            startTimer(savedTime)
        }

    }

    private fun resetTimer() {
        timer?.cancel()  // ?: law lehh qema a3mel cancel law maluch wa2af w mat3melch 7agga
        remainingTime = startTimeInMilliseconds
        updateTimerText()
        titleTextView.text = resources.getText(R.string.take_pomodoro)
        isTimerRunning = false
        progressBar.progress = 100
    }

    private fun startTimer(startTime : Long) {
        timer = object : CountDownTimer(startTime, 1000) {
            override fun onTick(timeLeft: Long) {
                remainingTime = timeLeft
                updateTimerText()
                progressBar.progress = ((remainingTime.toDouble() / startTimeInMilliseconds.toDouble()) * 100).toInt()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Finished!!", Toast.LENGTH_SHORT).show()
                isTimerRunning = false
            }

        }.start()
        isTimerRunning = true
    }

    private fun updateTimerText() {

        timerTextView.text = String.format(remainingTime.toString())
        val minutes = remainingTime / 1000 / 60
        val seconds = remainingTime / 1000 % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = formattedTime
    }

    private fun initializeViews() {
        titleTextView = findViewById(R.id.take_pomodoro_tv)
        timerTextView = findViewById(R.id.time_tv)
        startButton = findViewById(R.id.start_btn)
        resetTextView = findViewById(R.id.reset_tv)
        progressBar = findViewById(R.id.progress_circular)
    }
}