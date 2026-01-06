package com.example.bookexplorer.data.preferences

import android.content.Context
import android.content.SharedPreferences

class FavoritesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "favorites_prefs",
        Context.MODE_PRIVATE
    )

    private val KEY_FAVORITES = "favorites"

    fun addFavorite(id: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.add(id)
        saveFavorites(favorites)
    }

    fun removeFavorite(id: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.remove(id)
        saveFavorites(favorites)
    }

    fun isFavorite(id: String): Boolean {
        return getFavorites().contains(id)
    }

    fun getFavorites(): Set<String> {
        return prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    private fun saveFavorites(favorites: Set<String>) {
        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply()
    }
}

