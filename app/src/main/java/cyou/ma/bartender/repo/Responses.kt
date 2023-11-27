package cyou.ma.bartender.repo

import retrofit2.Response

/**
 * Maps a retrofit [Response] to a kotlin [Result] based on success
 */
fun <T> Response<T>.mapToResult(): Result<T> {
    return when (isSuccessful) {
        true -> Result.success(body()!!)
        false -> Result.failure(NetworkCallFailedException(message()))
    }
}

/**
 * Maps a retrofit [Response] to a kotlin [Result] based on success and applies transform
 */
fun <T, M> Response<T>.mapToResult(transform: ((T) -> M)): Result<M> {
    return when (isSuccessful) {
        true -> Result.success(transform(body()!!))
        false -> Result.failure(NetworkCallFailedException(message()))
    }
}

class NetworkCallFailedException(message: String? = null) : Throwable(message)
