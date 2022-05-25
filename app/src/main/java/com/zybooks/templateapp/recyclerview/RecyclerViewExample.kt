package com.zybooks.templateapp.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zybooks.templateapp.R

class RecyclerViewExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_example)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerviewWidget)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager (this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            if (i % 2 == 0) {
                data.add(ItemsViewModel(R.drawable.arrow, "Item " + i))
            }
            else
            {
                data.add(ItemsViewModel(R.drawable.arrow_up, "Item " + i))
            }
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }
}