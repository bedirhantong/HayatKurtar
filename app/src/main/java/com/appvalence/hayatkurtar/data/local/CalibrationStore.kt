package com.appvalence.hayatkurtar.data.local

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.appvalence.hayatkurtar.domain.model.CalibrationParams
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "calibration_store"
private val Context.dataStore by preferencesDataStore(name = STORE_NAME)

@Singleton
class CalibrationStore @Inject constructor(private val context: Context) {
    private val KEY_MEASURED = intPreferencesKey("measured_power")
    private val KEY_N = doublePreferencesKey("path_loss_exponent")
    private val KEY_ALPHA = doublePreferencesKey("smoothing_alpha")

    val params: Flow<CalibrationParams> = context.dataStore.data.map { p ->
        CalibrationParams(
            measuredPower = p[KEY_MEASURED] ?: -59,
            pathLossExponent = p[KEY_N] ?: 2.0,
            smoothingAlpha = p[KEY_ALPHA] ?: 0.5,
        )
    }

    suspend fun save(params: CalibrationParams) {
        context.dataStore.edit { p ->
            p[KEY_MEASURED] = params.measuredPower
            p[KEY_N] = params.pathLossExponent
            p[KEY_ALPHA] = params.smoothingAlpha
        }
    }
}


