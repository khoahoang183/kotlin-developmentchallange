package com.android.developmentchallenge.base

import com.android.developmentchallenge.di.apiModule
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

open class BaseKoinTest : KoinTest {

    // compositeDisposable
    val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(apiModule)
    }

    // Before test function
    @Before
    fun beforeTestCallback() {
        startKoin {
            modules(
                apiModule
            )
        }
    }

    // After test function
    @After
    fun afterTestCallback() {
        stopKoin()
    }
}