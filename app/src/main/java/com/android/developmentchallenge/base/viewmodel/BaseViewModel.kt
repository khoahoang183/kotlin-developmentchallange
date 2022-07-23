package com.android.developmentchallenge.base.viewmodel

import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.developmentchallenge.R
import com.android.developmentchallenge.util.interfaces.OnCallAPIListener
import com.android.developmentchallenge.util.interfaces.OnErrorCallAPIListener
import com.android.developmentchallenge.util.interfaces.OnJWTTimeoutListener
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import timber.log.Timber
import java.util.*

/**
 * Created by: Khoa Hoang Dang (Steve) - HK Studio
 * email: khoahoang.180397@gmail.com
 */

abstract class BaseViewModel : ViewModel(), KoinComponent {
    // CompositeDisposable rxjava
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    // Api disposable lasted
    lateinit var subscription: Disposable

    // Interface for send task working
    var onLoadingDataListener: OnCallAPIListener? = null

    // Interface for send error
    var onErrorLoadListener: OnErrorCallAPIListener? = null

    // Interface for send event key timeout
    var onJWTTimeoutListener: OnJWTTimeoutListener? = null

    // startForResult voice recognize
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var textToSpeechEngine: TextToSpeech

    // On clear view model
    override fun onCleared() {
        if (::subscription.isInitialized) {
            subscription.dispose()
        }
        compositeDisposable.dispose()
        compositeDisposable.clear()
        Timber.e("onCleared ${this@BaseViewModel.javaClass}")
        super.onCleared()
    }

    /**
     * Show dialog when load data from server
     */
    fun startWorking() {
        onLoadingDataListener?.onStartCallApi()
    }

    /**
     * Hide dialog when dialog is showing
     */
    fun stopWorking() {
        onLoadingDataListener?.onStopCallApi()
    }

    /**
     * Update status when download or upload file
     */
    fun onUpdateStatus(status: Int) {
        onLoadingDataListener?.onUpdate(status)
    }

    /**
     * Base lister call api on main thread with show dialog
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> callApiMainThreadWithDialog(
        observable: Observable<*>,
        crossinline onComplete: (T) -> Unit,
        crossinline onError: (Throwable) -> Unit
    ) {
        subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                startWorking()
            }
            .doOnTerminate {
                stopWorking()
            }
            .subscribe({
                if (it is T) {
                    // TODO : Check JWT key is timeout first
                    // If is error and cause by JWT timeout, need logout user
                    onComplete.invoke(it as T)
                }
            }, {
                onError.invoke(it)
                Timber.e(it)
                stopWorking()
            })

        compositeDisposable.add(
            subscription
        )
    }

    /**
     * Cast Throwable to object handle error
     * If can't cast this to object, we will show dialog error with common erro
     *
     * @param T : Type of object return
     * @param throwable : Throwable
     * @return object casted or null
     */
    inline fun <reified T> handleErrorResponse(throwable: Throwable): T? {
        when (throwable) {
            is HttpException -> {
                try {
                    return Gson().fromJson(
                        throwable.response()?.errorBody()?.charStream(),
                        T::class.java
                    )
                } catch (e: Exception) {
                    Timber.e(e)
                    onErrorLoadListener?.onErrorInt(R.string.dialog_common_unknown_error)
                }
            }
            else -> {
                // Other errors like Network ...
                onErrorLoadListener?.onErrorInt(R.string.dialog_common_unknown_error)
            }
        }
        return null
    }

    /**
     * Base call api without dialog
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> callApiMainThreadWithOutDialog(
        observable: Observable<*>,
        crossinline onComplete: (T) -> Unit,
        crossinline onError: (Throwable) -> Unit
    ) {
        subscription = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                startWorking()
            }
            .doOnTerminate {
                stopWorking()
            }
            .subscribe({
                stopWorking()
                if (it is T) {
                    onComplete.invoke(it as T)
                }
            }, {
                stopWorking()
                onError.invoke(it)
                Timber.e(it)
            })
        compositeDisposable.add(subscription)
    }

    /**
     * Save data to room by rxjava listener
     *
     * @param observable : is word
     * @param onComplete : send complete
     * @param onError : send error
     */
    fun workingSaveWithRoomByRx(
        observable: Completable,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        this@BaseViewModel.compositeDisposable.add(observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.e("Rx room save complete")
                onComplete.invoke()
            }, {
                Timber.e("Rx room save fail")
                Timber.e(it)
                onError.invoke(it)
            })
        )
    }

    /**
     * Load data from room by rxjava listener
     *
     * @param observable : is word
     * @param onComplete : send complete
     * @param onError : send error
     */
    inline fun <reified T> workingLoadWithRoomByRx(
        observable: Observable<*>,
        crossinline onComplete: (T) -> Unit,
        crossinline onError: (Throwable) -> Unit,
    ) {
        this@BaseViewModel.compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it is T) {
                        Timber.e("Rx room load complete")
                        onComplete.invoke(it)
                    } else {
                        Timber.e("Rx room load fail")
                    }
                }, {
                    Timber.e("Rx room load fail")
                    Timber.e(it)
                    onError.invoke(it)
                })
        )
    }

    /**
     * initialize Voice Recognizer
     */
    fun initializeVoiceRecognizer(engine: TextToSpeech, launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
        textToSpeechEngine = engine
        startForResult = launcher
    }

    /**
     * display Voice Recognizer
     */
    fun displayVoiceRecognizer() {
        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale(Locale.getDefault().language))
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Hello I'm Khoa"))
        })
    }

    /**
     * Speak to Voice Recognizer
     *
     * @param text
     * @return
     */
    fun speak(text: String) = viewModelScope.launch{
        textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}