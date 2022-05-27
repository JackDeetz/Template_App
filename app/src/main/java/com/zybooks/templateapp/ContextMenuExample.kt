package com.zybooks.templateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

class ContextMenuExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_context_menu_example)

//        registerForContextMenu(diceImageViewList[0])


    }
    //Context Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu_example, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Determine which menu option was selected
        return when (item.itemId) {
            R.id.action_one -> {
                //context menu user selects action_one
                var textViewToAlter : TextView = findViewById(R.id.textViewContextMenuResults)
                textViewToAlter.text = "Option One has been selected"
                true
            }
            R.id.action_two -> {
                //vice versa

                var textViewToAlter : TextView = findViewById(R.id.textViewContextMenuResults)
                textViewToAlter.text = "Option Two has been selected"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}