# Simple APNs
Simple send APNs Push library for Java/Kotlin

This library was written to send VoIP Push notifications, which are not supported by FCM.

## Usage

### Create APNs Configuration
```kotlin
val p8Path = "{P8_KEY_PATH}"
val keyId  = "{KEY_ID}"
val teamId = "{TEAM_ID}"
// Whether to use the production server. if install app is app store or test flight, set true
val isProduction = false 

val configuration = SimpleApnsConfiguration(p8Path, teamId, keyId, isProduction)
```

### Create Client
```kotlin
val client = SimpleApnsClient(configuration)
```

### Send Push
```kotlin
val token  = "{TOKEN}"
val topic  = "{TOPIC}"

val push = DefaultSimpleApnsPushDetails(
    token = token,
    topic = topic,
    payload = mapOf(
        "from" to "SKAIBlue"
    ),
    // Default ALERT
    // If you want to send VoIP Push, set to ApnsPushType.VOIP
    type = ApnsPushType.VOIP
)

client.send(push).thenAccept {
    // Success
}.exceptionally {
    // Failed
    println(it.message)
}.join()

```