package com.fithub.githubclient.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.fithub.githubclient.R
import com.fithub.githubclient.infrastructure.ProjectRepository
import com.fithub.githubclient.model.Project
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val myApplication: Application,
    private val mProjectId: String
) : AndroidViewModel(myApplication) {

    private val repository = ProjectRepository.instance
    val projectLiveData: MutableLiveData<Project> = MutableLiveData()
    var project = ObservableField<Project>()

    init {
        loadProject()
    }

    private fun loadProject() {
        viewModelScope.launch {
            try {
                val userId = getApplication<Application>().getString(R.string.github_user_name)
                val request = repository.getProjectDetail(userId, mProjectId)
                if (request.isSuccessful) {
                    projectLiveData.postValue(request.body())
                }
            } catch (e: Exception) {
                Log.e("loadProject:Failed", e.stackTrace.toString())
            }
        }
    }

    fun setProject(project: Project) {
        this.project.set(project)
    }

    // c.f. https://qiita.com/satorufujiwara/items/f42b176404287690f1d0
    class Factory(private val application: Application, private val projectId: String) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProjectViewModel(application, projectId) as T
        }
    }
}