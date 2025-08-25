package com.appvalence.hayatkurtar.data.mesh.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.appvalence.hayatkurtar.core.logging.MeshLog;
import com.appvalence.hayatkurtar.core.logging.MeshLogTags;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.mesh.MeshEvent;
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
import com.appvalence.hayatkurtar.domain.mesh.MeshStats;
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

/**
 * Service-specific statistics
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b$\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B_\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\nH\u00c6\u0003J\t\u0010+\u001a\u00020\nH\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003Jc\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u00101\u001a\u00020\u0003J\t\u00102\u001a\u00020\nH\u00d6\u0001J\t\u00103\u001a\u000204H\u00d6\u0001J\u000e\u00105\u001a\u0002062\u0006\u00107\u001a\u000208R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u000b\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011R\u001a\u0010\f\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0013\"\u0004\b\u001d\u0010\u0015R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0013\"\u0004\b\u001f\u0010\u0015R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0013\"\u0004\b!\u0010\u0015R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0013\"\u0004\b#\u0010\u0015\u00a8\u00069"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/service/ServiceStats;", "", "startTime", "", "messagesReceived", "messagesDelivered", "messagesForwarded", "messagesDropped", "emergencyMessagesSent", "connectedPeers", "", "knownRoutes", "lastHealthCheck", "(JJJJJJIIJ)V", "getConnectedPeers", "()I", "setConnectedPeers", "(I)V", "getEmergencyMessagesSent", "()J", "setEmergencyMessagesSent", "(J)V", "getKnownRoutes", "setKnownRoutes", "getLastHealthCheck", "setLastHealthCheck", "getMessagesDelivered", "setMessagesDelivered", "getMessagesDropped", "setMessagesDropped", "getMessagesForwarded", "setMessagesForwarded", "getMessagesReceived", "setMessagesReceived", "getStartTime", "setStartTime", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "getUptimeMs", "hashCode", "toString", "", "updateFromMeshStats", "", "meshStats", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshStats;", "mesh_debug"})
public final class ServiceStats {
    private long startTime;
    private long messagesReceived;
    private long messagesDelivered;
    private long messagesForwarded;
    private long messagesDropped;
    private long emergencyMessagesSent;
    private int connectedPeers;
    private int knownRoutes;
    private long lastHealthCheck;
    
    public ServiceStats(long startTime, long messagesReceived, long messagesDelivered, long messagesForwarded, long messagesDropped, long emergencyMessagesSent, int connectedPeers, int knownRoutes, long lastHealthCheck) {
        super();
    }
    
    public final long getStartTime() {
        return 0L;
    }
    
    public final void setStartTime(long p0) {
    }
    
    public final long getMessagesReceived() {
        return 0L;
    }
    
    public final void setMessagesReceived(long p0) {
    }
    
    public final long getMessagesDelivered() {
        return 0L;
    }
    
    public final void setMessagesDelivered(long p0) {
    }
    
    public final long getMessagesForwarded() {
        return 0L;
    }
    
    public final void setMessagesForwarded(long p0) {
    }
    
    public final long getMessagesDropped() {
        return 0L;
    }
    
    public final void setMessagesDropped(long p0) {
    }
    
    public final long getEmergencyMessagesSent() {
        return 0L;
    }
    
    public final void setEmergencyMessagesSent(long p0) {
    }
    
    public final int getConnectedPeers() {
        return 0;
    }
    
    public final void setConnectedPeers(int p0) {
    }
    
    public final int getKnownRoutes() {
        return 0;
    }
    
    public final void setKnownRoutes(int p0) {
    }
    
    public final long getLastHealthCheck() {
        return 0L;
    }
    
    public final void setLastHealthCheck(long p0) {
    }
    
    public final void updateFromMeshStats(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats) {
    }
    
    public final long getUptimeMs() {
        return 0L;
    }
    
    public ServiceStats() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final long component6() {
        return 0L;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.data.mesh.service.ServiceStats copy(long startTime, long messagesReceived, long messagesDelivered, long messagesForwarded, long messagesDropped, long emergencyMessagesSent, int connectedPeers, int knownRoutes, long lastHealthCheck) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}