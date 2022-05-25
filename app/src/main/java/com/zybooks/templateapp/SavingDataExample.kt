package com.zybooks.templateapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.PrintWriter

class SavingDataExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saving_data_example)


    }

    fun saveToActivitiesXLMFileButtonClicked(view: View) {
        // Save values in activity's default XML file
        // saves to SavingDataExample.xml
        val defaultSharedPref = getPreferences(Context.MODE_PRIVATE)
        var editor = defaultSharedPref.edit()
        var keyInput : EditText = findViewById(R.id.editTextInputKeyToSave)
        var valueInput : EditText = findViewById(R.id.editTextInputValueToSave)
        editor.putString(keyInput.text.toString(), valueInput.text.toString())
        editor.apply()
    }

    fun saveTomyprefsXLMFileButtonClicked(view: View) {
        var keyInput : EditText = findViewById(R.id.editTextOtherInputKeyToSave)
        var valueInput : EditText = findViewById(R.id.editTextOtherInputValueToSave)
        // Save values in myprefs.xml
        val namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE)
        var editor = namedSharedPref.edit()
        editor.putString(keyInput.text.toString(), valueInput.text.toString())
        editor.apply()

    }

    fun readSavedDataFromActivityXMLFileButtonClicked(view: View) {

        //Read stored values in activity's default XML file
        //reads from SavingDataExample.XML
        val sharedPref = getPreferences(MODE_PRIVATE)
        var keyLookUp : EditText = findViewById(R.id.editTextKeyLookUp)
        var valueOutput : TextView = findViewById(R.id.textViewReadFromXMLOutput)
        valueOutput.text = sharedPref.getString(keyLookUp.text.toString(), "Key Not Found")
    }
    fun readSavedDataFrommyprefXMLFileButtonClicked(view: View) {

        //Read stored values in activity's default XML file
        //reads from myprefs.XML
        val namedSharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE)
        var keyLookUp : EditText = findViewById(R.id.editTextKeyLookUp)
        var valueOutput : TextView = findViewById(R.id.textViewReadFromXMLOutput)
        valueOutput.text = namedSharedPref.getString(keyLookUp.text.toString(), "Key Not Found")
    }


    //function saves data to an internal file
    fun saveDataToInternalStorageButtonClicked(view: View) {
        //function
        fun writeToInternalFile() {

            val fileName : EditText = findViewById(R.id.editTextInputFileNameInternalAndExternal)

            val outputStream = openFileOutput(fileName.text.toString(), Context.MODE_PRIVATE)
            val writer = PrintWriter(outputStream)

            // Write each task on a separate line

            val fileContents : EditText = findViewById(R.id.editTextInputValueInternalAndExternal)
            writer.println(fileContents.text.toString())
//            writer.println("Wash the car")
//            writer.println("Volunteer at the hospital")
            writer.close()
        }
        writeToInternalFile()
    }

    //function reads from Internal Storage
    fun readDataFromInternalStorageButtonClicked(view: View) {
        fun readFromInternalFile(): String {

            val fileName : EditText = findViewById(R.id.editTextInputFileNameInternalAndExternal)

            val inputStream = openFileInput(fileName.text.toString())
            val reader = inputStream.bufferedReader()
            val stringBuilder = StringBuilder()
            val lineSeparator = System.getProperty("line.separator")

            // Append each task to stringBuilder
            reader.forEachLine { stringBuilder.append(it).append(lineSeparator) }

            val output :TextView = findViewById(R.id.textViewReadFileOutput)
            output.text = stringBuilder.toString()

            return stringBuilder.toString()
        }
        readFromInternalFile()
    }

    //function saves data to external storage
    fun saveDataToExternalStorageButtonClicked(view: View) {

        fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }
        fun writeToExternalFile() {
            if (isExternalStorageWritable()) {
                val fileName : EditText = findViewById(R.id.editTextInputFileNameInternalAndExternal)
                val file = File(getExternalFilesDir(null), fileName.text.toString())
//                Log.d(TAG, "File path = ${file.absolutePath}")
                val outputStream = FileOutputStream(file)
                val writer = PrintWriter(outputStream)

                // Write each task on a separate line
                val fileContents : EditText = findViewById(R.id.editTextInputValueInternalAndExternal)
                writer.println(fileContents.text.toString())
//                writer.println("Tidy up apartment")
//                writer.println("Walk the dog")
                writer.close()
            }
        }

        writeToExternalFile()
    }
    //function reads from external storage
    fun readDataFromExternalStorageButtonClicked(view: View) {
        fun isExternalStorageReadable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
                    Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY
        }

        fun readFromExternalFile(): String {
            val stringBuilder = StringBuilder()

            if (isExternalStorageReadable()) {

                val fileName : EditText = findViewById(R.id.editTextInputFileNameInternalAndExternal)

                val file = File(getExternalFilesDir(null), fileName.text.toString())
                val inputStream = FileInputStream(file)
                val reader = inputStream.bufferedReader()
                val lineSeparator = System.getProperty("line.separator")

                // Append each task to stringBuilder
                reader.forEachLine { stringBuilder.append(it).append(lineSeparator) }
            }

            val output :TextView = findViewById(R.id.textViewReadFileOutput)
            output.text = stringBuilder.toString()

            return stringBuilder.toString()
        }
        readFromExternalFile()
    }
}