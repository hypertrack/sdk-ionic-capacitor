package com.hypertrack.sdk.capacitor

import android.location.Location
import android.location.LocationListener
import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.hypertrack.sdk.*
import com.hypertrack.sdk.capacitor.common.*
import com.hypertrack.sdk.capacitor.common.WrapperResult

@CapacitorPlugin(name = "HyperTrackCapacitorPlugin")
class HyperTrackCapacitorPlugin : Plugin() {

    init {
        initListeners()
    }

    @PluginMethod
    fun addGeotag(call: PluginCall) {
        invokeSdkMethod(SdkMethod.addGeotag, call).toPluginCall(call)
    }

    @PluginMethod
    fun getDeviceId(call: PluginCall) {
        invokeSdkMethod(SdkMethod.getDeviceID, call).toPluginCall(call)
    }

    @PluginMethod
    fun getErrors(call: PluginCall) {
        invokeSdkMethod(SdkMethod.getErrors, call).toPluginCall(call)
    }

    @PluginMethod
    fun getIsAvailable(call: PluginCall) {
        invokeSdkMethod(SdkMethod.getIsAvailable, call).toPluginCall(call)
    }

    @PluginMethod
    fun getIsTracking(call: PluginCall) {
        invokeSdkMethod(SdkMethod.getIsTracking, call).toPluginCall(call)
    }

    @PluginMethod
    fun getLocation(call: PluginCall) {
        invokeSdkMethod(SdkMethod.getLocation, call).toPluginCall(call)
    }

    @PluginMethod
    fun getMetadata(call: PluginCall) {
        invokeSdkMethod(SdkMethod.getMetadata, call).toPluginCall(call)
    }

    @PluginMethod
    fun getName(call: PluginCall) {
        invokeSdkMethod(SdkMethod.getName, call).toPluginCall(call)
    }

    @PluginMethod
    fun setIsAvailable(call: PluginCall) {
        invokeSdkMethod(SdkMethod.setIsAvailable, call).toPluginCall(call)
    }

    @PluginMethod
    fun setIsTracking(call: PluginCall) {
        invokeSdkMethod(SdkMethod.setIsTracking, call).toPluginCall(call)
    }

    @PluginMethod
    fun setMetadata(call: PluginCall) {
        invokeSdkMethod(SdkMethod.setMetadata, call).toPluginCall(call)
    }

    @PluginMethod
    fun setName(call: PluginCall) {
        invokeSdkMethod(SdkMethod.setName, call).toPluginCall(call)
    }

    @PluginMethod
    fun onSubscribedToErrors(call: PluginCall) {
        sendErrorsEvent(Serialization.serializeErrors(HyperTrack.errors))
    }

    @PluginMethod
    fun onSubscribedToIsAvailable(call: PluginCall) {
        sendAvailabilityEvent(
            Serialization.serializeIsAvailable(
                HyperTrackSdkWrapper.sdkInstance.availability == Availability.AVAILABLE
            )
        )
    }

    @PluginMethod
    fun onSubscribedToIsTracking(call: PluginCall) {
        Log.v("ht_listener", "onSubscribedToTracking")
        sendIsTrackingEvent(
            Serialization.serializeIsTracking(HyperTrackSdkWrapper.sdkInstance.isTracking)
        )
    }

    @PluginMethod
    fun onSubscribeToLocation(call: PluginCall) {
        Log.v("ht_listener", "onSubscribeToLocation")
        HyperTrackSdkWrapper.sdkInstance.addLocationListener(object : LocationListener {
            override fun onLocationUpdate(location: Location) {
                notifyListeners(
                    "onLocationUpdate",
                    Serialization.serializeLocation(location).toJSObject()
                )
            }
        })
    }

    private fun invokeSdkMethod(
        method: SdkMethod,
        call: PluginCall
    ): Result<*> {
        val argsJson = call.data
        return when (method) {
            SdkMethod.initialize -> {
                withArgs<Map<String, Any?>, Unit>(argsJson) { args ->
                    HyperTrackSdkWrapper.initializeSdk(args).mapSuccess {
                        initListeners(it)
                    }
                }
            }
            SdkMethod.getDeviceID -> {
                HyperTrackSdkWrapper.getDeviceId()
            }
            SdkMethod.isTracking -> {
                HyperTrackSdkWrapper.isTracking()
            }
            SdkMethod.isAvailable -> {
                HyperTrackSdkWrapper.isAvailable()
            }
            SdkMethod.setAvailability -> {
                withArgs<Map<String, Boolean>, Unit>(argsJson) { args ->
                    HyperTrackSdkWrapper.setAvailability(args)
                }
            }
            SdkMethod.getLocation -> {
                HyperTrackSdkWrapper.getLocation()
            }
            SdkMethod.startTracking -> {
                HyperTrackSdkWrapper.startTracking()
            }
            SdkMethod.stopTracking -> {
                HyperTrackSdkWrapper.stopTracking()
            }
            SdkMethod.addGeotag -> {
                withArgs<Map<String, Any?>, Map<String, Any?>>(argsJson) { args ->
                    HyperTrackSdkWrapper.addGeotag(args)
                }
            }
            SdkMethod.setName -> {
                withArgs<Map<String, Any?>, Unit>(argsJson) { args ->
                    HyperTrackSdkWrapper.setName(args)
                }
            }
            SdkMethod.setMetadata -> {
                withArgs<Map<String, Any?>, Unit>(argsJson) { args ->
                    HyperTrackSdkWrapper.setMetadata(args)
                }
            }
            SdkMethod.sync -> {
                HyperTrackSdkWrapper.sync()
            }
        }
    }

    private fun initListeners() {
        sdk.addTrackingListener(object : TrackingStateObserver.OnTrackingStateChangeListener {
            override fun onTrackingStart() {
                Log.v("ht_listener", "onTrackingStart")
                sendIsTrackingEvent(Serialization.serializeIsTracking(true))
            }

            override fun onTrackingStop() {
                Log.v("ht_listener", "onTrackingStop")
                sendIsTrackingEvent(Serialization.serializeIsTracking(false))
            }

            override fun onError(error: TrackingError) {
                sendErrorsEvent(HyperTrackSdkWrapper.getErrors(error))
            }
        })

        sdk.addAvailabilityListener(object :
            AvailabilityStateObserver.OnAvailabilityStateChangeListener {
            override fun onAvailable() {
                sendAvailabilityEvent(Serialization.serializeIsAvailable(true))
            }

            override fun onUnavailable() {
                sendAvailabilityEvent(Serialization.serializeIsAvailable(false))
            }

            override fun onError(error: AvailabilityError) {
                // ignored, errors are handled by errorEventChannel
            }
        })
    }

    private fun sendIsTrackingEvent(data: Map<String, Any?>, retainUntilConsumed: Boolean = false) {
        notifyListeners(EVENT_TRACKING, data.toJSObject(), retainUntilConsumed)
    }

    private fun sendAvailabilityEvent(
        data: Map<String, Any?>,
        retainUntilConsumed: Boolean = false
    ) {
        notifyListeners(EVENT_AVAILABILITY, data.toJSObject(), retainUntilConsumed)
    }

    private fun sendErrorsEvent(
        data: List<Map<String, String>>,
        retainUntilConsumed: Boolean = false
    ) {
        notifyListeners(EVENT_ERRORS, mapOf(KEY_ERRORS to data).toJSObject(), retainUntilConsumed)
    }

    private inline fun <reified T, N> withArgs(
        args: JSObject,
        crossinline sdkMethodCall: (T) -> Result<N>
    ): Result<N> {
        return when (T::class) {
            Map::class -> {
                sdkMethodCall.invoke(args.toMap() as T)
            }
            else -> {
                Failure(IllegalArgumentException(args.toString()))
            }
        }
    }

    companion object {
        private const val EVENT_TRACKING = "onTrackingChanged"
        private const val EVENT_AVAILABILITY = "onAvailabilityChanged"
        private const val EVENT_ERRORS = "onError"
        private const val KEY_ERRORS = "errors"
    }
}
