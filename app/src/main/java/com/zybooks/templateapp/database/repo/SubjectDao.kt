package com.zybooks.templateapp.database.repo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zybooks.templateapp.database.model.Subject

@Dao
interface SubjectDao {
    //
    //@Query designates a database query, usually a SELECT statement, to be executed.
    // The query can bind parameters from the abstract method.
    // Ex: The @Query for getSubject() has an :id parameter that matches the id parameter in getSubject().
    @Query("SELECT * FROM Subject WHERE id = :id")
    fun getSubject(id: Long): LiveData<Subject?>

    @Query("SELECT * FROM Subject ORDER BY subject_title COLLATE NOCASE")
    fun getSubjects(): LiveData<List<Subject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubject(subject: Subject): Long

    @Update
    suspend fun updateSubject(subject: Subject)

    @Delete
    suspend fun deleteSubject(subject: Subject)
}