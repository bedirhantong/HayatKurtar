package com.appvalence.hayatkurtar.core.result

/**
 * A generic Result type for handling success/error cases without exceptions.
 * Inspired by Kotlin's Result but tailored for mesh networking use cases.
 */
sealed class MeshResult<out T> {
    data class Success<out T>(val data: T) : MeshResult<T>()
    data class Error(val exception: MeshException) : MeshResult<Nothing>()

    inline fun <R> map(transform: (T) -> R): MeshResult<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }

    inline fun <R> flatMap(transform: (T) -> MeshResult<R>): MeshResult<R> = when (this) {
        is Success -> transform(data)
        is Error -> this
    }

    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw exception
    }

    fun errorOrNull(): MeshException? = when (this) {
        is Success -> null
        is Error -> exception
    }
}

/**
 * Extension functions for easier Result construction
 */
fun <T> T.toSuccess(): MeshResult<T> = MeshResult.Success(this)
fun Throwable.toError(): MeshResult<Nothing> = MeshResult.Error(MeshException.wrap(this))
fun MeshException.toError(): MeshResult<Nothing> = MeshResult.Error(this)

/**
 * Safe try-catch wrapper that returns MeshResult
 */
inline fun <T> meshTry(block: () -> T): MeshResult<T> = try {
    MeshResult.Success(block())
} catch (e: Exception) {
    MeshResult.Error(MeshException.wrap(e))
}