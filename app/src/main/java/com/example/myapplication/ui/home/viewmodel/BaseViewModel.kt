package com.example.myapplication.ui.home.viewmodel

import androidx.lifecycle.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by PengFeifei on 2019-12-24.
 *
 * https://juejin.im/post/5d4d17e5f265da039401f6ea
 */

class ViewModelDsl<T> {

    internal lateinit var request: suspend () -> T

    internal var onLoading: (() -> Boolean?)? = null

    internal var onSuccess: ((T) -> Unit)? = null

    internal var onError: ((Exception) -> Boolean?)? = null

    internal var onFinally: (() -> Boolean?)? = null


    infix fun onLoading(onLoadingAction: (() -> Boolean?)?) {
        this.onLoading = onLoadingAction
    }

    infix fun onRequest(request: suspend () -> T) {
        this.request = request
    }

    infix fun onSuccess(onSuccess: ((T) -> Unit)?) {
        this.onSuccess = onSuccess
    }

    infix fun onError(onError: ((Exception) -> Boolean?)?) {
        this.onError = onError
    }

    infix fun onFinally(onFinally: (() -> Boolean?)?) {
        this.onFinally = onFinally
    }


    internal fun launch(viewModelScope: CoroutineScope) {
        viewModelScope.launch(context = Dispatchers.Main) {
            onLoading?.invoke()
            try {
                val response = withContext(Dispatchers.IO) {
                    request()
                }
                onSuccess?.invoke(response)
            } catch (e: Exception) {
                e.printStackTrace()
                onError?.invoke(e)
            } finally {
                onFinally?.invoke()
            }
        }
    }


}
/**
 * Created by PengFeifei on 2019-12-24.
 *
 * https://developer.android.google.cn/topic/libraries/architecture/coroutines#livedata
 */
open class RequestViewModel : ViewModel() {

    open val apiException: MutableLiveData<Throwable> = MutableLiveData()
    open val apiLoading: MutableLiveData<Boolean> = MutableLiveData()


    private fun <Response> api(apiDSL: ViewModelDsl<Response>.() -> Unit) {
        ViewModelDsl<Response>().apply(apiDSL).launch(viewModelScope)
    }

    @JvmOverloads
    protected fun <Response> apiCallback(
        request: suspend () -> Response,
        onSuccess: ((Response) -> Unit),
        onLoading: (() -> Boolean)? = null,
        onError: ((java.lang.Exception) -> Boolean)? = null,
        onFinally: (() -> Boolean)? = null
    ) {

        api<Response> {

            onRequest {
                request.invoke()
            }

            onSuccess {
                onSuccess.invoke(it)
            }

            onLoading {
                val override = onLoading?.invoke()
                if (override == null || !override) {
                    onApiLoading()
                }
                false
            }

            onError {
                val override = onError?.invoke(it)
                if (override == null || !override) {
                    onApiError(it)
                }
                false

            }

            onFinally {
                val override = onFinally?.invoke()
                if (override == null || !override) {
                    onApiFinally()
                }
                false
            }
        }
    }

    protected fun <T> apiDSL(apiDSL: ViewModelDsl<T>.() -> Unit) {
        api<T> {
            onRequest {
                ViewModelDsl<T>().apply(apiDSL).request()
            }

            onSuccess {
                ViewModelDsl<T>().apply(apiDSL).onSuccess?.invoke(it)
            }
            onLoading {
                val override = ViewModelDsl<T>().apply(apiDSL).onLoading?.invoke()
                if (override == null || !override) {
                    onApiLoading()
                }
                override
            }

            onError { error ->
                val override = ViewModelDsl<T>().apply(apiDSL).onError?.invoke(error)
                if (override == null || !override) {
                    onApiError(error)
                }
                override

            }

            onFinally {
                val override = ViewModelDsl<T>().apply(apiDSL).onFinally?.invoke()
                if (override == null || !override) {
                    onApiFinally()
                }
                override
            }
        }

    }

    protected fun <T> apiLiveData(
        context: CoroutineContext = EmptyCoroutineContext,
        timeoutInMs: Long = 3000L,
        request: suspend () -> T
    ): LiveData<Result<T>> {

        return liveData(context, timeoutInMs) {
            emit(Result.Loading())
            try {
                emit(withContext(Dispatchers.IO) {
                    Result.Success(request())
                })
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error<T>(e))
            } finally {
                emit(Result.Finally())
            }
        }
    }


    protected open fun onApiLoading() {
        apiLoading.value = true
    }

    protected open fun onApiError(e: Exception?) {
        apiLoading.value = false
        apiException.value = e
    }

    protected open fun onApiFinally() {
        apiLoading.value = false
    }
}

/**
 * Result必须加泛型 不然response的泛型就会被擦除!!
 */
sealed class Result<T> {
    class Loading<T> : Result<T>()
    class Finally<T> : Result<T>()
    data class Success<T>(val response: T) : Result<T>()
    data class Error<T>(val exception: Exception) : Result<T>()
}
