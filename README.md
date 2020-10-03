# Running Tracker
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.4.10-blue.svg)](https://kotlinlang.org) [![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://www.android.com/versions/lollipop-5-0/) [![Ktlint](https://camo.githubusercontent.com/5652fd33142bf88d0f46018325126931fe65d01d/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f636f64652532307374796c652d2545322539442541342d4646343038312e737667)](https://github.com/hamzasharuf/RunningTracker) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Running Tracker app allows you to track your run in either foreground or background. It uses the Google Maps SDK, MVVM Architecture, Dagger-Hilt, Navigation Components, Coroutines and a Room database.

## Table of Contents
- [Demo](#Demo)
- [Prerequisites](#Prerequisites)
- [Installation](#Installation)
- [Features](#Features)
- [Todo List](#Todo-List)
- [Contribution](#Contribution)
- [License](#License)

## Demo
<img src="images/01.gif" width=300 /> <img src="images/02.gif" width=300 /> <img src="images/03.gif" width=300 />

## Prerequisites
* Android studio
* Android device - or emulator - with Android Lollipop or above (v.21)
* Google Maps Api Key

## Installation
1. Clone the repository or download all files.
2. Get API key for google map API. Instructions can be found at [Google Developpers](https://developers.google.com/maps/documentation/javascript/get-api-key) 
3. Copy and paste api key into AndroidManifest.xml located in app/src/main folder.
4. Run the app on virtual device.

## Features
* Room Database
* MVVM
* Kotlin Coroutines
* Google Maps
* Dagger Hilt
* Navigation Components
* Foreground Service
* Responsive Layout

## Todo List
* Clean Architecture
* DataStore [Replace Shared Preferences]
* Dark Mode
* DataBinding


## Contribution
### Would you like to contribute code?
1. [Fork RunningTracker](https://github.com/hamzasharuf/RunningTracker).
2. Create a new branch ([using GitHub](https://help.github.com/articles/creating-and-deleting-branches-within-your-repository/)) or the command `git checkout -b branch-name develop`).
3. [Start a pull request](https://github.com/hamzasharuf/RunningTracker/compare). Reference [existing issues](https://github.com/hamzasharuf/RunningTracker/issues) when possible.





## License

    Copyright 2020 Hamza Sharaf

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

