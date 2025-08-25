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
 * Implementation that generates device ID from device characteristics
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/router/AndroidDeviceIdProvider;", "Lcom/appvalence/hayatkurtar/data/mesh/router/DeviceIdProvider;", "()V", "getDeviceId", "", "mesh_debug"})
public final class AndroidDeviceIdProvider implements com.appvalence.hayatkurtar.data.mesh.router.DeviceIdProvider {
    
    @javax.inject.Inject()
    public AndroidDeviceIdProvider() {
        super();
    }
    
    @java.lang.Override()
    public long getDeviceId() {
        return 0L;
    }
}