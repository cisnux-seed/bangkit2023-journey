package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode

class SettingsFragment : PreferenceFragmentCompat() {
    private val dailyReminder by lazy {
        DailyReminder()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val prefKeyDark = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        prefKeyDark?.setOnPreferenceChangeListener { _, newValue ->
            val uiMode = newValue as String
            updateTheme(
                nightMode = when (uiMode) {
                    requireActivity().getString(R.string.pref_dark_on) -> NightMode.ON
                    requireActivity().getString(R.string.pref_dark_off) -> NightMode.OFF
                    else -> NightMode.AUTO
                }
            )
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener { _, newValue ->
            val selected = newValue as Boolean
            if (selected) {
                dailyReminder.setDailyReminder(requireActivity())
            } else {
                dailyReminder.cancelAlarm(requireActivity())
            }
            true
        }
    }

    private fun updateTheme(nightMode: NightMode): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode.value)
        requireActivity().recreate()
        return true
    }
}