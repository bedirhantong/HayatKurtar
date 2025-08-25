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
 * Bluetooth mesh link implementation using RFCOMM
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0096@\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u001dH\u0016J\u001c\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0006\u0010\u001f\u001a\u00020 H\u0096@\u00a2\u0006\u0002\u0010!J\u001a\u0010\"\u001a\u00020\u001a2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u001a0$R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/appvalence/hayatkurtar/data/transport/bluetooth/BluetoothMeshLink;", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "peer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "socket", "Landroid/bluetooth/BluetoothSocket;", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Landroid/bluetooth/BluetoothSocket;)V", "connectionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "inputStream", "Ljava/io/InputStream;", "isConnected", "()Z", "linkQuality", "Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;", "getLinkQuality", "()Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;", "outputStream", "Ljava/io/OutputStream;", "getPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "readingJob", "Lkotlinx/coroutines/Job;", "disconnect", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeConnection", "Lkotlinx/coroutines/flow/Flow;", "send", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startReading", "onFrameReceived", "Lkotlin/Function1;", "bluetooth_debug"})
public final class BluetoothMeshLink implements com.appvalence.hayatkurtar.domain.transport.Link {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.Peer peer = null;
    @org.jetbrains.annotations.NotNull()
    private final android.bluetooth.BluetoothSocket socket = null;
    @org.jetbrains.annotations.Nullable()
    private java.io.InputStream inputStream;
    @org.jetbrains.annotations.Nullable()
    private java.io.OutputStream outputStream;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> connectionState = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job readingJob;
    
    public BluetoothMeshLink(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    android.bluetooth.BluetoothSocket socket) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.appvalence.hayatkurtar.domain.transport.Peer getPeer() {
        return null;
    }
    
    @java.lang.Override()
    public boolean isConnected() {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.appvalence.hayatkurtar.domain.transport.LinkQuality getLinkQuality() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object send(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object disconnect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> observeConnection() {
        return null;
    }
    
    public final void startReading(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.appvalence.hayatkurtar.core.protocol.Frame, kotlin.Unit> onFrameReceived) {
    }
}