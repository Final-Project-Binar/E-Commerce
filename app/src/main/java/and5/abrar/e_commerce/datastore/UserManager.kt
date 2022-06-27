package and5.abrar.e_commerce.datastore

import and5.abrar.e_commerce.R
import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context : Context) {

    private  val dataStore: DataStore<Preferences>  = context.createDataStore("login-prefs")
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    companion object {
        const val USER_TOKEN = "access_token"
        val EMAIL = preferencesKey<String>("EMAIL")
        val NAME = preferencesKey<String>("NAME")
        val ACCESS_TOKEN = preferencesKey<String>("ACCESS_TOKEN")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")

    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        .apply()
    }

        fun logout(){
        val edit = prefs.edit()
        edit
            .clear()
            .apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    suspend fun saveToken(
        email: String,
        name: String,
        accessToken: String,
        password: String
    ) {
        dataStore.edit {
            it[EMAIL] = email
            it[NAME] = name
            it[ACCESS_TOKEN] = accessToken
            it[PASSWORD] = password
        }
    }

    suspend fun setBoolean(boolean: Boolean) {
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

    suspend fun clearToken() {
        dataStore.edit {
            it.clear()
        }
    }

    val email: Flow<String> = dataStore.data.map {
        it[EMAIL] ?: ""
    }

    val name: Flow<String> = dataStore.data.map {
        it[NAME] ?: ""
    }

    val accessToken: Flow<String> = dataStore.data.map {
        it[ACCESS_TOKEN] ?: ""
    }

    val ceklogin: Flow<Boolean> = dataStore.data.map {
        it[BOOLEAN] ?: false
    }

    val password: Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }


}