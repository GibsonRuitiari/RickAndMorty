package us.jetpackcomposeperformance.rickandmorty

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority

class RickynMortyApplication: Application() {

  override fun onCreate() {
    super.onCreate()

    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.DEBUG)
  }
}