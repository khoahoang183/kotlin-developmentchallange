package com.android.developmentchallenge.view.home

import android.app.Activity
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.developmentchallenge.R
import com.android.developmentchallenge.base.view.BaseActivity
import com.android.developmentchallenge.const.EnumVoiceRecognizeResult
import com.android.developmentchallenge.const.LOG_EXCEPTION_PATTERN
import com.android.developmentchallenge.databinding.ActivityHomeBinding
import com.android.developmentchallenge.model.DailyForecastModel
import com.android.developmentchallenge.model.ForecastModel
import com.android.developmentchallenge.util.adapter.home.AdapterHomeForecast
import com.android.developmentchallenge.util.binding.bindingIsVisibleNoDataText
import com.android.developmentchallenge.util.ext.hideSoftKeyboard
import com.android.developmentchallenge.util.ext.setupSwipeRefresh
import com.android.developmentchallenge.viewmodel.home.HomeActivityViewModel
import timber.log.Timber
import java.util.*

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

class HomeActivity :
    BaseActivity<ActivityHomeBinding, HomeActivityViewModel>(R.layout.activity_home) {

    // init Engine TextToSpeech
    private val engineTextToSpeech: TextToSpeech by lazy {
        TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) engineTextToSpeech.language =
                Locale(Locale.getDefault().language)
        }
    }

    // init registerActivityResult
    private val registerActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val spokenText: String? =
                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    .let { text -> text?.get(0) }
            spokenText?.let {
                handleResultRecognizeString(it)
            }
        }
    }

    /**
     * Override initCreateDone
     *
     */
    override fun initCreateDone() {
        // Set current viewModel to dataBinding
        this@HomeActivity.dataBinding.viewModel = this@HomeActivity.viewModel

        configViewEvents()

        configViewModel()
    }

    /**
     * Handle events data of view
     */
    private fun configViewEvents() {
        try {
            // Event item click on list forecast
            this.viewModel.adapterHomeForecast.listener =
                object : AdapterHomeForecast.OnItemClickListener {
                    override fun onClick(model: ForecastModel) {
                        // Implement later based on brief
                    }
                }

            // Config SwipeRefresh
            this.dataBinding.refreshSwipe.setupSwipeRefresh()
            this.dataBinding.refreshSwipe.setOnRefreshListener {
                this.dataBinding.searchEdt.setText(this.viewModel.lastStateSearch)
                this.dataBinding.searchEdt.setSelection(this.viewModel.lastStateSearch.length)
                this.dataBinding.searchButton.performClick()
            }

            // Event click on searchButton
            this.dataBinding.searchButton.setOnClickListener {
                this.hideSoftKeyboard()
                this.dataBinding.searchEdt.text.toString().let { edtTextString ->
                    when {
                        edtTextString.isEmpty() -> {
                            this.viewModel.lastStateSearch = ""
                            this.viewModel.dailyForecastModel.value = DailyForecastModel()
                        }
                        edtTextString.length < 3 -> {
                            this.viewModel.toastMessCallback.postValue(getString(R.string.toast_limit_char_search))
                            if (this.dataBinding.refreshSwipe.isRefreshing)
                                this.dataBinding.refreshSwipe.isRefreshing = false
                        }
                        else -> {
                            this.viewModel.lastStateSearch = edtTextString
                            checkInternetAndStartCallApi {
                                // Call API getDailyForecast -> callback observe to dailyForecastModel
                                this.viewModel.getDailyForecast(edtTextString)
                            }
                        }
                    }
                }
            }

            // Event click on speechButton
            this.dataBinding.speechButton.setOnClickListener {
                this.viewModel.displayVoiceRecognizer()
            }

            // config searchEdt
            this.dataBinding.searchEdt.imeOptions = EditorInfo.IME_ACTION_DONE
            this.dataBinding.searchEdt.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    this.dataBinding.searchButton.performClick()
                    return@setOnEditorActionListener true
                }
                false
            }
        } catch (e: Exception) {
            Timber.e(
                LOG_EXCEPTION_PATTERN.format(
                    this::class.simpleName,
                    ::configViewEvents.name,
                    e.message
                )
            )
        }
    }

    /**
     * Handle config value of view model
     */
    private fun configViewModel() {
        try {
            // Init VoiceRecognizer
            this.viewModel.initializeVoiceRecognizer(engineTextToSpeech, registerActivityResult)

            // Observe value dailyForecastModel
            this.viewModel.dailyForecastModel.observe(this) {
                if (this.dataBinding.refreshSwipe.isRefreshing)
                    this.dataBinding.refreshSwipe.isRefreshing = false

                it?.list?.let { listItem ->
                    this.dataBinding.noDataTv.bindingIsVisibleNoDataText(listItem.size)
                    this.viewModel.adapterHomeForecast.updateNewItems(listItem)
                } ?: kotlin.run {
                    this.dataBinding.noDataTv.bindingIsVisibleNoDataText(0)
                    this.viewModel.adapterHomeForecast.updateNewItems(arrayListOf())
                }
            }

            // Observe toastMessCallback
            this.viewModel.toastMessCallback.observe(this) {
                it?.let {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Timber.e(
                LOG_EXCEPTION_PATTERN.format(
                    this::class.simpleName,
                    ::configViewModel.name,
                    e.message
                )
            )
        }
    }

    /**
     * handle Result Recognize String
     *
     * @param spokenText: Result of recognize
     */
    private fun handleResultRecognizeString(spokenText: String) {
        when (spokenText.lowercase()) {
            EnumVoiceRecognizeResult.RESULT_CLEAR.value -> {
                this.dataBinding.searchEdt.setText("")
            }
            EnumVoiceRecognizeResult.RESULT_REFRESH.value -> {
                this.dataBinding.searchEdt.setText(this.viewModel.lastStateSearch)
                this.dataBinding.searchEdt.setSelection(this.viewModel.lastStateSearch.length)
            }
            else -> {
                this.dataBinding.searchEdt.setText(spokenText)
                this.dataBinding.searchEdt.setSelection(spokenText.length)
            }
        }
        this.dataBinding.searchButton.performClick()
    }
}