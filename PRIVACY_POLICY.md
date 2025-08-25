# Privacy Policy for HayatKurtar

**Last updated: December 2024**

## Introduction

HayatKurtar ("we", "our", or "us") is committed to protecting your privacy. This Privacy Policy explains how we collect, use, and safeguard your information when you use our emergency mesh networking mobile application (the "Service").

## Information We Collect

### Personal Information
- **Device Name**: You provide a device name to help other users identify you in the mesh network
- **Messages**: Chat messages you send and receive through the mesh network
- **Contact Lists**: Identity keys and verification status of contacts you communicate with

### Automatically Collected Information
- **Device Information**: Device model, operating system version, unique device identifiers
- **Network Information**: Bluetooth and Wi-Fi connectivity status, signal strength data
- **Usage Statistics**: Number of messages sent/received, network uptime, connection quality metrics

### Location Information
- **Approximate Location**: Wi-Fi Direct functionality requires location permissions for peer discovery
- **Note**: We do not track or store your precise location. Location permissions are used solely for Wi-Fi Direct device discovery as required by Android.

## How We Use Your Information

### Primary Purposes
- **Emergency Communication**: Enable offline mesh networking for emergency scenarios
- **Network Routing**: Route messages through multi-hop mesh network paths
- **Service Reliability**: Maintain stable connections and optimize network performance
- **Security**: Encrypt communications and verify contact identities

### Legal Basis
We process your information based on:
- **Legitimate Interest**: Providing emergency communication services
- **Consent**: When you voluntarily provide device names and contact information
- **Legal Obligation**: Compliance with applicable laws and regulations

## Data Storage and Security

### Local Storage
- **On-Device**: All messages and contact information are stored locally on your device
- **Encryption**: Data is encrypted using industry-standard AES-GCM encryption
- **No Cloud Storage**: We do not store your personal data on remote servers

### Security Measures
- **End-to-End Encryption**: All communications use Ed25519/X25519 cryptographic protocols
- **Local Database**: Messages stored in encrypted local database with secure deletion
- **Key Management**: Cryptographic keys protected by Android Keystore
- **Perfect Forward Secrecy**: Session keys are regularly rotated

## Data Sharing

### We Do Not Share Your Data
- **No Third Parties**: We do not sell, trade, or share your personal information with third parties
- **No Analytics**: We do not use third-party analytics or tracking services
- **No Advertising**: The app contains no advertisements or advertising networks

### Emergency Scenarios
In genuine emergency situations, messages may be automatically relayed through the mesh network to reach rescue services or emergency contacts. This is a core feature of the emergency communication system.

## Your Rights and Controls

### User Controls
- **Delete Messages**: You can delete individual messages or entire conversations
- **Remove Contacts**: You can remove contacts and their associated encryption keys
- **Disable Features**: You can disable encryption, mesh routing, or specific transport methods
- **App Uninstallation**: Removing the app deletes all locally stored data

### Data Portability
- **QR Code Export**: Identity keys can be exported via QR codes for sharing
- **Local Backup**: Users can backup their own data using device backup features

## Data Retention

### Message Retention
- **User Control**: Messages are retained until manually deleted by the user
- **Automatic Cleanup**: Old messages may be automatically removed to free storage space
- **Emergency Priority**: SOS messages are retained longer to ensure delivery

### Network Data
- **Temporary Storage**: Network routing information is stored temporarily for efficient message delivery
- **Automatic Expiry**: Routing caches and network state data expire automatically

## Children's Privacy

HayatKurtar is designed for emergency communication and is not intended for children under 13. We do not knowingly collect personal information from children under 13. If you believe we have collected information from a child under 13, please contact us immediately.

## International Users

HayatKurtar operates as a local mesh network and does not transmit data across international borders. All data processing occurs locally on your device and nearby devices within the mesh network.

## Changes to This Privacy Policy

We may update this Privacy Policy periodically. We will notify users of significant changes through:
- **In-App Notifications**: Important changes will be displayed in the app
- **Version Updates**: Privacy policy updates will be included in app update notes

## Contact Information

If you have questions about this Privacy Policy or our data practices:

- **GitHub Issues**: https://github.com/appvalence/hayatkurtar/issues
- **Email**: privacy@appvalence.com

## Emergency Disclaimer

HayatKurtar is designed for emergency communication scenarios. In life-threatening situations:
- **Emergency Services**: Always contact official emergency services (911, 112, etc.) when possible
- **Backup Communication**: Use HayatKurtar as supplementary communication when traditional networks fail
- **No Guarantee**: We cannot guarantee message delivery in all circumstances

## Technical Information

### Bluetooth and Wi-Fi Usage
- **Purpose**: Bluetooth Classic and Wi-Fi Direct are used solely for peer-to-peer communication
- **Range**: Communications are limited to the range of Bluetooth (~30m) and Wi-Fi Direct (~200m)
- **No Internet**: The app functions completely offline without internet connectivity

### Encryption Details
- **Link-Layer**: X25519 key exchange with AES-GCM encryption
- **End-to-End**: Ed25519 identity keys with X25519 session keys
- **Forward Secrecy**: Regular key rotation ensures past communications remain secure

---

**This privacy policy ensures transparency about our data practices and your rights while using HayatKurtar for emergency mesh networking communication.**