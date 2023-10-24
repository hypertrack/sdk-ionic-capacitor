package com.hypertrack.sdk.capacitor

import com.getcapacitor.JSArray
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.hypertrack.sdk.capacitor.common.Failure
import com.hypertrack.sdk.capacitor.common.Serialized
import com.hypertrack.sdk.capacitor.common.WrapperResult
import com.hypertrack.sdk.capacitor.common.Success
import org.json.JSONArray
import org.json.JSONObject

private const val KEY_ERRORS = "errors"

fun serializeErrorsForCapacitor(errors: List<Serialized>): Serialized {
    return mapOf(
        KEY_ERRORS to errors
    )
}

@Suppress("UNCHECKED_CAST")
internal fun <T> WrapperResult<T>.toPluginCall(call: PluginCall) {
    when (this) {
        is Success -> {
            when (this.success) {
                is Map<*, *> -> {
                    call.resolve((this.success as Map<String, Any>).toJSObject())
                }
                is Unit -> {
                    call.resolve()
                }
                else -> {
                    val exception = IllegalArgumentException(this.success.toString())
                    call.reject(exception.message, exception)
                }
            }
        }
        is Failure -> {
            val exception = this.failure
            if (exception is Exception) {
                call.reject(exception.message, exception)
            } else {
                throw exception
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
internal fun List<Any>.toJSArray(): JSArray {
    return JSArray().also { writableArray ->
        forEach {
            when (it) {
                is Map<*, *> -> {
                    writableArray.put((it as Map<String, Any>).toJSObject())
                }
                is String -> {
                    writableArray.put(it)
                }
                else -> {
                    throw Exception(IllegalArgumentException(it.javaClass.toString()))
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
internal fun Map<String, Any?>.toJSObject(): JSObject {
    val map = this
    return JSObject().apply {
        map.forEach { (key, value) ->
            when (val value = value) {
                is Boolean -> {
                    put(key, value)
                }
                is Double, is Float -> {
                    put(key, value)
                }
                is Int -> {
                    put(key, value)
                }
                is List<*> -> {
                    put(key, (value as List<String>).toJSArray())
                }
                is Map<*, *> -> {
                    put(key, (value as Map<String, Any>).toJSObject())
                }
                is String -> {
                    put(key, value)
                }
                null -> {
                    throw IllegalArgumentException(
                        "Invalid JSON: $this \n Null JSON values are not supported"
                    )
                }
                else -> {
                    throw Exception(IllegalArgumentException(value.javaClass.toString()))
                }
            }
        }
    }
}

fun JSONObject.toMap(): Map<String, Any?> {
    return keys().asSequence().associateWith { key ->
        when (val value = this.get(key)) {
            is Boolean,
            is Double,
            is Int,
            is String -> {
                value
            }
            is JSONArray -> {
                value.toList()
            }
            is JSONObject -> {
                value.toMap()
            }
            else -> {
                null
            }
        }
    }
}

private fun JSONArray.toList(): List<Any> {
    return (0..length()).mapNotNull { index ->
        when (val value = this.get(index)) {
            is Boolean,
            is Double,
            is Int,
            is String -> {
                value
            }
            is JSONArray -> {
                value.toList()
            }
            is JSONObject -> {
                value.toMap()
            }
            else -> {
                null
            }
        }
    }
}
