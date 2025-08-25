package com.appvalence.hayatkurtar.core.result;

/**
 * A generic Result type for handling success/error cases without exceptions.
 * Inspired by Kotlin's Result but tailored for mesh networking use cases.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\u0011\u0012B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005J2\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0000\"\u0004\b\u0001\u0010\u00072\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00000\tH\u0086\b\u00f8\u0001\u0000J\r\u0010\n\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010\u000bJ\u000b\u0010\f\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u000eJ,\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0000\"\u0004\b\u0001\u0010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u0002H\u00070\tH\u0086\b\u00f8\u0001\u0000\u0082\u0001\u0002\u0013\u0014\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "T", "", "()V", "errorOrNull", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "flatMap", "R", "transform", "Lkotlin/Function1;", "getOrNull", "()Ljava/lang/Object;", "getOrThrow", "isError", "", "isSuccess", "map", "Error", "Success", "Lcom/appvalence/hayatkurtar/core/result/MeshResult$Error;", "Lcom/appvalence/hayatkurtar/core/result/MeshResult$Success;", "core_debug"})
public abstract class MeshResult<T extends java.lang.Object> {
    
    private MeshResult() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final <R extends java.lang.Object>com.appvalence.hayatkurtar.core.result.MeshResult<R> map(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, ? extends R> transform) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final <R extends java.lang.Object>com.appvalence.hayatkurtar.core.result.MeshResult<R> flatMap(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super T, ? extends com.appvalence.hayatkurtar.core.result.MeshResult<? extends R>> transform) {
        return null;
    }
    
    public final boolean isSuccess() {
        return false;
    }
    
    public final boolean isError() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final T getOrNull() {
        return null;
    }
    
    public final T getOrThrow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.appvalence.hayatkurtar.core.result.MeshException errorOrNull() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\t\u0010\b\u001a\u00020\u0004H\u00c6\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshResult$Error;", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "exception", "Lcom/appvalence/hayatkurtar/core/result/MeshException;", "(Lcom/appvalence/hayatkurtar/core/result/MeshException;)V", "getException", "()Lcom/appvalence/hayatkurtar/core/result/MeshException;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "core_debug"})
    public static final class Error extends com.appvalence.hayatkurtar.core.result.MeshResult {
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.core.result.MeshException exception = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.core.result.MeshException exception) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.core.result.MeshException getException() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.core.result.MeshException component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.core.result.MeshResult.Error copy(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.core.result.MeshException exception) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00028\u0001H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u0001H\u00c6\u0001\u00a2\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0013\u0010\u0003\u001a\u00028\u0001\u00a2\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/appvalence/hayatkurtar/core/result/MeshResult$Success;", "T", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "data", "(Ljava/lang/Object;)V", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "component1", "copy", "(Ljava/lang/Object;)Lcom/appvalence/hayatkurtar/core/result/MeshResult$Success;", "equals", "", "other", "", "hashCode", "", "toString", "", "core_debug"})
    public static final class Success<T extends java.lang.Object> extends com.appvalence.hayatkurtar.core.result.MeshResult<T> {
        private final T data = null;
        
        public Success(T data) {
        }
        
        public final T getData() {
            return null;
        }
        
        public final T component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.core.result.MeshResult.Success<T> copy(T data) {
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
}