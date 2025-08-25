# HayatKurtar ProGuard Rules for Release Build

# Preserve line numbers for crash reports
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep generic signatures for reflection
-keepattributes Signature

# Keep all annotations
-keepattributes *Annotation*

# ================================
# Kotlin & Coroutines
# ================================

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# ================================
# Jetpack Compose
# ================================

# Keep Compose runtime classes
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep class androidx.compose.foundation.** { *; }

# Keep Compose compiler generated classes
-keep class **.*ComposableSingletons* { *; }
-keep class **.*LiveLiterals* { *; }

# ================================
# Hilt/Dagger
# ================================

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class **_HiltModules* { *; }
-keep class **_ComponentTreeDeps* { *; }
-keep class **_Factory* { *; }
-keep class **_MembersInjector* { *; }

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# ================================
# Room Database
# ================================

# Keep Room entities and DAOs
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keep class androidx.room.** { *; }

# ================================
# Mesh Networking Components
# ================================

# Keep mesh networking core classes
-keep class com.appvalence.hayatkurtar.core.** { *; }
-keep class com.appvalence.hayatkurtar.domain.** { *; }
-keep class com.appvalence.hayatkurtar.data.mesh.** { *; }
-keep class com.appvalence.hayatkurtar.data.transport.** { *; }

# Keep protocol frame structure
-keep class com.appvalence.hayatkurtar.core.protocol.Frame { *; }
-keep class com.appvalence.hayatkurtar.core.protocol.FrameType { *; }
-keep class com.appvalence.hayatkurtar.core.protocol.Priority { *; }

# Keep transport interfaces
-keep interface com.appvalence.hayatkurtar.domain.transport.** { *; }

# ================================
# Cryptography
# ================================

# Keep Tink crypto classes
-keep class com.google.crypto.tink.** { *; }
-dontwarn com.google.crypto.tink.**

# Keep Android Security Crypto
-keep class androidx.security.crypto.** { *; }

# Keep crypto manager classes
-keep class com.appvalence.hayatkurtar.core.crypto.** { *; }

# ================================
# Bluetooth & Wi-Fi
# ================================

# Keep Bluetooth classes
-keep class android.bluetooth.** { *; }
-keep class com.appvalence.bluetooth.** { *; }

# Keep Wi-Fi Direct classes
-keep class android.net.wifi.** { *; }
-keep class android.net.wifi.p2p.** { *; }

# ================================
# Serialization & Parceling
# ================================

# Keep Parcelable classes
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keep class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# ================================
# Error Handling & Debugging
# ================================

# Keep exception classes for crash reporting
-keep public class * extends java.lang.Exception

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ================================
# Android Components
# ================================

# Keep Services
-keep public class * extends android.app.Service
-keep public class * extends android.app.IntentService

# Keep BroadcastReceivers
-keep public class * extends android.content.BroadcastReceiver

# Keep Activities
-keep public class * extends android.app.Activity
-keep public class * extends androidx.activity.ComponentActivity

# ================================
# Reflection & Native Methods
# ================================

# Keep classes that use reflection
-keepclassmembers class * {
    @androidx.annotation.Keep <methods>;
    @androidx.annotation.Keep <fields>;
    @androidx.annotation.Keep <init>(...);
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# ================================
# General Rules
# ================================

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# Remove debug annotations
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void checkNotNull(java.lang.Object);
    public static void checkNotNull(java.lang.Object, java.lang.String);
    public static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    public static void checkNotNullParameter(java.lang.Object, java.lang.String);
    public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
    public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
}

# Optimization settings
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# Keep BuildConfig for version information
-keep class **.BuildConfig { *; }