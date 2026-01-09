# 📚 BookExplorer

Aplikacja Android do przeglądania książek z wykorzystaniem Open Library API. Aplikacja umożliwia przeglądanie listy książek, wyświetlanie szczegółów oraz zarządzanie ulubionymi pozycjami.

## 🎯 Funkcjonalności

- ✅ Przeglądanie listy książek z kategorii "fiction"
- ✅ Wyświetlanie szczegółów książki (tytuł, autorzy, okładka, opis, rok publikacji)
- ✅ Dodawanie/usuwanie książek z ulubionych
- ✅ Przeglądanie listy ulubionych książek
- ✅ Nawigacja między ekranami
- ✅ Obsługa stanów ładowania i błędów

## 🛠️ Technologie

### Język i Platforma
- **Kotlin 2.0.21** - język programowania
- **Android SDK** - minSdk 26, targetSdk 35, compileSdk 36

### UI Framework
- **Jetpack Compose** - nowoczesny framework UI
- **Material Design 3** - system designu
- **Navigation Compose 2.7.7** - nawigacja między ekranami

### Biblioteki
- **Retrofit 2.9.0** - klient HTTP do komunikacji z API
- **Gson Converter 2.9.0** - konwersja JSON ↔ obiekty Kotlin
- **Coil 2.5.0** - ładowanie i wyświetlanie obrazów
- **ViewModel Compose 2.7.0** - zarządzanie stanem UI
- **Lifecycle Runtime KTX 2.10.0** - obsługa cyklu życia

### Architektura
- **MVVM (Model-View-ViewModel)** - wzorzec architektoniczny
- **Repository Pattern** - abstrakcja nad źródłem danych
- **StateFlow** - reaktywny strumień danych
- **Kotlin Coroutines** - operacje asynchroniczne

### Przechowywanie danych
- **SharedPreferences** - lokalne przechowywanie ulubionych książek

### API
- **Open Library API** - https://openlibrary.org/

## 📁 Struktura projektu
