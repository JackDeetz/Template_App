package com.zybooks.templateapp.database.repo

import androidx.room.*
import com.zybooks.templateapp.database.model.Question
import androidx.lifecycle.LiveData

// @Dao annotation designates a DAO's public interface that defines methods
// to select, insert, update, and delete database entities.
// Room implements the interface automatically, writing all the code necessary to interact with SQLite.
@Dao
interface QuestionDao {
    //@Query designates a database query, usually a SELECT statement, to be executed.
    // The query can bind parameters from the abstract method.
    // Ex: The @Query for getSubject() has an :id parameter that matches the id parameter in getSubject().
    @Query("SELECT * FROM Question WHERE sequential_unique_id = :id")
    fun getQuestion(id: Long): LiveData<Question?>

    @Query("SELECT * FROM Question WHERE which_subject_id = :subjectId ORDER BY sequential_unique_id")
    fun getQuestions(subjectId: Long): LiveData<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestion(question: Question): Long

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)
}