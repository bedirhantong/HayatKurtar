package com.appvalence.hayatkurtar.core.logging

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Log levels for mesh networking
 */
enum class MeshLogLevel(val value: Int, val tag: String) {
    VERBOSE(2, "V"),
    DEBUG(3, "D"),
    INFO(4, "I"),
    WARN(5, "W"),
    ERROR(6, "E");

    fun isLoggable(minLevel: MeshLogLevel): Boolean = value >= minLevel.value
}

/**
 * Mesh-specific logger with offline file logging capability
 */
class MeshLogger private constructor(
    private val context: Context,
    private val minLogLevel: MeshLogLevel = MeshLogLevel.DEBUG
) {
    companion object {
        private const val LOG_TAG = "HayatKurtar"
        private const val LOG_FILE_NAME = "mesh_logs.txt"
        private const val MAX_LOG_FILE_SIZE = 2 * 1024 * 1024L // 2 MB
        private const val MAX_LOG_FILES = 3

        @Volatile
        private var INSTANCE: MeshLogger? = null

        fun initialize(context: Context, minLogLevel: MeshLogLevel = MeshLogLevel.DEBUG) {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = MeshLogger(context.applicationContext, minLogLevel)
                    }
                }
            }
        }

        fun getInstance(): MeshLogger {
            return INSTANCE ?: throw IllegalStateException("MeshLogger not initialized")
        }
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    private val logScope = CoroutineScope(Dispatchers.IO)
    private val fileMutex = Mutex()
    private val logsDir: File = File(context.filesDir, "logs").apply { 
        if (!exists()) mkdirs() 
    }

    /**
     * Log verbose message
     */
    fun v(tag: String, message: String, throwable: Throwable? = null) {
        log(MeshLogLevel.VERBOSE, tag, message, throwable)
    }

    /**
     * Log debug message
     */
    fun d(tag: String, message: String, throwable: Throwable? = null) {
        log(MeshLogLevel.DEBUG, tag, message, throwable)
    }

    /**
     * Log info message
     */
    fun i(tag: String, message: String, throwable: Throwable? = null) {
        log(MeshLogLevel.INFO, tag, message, throwable)
    }

    /**
     * Log warning message
     */
    fun w(tag: String, message: String, throwable: Throwable? = null) {
        log(MeshLogLevel.WARN, tag, message, throwable)
    }

    /**
     * Log error message
     */
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        log(MeshLogLevel.ERROR, tag, message, throwable)
    }

    /**
     * Main logging function
     */
    private fun log(level: MeshLogLevel, tag: String, message: String, throwable: Throwable?) {
        if (!level.isLoggable(minLogLevel)) return

        val fullTag = "$LOG_TAG:$tag"
        val logMessage = message + if (throwable != null) "\n${Log.getStackTraceString(throwable)}" else ""

        // Log to Android logcat
        when (level) {
            MeshLogLevel.VERBOSE -> Log.v(fullTag, logMessage)
            MeshLogLevel.DEBUG -> Log.d(fullTag, logMessage)
            MeshLogLevel.INFO -> Log.i(fullTag, logMessage)
            MeshLogLevel.WARN -> Log.w(fullTag, logMessage)
            MeshLogLevel.ERROR -> Log.e(fullTag, logMessage)
        }

        // Log to file asynchronously
        logScope.launch {
            writeToFile(level, tag, message, throwable)
        }
    }

    /**
     * Write log entry to file
     */
    private suspend fun writeToFile(
        level: MeshLogLevel, 
        tag: String, 
        message: String, 
        throwable: Throwable?
    ) {
        fileMutex.withLock {
            try {
                val logFile = File(logsDir, LOG_FILE_NAME)
                
                // Rotate log if too large
                if (logFile.exists() && logFile.length() > MAX_LOG_FILE_SIZE) {
                    rotateLogFiles()
                }

                val timestamp = dateFormat.format(Date())
                val logEntry = buildString {
                    append("$timestamp ${level.tag}/$tag: $message")
                    if (throwable != null) {
                        append("\n${Log.getStackTraceString(throwable)}")
                    }
                    append("\n")
                }

                FileWriter(logFile, true).use { writer ->
                    writer.write(logEntry)
                    writer.flush()
                }
            } catch (e: Exception) {
                // Silently fail - don't crash the app if logging fails
                Log.e(LOG_TAG, "Failed to write to log file", e)
            }
        }
    }

    /**
     * Rotate log files when they get too large
     */
    private fun rotateLogFiles() {
        try {
            // Move existing logs
            for (i in MAX_LOG_FILES - 1 downTo 1) {
                val oldFile = File(logsDir, "mesh_logs_$i.txt")
                val newFile = File(logsDir, "mesh_logs_${i + 1}.txt")
                if (oldFile.exists()) {
                    if (i == MAX_LOG_FILES - 1) {
                        oldFile.delete() // Delete oldest
                    } else {
                        oldFile.renameTo(newFile)
                    }
                }
            }

            // Move current log to _1
            val currentLog = File(logsDir, LOG_FILE_NAME)
            if (currentLog.exists()) {
                currentLog.renameTo(File(logsDir, "mesh_logs_1.txt"))
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Failed to rotate log files", e)
        }
    }

    /**
     * Get all log files for export
     */
    fun getLogFiles(): List<File> {
        return listOf(
            File(logsDir, LOG_FILE_NAME),
            File(logsDir, "mesh_logs_1.txt"),
            File(logsDir, "mesh_logs_2.txt"),
            File(logsDir, "mesh_logs_3.txt")
        ).filter { it.exists() }
    }

    /**
     * Export logs to external storage
     */
    suspend fun exportLogs(targetDir: File): Result<List<File>> = fileMutex.withLock {
        try {
            if (!targetDir.exists()) {
                targetDir.mkdirs()
            }

            val exportedFiles = mutableListOf<File>()
            getLogFiles().forEach { logFile ->
                val targetFile = File(targetDir, logFile.name)
                logFile.copyTo(targetFile, overwrite = true)
                exportedFiles.add(targetFile)
            }

            Result.success(exportedFiles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Clear all log files
     */
    suspend fun clearLogs() = fileMutex.withLock {
        try {
            getLogFiles().forEach { it.delete() }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Failed to clear logs", e)
        }
    }
}

/**
 * Extension functions for easier logging
 */
object MeshLog {
    fun v(tag: String, message: String, throwable: Throwable? = null) {
        MeshLogger.getInstance().v(tag, message, throwable)
    }

    fun d(tag: String, message: String, throwable: Throwable? = null) {
        MeshLogger.getInstance().d(tag, message, throwable)
    }

    fun i(tag: String, message: String, throwable: Throwable? = null) {
        MeshLogger.getInstance().i(tag, message, throwable)
    }

    fun w(tag: String, message: String, throwable: Throwable? = null) {
        MeshLogger.getInstance().w(tag, message, throwable)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        MeshLogger.getInstance().e(tag, message, throwable)
    }
}

/**
 * Specialized loggers for different components
 */
object MeshLogTags {
    const val TRANSPORT = "Transport"
    const val MESH_ROUTER = "MeshRouter"
    const val BLUETOOTH = "Bluetooth"
    const val WIFI_DIRECT = "WiFiDirect"
    const val CRYPTO = "Crypto"
    const val PROTOCOL = "Protocol"
    const val SERVICE = "Service"
    const val UI = "UI"
}