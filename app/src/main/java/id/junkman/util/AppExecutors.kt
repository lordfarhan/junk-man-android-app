package id.junkman.util

import android.os.Handler
import android.os.Looper
import android.support.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author farhan
 * created at at 11:41 on 29/05/21.
 */
class AppExecutors @VisibleForTesting constructor(
  private val diskIO: Executor,
  private val mainThread: Executor
) {

  companion object {
    private const val THREAD_COUNT = 3
  }

  constructor() : this(
    Executors.newSingleThreadExecutor(),
    MainThreadExecutor()
  )

  fun diskIO(): Executor = diskIO

  fun mainThread(): Executor = mainThread

  private class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
      mainThreadHandler.post(command)
    }
  }
}