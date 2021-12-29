package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //removing item from list
                listOfTasks.removeAt(position)
                //tells the adapter about the changes
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }


        loadItems()
        //Look up the recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //setting up the button and input field

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener{
            //takes the input text
            val inputtedTask = inputTextField.text.toString()

            //add to list
            listOfTasks.add(inputtedTask)

            //notify the adapter that item has been inserted

            adapter.notifyItemInserted(listOfTasks.size-1)

            //reset text field
            inputTextField.setText("")

            saveItems()
        }
    }
    //save the data the user inputs
    //By writing and reading from a a file

    //create a method to get the file
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    //Load the items by reading every line in our file
    fun loadItems(){
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    //save items by writing them into our data file
    fun saveItems(){
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks )
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
}