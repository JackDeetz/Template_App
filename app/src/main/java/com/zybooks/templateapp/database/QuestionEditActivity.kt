package com.zybooks.templateapp.database

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zybooks.templateapp.database.model.Question
import androidx.lifecycle.ViewModelProvider
import com.zybooks.templateapp.R
import com.zybooks.templateapp.database.viewmodel.QuestionDetailViewModel

class QuestionEditActivity : AppCompatActivity() {

    private lateinit var questionEditText: EditText
    private lateinit var answerEditText: EditText
    private var questionId = 0L
    private lateinit var question: Question

    private val questionDetailViewModel: QuestionDetailViewModel by lazy {
        ViewModelProvider(this).get(QuestionDetailViewModel::class.java)
    }

    companion object {
        const val EXTRA_QUESTION_ID = "com.zybooks.studyhelper.question_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_edit)

        questionEditText = findViewById(R.id.question_edit_text)
        answerEditText = findViewById(R.id.answer_edit_text)

        findViewById<FloatingActionButton>(R.id.save_button).setOnClickListener { saveButtonClick() }

        // Get question ID from QuestionActivity
        questionId = intent.getLongExtra(EXTRA_QUESTION_ID, -1L)

        if (questionId == -1L) {
            // Add new question
            question = Question()
            question.which_subject_id = intent.getLongExtra(QuestionActivity.EXTRA_SUBJECT_ID, 0)

            setTitle(R.string.add_question)
        } else {
            // Display existing question from ViewModel
            questionDetailViewModel.loadQuestion(questionId)
            questionDetailViewModel.questionLiveData.observe(this,
                { question ->
                    this.question = question
                    updateUI()
                })
            setTitle(R.string.edit_question)
        }
    }

    private fun updateUI() {
        questionEditText.setText(question.question_text)
        answerEditText.setText(question.answer_text)
    }

    private fun saveButtonClick() {
        question.question_text = questionEditText.text.toString()
        question.answer_text = answerEditText.text.toString()

        // Save new or existing question
        if (questionId == -1L) {
            questionDetailViewModel.addQuestion(question)
        } else {
            questionDetailViewModel.updateQuestion(question)
        }

        // Send back OK result
        setResult(RESULT_OK)
        finish()
    }
}