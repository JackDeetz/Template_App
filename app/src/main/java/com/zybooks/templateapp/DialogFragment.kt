package com.zybooks.templateapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogFragment : DialogFragment() {
    interface DialogFragmentListener {
        fun dialogFragmentOkButtonPressed(dialog : DialogFragment)
        fun dialogFragmentNoButtonPressed(dialog : DialogFragment)
        fun dialogFragmentNeutralButtonPressed(dialog : DialogFragment)
    }

    lateinit var listener : DialogFragmentListener

    override fun onCreateDialog(savedInstanceState: Bundle?)
            : Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Alert Dialog Title")
        builder.setMessage("Alert Dialog Message")
        builder.setPositiveButton("Ok Button", DialogInterface.OnClickListener { dialog, id ->
            listener.dialogFragmentOkButtonPressed(this) })

        builder.setNegativeButton("No Button", DialogInterface.OnClickListener { dialog, id ->
            listener.dialogFragmentNoButtonPressed(this) })

        builder.setNeutralButton("Neutral Button", DialogInterface.OnClickListener { dialog, id ->
            listener.dialogFragmentNeutralButtonPressed(this) })

        return builder.create()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DialogFragmentListener
    }
}