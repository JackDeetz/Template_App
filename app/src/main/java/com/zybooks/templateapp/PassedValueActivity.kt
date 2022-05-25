package com.zybooks.templateapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

private lateinit var passedValue : String
class PassedValueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passed_value)
        passedValue = intent.getStringExtra("Passed_Value")!!

        val showPassedValueTextField : TextView = findViewById(R.id.textView16)
        showPassedValueTextField.text = passedValue

    }

    fun backClicked(view: View) {
        var intent = Intent()
        var editText : EditText = findViewById(R.id.editText)
        var value = editText.text.toString()
        intent.putExtra("Passed_Value", value)
        setResult(RESULT_OK, intent)
        finish()
    }
    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}