package com.fithub.githubclient.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fithub.githubclient.R
import com.fithub.githubclient.infrastructure.ProjectRepository
import com.fithub.githubclient.model.Project
import kotlinx.coroutines.launch
import kotlin.Exception

class ProjectListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProjectRepository.instance

    // 監視対象のLiveData
    var projectListLiveData: MutableLiveData<List<Project>> = MutableLiveData()

    init {
        loadProjectList()
    }

    private fun loadProjectList() {
        // viewModelScope->ViewModel.onCleared() のタイミングでキャンセルされる CoroutineScope
        viewModelScope.launch {
            try {
                val userId = getApplication<Application>().getString(R.string.github_user_name)
                val request = repository.getProjectList(userId)
                if (request.isSuccessful) {
                    projectListLiveData.postValue(request.body())
                }
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}