package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

/**
 * System error information
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J3\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\u00072\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/SystemError;", "", "type", "Lcom/appvalence/hayatkurtar/domain/system/ErrorType;", "message", "", "isRecoverable", "", "suggestedAction", "(Lcom/appvalence/hayatkurtar/domain/system/ErrorType;Ljava/lang/String;ZLjava/lang/String;)V", "()Z", "getMessage", "()Ljava/lang/String;", "getSuggestedAction", "getType", "()Lcom/appvalence/hayatkurtar/domain/system/ErrorType;", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "domain_debug"})
public final class SystemError {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.ErrorType type = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String message = null;
    private final boolean isRecoverable = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String suggestedAction = null;
    
    public SystemError(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ErrorType type, @org.jetbrains.annotations.NotNull()
    java.lang.String message, boolean isRecoverable, @org.jetbrains.annotations.Nullable()
    java.lang.String suggestedAction) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ErrorType getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessage() {
        return null;
    }
    
    public final boolean isRecoverable() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSuggestedAction() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.ErrorType component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.system.SystemError copy(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.ErrorType type, @org.jetbrains.annotations.NotNull()
    java.lang.String message, boolean isRecoverable, @org.jetbrains.annotations.Nullable()
    java.lang.String suggestedAction) {
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