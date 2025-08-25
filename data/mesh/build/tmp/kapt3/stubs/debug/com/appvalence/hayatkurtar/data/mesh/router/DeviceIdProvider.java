package com.appvalence.hayatkurtar.data.mesh.router;

import com.appvalence.hayatkurtar.core.logging.MeshLog;
import com.appvalence.hayatkurtar.core.logging.MeshLogTags;
import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.protocol.FrameType;
import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshException;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.data.mesh.store.RoomMessageStore;
import com.appvalence.hayatkurtar.domain.mesh.*;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provides unique device identifier
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/router/DeviceIdProvider;", "", "getDeviceId", "", "mesh_debug"})
public abstract interface DeviceIdProvider {
    
    public abstract long getDeviceId();
}