package com.zybooks.templateapp.database.repo

import androidx.lifecycle.LiveData
import android.content.Context
import androidx.room.Room
import com.zybooks.templateapp.database.model.Question
import com.zybooks.templateapp.database.model.Subject
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//repository is complete interface for database.
class ExampleRepository private constructor(context: Context) {


    fun getSubject(subjectId: Long): LiveData<Subject?> = subjectDao.getSubject(subjectId)

    fun getSubjects(): LiveData<List<Subject>> = subjectDao.getSubjects()

    fun getQuestion(questionId: Long): LiveData<Question?> = questionDao.getQuestion(questionId)

    fun getQuestions(subjectId: Long): LiveData<List<Question>> = questionDao.getQuestions(subjectId)

    companion object {  // companion object collection of 'static' objects/functions

        //static instance of Repository, if instantiated, returns instance, otherwise starts an instance and returns it
        private var instance: ExampleRepository? = null

        fun getInstance(context: Context): ExampleRepository {
            if (instance == null) {
                instance = ExampleRepository(context)
            }
            return instance!!
        }
    }
//The Room.Builder method addCallback() adds a RoomDatabase.Callback object.
// The callback's onCreate() method executes when the database is first created
// and is an ideal location to add starter data to the database.
    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //repo instance called to run on a background thread
            CoroutineScope(Dispatchers.IO).launch {
                instance?.addStarterData()
            }
        }
    }


    private val database : ExampleDatabase = Room.databaseBuilder(
        context.applicationContext,
        ExampleDatabase::class.java,
        "study5.db"
    )   //update database name for new database
        .allowMainThreadQueries()
        .addCallback(databaseCallback)
        .build()

    private val subjectDao = database.subjectDao()
    private val questionDao = database.questionDao()


    suspend fun addSubject(subject: Subject) {
        subject.id = subjectDao.addSubject(subject)
    }

    suspend fun addQuestion(question: Question) {
        question.sequential_unique_id = questionDao.addQuestion(question)
    }

    suspend fun deleteSubject(subject: Subject) = subjectDao.deleteSubject(subject)

    suspend fun updateQuestion(question: Question) = questionDao.updateQuestion(question)

    suspend fun deleteQuestion(question: Question) = questionDao.deleteQuestion(question)

    //assigns test starter data when databaseCallback is called (which is when a new database is created with no data)
    private suspend fun addStarterData() {
        var subjectId = subjectDao.addSubject(Subject(subject_title = "Math"))
        questionDao.addQuestion(
            Question(
                question_text = "What is 2 + 3?",
                answer_text = "2 + 3 = 5",
                which_subject_id = subjectId
            )
        )
        questionDao.addQuestion(
            Question(
                question_text = "What is pi?",
                answer_text = "The ratio of a circle's circumference to its diameter.",
                which_subject_id = subjectId
            )
        )

        subjectId = subjectDao.addSubject(Subject(subject_title = "History"))
        questionDao.addQuestion(
            Question(
                question_text = "On what date was the U.S. Declaration of Independence adopted?",
                answer_text = "July 4, 1776",
                which_subject_id = subjectId
            )
        )

        subjectDao.addSubject(Subject(subject_title = "Computing"))
    }
}