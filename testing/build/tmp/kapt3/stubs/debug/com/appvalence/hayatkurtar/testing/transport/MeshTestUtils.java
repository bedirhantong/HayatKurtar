package com.appvalence.hayatkurtar.testing.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.*;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test utilities for mesh networking scenarios
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004J\u0018\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/appvalence/hayatkurtar/testing/transport/MeshTestUtils;", "", "()V", "createThreeNodeMesh", "Lkotlin/Triple;", "Lcom/appvalence/hayatkurtar/testing/transport/FakeTransportStrategy;", "waitForPropagation", "", "delayMs", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "testing_debug"})
public final class MeshTestUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.testing.transport.MeshTestUtils INSTANCE = null;
    
    private MeshTestUtils() {
        super();
    }
    
    /**
     * Create a simple 3-node mesh network for testing A -> B -> C scenarios
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Triple<com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy, com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy, com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy> createThreeNodeMesh() {
        return null;
    }
    
    /**
     * Wait for message propagation in test scenarios
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object waitForPropagation(long delayMs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}