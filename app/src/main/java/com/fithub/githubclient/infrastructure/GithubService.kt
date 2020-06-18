package com.fithub.githubclient.infrastructure

import com.fithub.githubclient.model.Project
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val HTTPS_API_GITHUB_URL = "https://api.github.com"

interface GithubService {

    // 一覧
    @GET("users/{userId}/repos")
    suspend fun getProjectList(@Path("userId") userId: String): Response<List<Project>>

    // 詳細
    @GET("/repos/{userId}/{reponame}")
    suspend fun getProjectDetail(@Path("userId") userId: String, @Path("reponame") projectName: String): Response<Project>
}