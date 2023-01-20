package com.henra.perqaragame.core

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val currentUrl = chain.request().url().newBuilder()
            .addQueryParameter("key", "bb5fe39374644d668f299daf7635a117")
        val request = chain.request().newBuilder().url(currentUrl.build())
        return chain.proceed(request.build())
    }
}