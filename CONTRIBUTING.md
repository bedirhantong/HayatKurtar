# Contributing to HayatKurtar

Thank you for your interest in contributing to HayatKurtar! This emergency mesh networking app can save lives, and every contribution helps make emergency communication more reliable and accessible.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [How to Contribute](#how-to-contribute)
- [Development Guidelines](#development-guidelines)
- [Testing Guidelines](#testing-guidelines)
- [Pull Request Process](#pull-request-process)
- [Community Guidelines](#community-guidelines)

## Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inclusive environment for all contributors, regardless of background, experience level, or identity. This project aims to save lives through technology, and we believe diverse perspectives make us stronger.

### Expected Behavior

- Be respectful and constructive in all interactions
- Focus on what's best for the emergency communication community
- Show empathy towards other community members
- Give credit where credit is due
- Be patient with newcomers and help them learn

### Unacceptable Behavior

- Harassment, trolling, or discriminatory language
- Publishing private information without consent
- Commercial promotion or spam
- Disrupting discussions or development work

## Getting Started

### Areas Where We Need Help

**🚨 Critical Areas (Emergency Impact)**
- Battery optimization for extended emergency use
- Network resilience and auto-healing improvements
- SOS message reliability and routing
- Cross-platform testing (different Android versions)
- Security audit and cryptographic review

**🔧 Technical Improvements**
- Performance optimization
- UI/UX improvements for emergency scenarios
- Additional transport protocols (LoRa, etc.)
- Mesh network topology visualization
- Advanced routing algorithms

**📚 Documentation & Community**
- User guides and tutorials
- Translation to more languages
- Emergency response training materials
- API documentation
- Video tutorials for setup and usage

**🧪 Testing & Quality Assurance**
- Hardware compatibility testing
- Stress testing with many devices
- Real-world emergency scenario testing
- Automated testing improvements
- Security penetration testing

### Skills We're Looking For

- **Android Development**: Kotlin, Jetpack Compose, Architecture Components
- **Networking**: Bluetooth, Wi-Fi Direct, mesh protocols
- **Cryptography**: Experience with encryption, key management
- **UX/UI Design**: Emergency-focused user interface design
- **Security**: Android security, cryptographic protocols
- **Documentation**: Technical writing, user guides
- **Translation**: Multiple languages for global emergency use
- **Emergency Response**: Real-world emergency communication experience

## Development Setup

### Prerequisites

- **Android Studio**: Latest stable version
- **JDK**: OpenJDK 17 or higher
- **Android SDK**: API 24+ (Android 7.0+)
- **Git**: For version control
- **Physical Android Device**: For Bluetooth/Wi-Fi testing

### Environment Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/appvalence/hayatkurtar.git
   cd hayatkurtar
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Sync Project**
   - Let Android Studio sync Gradle dependencies
   - Resolve any SDK or dependency issues

4. **Build and Test**
   ```bash
   ./gradlew clean assembleDebug
   ./gradlew test
   ```

### Development Environment

**Recommended Setup:**
- Two or more physical Android devices for mesh testing
- Devices with different Android versions (API 24-35)
- Both Bluetooth and Wi-Fi Direct capable devices
- Consider devices with different hardware capabilities

**Testing Environment:**
- Real devices required (emulators don't support Bluetooth/Wi-Fi Direct)
- Test in various physical environments (indoor/outdoor)
- Test with devices at different distances
- Consider battery-constrained scenarios

## How to Contribute

### 1. Choose an Issue

**For Beginners:**
- Look for issues labeled `good first issue`
- Start with documentation or simple bug fixes
- Ask questions if anything is unclear

**For Experienced Developers:**
- Check issues labeled `help wanted` or `critical`
- Consider security and performance improvements
- Work on complex mesh networking features

### 2. Fork and Branch

```bash
# Fork the repository on GitHub, then:
git clone https://github.com/yourusername/hayatkurtar.git
cd hayatkurtar
git checkout -b feature/your-feature-name
```

### 3. Make Changes

- Follow the coding standards (see below)
- Write tests for new functionality
- Update documentation as needed
- Test thoroughly on real devices

### 4. Commit and Push

```bash
git add .
git commit -m "type: brief description

Detailed explanation of what was changed and why.
Include any relevant issue numbers."
git push origin feature/your-feature-name
```

**Commit Message Format:**
- `feat:` New features
- `fix:` Bug fixes
- `docs:` Documentation changes
- `style:` Code style changes
- `refactor:` Code refactoring
- `test:` Adding tests
- `security:` Security improvements

### 5. Create Pull Request

- Use the PR template
- Describe changes clearly
- Link relevant issues
- Include testing information
- Add screenshots for UI changes

## Development Guidelines

### Code Style

**Kotlin Code Style:**
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use ktlint for automatic formatting
- Prefer immutable data structures
- Use meaningful variable and function names

**Architecture Principles:**
- Follow Clean Architecture patterns
- Use SOLID principles
- Maintain separation of concerns
- Keep modules loosely coupled

**Emergency-Focused Development:**
- Prioritize reliability over features
- Consider battery life in all decisions
- Design for stress and emergency conditions
- Ensure accessibility for all users

### Module Structure

```
:app (main application)
├── :presentation (UI components)
├── :domain (business logic)
└── :di (dependency injection)

:data (data layer)
├── :data:mesh (mesh networking core)
├── :data:transport:bluetooth (Bluetooth transport)
├── :data:transport:wifidirect (Wi-Fi Direct transport)
└── :core (shared utilities)

:testing (test utilities)
```

### Security Guidelines

**Cryptographic Code:**
- Never implement crypto algorithms yourself
- Use established libraries (Tink, etc.)
- Have crypto code reviewed by security experts
- Document security assumptions clearly

**Key Management:**
- Use Android Keystore when possible
- Implement proper key rotation
- Clear sensitive data from memory
- Follow perfect forward secrecy principles

**Network Security:**
- Validate all incoming data
- Implement proper authentication
- Use secure defaults
- Consider DoS attack prevention

## Testing Guidelines

### Unit Testing

- Test all business logic components
- Mock external dependencies
- Use FakeTransport for network testing
- Maintain >80% code coverage for critical paths

```kotlin
@Test
fun `mesh router forwards messages correctly`() {
    // Arrange
    val mockTransport = mockk<TransportStrategy>()
    val meshRouter = DefaultMeshRouter(mockTransport)
    
    // Act & Assert
    // Test mesh routing logic
}
```

### Integration Testing

- Test complete user flows
- Use TestMeshNetwork for multi-device simulation
- Test error conditions and edge cases
- Verify security implementations

### Device Testing

**Required Testing:**
- Multiple Android versions (API 24-35)
- Different hardware configurations
- Various Bluetooth/Wi-Fi capabilities
- Battery-constrained scenarios
- Network congestion situations

**Emergency Scenario Testing:**
- SOS message broadcasting
- Network resilience under stress
- Device failure recovery
- Range and routing limits

### Performance Testing

- Battery usage optimization
- Memory leak detection
- Large network scaling
- Message throughput testing
- Connection stability testing

## Pull Request Process

### Before Submitting

**Code Quality Checklist:**
- [ ] Code follows style guidelines
- [ ] All tests pass
- [ ] No new lint warnings
- [ ] Security review completed (if applicable)
- [ ] Documentation updated
- [ ] Emergency scenarios considered

**Testing Checklist:**
- [ ] Unit tests added/updated
- [ ] Integration tests pass
- [ ] Manual testing on real devices
- [ ] Performance impact assessed
- [ ] Security implications reviewed

### PR Template

```markdown
## Description
Brief description of changes and motivation.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Security improvement
- [ ] Performance optimization
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests pass
- [ ] Manual testing completed
- [ ] Tested on multiple devices/versions

## Emergency Impact
How do these changes affect emergency communication reliability?

## Screenshots (if applicable)
Include screenshots for UI changes.

## Additional Notes
Any additional information or considerations.
```

### Review Process

**What Reviewers Look For:**
- Code correctness and style
- Test coverage and quality
- Security implications
- Performance impact
- Emergency use case impact
- Documentation completeness

**Review Timeline:**
- Initial review within 48 hours
- Security-critical changes may take longer
- Community discussion for major features
- Maintainer approval required for merge

## Community Guidelines

### Communication Channels

**GitHub Issues:**
- Bug reports and feature requests
- Technical discussions
- Security vulnerability reports (use security template)

**Email:**
- Private security reports: security@appvalence.com
- General inquiries: opensource@appvalence.com

### Getting Help

**For Contributors:**
- Comment on issues for clarification
- Join discussions on proposed changes
- Ask for help in PR reviews
- Suggest improvements to this guide

**For Users:**
- Use GitHub Issues for bug reports
- Check FAQ.md for common questions
- Contribute to documentation improvements

### Recognition

We believe in recognizing all contributors:
- Contributors listed in CONTRIBUTORS.md
- Significant contributions noted in release notes
- Special recognition for security improvements
- Emergency response community acknowledgment

## Security and Responsible Disclosure

### Reporting Security Issues

**Please do not create public issues for security vulnerabilities.**

Send security reports to: security@appvalence.com

Include:
- Description of the vulnerability
- Steps to reproduce
- Potential impact on emergency communications
- Suggested fixes (if any)

### Security Review Process

1. Acknowledge receipt within 24 hours
2. Initial assessment within 72 hours
3. Fix development and testing
4. Coordinated disclosure timeline
5. Credit to reporter (if desired)

## Final Notes

### Emergency Context

Remember that HayatKurtar is designed for emergency scenarios:
- **Lives may depend on your code**
- **Reliability trumps features**
- **Consider worst-case scenarios**
- **Test under stress conditions**

### License

By contributing to HayatKurtar, you agree that your contributions will be licensed under the MIT License.

### Questions?

If you have questions about contributing:
- Open a GitHub issue with the `question` label
- Email us at opensource@appvalence.com
- Check existing issues and documentation

---

**Thank you for contributing to emergency communication technology. Your work helps ensure that no one is left behind during disasters.**

*"In times of crisis, communication saves lives."*