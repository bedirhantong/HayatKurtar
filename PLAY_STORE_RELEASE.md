# HayatKurtar - Play Store Release Information

## App Description

### Short Description
Offline emergency communication through mesh networking. Connect when infrastructure fails.

### Full Description

**HayatKurtar (Life Saver)** - Emergency Mesh Network Communication

When disasters strike and traditional communication infrastructure fails, HayatKurtar creates a resilient mesh network using Bluetooth and Wi-Fi Direct to keep you connected with others.

🌐 **Offline Mesh Networking**
• Multi-hop message routing without internet
• Automatic relay through intermediate devices
• Self-healing network that adapts to changes

🛡️ **Military-Grade Security**
• End-to-end encryption with Ed25519/X25519
• Perfect forward secrecy
• No data stored on external servers

🆘 **Emergency Features**
• SOS broadcasting with priority routing
• Extended range for emergency messages
• Automatic relay by all nearby devices

🔋 **Battery Optimized**
• Adaptive scanning based on battery level
• Efficient background operation
• Smart transport selection

🌍 **Dual Connectivity**
• Bluetooth Classic for low power
• Wi-Fi Direct for high throughput
• Simultaneous multi-transport operation

**Perfect for:**
• Natural disasters (earthquakes, floods, hurricanes)
• Remote area communication
• Search and rescue operations
• Community emergency coordination
• Off-grid adventures

**Technical Highlights:**
• Clean Architecture with SOLID principles
• Modern Android development (Jetpack Compose, Kotlin)
• Open source and privacy-focused
• Works completely offline
• No registration or accounts required

HayatKurtar means \"Life Saver\" in Turkish - because in times of crisis, communication saves lives.

## Keywords
emergency, mesh network, offline, bluetooth, wifi direct, disaster, communication, SOS, rescue, earthquake, privacy, encryption, p2p

## App Category
Communication

## Content Rating
Everyone

## Target Audience
- Emergency responders
- Community safety coordinators
- Outdoor enthusiasts
- Travelers to remote areas
- Privacy-conscious users
- Disaster preparedness communities

## Key Features List
- Offline mesh networking
- End-to-end encryption
- SOS emergency broadcasting
- Multi-hop message routing
- Bluetooth + Wi-Fi Direct
- Battery optimization
- Open source
- No internet required
- Self-healing network
- Zero data collection

## Screenshots Needed
1. Main chat interface showing mesh connections
2. Device discovery screen with nearby peers
3. SOS emergency message interface
4. Network status showing multi-hop routing
5. Security/encryption settings
6. Mesh network visualization

## Promotional Graphics
- Feature graphic (1024x500)
- Phone screenshots (multiple sizes)
- Tablet screenshots (if applicable)
- App icon (512x512)

## Privacy Information
- Privacy Policy: Available in PRIVACY_POLICY.md
- Data collected: Device names, local messages only
- Data sharing: None (offline operation)
- Data retention: User controlled
- Encryption: End-to-end with local storage

## Permissions Justification

**Bluetooth Permissions:**
- BLUETOOTH_SCAN: Find nearby emergency communication devices
- BLUETOOTH_CONNECT: Establish peer connections for mesh network
- BLUETOOTH_ADVERTISE: Make device discoverable for emergency contact

**Wi-Fi Permissions:**
- ACCESS_WIFI_STATE: Check Wi-Fi Direct availability
- CHANGE_WIFI_STATE: Enable Wi-Fi Direct for extended range communication
- NEARBY_WIFI_DEVICES: Discover Wi-Fi Direct peers without location

**Location Permission:**
- ACCESS_FINE_LOCATION: Required by Android for Wi-Fi Direct peer discovery (API ≤32)

**Service Permissions:**
- FOREGROUND_SERVICE: Maintain emergency communication in background
- WAKE_LOCK: Ensure reliable message delivery

## Version History

### Version 1.0.0 (Initial Release)
- Complete offline mesh networking implementation
- Bluetooth Classic and Wi-Fi Direct support
- End-to-end encryption with Ed25519/X25519
- SOS emergency broadcasting
- Multi-hop message routing
- Battery optimization
- Turkish and English localization
- Privacy-focused design (no data collection)

## Release Checklist

- [x] App builds successfully
- [x] All mesh networking features tested
- [x] End-to-end encryption verified
- [x] ProGuard rules configured
- [x] Privacy policy created
- [x] Multilingual support (English/Turkish)
- [x] App permissions documented
- [ ] App icons optimized
- [ ] Screenshots captured
- [ ] Feature graphic created
- [ ] Release APK signed
- [ ] Play Console metadata uploaded

## Support Information

**Developer Contact:**
- GitHub: https://github.com/appvalence/hayatkurtar
- Issues: https://github.com/appvalence/hayatkurtar/issues
- Email: support@appvalence.com

**Documentation:**
- Architecture: ARCHITECTURE.md
- Privacy Policy: PRIVACY_POLICY.md
- User Guide: README.md

## Legal Information

**License:** MIT License
**Copyright:** 2024 AppValence
**Disclaimer:** This app is designed for emergency communication. Always contact official emergency services when possible.

---

**Note:** This app operates completely offline and does not collect or transmit any personal data to external servers. All communication is peer-to-peer and encrypted locally.