package com.hypertrack.sdk.capacitor

import android.os.Bundle
import com.getcapacitor.BridgeActivity

class MainActivity : BridgeActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPlugin(HyperTrackCapacitorPlugin::class.java)
    }
}
