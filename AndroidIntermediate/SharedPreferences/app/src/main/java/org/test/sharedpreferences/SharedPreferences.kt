package org.test.sharedpreferences

import android.content.Context

class SharedPreferences(private val context: Context) {
    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val color: String
        get() {
            return preference.getString(COLOR, null) ?: "BLACK"
        }

    val number: Int
        get() {
            return preference.getInt(NUMBER, 0)
        }

    fun setColor(color: String) {
        val editor = preference.edit().apply {
            putString(COLOR, color)
        }
        editor.apply()
    }

    fun setNumber(number: Int) {
        val editor = preference.edit().apply {
            putInt(NUMBER, number)
        }
        editor.apply()
    }

    fun clear() =
        preference.edit().apply {
            remove(COLOR)
            remove(NUMBER)
        }.apply()

    companion object {
        private const val PREFS_NAME = "colorPreferences"
        private const val NUMBER = "number"
        private const val COLOR = "color"
    }
}