package com.zybooks.templateapp.database
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.zybooks.templateapp.R

class SubjectDialogFragment: DialogFragment() {

    interface OnSubjectEnteredListener {
        fun onSubjectEntered(subjectText: String)
    }

    private lateinit var listener: OnSubjectEnteredListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val subjectEditText = EditText(requireActivity())
        subjectEditText.inputType = InputType.TYPE_CLASS_TEXT
        subjectEditText.maxLines = 1
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.subject)
            .setView(subjectEditText)
            .setPositiveButton(R.string.create) { dialog, whichButton ->
                // Notify listener
                val subject = subjectEditText.text.toString()
                listener.onSubjectEntered(subject.trim())
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnSubjectEnteredListener
    }
}