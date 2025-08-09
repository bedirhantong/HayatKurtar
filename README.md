# HayatKurtar (Offline Bluetooth Chat)

- Kotlin 1.9.22, AGP 8.2.1, Gradle 8.2.1, Java 17
- Jetpack Compose, Hilt, Room, Coroutines, Navigation
- Offline Bluetooth (classic + BLE placeholder), AES-GCM via Tink, X25519 DH

## Build

```bash
./gradlew clean assembleDebug
```

Android Studio ile açıp çalıştırabilirsiniz.

## Mimari

- presentation: Compose ekranları, ViewModel
- domain: entity, repository interface, use-case
- data: repository implementasyonu, bluetooth, crypto, room

## İzinler

Manifest içinde Android 12+ ve altı için gerekli Bluetooth/konum izinleri eklidir. Runtime izin isteme (UI) örneği basit tutulmuştur.

## Test

```bash
./gradlew test
```

- `SendMessageUseCaseTest` temel delege davranışını doğrular (Bluetooth mock'lu yaklaşım).

## Notlar

- Demo amaçlı Bluetooth tarama/bağlantı ve mesajlaşma akışı minimal tutuldu, gerçek cihazda GATT/RFCOMM akışı eklenmelidir.
- AES-GCM için Tink kullanılır; anahtarlar Android Keystore ile korunur.

