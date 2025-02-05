package com.hypertrack.sdk.capacitor.common

internal sealed class WrapperResult<SuccessType> {
    fun <MappedSuccess> flatMapSuccess(onSuccess: (SuccessType) -> WrapperResult<MappedSuccess>): WrapperResult<MappedSuccess> =
        when (this) {
            is Success -> {
                onSuccess.invoke(this.success)
            }

            is Failure -> {
                Failure<MappedSuccess>(this.failure)
            }
        }

    fun <MappedSuccess> mapSuccess(onSuccess: (SuccessType) -> MappedSuccess): WrapperResult<MappedSuccess> =
        when (this) {
            is Success -> {
                Success(onSuccess.invoke(this.success))
            }

            is Failure -> {
                Failure<MappedSuccess>(this.failure)
            }
        }

    fun getOrThrow(): SuccessType =
        when (this) {
            is Success -> this.success
            is Failure -> throw Exception(
                "Result unwrapping failed: ${this.failure}",
                this.failure,
            )
        }

    companion object {
        fun <SuccessType> tryAsResult(block: () -> SuccessType): WrapperResult<SuccessType> =
            try {
                Success(block.invoke())
            } catch (e: Exception) {
                Failure(e)
            }
    }
}

internal data class Success<SuccessType>(
    val success: SuccessType,
) : WrapperResult<SuccessType>()

internal data class Failure<SuccessType>(
    val failure: Throwable,
) : WrapperResult<SuccessType>()
