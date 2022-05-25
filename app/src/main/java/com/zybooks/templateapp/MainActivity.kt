package com.zybooks.templateapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.zybooks.templateapp.recyclerview.RecyclerViewExample

private var KEY_TO_VALUE : String = "Key"
private var value : Int = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //load saved values from a paused activity
//        if (savedInstanceState != null) {
//            var value = savedInstanceState.getString(KEY_TO_VALUE)
//        }
    }
    //method called after onStart()
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            value = savedInstanceState.getInt(KEY_TO_VALUE)
        }
    }
    //called before destroying activity
    override fun onSaveInstanceState(outState: Bundle) {
        //save values to save state to be loaded in onCreate
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_TO_VALUE, value + 1)
        var textInput : EditText = findViewById(R.id.menuDataToPassToActivityEditText)
        var textValue = textInput.text.toString()
        outState.putString("textEntryKey", textValue)
    }

    override fun onStart() {
        super.onStart()
        //method is called after onCreate() and prepares the activity to be visible.
        //method is called upon return from onStop()
    }

    override fun onResume() {
        super.onResume()
        //method is called after onStart(), and the activity enters the foreground.
        var mATVSV : TextView = findViewById(R.id.menuActivityPersistentDataTextView)
        mATVSV.text = "Activity has been destroyed and created " + value.toString() + " times."
    }

    override fun onPause() {
        super.onPause()
        //method is called when the activity pauses but is still visible.
    }

    override fun onStop() {
        super.onStop()
        //method is called after onPause() when the activity is stopped and no longer visible.
    }


    override fun onDestroy() {
        super.onDestroy()
        //method is called after onStop() and immediately before the activity is destroyed.
    }

    //method called when layout examples activity button clicked
    //creates Intent, attaches LayoutExamples Activity
    //launches LayoutExmaples Activity ~~Pass into new New activity via Intent, No passing back~~
    fun layoutButtonClicked(view: View) {
        val intent = Intent(this, LayoutExamples::class.java)
        intent.putExtra("A_Value", "Testing")
        startActivity(intent)
    }
    //method called when Pass value to new activity button is pressed
    //creates Intent to package values to pass to new activity
    //launches "registerForActivityResult()" with the packedIntent
    fun passValueToActivityButtonClicked(view: View) {
        //Send the current color ID to ColorActivity
        val intent = Intent(this, PassedValueActivity::class.java)

        var textInputView : EditText = findViewById(R.id.menuDataToPassToActivityEditText)
        intent.putExtra("Passed_Value", textInputView.text.toString())
        PassedValueActivityLauncher.launch(intent)
    }
    //returned values are packaged in 'result'
    private val PassedValueActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Create the "on" button color based on the chosen color ID from ColorActivity
            var passedValue : String? = result.data!!.getStringExtra("Passed_Value")
            var showTextView : TextView = findViewById(R.id.menuReturnFromActivityDataOutputTextView)
            showTextView.text = passedValue

        }
    }

    fun fragmentButtonClicked(view: View) {
        startActivity(Intent(this, FragmentActivityWithFragments::class.java))
    }

    fun contextMenuButtonClicked(view: View) {
        startActivity(Intent(this, ContextMenuExample::class.java))}

    fun implicitIntentButtonClicked(view: View) {
        startActivity(Intent(this, ImplicitIntentExample::class.java))}

    fun appBarButtonClicked(view: View) {
        startActivity(Intent(this, AppBarExample::class.java))}

    fun touchEventsButtonClicked(view: View) {
        startActivity(Intent(this, TouchEventExample::class.java))}

    fun touchGesturesButtonClicked(view: View) {
        startActivity(Intent(this, TouchGesturesExample::class.java))}

    fun dialogFragmentButtonClicked(view: View) {
        startActivity(Intent(this, DialogFragmentExample::class.java))}

    fun savingDataButtonClicked(view: View) {
        startActivity(Intent(this, SavingDataExample::class.java))}

    fun databaseButtonClicked(view: View) {
    //    startActivity(Intent(this, DatabaseExample::class.java))
    }

    fun backgroundTasksButtonClicked(view: View) {
        startActivity(Intent(this, BackgroundTaskExample::class.java))}

    fun shapeCustomDrawablesButtonClicked(view: View) {
        startActivity(Intent(this, ShapeAndCustomDrawablesExample::class.java))}

    fun animationsButtonClicked(view: View) {
        startActivity(Intent(this, AnimationsExample::class.java))}

    fun soundsButtonClicked(view: View) {
        startActivity(Intent(this, SoundsExample::class.java))}

    fun surfaceViewButtonClicked(view: View) {
        startActivity(Intent(this, SurfaceViewExample::class.java))}

    fun motionSensorsButtonClicked(view: View) {
        startActivity(Intent(this, SensorsExample::class.java))}

    fun recyclerViewButtonClicked(view: View) {
        startActivity(Intent(this, RecyclerViewExample::class.java))
    }

}
