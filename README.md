# New York Times Reader

An Android application that displays the latest technology articles from The New York Times. Built with modern Android development practices including Jetpack Compose, Clean Architecture, and offline-first capabilities.

## Features

- **Article List**: Displays the latest articles from the NYT "tech" topic with images, titles, and abstracts
- **Offline Support**: Implements a cache-first strategy - loads articles from local database when cache is valid, fetches from network when expired or empty
- **Pull to Refresh**: Swipe down to force refresh articles from the network
- **Article Detail**: View full article details including title, abstract, author, and featured image

https://github.com/user-attachments/assets/625bf7cb-7cc0-42e7-886b-58c5b92d4f70



## Architecture

This app follows a **3-layered Clean Architecture** pattern. The main logic is being tested using unit tests and TDD.

### Presentation Layer
- Single Activity with Jetpack Compose for UI and using Jetpack Navigation
- MVVM pattern with `ViewModel` and `LiveData`
- Navigation Compose for screen navigation

### Domain Layer
- Pure Kotlin layer containing business logic
- Use cases: `GetArticlesUseCase`, `RefreshArticlesUseCase`, `GetArticleDetailUseCase`
- Domain models independent of data sources

### Data Layer
- Repository pattern orchestrating data from multiple sources
- Remote data source: Retrofit + RxJava for NYT API calls
- Local data source: Room database for offline storage
- Cache policy using SharedPreferences to determine data freshness

## Project Structure

```
app/src/main/java/com/newyorktimesreader/
├── data/
│   ├── di/                          # Hilt modules (Network, Database, Repository)
│   ├── repositories/
│   │   ├── ArticlesRepositoryImpl.kt
│   │   └── cachepolicy/             # Cache validation logic
│   └── source/
│       ├── database/                # Room entities, DAOs
│       └── remote/                  # Retrofit service, response models
├── domain/
│   ├── di/                          # Use case modules
│   ├── model/                       # Domain models
│   ├── repository/                  # Repository interfaces
│   ├── GetArticlesUseCase.kt
│   ├── GetArticleDetailUseCase.kt
│   └── RefreshArticlesUseCase.kt
├── presentation/
│   ├── common/                      # Shared components (BaseViewModel and TopBar)
│   ├── detail/                      # Detail screen
│   ├── home/                        # Home screen with article list
│   ├── navigation/                  # Navigation setup
│   └── theme/                       # Material theme configuration
├── MainActivity.kt
└── NYTApplication.kt
```

## Setup

### Prerequisites
- Android Studio Ladybug or newer
- JDK 11+
- Android SDK 23+ (minSdk)

### API Key Configuration

1. Obtain an API key from [NYT Developer Portal](https://developer.nytimes.com/)
2. Create or edit `local.properties` in the project root:
   ```properties
   NYT_API_KEY=your_api_key_here
   ```
3. The API key is injected at build time via `BuildConfig.NYT_API_KEY`

## Cache Strategy

The app implements an offline-first approach:

1. On app launch, check if cached data exists and is valid
2. If cache is valid, return data from Room database
3. If cache is invalid or empty, fetch from NYT API
4. Store fetched articles in Room and update cache timestamp
5. Pull-to-refresh always fetches from network and updates cache

## Requirements

- **Compile SDK**: 36
- **Min SDK**: 23 (Android 6.0)
- **Target SDK**: 36
