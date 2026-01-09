package com.example.bookexplorer.data.preferences

import android.content.Context
import android.content.SharedPreferences

class ThemeManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "theme_prefs",
        Context.MODE_PRIVATE
    )

    private val KEY_DARK_MODE = "dark_mode"

    fun getDarkModePreference(): Boolean? {
        if (!prefs.contains(KEY_DARK_MODE)) {
            return null
        }
        return prefs.getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
    }

    fun toggleDarkMode() {
        val current = getDarkModePreference() ?: false
        setDarkMode(!current)
    }
}
