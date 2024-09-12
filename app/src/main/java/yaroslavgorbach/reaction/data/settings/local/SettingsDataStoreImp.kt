package yaroslavgorbach.reaction.data.settings.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStoreImp : SettingsDataStore {

    companion object {
        private val IS_FIRST_APP_OPEN_KEY = booleanPreferencesKey("IS_FIRST_APP_OPEN_KEY")
        private val ADD_COUNTER_KEY = intPreferencesKey("ADD_COUNTER_KEY")
    }

    override fun observeIsFirstAppOpen(context: Context): Flow<Boolean> {
        return context.settingsDataStore.data
            .map { prefs ->
                prefs[IS_FIRST_APP_OPEN_KEY] ?: true
            }
    }

    override suspend fun changeIsFirstAppOpen(context: Context, isFirstAppOpen: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[IS_FIRST_APP_OPEN_KEY] = isFirstAppOpen
        }
    }

    override suspend fun incAddCounter(context: Context) {
        context.settingsDataStore.edit { prefs ->
            prefs[ADD_COUNTER_KEY] = prefs[ADD_COUNTER_KEY]?.plus(1) ?: 0
        }
    }

    override suspend fun resetAddCounter(context: Context) {
        Log.v("dasdasd", "resetAddCounter")
        context.settingsDataStore.edit { prefs ->
            prefs[ADD_COUNTER_KEY] = 0
        }
    }

    override fun getAddCounter(context: Context): Flow<Int> {
        return context.settingsDataStore.data
            .map { prefs -> prefs[ADD_COUNTER_KEY] ?: 0 }
    }
}