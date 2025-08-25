package com.appvalence.hayatkurtar.core.logging;

import android.content.Context;
import android.util.Log;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Log levels for mesh networking
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/appvalence/hayatkurtar/core/logging/MeshLogLevel;", "", "value", "", "tag", "", "(Ljava/lang/String;IILjava/lang/String;)V", "getTag", "()Ljava/lang/String;", "getValue", "()I", "isLoggable", "", "minLevel", "VERBOSE", "DEBUG", "INFO", "WARN", "ERROR", "core_debug"})
public enum MeshLogLevel {
    /*public static final*/ VERBOSE /* = new VERBOSE(0, null) */,
    /*public static final*/ DEBUG /* = new DEBUG(0, null) */,
    /*public static final*/ INFO /* = new INFO(0, null) */,
    /*public static final*/ WARN /* = new WARN(0, null) */,
    /*public static final*/ ERROR /* = new ERROR(0, null) */;
    private final int value = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tag = null;
    
    MeshLogLevel(int value, java.lang.String tag) {
    }
    
    public final int getValue() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTag() {
        return null;
    }
    
    public final boolean isLoggable(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.logging.MeshLogLevel minLevel) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.appvalence.hayatkurtar.core.logging.MeshLogLevel> getEntries() {
        return null;
    }
}