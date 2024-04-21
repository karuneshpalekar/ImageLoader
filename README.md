<h1>ImageLoaderX</h1>
<h3>An Android Application showcasing an Asynchronous Image Loading Library and Pagination along with Clean Architecture along with Caching</h3>

Screenshots from the application : 

| ![list](preview/preview2.jpg) | ![list](preview/preview3.jpg) |
|----------|:----------:|


<h3>Architecture Used</h3>

![Architecture](preview/architecture-graph.png)

## Tech stack 

- [Kotlin][1] based
- [Coroutines][2] for asynchronous.
- [Paging][3] - Load and display pages of data from a larger dataset from local storage or over a network
- [Lifecycle][4] - Create a UI that automatically responds to lifecycle events.
- [LiveData][5] - Notify domain layer data to views.
- [Navigation][6] - Handle everything needed for in-app navigation.
- [ViewModel][7] - UI-related data holder, lifecycle aware.
- [Hilt][8] - For DI

[1]: https://kotlinlang.org/
[2]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[3]: https://developer.android.com/topic/libraries/architecture/paging/v3-overview
[4]: https://developer.android.com/topic/libraries/architecture/lifecycle
[5]: https://developer.android.com/topic/libraries/architecture/livedata
[6]: https://developer.android.com/jetpack/compose/navigation
[7]: https://developer.android.com/topic/libraries/architecture/viewmodel
[8]: https://developer.android.com/training/dependency-injection/hilt-android

## Features

- Asynchronous Image Loading Library
- Pagination 
- Local Caching
- MVVM Architecture
- Dependency injection with Hilt

## Future Scope
  
- Testing 
- Code Optimisations


## Tested on devices 

- Oneplus 9 pro 
- Samsung S20 FE
- Samsung A31 
- Samsung M31s 
- Pixel Emulators 
