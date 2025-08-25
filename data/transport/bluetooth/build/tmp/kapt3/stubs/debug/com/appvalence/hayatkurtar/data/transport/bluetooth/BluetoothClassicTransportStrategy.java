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

/**
 * Bluetooth Classic transport strategy using RFCOMM/SPP for mesh networking
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00ae\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u00100\u001a\b\u0012\u0004\u0012\u000202012\u0006\u00103\u001a\u000204H\u0096@\u00a2\u0006\u0002\u00105J\u001c\u00106\u001a\b\u0012\u0004\u0012\u000207012\u0006\u00108\u001a\u00020&H\u0096@\u00a2\u0006\u0002\u00109J\u001c\u0010:\u001a\b\u0012\u0004\u0012\u000207012\u0006\u00108\u001a\u00020&H\u0083@\u00a2\u0006\u0002\u00109J\u000e\u0010;\u001a\b\u0012\u0004\u0012\u00020&0<H\u0016J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00020&0>H\u0016J\u0012\u0010?\u001a\u0004\u0018\u0001072\u0006\u0010@\u001a\u00020\u0016H\u0016J\u0016\u0010A\u001a\u0002022\u0006\u0010B\u001a\u00020CH\u0083@\u00a2\u0006\u0002\u0010DJ$\u0010E\u001a\b\u0012\u0004\u0012\u000202012\u0006\u00108\u001a\u00020&2\u0006\u00103\u001a\u000204H\u0096@\u00a2\u0006\u0002\u0010FJ\u0016\u0010G\u001a\b\u0012\u0004\u0012\u00020\u001c0<2\u0006\u0010'\u001a\u00020(H\u0016J\b\u0010H\u001a\u000202H\u0002J\b\u0010I\u001a\u000202H\u0003J\u000e\u0010J\u001a\u000202H\u0096@\u00a2\u0006\u0002\u0010KR\u0016\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u0004\u0018\u00010\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020\u0016X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020+0\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010,\u001a\u00020-X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/\u00a8\u0006L"}, d2 = {"Lcom/appvalence/hayatkurtar/data/transport/bluetooth/BluetoothClassicTransportStrategy;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStrategy;", "context", "Landroid/content/Context;", "bluetoothController", "Lcom/appvalence/bluetooth/api/BluetoothController;", "scanner", "Lcom/appvalence/bluetooth/api/HighPerformanceScanner;", "(Landroid/content/Context;Lcom/appvalence/bluetooth/api/BluetoothController;Lcom/appvalence/bluetooth/api/HighPerformanceScanner;)V", "MESH_SPP_UUID", "Ljava/util/UUID;", "kotlin.jvm.PlatformType", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "getBluetoothAdapter", "()Landroid/bluetooth/BluetoothAdapter;", "bluetoothAdapter$delegate", "Lkotlin/Lazy;", "config", "Lcom/appvalence/hayatkurtar/domain/transport/TransportConfig;", "connections", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/appvalence/hayatkurtar/data/transport/bluetooth/BluetoothMeshLink;", "discoveryJob", "Lkotlinx/coroutines/Job;", "eventChannel", "Lkotlinx/coroutines/channels/Channel;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "isAvailable", "", "()Z", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "name", "getName", "()Ljava/lang/String;", "peerChannel", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "serverJob", "serverSocket", "Landroid/bluetooth/BluetoothServerSocket;", "transportType", "Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "getTransportType", "()Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "broadcast", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectTo", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "peer", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectToDevice", "discoverPeers", "Lkotlinx/coroutines/flow/Flow;", "getConnectedPeers", "", "getLink", "peerId", "handleIncomingConnection", "socket", "Landroid/bluetooth/BluetoothSocket;", "(Landroid/bluetooth/BluetoothSocket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendTo", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "startConnectionMonitoring", "startServerSocket", "stop", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bluetooth_debug"})
public final class BluetoothClassicTransportStrategy implements com.appvalence.hayatkurtar.domain.transport.TransportStrategy {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.bluetooth.api.BluetoothController bluetoothController = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.bluetooth.api.HighPerformanceScanner scanner = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = "Bluetooth-Classic";
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.TransportType transportType = com.appvalence.hayatkurtar.domain.transport.TransportType.BLUETOOTH_CLASSIC;
    private final java.util.UUID MESH_SPP_UUID = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy bluetoothAdapter$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.appvalence.hayatkurtar.data.transport.bluetooth.BluetoothMeshLink> connections = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.bluetooth.BluetoothServerSocket> serverSocket = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex mutex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<com.appvalence.hayatkurtar.domain.transport.TransportEvent> eventChannel = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<com.appvalence.hayatkurtar.domain.transport.Peer> peerChannel = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.CoroutineScope scope;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job serverJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job discoveryJob;
    @org.jetbrains.annotations.NotNull()
    private com.appvalence.hayatkurtar.domain.transport.TransportConfig config;
    
    @javax.inject.Inject()
    public BluetoothClassicTransportStrategy(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.appvalence.bluetooth.api.BluetoothController bluetoothController, @org.jetbrains.annotations.NotNull()
    com.appvalence.bluetooth.api.HighPerformanceScanner scanner) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String getName() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.appvalence.hayatkurtar.domain.transport.TransportType getTransportType() {
        return null;
    }
    
    private final android.bluetooth.BluetoothAdapter getBluetoothAdapter() {
        return null;
    }
    
    @java.lang.Override()
    public boolean isAvailable() {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.TransportEvent> start(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.Peer> discoverPeers() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object connectTo(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<? extends com.appvalence.hayatkurtar.domain.transport.Link>> $completion) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final java.lang.Object connectToDevice(com.appvalence.hayatkurtar.domain.transport.Peer peer, kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<? extends com.appvalence.hayatkurtar.domain.transport.Link>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object broadcast(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object sendTo(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<com.appvalence.hayatkurtar.domain.transport.Peer> getConnectedPeers() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public com.appvalence.hayatkurtar.domain.transport.Link getLink(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object stop(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void startServerSocket() {
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final java.lang.Object handleIncomingConnection(android.bluetooth.BluetoothSocket socket, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void startConnectionMonitoring() {
    }
}