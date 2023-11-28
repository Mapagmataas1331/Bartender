package cyou.ma.bartender.repo

import retrofit2.Response

fun <T> Response<T>.mapToResult(): Result<T> {
  return when (isSuccessful) {
    true -> Result.success(body()!!)
    false -> Result.failure(NetworkCallFailedException(message()))
  }
}

fun <T, M> Response<T>.mapToResult(transform: ((T) -> M)): Result<M> {
  return when (isSuccessful) {
    true -> Result.success(transform(body()!!))
    false -> Result.failure(NetworkCallFailedException(message()))
  }
}

class NetworkCallFailedException(message: String? = null) : Throwable(message)
