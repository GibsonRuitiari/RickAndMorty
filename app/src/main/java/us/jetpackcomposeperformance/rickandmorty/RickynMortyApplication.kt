package us.jetpackcomposeperformance.rickandmorty

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import logcat.AndroidLogcatLogger
import logcat.LogPriority

@HiltAndroidApp
class RickynMortyApplication: Application() {

  override fun onCreate() {
    super.onCreate()

    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.DEBUG)
  }
}