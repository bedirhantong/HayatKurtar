# HayatKurtar - Frequently Asked Questions (FAQ)

## General Questions

### What is HayatKurtar?
HayatKurtar (which means "Life Saver" in Turkish) is an emergency communication app that creates a mesh network using Bluetooth and Wi-Fi Direct. It allows people to communicate during disasters when traditional internet and cellular networks fail.

### How does mesh networking work?
Mesh networking allows devices to connect directly to each other and relay messages through multiple "hops." If you want to send a message to someone far away, it can travel through several intermediate devices to reach its destination, even if you're not directly connected to the recipient.

### Do I need internet to use HayatKurtar?
No! HayatKurtar works completely offline. It creates a local network between nearby devices using Bluetooth and Wi-Fi Direct. No internet, cellular data, or external servers are required.

### Is HayatKurtar free?
Yes, HayatKurtar is completely free and open source. There are no in-app purchases, subscriptions, or hidden costs.

## Setup and Installation

### What devices are compatible?
- Android 7.0+ (API 24+)
- Bluetooth 4.0+ support
- Wi-Fi Direct capability (recommended but optional)
- Both phones and tablets are supported

### What permissions does the app need?
**Essential permissions:**
- **Bluetooth**: To connect with nearby devices
- **Wi-Fi**: For Wi-Fi Direct high-speed connections
- **Foreground Service**: To maintain connections in background
- **Location** (Android 12 and below): Required by Android for Wi-Fi Direct discovery

**Why location permission?** On older Android versions, the system requires location permission for Wi-Fi Direct peer discovery. HayatKurtar does not track your location.

### How do I set up HayatKurtar?
1. Install the app and grant permissions
2. Choose a device name that others can recognize
3. Enable Bluetooth and Wi-Fi on your device
4. Start scanning for nearby devices
5. Connect to other HayatKurtar users

## Using the App

### How do I find other users?
1. Open the app and tap "Scan Devices"
2. Make sure both devices have Bluetooth and Wi-Fi enabled
3. Other HayatKurtar users should appear in the nearby devices list
4. Tap on a device to connect and start chatting

### How far can messages travel?
- **Bluetooth range**: ~10-30 meters
- **Wi-Fi Direct range**: ~50-200 meters
- **Multi-hop range**: Unlimited through relay devices

Messages can travel much further through multi-hop routing, where intermediate devices automatically relay your messages to extend the range.

### How do I send emergency SOS messages?
1. Long press the send button in any chat
2. Type your emergency message
3. The message will be broadcast with high priority
4. All nearby devices will automatically relay the SOS message

### What makes SOS messages special?
- **Higher priority**: SOS messages are sent and relayed first
- **Extended range**: SOS messages use maximum TTL (10 hops)
- **Auto-relay**: All devices automatically forward SOS messages
- **Persistent**: SOS messages are stored until acknowledged

## Security and Privacy

### Is my communication secure?
Yes! HayatKurtar uses military-grade encryption:
- **End-to-end encryption**: Ed25519/X25519 cryptographic protocols
- **Perfect forward secrecy**: Keys change regularly
- **Local storage only**: No data sent to external servers

### How do I verify a contact's identity?
1. Open a chat with the contact
2. Go to contact settings
3. Compare identity key fingerprints in person
4. Mark the contact as "verified"

### Can others read my messages?
No. All messages are encrypted end-to-end. Even if someone intercepts network traffic, they cannot read your messages without the encryption keys.

### Does HayatKurtar collect my data?
No. HayatKurtar:
- Stores all data locally on your device
- Does not send data to external servers
- Does not collect analytics or telemetry
- Does not require accounts or registration

## Technical Questions

### Can I use both Bluetooth and Wi-Fi Direct?
Yes! HayatKurtar automatically uses both transports simultaneously for optimal performance:
- **Bluetooth**: Lower power, reliable for text messages
- **Wi-Fi Direct**: Higher throughput for larger data

### What happens if my device goes offline?
Messages intended for you are stored by intermediate devices and delivered when you come back online (store-and-forward capability).

### How many devices can connect to my phone?
- **Bluetooth**: Up to 7 simultaneous connections
- **Wi-Fi Direct**: Up to 8+ connections
- **Total network**: Unlimited devices through multi-hop routing

### Does the app drain my battery?
HayatKurtar includes battery optimization features:
- Adaptive scanning based on battery level
- Efficient background operation
- Smart transport selection
- Typical usage: 5-15% battery per hour

## Troubleshooting

### I can't find other devices
**Check these items:**
- Bluetooth and Wi-Fi are enabled on both devices
- Both devices have HayatKurtar installed and running
- Devices are within Bluetooth range (~30 meters)
- Try restarting the scan or the app
- Check that permissions are granted

### Messages aren't being delivered
**Possible causes:**
- Recipient device is offline or out of range
- Network congestion (too many messages)
- Check if there are intermediate devices to relay the message
- Try sending the message as SOS for higher priority

### The app crashes or freezes
1. Force close and restart the app
2. Restart Bluetooth and Wi-Fi on your device
3. Clear app cache (in Android settings)
4. If problems persist, report the issue on GitHub

### Battery drains too quickly
**Optimization tips:**
- Enable battery optimization in app settings
- Reduce scanning frequency in congested areas
- Close the app when not needed for emergencies
- Disable Wi-Fi Direct if only text messaging is needed

## Emergency Scenarios

### How should I use HayatKurtar during an emergency?
1. **Keep the app running** in the background
2. **Share your device name** with family/rescue teams
3. **Send location information** via messages when safe
4. **Use SOS features** for urgent communications
5. **Help relay messages** for others in need

### What if cellular networks are down?
HayatKurtar works independently of cellular networks. It creates its own local communication network using device-to-device connections.

### Can rescue teams use HayatKurtar?
Yes! Rescue teams can:
- Install HayatKurtar to communicate with survivors
- Use the mesh network to coordinate rescue efforts
- Receive SOS messages from affected areas
- Set up communication relays to extend range

### How do I prepare for emergencies?
**Before disaster strikes:**
- Install and test HayatKurtar
- Share the app with family and neighbors
- Practice using SOS features
- Keep devices charged with portable batteries
- Plan communication protocols with your group

## Advanced Features

### What is identity verification?
Identity verification ensures you're communicating with the right person by comparing cryptographic fingerprints. This prevents impersonation attacks.

### How does QR code sharing work?
You can share your identity key via QR code:
1. Generate QR code in settings
2. Show it to contacts to scan
3. This establishes secure communication
4. Verify fingerprints for additional security

### Can I customize network settings?
Advanced users can adjust:
- TTL (time-to-live) for messages
- Scanning intervals
- Transport priorities
- Battery optimization levels

### What is store-and-forward?
Store-and-forward means that if the recipient is offline, intermediate devices temporarily store the message and deliver it when the recipient comes back online.

## Community and Support

### How can I report bugs or request features?
- **GitHub Issues**: https://github.com/appvalence/hayatkurtar/issues
- **Email**: support@appvalence.com

### Is the code open source?
Yes! HayatKurtar is fully open source under the MIT license. You can:
- Review the source code
- Contribute improvements
- Build your own version
- Audit security implementations

### How can I contribute?
- Report bugs and suggest features
- Help with translations
- Contribute code improvements
- Help with documentation
- Share the app with others

### Where can I learn more?
- **Architecture Guide**: ARCHITECTURE.md
- **Privacy Policy**: PRIVACY_POLICY.md
- **GitHub Repository**: https://github.com/appvalence/hayatkurtar

---

## Emergency Disclaimer

**HayatKurtar is designed to supplement, not replace, official emergency services.**

- Always contact official emergency services (911, 112, etc.) when possible
- Use HayatKurtar when traditional communication methods fail
- Practice using the app before emergencies occur
- Keep devices charged and ready

**In life-threatening situations, your safety is the top priority. Use any available means to get help.**

---

*"In times of crisis, communication saves lives. HayatKurtar ensures no one is left behind."*