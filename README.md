# Official Android SDK for [Stream Chat](https://getstream.io/chat/)

![Build status](https://github.com/GetStream/stream-chat-android/workflows/Build%20and%20test/badge.svg) ![GitHub release](https://img.shields.io/github/v/release/GetStream/stream-chat-android)

<p align="center">
  <a href="https://getstream.io/tutorials/android-chat/">
    <img src="/docs/android-chat-messaging-banner.png" width="100%" />
  </a>
</p>

This is the official Android SDK for [Stream Chat](https://getstream.io/chat), a service for building chat and messaging applications. This library includes both a low-level chat SDK and a set of reusable UI components. Most users start with the UI components, and fall back to the lower level API when they want to customize things.

<img align="right" src="https://getstream.imgix.net/images/chat-android/android_chat_art@1x.png?auto=format,enhance" width="50%" />

**Quick Links for getting started**

* [Register](https://getstream.io/chat/trial/) to get an API key for Stream Chat
* [Chat Tutorial (Kotlin)](https://getstream.io/tutorials/android-chat/#kotlin)
* [Chat Tutorial (Java)](https://getstream.io/tutorials/android-chat/#java)
* [Documentation (Kotlin)](https://getstream.io/chat/docs/android/?language=kotlin)
* [Documentation (Java)](https://getstream.io/chat/docs/android/?language=java)
* [API docs (Dokka)](https://getstream.github.io/stream-chat-android/)
* [Sample app](/stream-chat-android-ui-components-sample)
* [Wiki (Cookbooks/Docs)](https://github.com/GetStream/stream-chat-android/wiki)

## Java/Kotlin Chat Tutorial

The best place to start is the [Android Chat Tutorial](https://getstream.io/tutorials/android-chat/#kotlin). It teaches you how to use this SDK and also shows you how to make frequently required changes. You can use either [Java](https://getstream.io/tutorials/android-chat/#java) or [Kotlin](https://getstream.io/tutorials/android-chat/#kotlin) depending on your preference.

## Sample App

This repo includes a fully functional example app featuring threads, reactions, typing indicators, optimistic UI updates and offline storage. To run the sample app, start by cloning this repo:

```shell
git clone git@github.com:GetStream/stream-chat-android.git
```

Next, open [Android Studio](https://developer.android.com/studio) and open the newly created project folder. You'll want to run the [`stream-chat-android-ui-components-sample`](/stream-chat-android-ui-components-sample) app.

> The `stream-chat-android-sample` module contains the sample app for our previous UI implementation. 

## Docs

The official documentation for the Chat SDK is available [on our website](https://getstream.io/chat/docs/android/?language=kotlin). Each feature's page shows how to use it with the Android SDK, plus there are further Android-exclusive docs under the Android sections on the top.

The Chat Android SDKs support both Kotlin and Java usage, but *we strongly recommend using Kotlin*. The documentation is available in both languages.

You can also take a look at the [full API documentation](https://getstream.github.io/stream-chat-android/) (generated by Dokka).

This SDK consists of the following modules / artifacts:

- [Chat client](/stream-chat-android-client)
- [Offline support and `LiveData` APIs](/stream-chat-android-offline)
- [Chat UI/UX](/stream-chat-android-ui-components)

With these modules, the SDK provides:

- A low-level client for making API calls and receiving chat events
- Offline support and LiveData APIs module
- Ready to use ViewModels for displaying a list of channels and a conversation 
- Reusable chat views:
    - [Channel List View](https://getstream.io/chat/docs/android/channel_list_view/?language=kotlin)
    - [Channel List Header View](https://getstream.io/chat/docs/android/channel_list_header_view/?language=kotlin)
    - [Message List View](https://getstream.io/chat/docs/android/message_list_view/?language=kotlin)
    - [Message List Header View](https://getstream.io/chat/docs/android/message_list_header_view/?language=kotlin)
    - [Message Input View](https://getstream.io/chat/docs/android/message_input_view/?language=kotlin)
    - [Search Input View](https://getstream.io/chat/docs/android/search_input_view/?language=kotlin)
    - [Search Result List View](https://getstream.io/chat/docs/android/search_result_list_view/?language=kotlin)
    - [Attachment Gallery](https://getstream.io/chat/docs/android/attachmentgallery/?language=kotlin)
    
## Supported features

- Channels list UI
- Channel UI
- Message reactions
- Link preview
- Image, video and file attachments
- Editing and deleting messages
- Typing indicators
- Read indicators
- Push notifications
- Image gallery
- GIF support
- Light and dark themes
- Style customization
- UI customization
- Threads
- Slash commands
- Markdown message formatting
- Count for unread messages

## Installing the Kotlin/Java Chat SDK

**Step 1**: Add `mavenCentral` to your repositories in your *project* level `build.gradle` file:

```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```

**Step 2**: Add the library as a dependency in your *module* level `build.gradle` file:

> See the [releases page](https://github.com/GetStream/stream-chat-android/releases) for the latest version number.

```gradle
android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    // for Kotlin projects
    kotlinOptions {
        jvmTarget = '1.8'
    }
}

dependencies {
    implementation "io.getstream:stream-chat-android:$stream_version"

    // for Java projects
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
}
```

## Setup Stream Chat

Make sure to initialize the SDK only once; the best place to do this is in your `Application` class.

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val apiKey: String = ...
        val user = User().apply {
            id = ...
            image = ...
            name = ...
        }

        val client = ChatClient.Builder(apiKey, this).build()
        val domain = ChatDomain.Builder(client, user, this).offlineEnabled().build()
        val ui = ChatUI.Builder(this).build()
    }
}
```

With this, you will be able to retrieve instances of the different components from any part of your application using `instance()`. Here's an example:

```kotlin
class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chatClient = ChatClient.instance()
        val chatDomain = ChatDomain.instance()
        val chatUI = ChatUI.instance()
    }
}
```

## Online status

Connection status to Chat is available via `ChatDomain.instance().online` which returns a LiveData object you can attach observers to.

```kotlin
ChatDomain.instance().online.observe(...)
```

## Markdown support

Markdown support is based on [Markwon 4](https://github.com/noties/Markwon). The SDK doesn't support all `Markwon` features, support is limited to these plugins:

- [CorePlugin](https://noties.io/Markwon/docs/v4/core/core-plugin.html)
- [LinkifyPlugin](https://noties.io/Markwon/docs/v4/linkify/)
- [ImagesPlugin](https://noties.io/Markwon/docs/v4/image/)
- [StrikethroughPlugin](https://noties.io/Markwon/docs/v4/ext-strikethrough/)

If you want to use a library other than Markwon or extend the Markwon plugins, you can use the code below to customize Markdown rendering when you build your `ChatUI` instance:

```kotlin
val ui = ChatUI.Builder(context)
    .withMarkdown { textView, text ->
        // do custom rendering here
        textView.text = text
    }
    .build()
```

## Debug and development

### Logging

By default, logging is disabled. You can enable logs and set a log level when initializing `ChatClient`:

```kotlin
val client = ChatClient.Builder(apiKey, context)
    .logLevel(ChatLogLevel.ALL)
    .build()
```

If you need to intercept logs, you can also pass in your own `ChatLoggerHandler`:

```kotlin
val client = ChatClient.Builder(apiKey, context)
    .logLevel(ChatLogLevel.ALL)
    .loggerHandler(object : ChatLoggerHandler {
        override fun logD(tag: Any, message: String) {
            // custom logging
        }

        ...
    })
    .build()
```

To intercept socket errors:

```kotlin
client.subscribeFor<ErrorEvent> { errorEvent: ErrorEvent ->
    println(errorEvent)
}
```

All SDK log tags have the `Chat:` prefix, so you can filter for that those in the logs:

```bash
adb logcat com.your.package | grep "Chat:"
```

Here's a set of useful tags for debugging network communication:

- `Chat:Http`
- `Chat:Events`
- `Chat:SocketService`
