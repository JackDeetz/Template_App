package com.zybooks.templateapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.work.*
import kotlinx.coroutines.*
import java.text.NumberFormat
import java.util.*

class BackgroundTaskExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_task_example)
    }


    private fun fibonacci(n: Long): Long {
        return if (n <= 1) n else fibonacci(n - 1) + fibonacci(n - 2)
    }

    fun runBackgroundThreadButtonClicked(view: View) {

        val progressBar: ImageView = findViewById(R.id.imageView3)
        // Display progress bar
        progressBar.visibility = View.VISIBLE

        // Get number from UI
        val num = 30.toLong()

        // Clear previous result
        val resultTextView: TextView = findViewById(R.id.textView30)
        resultTextView.text = ""

        // Create a background thread
        val thread = Thread {

            // Find the Fibonacci number
            val fibNumber = fibonacci(num)

            // UI should only be updated by main thread
            runOnUiThread {
                resultTextView.text = "Result:" +
                        NumberFormat.getNumberInstance(Locale.US).format(fibNumber)

                // Hide progress bar
                progressBar.visibility = View.INVISIBLE
            }
        }

        thread.start()
    }

    fun coroutineMainThreadButtonClicked(view: View) {
        val scope = CoroutineScope(Dispatchers.Main)
        val resultTextView: TextView = findViewById(R.id.textView31)
        resultTextView.text = ""
        val progressBar: ImageView = findViewById(R.id.imageView3)
        scope.launch()
        {
            val fibNumber = fibonacci(30)
            resultTextView.text = "Result:" +
                    NumberFormat.getNumberInstance(Locale.US).format(fibNumber)

            progressBar.visibility = View.INVISIBLE
        }
        progressBar.visibility = View.VISIBLE

    }

    fun coroutineDefaultThreadButtonClicked(view: View) {
        val scope = CoroutineScope(Dispatchers.Main)
        val progressBar: ImageView = findViewById(R.id.imageView3)
        progressBar.visibility = View.VISIBLE
        val resultTextView: TextView = findViewById(R.id.textView31)
        resultTextView.text = ""
        scope.launch()
        {
            scope.launch(Dispatchers.Default)
            {
                val fibNumber = fibonacci(30)
                runOnUiThread {
                    resultTextView.text = "Result:" +
                            NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    fun coroutineIOThreadButtonClicked(view: View) {

        val resultBelowTextView : TextView = findViewById(R.id.textView32)
        resultBelowTextView.text = ""
        val resultTextView: TextView = findViewById(R.id.textView31)
        resultTextView.text = ""
        val resultTextViewBottom: TextView = findViewById(R.id.textView41)
        resultTextViewBottom.text = ""
        val progressBar: ImageView = findViewById(R.id.imageView3)
        progressBar.visibility = View.VISIBLE
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch()
        {
            scope.launch(Dispatchers.IO)
            {
                runOnUiThread {
                    resultTextView.text = "IO thread working"
                }
                val fibNumber = fibonacci(40)
                runOnUiThread {
                    resultTextView.text = "Result:" +
                                         NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
                    progressBar.visibility = View.INVISIBLE
                }
            }
            runOnUiThread {
                resultTextViewBottom.text = "Background Thread Begins"
            }
                val result = displaySuspendedMessageWithContext() //runs on a background thread
                runOnUiThread {
                    resultTextViewBottom.text =
                        "Example of a suspended method\non a background thread\nbeing called from a coroutine scope\n" + result.toString()
                }
                displaySuspendedMessage() //runs on the main thread
            }
        }


    suspend fun displaySuspendedMessage() {

        val resultBelowTextView : TextView = findViewById(R.id.textView32)
        resultBelowTextView.text = "MAIN thread suspended function begins delay of 5000L"

        delay(5000L)
        resultBelowTextView.text =
            "Example of a suspended method\non the main thread\nbeing called from a coroutine scope"
    }

    suspend fun displaySuspendedMessageWithContext(): Long = withContext(Dispatchers.Default) {
        return@withContext fibonacci(40)

    }


    //Example of creating jobs that can be cancellable
    var fibonacciJob: Job? = null

    fun coroutineJobButtonClicked(view: View) {
        val progressBar: ImageView = findViewById(R.id.imageView3)
        val resultTextView: TextView = findViewById(R.id.textView31)
        progressBar.visibility = View.VISIBLE
        val num = 40.toLong()
        resultTextView.text = ""

        fibonacciJob = CoroutineScope(Dispatchers.Main).launch {
            val fibNumber = fibonacciSuspend(num)

            resultTextView.text = "Result: " +
                    NumberFormat.getNumberInstance(Locale.US).format(fibNumber)

            progressBar.visibility = View.INVISIBLE
        }
    }

    suspend fun fibonacciSuspend(n: Long): Long =
        withContext(Dispatchers.Default) {
            return@withContext fibonacci(n)
        }

    fun cancelClick(view: View) {
        val progressBar: ImageView = findViewById(R.id.imageView3)
        if (fibonacciJob?.isActive == true) {
            fibonacciJob?.cancel()
            progressBar.visibility = View.INVISIBLE
        }
    }


    //Clock class to run on background coroutine
    class TimerModel {
        private var targetTime: Long = 0
        private var timeLeft: Long = 0
        private var running = false
        private var durationMillis: Long = 0

        val isRunning: Boolean
            get() {
                return running
            }

        fun start(millisLeft: Long) {
            durationMillis = millisLeft
            targetTime = SystemClock.uptimeMillis() + durationMillis
            running = true
        }

        fun start(hours: Int, minutes: Int, seconds: Int) {
            // Add 1 sec to duration so timer stays on current second longer
            durationMillis = ((hours * 60 * 60 + minutes * 60 + seconds + 1) * 1000).toLong()
            targetTime = SystemClock.uptimeMillis() + durationMillis
            running = true
        }

        fun stop() {
            running = false
        }

        fun pause() {
            timeLeft = targetTime - SystemClock.uptimeMillis()
            running = false
        }

        fun resume() {
            targetTime = SystemClock.uptimeMillis() + timeLeft
            running = true
        }

        val remainingMilliseconds: Long
            get() {
                return if (running) {
                    0L.coerceAtLeast(targetTime - SystemClock.uptimeMillis())
                } else 0
            }
        val remainingSeconds: Int
            get() {
                return if (running) {
                    (remainingMilliseconds / 1000 % 60).toInt()
                } else 0
            }
        val progressPercent: Int
            get() {
                return if (durationMillis != 1000L) {
                    100.coerceAtMost(
                        100 - ((remainingMilliseconds - 1000) * 100 /
                                (durationMillis - 1000)).toInt()
                    )
                } else 0
            }

        override fun toString(): String {
            return String.format(Locale.getDefault(), ":%02d", remainingSeconds)
        }
    }

    //start timer on Main coroutine
    val timerModel = TimerModel()
    var timerJob: Job? = null
    fun startTimerButtonClicked(view: View) {
        var button: Button = findViewById(R.id.button27)
        var timerOutput: TextView = findViewById(R.id.timerOuput)
        // Start the model
        var hours = 0
        var minutes = 0
        var seconds = 10

        if (timerModel.isRunning) {
            timerModel.pause()
            timerJob?.cancel()
            timerOutput.text = timerOutput.text.toString() + "!!Paused!!"
            button.text = "resume"
        } else if (button.text.toString() == "resume") {
            button.text = "start 10 second timer"
            timerModel.resume()
            timerJob = CoroutineScope(Dispatchers.Main).launch {
                updateTimer()
            }
        } else {
            button.text = "Pause"
            timerModel.start(hours, minutes, seconds)
            // Run coroutine on main thread
            timerJob = CoroutineScope(Dispatchers.Main).launch {
                updateTimer()
            }
        }
    }

    //runs on a coroutine!
    private suspend fun updateTimer() {
        var timeLeftTextView: TextView = findViewById(R.id.timerOuput)
        while (timerModel.progressPercent < 100) {

            // Show remaining time and progress
            timeLeftTextView.text = timerModel.toString()
//            timeRemainingProgressBar.progress = timerModel.progressPercent

            if (timerModel.progressPercent < 100) {
                delay(100)
            }
        }
        timerCompleted()
    }

    private fun timerCompleted() {
        timerModel.stop()
        var timeLeftTextView: TextView = findViewById(R.id.timerOuput)
        timeLeftTextView.text = "Done"
        var button: Button = findViewById(R.id.button27)
        button.text = "start 10 second timer"
    }

    //override onStop for activity to start a notification on the system notification
    override fun onStop() {
        super.onStop()

        // Start the Worker if the timer is running
        if (timerModel.isRunning) {
            val timerWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<TimerWorker>()
                .setInputData(workDataOf(
                    "com.zybooks.templateapp.MILLIS_LEFT" to timerModel.remainingMilliseconds
                )).build()

            WorkManager.getInstance(applicationContext).enqueue(timerWorkRequest)
        }
    }
//creates a notification via a system service with 10 second timer information
    class TimerWorker(context: Context, parameters: WorkerParameters) :
        Worker(context, parameters) {
        val CHANNEL_ID_TIMER = "channel_timer"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        override fun doWork(): Result {

            // Get remaining milliseconds from MainActivity
            val remainingMillis = inputData.getLong("com.zybooks.templateapp.MILLIS_LEFT", 0)

            // Can't continue without remaining time
            if (remainingMillis == 0L) {
                return Result.failure()
            }

            // Create notification channel for all notifications
            createTimerNotificationChannel()

            // Start a new TimerModel
            val timerModel = TimerModel()
            timerModel.start(remainingMillis)

            while (timerModel.isRunning) {

                // New notification shows remaining time
                createTimerNotification(("Notification message : Time left Bitches!!! " + timerModel.toString()))

                // Wait one second
                Thread.sleep(1000)

                if (timerModel.remainingMilliseconds == 0L) {
                    timerModel.stop()

                    // Last notification
                    createTimerNotification("Notification message : timer is finished")
                }
            }
            return Result.success()
        }
        private fun createTimerNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = applicationContext.getString(R.string.channel_name)
                val description = applicationContext.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_LOW
                val channel = NotificationChannel(CHANNEL_ID_TIMER, name, importance)
                channel.description = description

                // Register channel with system
                notificationManager.createNotificationChannel(channel)
            }
        }

        fun createTimerNotification(text: String) {
            // Create notification with various properties
            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID_TIMER)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(applicationContext.getString(R.string.app_name))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()

            // Post notification
            notificationManager.notify(0, notification)
        }
    }
}
