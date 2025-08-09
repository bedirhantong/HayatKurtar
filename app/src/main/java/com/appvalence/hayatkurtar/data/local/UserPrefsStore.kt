package com.appvalence.hayatkurtar.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dagger.hilt.android.qualifiers.ApplicationContext

private const val PREFS_NAME = "user_prefs_store"
private val Context.userPrefsDataStore by preferencesDataStore(name = PREFS_NAME)

@Singleton
class UserPrefsStore @Inject constructor(@ApplicationContext private val context: Context) {
    private object Keys {
        val DISCOVERABLE_PROMPT_SHOWN: Preferences.Key<Boolean> = booleanPreferencesKey("discoverable_prompt_shown")
        val AUTO_VISIBILITY: Preferences.Key<Boolean> = booleanPreferencesKey("auto_visibility")
    }

    val discoverablePromptShown: Flow<Boolean> = context.userPrefsDataStore.data.map { p ->
        p[Keys.DISCOVERABLE_PROMPT_SHOWN] ?: false
    }

    val autoVisibilityEnabled: Flow<Boolean> = context.userPrefsDataStore.data.map { p ->
        p[Keys.AUTO_VISIBILITY] ?: false
    }

    suspend fun setDiscoverablePromptShown(shown: Boolean) {
        context.userPrefsDataStore.edit { it[Keys.DISCOVERABLE_PROMPT_SHOWN] = shown }
    }

    suspend fun setAutoVisibilityEnabled(enabled: Boolean) {
        context.userPrefsDataStore.edit { it[Keys.AUTO_VISIBILITY] = enabled }
    }
}


