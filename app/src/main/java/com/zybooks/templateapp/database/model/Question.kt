package com.zybooks.templateapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

//@ForeignKey annotation is used in an @Entity annotation to specify the table's foreign keys with the following properties:
//
//entity - The parent entity containing the primary key referenced by the foreign key.
//parentColumns - The parent entity's column(s) the foreign key references.
//childColumns - The child entity's column(s) that reference the parent entity's column(s)
//onDelete - The action to take when the parent entity is deleted from the database.
@Entity(foreignKeys = [
    ForeignKey(entity = Subject::class,
        parentColumns = ["id"],
        childColumns = ["which_subject_id"],
        onDelete = CASCADE)
])
data class Question(
    //@PrimaryKey designates which field is the table's primary key.
    // An entity must have at least one field annotated with @PrimaryKey.
    // Typically the primary key is an integer or long field.
    // Setting the autoGenerate property to true makes SQLite automatically generate unique numbers for the primary key.
    @PrimaryKey(autoGenerate = true)
    var sequential_unique_id: Long = 0, //appears as though first entry is 1, second is 2, if you delete two and add three ID remains 3
    //variable reference names become column labels in database
    var question_text: String = "",
    var answer_text: String = "",
    //@ColumnInfo with the name property specifies a column name for a field. If @ColumnInfo is not present, the field's name is used to name the column.
    @ColumnInfo(name = "which_subject_id")
    var which_subject_id: Long = 0) {
}