package com.zybooks.templateapp.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity is a class annotated with @Entity that defines the columns and keys of a database table.
//A SQLite table is created for each entity class, and the entity's fields define the table columns.
//@Entity designates the entity class. The class name is used to name the table unless the optional tableName property specifies a different table name.
@Entity
data class Subject(  // subject(subject_title = "thisSubject") returns a Subject data object with an autogenerated id, and autogenerated updateTime

    //@PrimaryKey designates which field is the table's primary key.
    // An entity must have at least one field annotated with @PrimaryKey.
    // Typically the primary key is an integer or long field.
    // Setting the autoGenerate property to true makes SQLite automatically generate unique numbers for the primary key.
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    //@NonNull indicates the field should not be null. SQLite does not allow a primary key to be null.
    @NonNull
    var subject_title: String,

    //@ColumnInfo with the name property specifies a column name for a field. If @ColumnInfo is not present, the field's name is used to name the column.
    @ColumnInfo(name = "updated")
    var updateTime: Long = System.currentTimeMillis()) {
}