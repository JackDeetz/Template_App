package com.zybooks.templateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LayoutExamples : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_examples)

        var passedValue = intent.getStringExtra("A_Value")
        var textfield : TextView = findViewById(R.id.textView20)
        textfield.text = passedValue
    }
}