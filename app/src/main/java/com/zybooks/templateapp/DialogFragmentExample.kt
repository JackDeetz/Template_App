package com.zybooks.templateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class DialogFragmentExample : AppCompatActivity(), DialogFragment.DialogFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_fragment_example)
    }

    fun callDialogFragmentButtonClicked(view: View) {
        val dialog = DialogFragment()
        dialog.show(supportFragmentManager, "warningDialog")

    }

    override fun dialogFragmentOkButtonPressed(dialog: androidx.fragment.app.DialogFragment) {
        var textViewOutput: TextView = findViewById(R.id.textViewDialogFragmentOutput)
        textViewOutput.text = "User selected Ok"
    }

    override fun dialogFragmentNoButtonPressed(dialog: androidx.fragment.app.DialogFragment) {
        var textViewOutput: TextView = findViewById(R.id.textViewDialogFragmentOutput)
        textViewOutput.text = "User selected No"
    }

    override fun dialogFragmentNeutralButtonPressed(dialog: androidx.fragment.app.DialogFragment) {
        var textViewOutput: TextView = findViewById(R.id.textViewDialogFragmentOutput)
        textViewOutput.text = "User selected Neutral"
    }
}