package and5.abrar.e_commerce.datastore

import and5.abrar.e_commerce.R
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context : Context) {

    private  val dataStore: DataStore<Preferences>  = context.createDataStore("login-prefs")
    private val finger : DataStore<Preferences> = context.createDataStore("fingerprint")
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    companion object {
        const val USER_TOKEN = "access_token"
        const val USERNAME = "USERNAME"
        const val IMAGEUSER = "IMAGEUSER"
        const val KOTA = "KOTA"
        val EMAIL = preferencesKey<String>("EMAIL")
        val NAME = preferencesKey<String>("NAME")
        val HARGA = preferencesKey<String>("HARGA")
        val KATEGORI = preferencesKey<String>("KATEGORI")
        val LOKASI = preferencesKey<String>("LOKASI")
        val DESC = preferencesKey<String>("DESC")
        val IMAGE = preferencesKey<String>("IMAGE")
        val ACCESS_TOKEN = preferencesKey<String>("ACCESS_TOKEN")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val BOOLEAN = preferencesKey<Boolean>("BOOLEAN")
    }
    suspend fun preview(
        nama : String,
        harga : String,
        kategori : String,
        deskripsi : String,
        lokasi : String,
        image : String
    ){
        dataStore.edit {
            it[NAME] = nama
            it[HARGA] = harga
            it[KATEGORI] = kategori
            it[LOKASI] = lokasi
            it[DESC] = deskripsi
            it[IMAGE] = image
        }
    }
    suspend fun logindata(
        email: String,
    password: String
    ){
        dataStore.edit {
            it[EMAIL] = email
            it[PASSWORD]= password
        }
    }
    suspend fun finger(
        email : String,
        password : String
    ){
        finger.edit {
            it[EMAIL] = email
            it[PASSWORD] = password
        }
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
    fun datasellerusername(): String?{
        return prefs.getString(USERNAME,null)
    }

    fun datasellerimage(): String? {
        return prefs.getString(IMAGEUSER,null)
    }

    fun datasellerkota():String? {
        return prefs.getString(KOTA,null)
    }

    suspend fun setBoolean(boolean: Boolean) {
        dataStore.edit {
            it[BOOLEAN] = boolean
        }
    }

    suspend fun clearfinger(){
        finger.edit {
            it.clear()
        }
    }

    suspend fun clearPreview() {
        dataStore.edit {
            it.clear()
        }
    }

    val fingerEmail : Flow<String> = finger.data.map {
        it[EMAIL] ?: ""
    }

    val fingerPassword : Flow<String> = finger.data.map{
        it[PASSWORD] ?: ""
    }

    val email: Flow<String> = dataStore.data.map {
        it[EMAIL] ?: ""
    }

    val name: Flow<String> = dataStore.data.map {
        it[NAME] ?: ""
    }

    val harga : Flow<String> = dataStore.data.map {
        it[HARGA] ?: ""
    }

    val kategori: Flow<String> = dataStore.data.map {
        it[KATEGORI] ?: ""
    }

    val lokasi: Flow<String> = dataStore.data.map {
        it[LOKASI] ?: ""
    }

    val deskripsi: Flow<String> = dataStore.data.map {
        it[DESC] ?: ""
    }

    val gambar: Flow<String> = dataStore.data.map {
        it[IMAGE] ?: ""
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