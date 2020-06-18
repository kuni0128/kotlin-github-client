package com.fithub.githubclient.infrastructure

import com.fithub.githubclient.model.Project
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(HTTPS_API_GITHUB_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var githubService: GithubService = retrofit.create(GithubService::class.java)

    suspend fun getProjectList(userId: String): Response<List<Project>> =
        githubService.getProjectList(userId)

    suspend fun getProjectDetail(userId: String, projectName: String): Response<Project> =
        githubService.getProjectDetail(userId, projectName)

    companion object Factory {
        val instance: ProjectRepository
            @Synchronized get() = ProjectRepository()
    }
}