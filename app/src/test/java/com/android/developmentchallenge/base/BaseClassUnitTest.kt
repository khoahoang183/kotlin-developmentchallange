package com.android.developmentchallenge.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.android.developmentchallenge.base.viewmodel.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */
abstract class BaseClassUnitTest<T : ViewDataBinding, K : BaseViewModel>() {

    // Data Binding
    lateinit var dataBinding: T

    // View model
    lateinit var viewModel: K

    abstract fun initializeData()
}