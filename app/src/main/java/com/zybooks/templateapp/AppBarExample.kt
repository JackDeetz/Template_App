package com.zybooks.templateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class AppBarExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_bar_example)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Determine which menu option was selected
        return when (item.itemId) {
            R.id.app_bar_action_one -> {
                var textViewVariable : TextView = findViewById(R.id.textViewAppBarResults)
                textViewVariable.text = "App Bar Menu Option One Selected"
                true
            }
            R.id.app_bar_action_two -> {
                var textViewVariable : TextView = findViewById(R.id.textViewAppBarResults)
                textViewVariable.text = "App Bar Menu Option Two Selected"
                true
            }
            R.id.app_bar_action_three -> {
                var textViewVariable : TextView = findViewById(R.id.textViewAppBarResults)
                textViewVariable.text = "App Bar Menu Option Three Selected"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}