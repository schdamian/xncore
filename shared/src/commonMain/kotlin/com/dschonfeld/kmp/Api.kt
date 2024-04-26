package com.dschonfeld.kmp

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Api {

    private var apiClient: ApiClient? = null

    fun getClient(): ApiClient {
        if (apiClient == null)
            apiClient = ApiClient()
        return apiClient!!
    }
}