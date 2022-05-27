package com.zybooks.templateapp.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zybooks.templateapp.database.model.Question
import com.zybooks.templateapp.database.repo.ExampleRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class QuestionDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val studyRepo = ExampleRepository.getInstance(application)

    private val questionIdLiveData = MutableLiveData<Long>()

    val questionLiveData: LiveData<Question> =
        Transformations.switchMap(questionIdLiveData) { questionId ->
            studyRepo.getQuestion(questionId)
        }

    fun loadQuestion(questionId: Long) {
        questionIdLiveData.value = questionId
    }

    fun addQuestion(question: Question) = viewModelScope.launch {
        studyRepo.addQuestion(question)
    }

    fun updateQuestion(question: Question) = viewModelScope.launch {
        studyRepo.updateQuestion(question)
    }
}