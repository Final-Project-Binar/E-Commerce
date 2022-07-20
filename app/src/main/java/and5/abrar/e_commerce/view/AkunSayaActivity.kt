@file:Suppress("DEPRECATION")

package and5.abrar.e_commerce.view

import and5.abrar.e_commerce.R
import and5.abrar.e_commerce.view.buyer.NotifikasiBuyerActivity
import and5.abrar.e_commerce.view.buyer.WishListActivity
import and5.abrar.e_commerce.view.seller.DaftarJualActivity
import and5.abrar.e_commerce.view.seller.LengkapiDetailProductActivity
import and5.abrar.e_commerce.viewmodel.ViewModelHome
import and5.abrar.e_commerce.viewmodel.ViewModelProductSeller
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_akun_saya.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class AkunSayaActivity : AppCompatActivity() {

    private lateinit var  userManager: and5.abrar.e_commerce.datastore.UserManager
    // create a CancellationSignal variable and assign a value null to it
    private var cancellationSignal: CancellationSignal? = null
    private var email : String = ""
    private var pass : String = ""
    // create an authenticationCallback
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            // here we need to implement two methods
            // onAuthenticationError and onAuthenticationSucceeded
            // If the fingerprint is not recognized by the app it will call
            // onAuthenticationError and show a toast
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                notifyUser("Authentication Error : $errString")
            }

            // If the fingerprint is recognized by the app then it will call
            // onAuthenticationSucceeded and show a toast that Authentication has Succeed
            // Here you can also start a new activity after that
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                GlobalScope.launch {
                    userManager.finger(email,pass)
                }
                notifyUser("Pendaftaran FingerPrint Berhasil")
            }
        }
    private val bottomNavigasi = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.notifikasi -> {
                startActivity(Intent(this, NotifikasiBuyerActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.dashboard -> {
                startActivity(Intent(this, HomeActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.jual -> {
                startActivity(Intent(this, LengkapiDetailProductActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.akun -> {
                Toast.makeText(this, "Kamu Sedang Berada Di Akun", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener false
            }
            R.id.daftar_jual -> {
                startActivity(Intent(this, DaftarJualActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akun_saya)
        val botnav = findViewById<BottomNavigationView>(R.id.navigation)
        botnav.setOnNavigationItemSelectedListener(bottomNavigasi)
        userManager = and5.abrar.e_commerce.datastore.UserManager(this)
        checkBiometricSupport()
        val viewModelDataSeller = ViewModelProvider(this)[ViewModelProductSeller::class.java]
        viewModelDataSeller.getSeller(token = userManager.fetchAuthToken().toString())
        viewModelDataSeller.seller.observe(this) {
            Glide.with(applicationContext).load(it.imageUrl).into(icon_foto)
            username_akunsaya.text = it.fullName
        }
        userManager.email.asLiveData().observe(this){
            email = it
        }
        userManager.password.asLiveData().observe(this){
            pass = it
        }
        userManager.ceklogin.asLiveData().observe(this){
            if (it == true){
                akunsaya_login.isInvisible = true
                keluar()
                akunsaya_favorite.isVisible = true
                onBiometric.isInvisible = true
            }else{
                akunsaya_btnkeluar.isInvisible = true
                akunsaya_login.isVisible = true
                akunsaya_favorite.isInvisible = true
                onBiometric.isVisible = true
                akunsaya_login.setOnClickListener{
                    startActivity(Intent(this@AkunSayaActivity, LoginActivity::class.java))
                }
            }
        }
        ubahAkun()
        changePassword()
        fingerprint()
        goToFavorite()
    }

    private fun keluar(){
        val viewModel = ViewModelProvider(this)[ViewModelHome::class.java]
        val dataUserManager = and5.abrar.e_commerce.datastore.UserManager(this)
        akunsaya_btnkeluar.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("KONFIRMASI LOGOUT")
                .setMessage("Anda Yakin Ingin Logout ?")

                .setPositiveButton("YA"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(this@AkunSayaActivity, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        dataUserManager.setBoolean(false)
                        dataUserManager.logout()
                        dataUserManager.clearPreview()
                        viewModel.deleteoffline()
                        startActivity(Intent(this@AkunSayaActivity, HomeActivity::class.java))
                        finish()
                    }
                }
                .setNegativeButton("TIDAK"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(this, "Tidak Jadi Keluar", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }

                .setNeutralButton("NANTI"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    Toast.makeText(this, "Tidak Jadi Logout", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }

    private fun ubahAkun(){
        ubah_akun.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
    private fun fingerprint(){
        onBiometric.setOnClickListener {
            GlobalScope.launch {
                userManager.clearfinger()
            }
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Tekan Jari Anda ke fingerprint")
                .setNegativeButton("Cancel", this.mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                    notifyUser("Authentication Cancelled")
                }).build()

            // start the authenticationCallback in mainExecutor
            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }
    }
    private fun changePassword(){
        pengaturanAkun.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
    }
    private fun goToFavorite(){
        goToFavorite.setOnClickListener {
            startActivity(Intent(this, WishListActivity::class.java))
        }
    }
    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was Cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

    // it checks whether the app the app has fingerprint permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isDeviceSecure) {
            onBiometric.isInvisible = true
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    // this is a toast method which is responsible for showing toast
    // it takes a string as parameter
    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}