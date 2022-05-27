package com.zybooks.templateapp.database.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zybooks.templateapp.database.model.Question
import com.zybooks.templateapp.database.model.Subject

//@Database annotation designates the Room database class
//and uses the property entities to name the database's entities and version to name the database version number.
@Database(entities = [Question::class, Subject::class], version = 5) //update version number and change repository database file name when changing database
abstract class ExampleDatabase : RoomDatabase() {


    abstract fun questionDao(): QuestionDao
    abstract fun subjectDao(): SubjectDao
}