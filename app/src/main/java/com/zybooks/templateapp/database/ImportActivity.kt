package com.zybooks.templateapp.database

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import com.android.volley.VolleyError
import com.zybooks.templateapp.R
import com.zybooks.templateapp.database.StudyFetcher.OnStudyDataReceivedListener
import com.zybooks.templateapp.database.model.Question
import com.zybooks.templateapp.database.model.Subject
import com.zybooks.templateapp.database.viewmodel.QuestionDetailViewModel
import com.zybooks.templateapp.database.viewmodel.SubjectListViewModel

class ImportActivity : AppCompatActivity() {

    private lateinit var subjectLayoutContainer: LinearLayout
    private lateinit var studyFetcher: StudyFetcher
    private lateinit var loadingProgressBar: ProgressBar

    private val subjectListViewModel: SubjectListViewModel by lazy {
        ViewModelProvider(this).get(SubjectListViewModel::class.java)
    }

    private val questionDetailViewModel: QuestionDetailViewModel by lazy {
        ViewModelProvider(this).get(QuestionDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import)

        subjectLayoutContainer = findViewById(R.id.subject_layout)

        findViewById<Button>(R.id.import_button).setOnClickListener { importButtonClick() }

        // Show progress bar
        loadingProgressBar = findViewById(R.id.loading_progress_bar)
        loadingProgressBar.visibility = View.VISIBLE

        studyFetcher = StudyFetcher(this)
        studyFetcher.fetchSubjects(fetchListener)
    }

    private val fetchListener = object : OnStudyDataReceivedListener {
        override fun onSubjectsReceived(subjectList: List<Subject>) {

            // Hide progress bar
            loadingProgressBar.visibility = View.GONE

            // Create a checkbox for each subject
            for (subject in subjectList) {
                val checkBox = CheckBox(applicationContext)
                checkBox.textSize = 24f
                checkBox.text = subject.subject_title
                checkBox.tag = subject
                subjectLayoutContainer.addView(checkBox)
            }
        }

        override fun onQuestionsReceived(subject: Subject, questionList: List<Question>) {
            if (questionList.isNotEmpty()) {

                // Add the questions to the database
                for (question in questionList) {
                    question.which_subject_id = subject.id
                    questionDetailViewModel.addQuestion(question)
                }

                Toast.makeText(applicationContext, "${subject.subject_title} imported successfully",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "${subject.subject_title} contained no questions",
                    Toast.LENGTH_SHORT).show()
            }
        }

        override fun onErrorResponse(error: VolleyError) {
            Toast.makeText(applicationContext, "Error loading subjects. Try again later.",
                Toast.LENGTH_LONG).show()
            error.printStackTrace()
            loadingProgressBar.visibility = View.GONE
        }
    }

    private fun importButtonClick() {

        // Extract Subjects from selected checkbox tags
        for (child in subjectLayoutContainer.children) {
            val checkBox = child as CheckBox
            if (checkBox.isChecked) {
                val subject = checkBox.tag as Subject

                // Add subject, then fetch questions
                subjectListViewModel.addSubject(subject)
                studyFetcher.fetchQuestions(subject, fetchListener)
            }
        }
    }
}
