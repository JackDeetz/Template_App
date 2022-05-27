package com.zybooks.templateapp.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.zybooks.templateapp.database.model.Subject
import com.zybooks.templateapp.database.repo.ExampleRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SubjectListViewModel(application: Application) : AndroidViewModel(application) {

    private val studyRepo = ExampleRepository.getInstance(application.applicationContext)

    val subjectListLiveData: LiveData<List<Subject>> = studyRepo.getSubjects()

    fun addSubject(subject: Subject) = viewModelScope.launch {
        studyRepo.addSubject(subject)
    }

    fun deleteSubject(subject: Subject) = viewModelScope.launch {
        studyRepo.deleteSubject(subject)
    }
}