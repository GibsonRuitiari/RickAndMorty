package us.jetpackcomposeperformance.rickandmorty.data.util

import logcat.logcat

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
  return try {
    Result.success(apiCall.invoke())
  } catch (exception: Exception) {
    logcat("SafeApiCal") { "Exception occurred ${exception.message}" }
    Result.failure(exception)
  } catch (error: Error) {
    logcat("SafeApiCal") { "Error occurred ${error.message}" }
    Result.failure(error)
  }
}
