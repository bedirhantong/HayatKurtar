package com.appvalence.hayatkurtar.presentation.e2e

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.core.crypto.E2EContact
import com.appvalence.hayatkurtar.core.crypto.E2EEncryptionManager
import com.appvalence.hayatkurtar.core.crypto.E2EIdentity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Base64
import javax.inject.Inject

/**
 * E2E encryption management screen with QR code sharing
 */
@Composable
fun E2EEncryptionScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: E2EViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadE2EData()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            
            Text(
                text = "End-to-End Encryption",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            uiState.error != null -> {
                ErrorCard(
                    error = uiState.error!!,
                    onRetry = { viewModel.loadE2EData() }
                )
            }
            
            else -> {
                E2EContent(
                    uiState = uiState,
                    onShareKey = viewModel::sharePublicKey,
                    onScanQR = viewModel::scanQRCode,
                    onRemoveContact = viewModel::removeContact,
                    onAddContact = viewModel::addContact
                )
            }
        }
    }
}

@Composable
private fun E2EContent(
    uiState: E2EUiState,
    onShareKey: () -> Unit,
    onScanQR: () -> Unit,
    onRemoveContact: (String) -> Unit,
    onAddContact: (String, ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // User's key sharing section
        UserKeySection(
            userPublicKey = uiState.userPublicKey,
            qrCodeBitmap = uiState.qrCodeBitmap,
            onShareKey = onShareKey
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Contacts management
        ContactsSection(
            contacts = uiState.contacts,
            onScanQR = onScanQR,
            onRemoveContact = onRemoveContact,
            onAddContact = onAddContact
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // E2E information
        InfoSection()
    }
}

@Composable
private fun UserKeySection(
    userPublicKey: ByteArray?,
    qrCodeBitmap: Bitmap?,
    onShareKey: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Your Encryption Key",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Share this QR code with trusted contacts to enable secure messaging",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // QR Code
            if (qrCodeBitmap != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = androidx.compose.ui.graphics.Color.White
                    )
                ) {
                    Image(
                        bitmap = qrCodeBitmap.asImageBitmap(),
                        contentDescription = "QR Code",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = onShareKey,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Share Key")
                }
            } else {
                CircularProgressIndicator()
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Key fingerprint
            if (userPublicKey != null) {
                Text(
                    text = "Fingerprint: ${getKeyFingerprint(userPublicKey)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ContactsSection(
    contacts: List<E2EContact>,
    onScanQR: () -> Unit,
    onRemoveContact: (String) -> Unit,
    onAddContact: (String, ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Trusted Contacts",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(onClick = onScanQR) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = "Scan QR Code"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (contacts.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ContactPage,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "No Trusted Contacts",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Scan a contact's QR code to add them for secure messaging",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedButton(
                        onClick = onScanQR,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Scan QR Code")
                    }
                }
            } else {
                contacts.forEach { contact ->
                    ContactCard(
                        contact = contact,
                        onRemove = { onRemoveContact(contact.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun ContactCard(
    contact: E2EContact,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.id,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = getKeyFingerprint(contact.publicKey),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove contact",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun InfoSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "How E2E Encryption Works",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val infoPoints = listOf(
                "Each user has a unique encryption key pair",
                "QR codes contain your public key for sharing",
                "Messages are encrypted on your device before sending",
                "Only intended recipients can decrypt messages",
                "Keys are stored securely on your device only",
                "Even in mesh routing, messages remain encrypted"
            )
            
            infoPoints.forEach { point ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "• ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = point,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorCard(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Retry")
            }
        }
    }
}

// ViewModel
@HiltViewModel
class E2EViewModel @Inject constructor(
    private val e2eManager: E2EEncryptionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(E2EUiState())
    val uiState: StateFlow<E2EUiState> = _uiState.asStateFlow()

    fun loadE2EData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Initialize E2E encryption
                val identity = e2eManager.initialize().getOrThrow()
                val contacts = e2eManager.getContacts().getOrThrow()
                val qrBitmap = generateQRCode(identity.publicKey)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    userPublicKey = identity.publicKey,
                    contacts = contacts,
                    qrCodeBitmap = qrBitmap
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun sharePublicKey() {
        // Implementation would use Android's share intent
        // to share the public key or QR code
    }

    fun scanQRCode() {
        // Implementation would launch camera QR scanner
        // and call addContact when QR is scanned
    }

    fun addContact(contactId: String, publicKey: ByteArray) {
        viewModelScope.launch {
            try {
                e2eManager.addContact(contactId, publicKey)
                loadE2EData() // Refresh
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to add contact: ${e.message}"
                )
            }
        }
    }

    fun removeContact(contactId: String) {
        viewModelScope.launch {
            try {
                e2eManager.removeContact(contactId)
                loadE2EData() // Refresh
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to remove contact: ${e.message}"
                )
            }
        }
    }

    private fun generateQRCode(publicKey: ByteArray): Bitmap {
        val publicKeyString = Base64.getEncoder().encodeToString(publicKey)
        val writer = MultiFormatWriter()
        val bitMatrix: BitMatrix = writer.encode(
            publicKeyString,
            BarcodeFormat.QR_CODE,
            512,
            512
        )
        
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        
        return bitmap
    }
}

// UI State
data class E2EUiState(
    val isLoading: Boolean = false,
    val userPublicKey: ByteArray? = null,
    val contacts: List<E2EContact> = emptyList(),
    val qrCodeBitmap: Bitmap? = null,
    val error: String? = null
)

// Utility functions
private fun getKeyFingerprint(publicKey: ByteArray): String {
    val hash = publicKey.hashCode()
    return String.format("%08X", hash).chunked(4).joinToString("-")
}