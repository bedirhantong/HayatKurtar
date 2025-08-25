package com.appvalence.hayatkurtar.data.transport.wifidirect;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.*;
import android.os.Looper;
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
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Wi-Fi Direct transport strategy implementation
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00aa\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001:\u0001NB\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010/\u001a\b\u0012\u0004\u0012\u000201002\u0006\u00102\u001a\u000203H\u0096@\u00a2\u0006\u0002\u00104J\u001c\u00105\u001a\b\u0012\u0004\u0012\u000206002\u0006\u00107\u001a\u00020\"H\u0096@\u00a2\u0006\u0002\u00108J$\u00109\u001a\b\u0012\u0004\u0012\u000206002\u0006\u0010:\u001a\u00020\u000e2\u0006\u00107\u001a\u00020\"H\u0083@\u00a2\u0006\u0002\u0010;J\u000e\u0010<\u001a\b\u0012\u0004\u0012\u00020\"0=H\u0016J\u000e\u0010>\u001a\b\u0012\u0004\u0012\u00020\"0?H\u0016J\u0012\u0010@\u001a\u0004\u0018\u0001062\u0006\u0010A\u001a\u00020\u000bH\u0016J\u0016\u0010B\u001a\u0002012\u0006\u0010C\u001a\u00020DH\u0082@\u00a2\u0006\u0002\u0010EJ\b\u0010F\u001a\u000201H\u0003J$\u0010G\u001a\b\u0012\u0004\u0012\u000201002\u0006\u00107\u001a\u00020\"2\u0006\u00102\u001a\u000203H\u0096@\u00a2\u0006\u0002\u0010HJ\u0016\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00110=2\u0006\u0010%\u001a\u00020&H\u0016J\u000e\u0010J\u001a\u000201H\u0083@\u00a2\u0006\u0002\u0010KJ\b\u0010L\u001a\u000201H\u0002J\u000e\u0010M\u001a\u000201H\u0096@\u00a2\u0006\u0002\u0010KR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0016\u001a\u0004\u0018\u00010\u00178BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u000bX\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0018\u00010$R\u00020\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u0004\u0018\u00010(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u0004\u0018\u00010*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010+\u001a\u00020,X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.\u00a8\u0006O"}, d2 = {"Lcom/appvalence/hayatkurtar/data/transport/wifidirect/WiFiDirectTransportStrategy;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStrategy;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "channel", "Landroid/net/wifi/p2p/WifiP2pManager$Channel;", "config", "Lcom/appvalence/hayatkurtar/domain/transport/TransportConfig;", "connections", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/appvalence/hayatkurtar/data/transport/wifidirect/WiFiDirectLink;", "discoveredPeers", "Landroid/net/wifi/p2p/WifiP2pDevice;", "eventChannel", "Lkotlinx/coroutines/channels/Channel;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportEvent;", "isAvailable", "", "()Z", "isInitialized", "manager", "Landroid/net/wifi/p2p/WifiP2pManager;", "getManager", "()Landroid/net/wifi/p2p/WifiP2pManager;", "manager$delegate", "Lkotlin/Lazy;", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "name", "getName", "()Ljava/lang/String;", "peerChannel", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "receiver", "Lcom/appvalence/hayatkurtar/data/transport/wifidirect/WiFiDirectTransportStrategy$WiFiDirectBroadcastReceiver;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "serverJob", "Lkotlinx/coroutines/Job;", "serverSocket", "Ljava/net/ServerSocket;", "transportType", "Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "getTransportType", "()Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "broadcast", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectTo", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "peer", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "connectToDevice", "device", "(Landroid/net/wifi/p2p/WifiP2pDevice;Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "discoverPeers", "Lkotlinx/coroutines/flow/Flow;", "getConnectedPeers", "", "getLink", "peerId", "handleIncomingConnection", "socket", "Ljava/net/Socket;", "(Ljava/net/Socket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "initialize", "sendTo", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "startPeerDiscovery", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startServerSocket", "stop", "WiFiDirectBroadcastReceiver", "wifidirect_debug"})
public final class WiFiDirectTransportStrategy implements com.appvalence.hayatkurtar.domain.transport.TransportStrategy {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = "WiFi-Direct";
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.TransportType transportType = com.appvalence.hayatkurtar.domain.transport.TransportType.WIFI_DIRECT;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy manager$delegate = null;
    @org.jetbrains.annotations.Nullable()
    private android.net.wifi.p2p.WifiP2pManager.Channel channel;
    private boolean isInitialized = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.appvalence.hayatkurtar.data.transport.wifidirect.WiFiDirectLink> connections = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.net.wifi.p2p.WifiP2pDevice> discoveredPeers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex mutex = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<com.appvalence.hayatkurtar.domain.transport.TransportEvent> eventChannel = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<com.appvalence.hayatkurtar.domain.transport.Peer> peerChannel = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.CoroutineScope scope;
    @org.jetbrains.annotations.Nullable()
    private com.appvalence.hayatkurtar.data.transport.wifidirect.WiFiDirectTransportStrategy.WiFiDirectBroadcastReceiver receiver;
    @org.jetbrains.annotations.Nullable()
    private java.net.ServerSocket serverSocket;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job serverJob;
    @org.jetbrains.annotations.NotNull()
    private com.appvalence.hayatkurtar.domain.transport.TransportConfig config;
    
    @javax.inject.Inject()
    public WiFiDirectTransportStrategy(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
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
    
    private final android.net.wifi.p2p.WifiP2pManager getManager() {
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
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void initialize() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.Peer> discoverPeers() {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final java.lang.Object startPeerDiscovery(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
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
    private final java.lang.Object connectToDevice(android.net.wifi.p2p.WifiP2pDevice device, com.appvalence.hayatkurtar.domain.transport.Peer peer, kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<? extends com.appvalence.hayatkurtar.domain.transport.Link>> $completion) {
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
    
    private final void startServerSocket() {
    }
    
    private final java.lang.Object handleIncomingConnection(java.net.Socket socket, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Broadcast receiver for Wi-Fi Direct events
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0017\u00a8\u0006\u000f"}, d2 = {"Lcom/appvalence/hayatkurtar/data/transport/wifidirect/WiFiDirectTransportStrategy$WiFiDirectBroadcastReceiver;", "Landroid/content/BroadcastReceiver;", "(Lcom/appvalence/hayatkurtar/data/transport/wifidirect/WiFiDirectTransportStrategy;)V", "handleConnectionEstablished", "", "info", "Landroid/net/wifi/p2p/WifiP2pInfo;", "handlePeersChanged", "peerList", "Landroid/net/wifi/p2p/WifiP2pDeviceList;", "onReceive", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "wifidirect_debug"})
    final class WiFiDirectBroadcastReceiver extends android.content.BroadcastReceiver {
        
        public WiFiDirectBroadcastReceiver() {
            super();
        }
        
        @java.lang.Override()
        @android.annotation.SuppressLint(value = {"MissingPermission"})
        public void onReceive(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        android.content.Intent intent) {
        }
        
        private final void handlePeersChanged(android.net.wifi.p2p.WifiP2pDeviceList peerList) {
        }
        
        private final void handleConnectionEstablished(android.net.wifi.p2p.WifiP2pInfo info) {
        }
    }
}