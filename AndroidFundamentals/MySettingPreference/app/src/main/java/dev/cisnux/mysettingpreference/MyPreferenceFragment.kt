package dev.cisnux.mysettingpreference

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var NAME: String
    private lateinit var EMAIL: String
    private lateinit var AGE: String
    private lateinit var PHONE: String
    private lateinit var LOVE: String

    private lateinit var namePreference: EditTextPreference
    private lateinit var emailPreference: EditTextPreference
    private lateinit var agePreference: EditTextPreference
    private lateinit var phonePreference: EditTextPreference
    private lateinit var isLoveMuPreferences: CheckBoxPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        sh?.let {
            namePreference.summary = sh.getString(NAME, DEFAULT_VALUE)
            emailPreference.summary = sh.getString(EMAIL, DEFAULT_VALUE)
            agePreference.summary = sh.getString(AGE, DEFAULT_VALUE)
            phonePreference.summary = sh.getString(PHONE, DEFAULT_VALUE)
            isLoveMuPreferences.isChecked = sh.getBoolean(LOVE, false)
        }
    }

    private fun init() {
        NAME = getString(R.string.key_name)
        EMAIL = getString(R.string.key_email)
        AGE = getString(R.string.key_age)
        PHONE = getString(R.string.key_phone)
        LOVE = getString(R.string.key_love)

        namePreference = findPreference<EditTextPreference>(NAME) as EditTextPreference
        emailPreference = findPreference<EditTextPreference>(EMAIL) as EditTextPreference
        agePreference = findPreference<EditTextPreference>(AGE) as EditTextPreference
        phonePreference = findPreference<EditTextPreference>(PHONE) as EditTextPreference
        isLoveMuPreferences = findPreference<CheckBoxPreference>(LOVE) as CheckBoxPreference
    }

    companion object {
        private const val DEFAULT_VALUE = "Tidak Ada"
    }

    /**
     * Kode di atas digunakan untuk me-register
     * ketika aplikasi dibuka dan me-unregister
     * ketika aplikasi ditutup. Hal ini supaya listener
     * tidak berjalan terus menerus dan menyebabkan memory leak.
     * */
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    /**
     * Kode di atas digunakan untuk mengecek apakah
     * terjadi perubahan pada data yang tersimpan.
     * Jika terdapat value yang berubah maka akan memanggil
     * listener onSharedPreferenceChanged.
     * Untuk mendapatkan value tersebut, lakukan validasi terlebih dahulu.
     * Dengan mencocokkan key mana yang berubah, hasilnya juga akan berubah.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == NAME)
            namePreference.summary = sharedPreferences.getString(NAME, DEFAULT_VALUE)
        if (key == EMAIL)
            emailPreference.summary = sharedPreferences.getString(EMAIL, DEFAULT_VALUE)
        if (key == AGE)
            agePreference.summary = sharedPreferences.getString(AGE, DEFAULT_VALUE)
        if (key == PHONE)
            phonePreference.summary = sharedPreferences.getString(PHONE, DEFAULT_VALUE)
        if (key == LOVE)
            isLoveMuPreferences.isChecked = sharedPreferences.getBoolean(LOVE, false)
    }
}