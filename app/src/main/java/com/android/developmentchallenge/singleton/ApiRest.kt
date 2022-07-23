package com.android.developmentchallenge.singleton

import androidx.databinding.library.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

object ApiRest {
    private var baseURL: String = ""
    private var additionalHeaders: HashMap<String, String> = HashMap()

    /**
     * set default base url for api requests
     *
     * @param baseURL String
     * @return ApiRestConfig
     */
    fun updateAPIBaseURL(baseURL: String): ApiRest {
        this@ApiRest.baseURL = baseURL
        return this@ApiRest
    }

    /**
     * add additional headers for each requests
     *
     * @param additionalHeaders Map<String, String>
     * @return ApiRestConfig
     */
    fun updateAdditionalHeaders(additionalHeaders: Map<String, String?>): ApiRest {
        additionalHeaders.forEach { headerItem ->
            val headerValue = headerItem.value
            if (headerValue.isNullOrBlank()) {
                this@ApiRest.additionalHeaders.remove(headerItem.key)
            } else {
                this@ApiRest.additionalHeaders[headerItem.key] = headerValue
            }
        }
        return this@ApiRest
    }

    /**
     * create new service interface with default configs ([ApiRest.config])
     *
     * @param apiService Class<T>
     * @return T
     */
    operator fun <T> get(apiService: Class<T>): T {
        return this@ApiRest.getRetrofit().create(apiService)
    }

    /**
     * Create new retrofit with default configs ([ApiRest.config])
     * because config may be change in runtime (example user's access token will update after user login),
     * so need to create each retrofit for each requests is better than using instance
     *
     * @return Retrofit
     */
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(this@ApiRest.baseURL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Handle response bad request with message body
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient(createInterceptor()))
            .build()
    }

    /**
     * Create okhttp client for retrofit
     *
     * @param requestInterceptor is Interceptor
     * @return new OkHttpClient
     */
    private fun createOkHttpClient(requestInterceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(log())
        return builder.build()
    }

    /**
     * Create Interceptor for create okhttp on next step
     *
     * @return
     */
    private fun createInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()
            .method(original.method, original.body)

        additionalHeaders.forEach {
            builder.addHeader(it.key, it.value)
        }

        val newRequest = builder.build()
        return@Interceptor chain.proceed(newRequest)
    }

    /**
     * Config log debug for using by mode build
     *
     * @return :Interceptor
     */
    private fun log(): Interceptor {
        val logging = HttpLoggingInterceptor()
        val level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        logging.setLevel(level)

        return logging
    }
}