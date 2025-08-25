package com.appvalence.hayatkurtar.data.transport.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.*;
import android.content.Context;
import com.appvalence.bluetooth.api.BluetoothController;
import com.appvalence.bluetooth.api.DiscoveredDevice;
import com.appvalence.bluetooth.api.HighPerformanceScanner;
import com.appvalence.hayatkurtar.core.logging.MeshLog;
import com.appvalence.hayatkurtar.core.logging.MeshLogTags;
import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.protocol.FrameCodec;
import com.appvalence.hayatkurtar.core.result.MeshException;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.*;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u00a8\u0006\u0003"}, d2 = {"toPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "Lcom/appvalence/bluetooth/api/DiscoveredDevice;", "bluetooth_debug"})
public final class BluetoothClassicTransportStrategyKt {
    
    /**
     * Extension function to convert DiscoveredDevice to Peer
     */
    private static final com.appvalence.hayatkurtar.domain.transport.Peer toPeer(com.appvalence.bluetooth.api.DiscoveredDevice $this$toPeer) {
        return null;
    }
}