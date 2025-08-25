package com.appvalence.hayatkurtar.data.permissions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.appvalence.hayatkurtar.domain.permissions.*;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0002\u00a8\u0006\u0004"}, d2 = {"toManifestPermissions", "", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "mesh_debug"})
public final class AndroidPermissionManagerKt {
    
    /**
     * Extension to convert domain permission to Android manifest permissions
     */
    private static final java.util.List<java.lang.String> toManifestPermissions(com.appvalence.hayatkurtar.domain.permissions.MeshPermission $this$toManifestPermissions) {
        return null;
    }
}