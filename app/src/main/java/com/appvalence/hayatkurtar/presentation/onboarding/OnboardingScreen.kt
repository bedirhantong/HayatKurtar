package com.appvalence.hayatkurtar.presentation.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Feature(val icon: @Composable () -> Unit, val title: String, val description: String)
private data class Review(val name: String, val role: String, val text: String, val rating: Int)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val background = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0D1117),
            Color(0xFF161B22),
            Color(0xFF233A5B),
            Color(0xFF296A9F)
        )
    )

    val features = remember {
        listOf(
            Feature({ IconContainer { Icon(Icons.Default.Bluetooth, contentDescription = null, tint = Color.White) } },
                "Yakındaki Kişilerle Çevrimdışı İletişim",
                "İnternet olmadan, Bluetooth ile güvenilir sohbet ve cihaz keşfi."),
            Feature({ IconContainer { Icon(Icons.Default.SignalCellularAlt, contentDescription = null, tint = Color.White) } },
                "Yüksek Performanslı Keşif",
                "Klasik + BLE tarama ile hızlı ve enerji verimli cihaz bulma."),
            Feature({ IconContainer { Icon(Icons.Default.Security, contentDescription = null, tint = Color.White) } },
                "Uçtan Uca Güvenlik",
                "AES-GCM ve güvenli anahtar yönetimi ile mesajlarınız sizinle kalır.")
        )
    }

    val reviews = remember {
        listOf(
            Review("Arda K.", "Güvenlik Mühendisi", "Offline senaryolarda bile stabil ve güven veriyor.", 5),
            Review("Ece T.", "Saha Operasyonları", "Acil durumlarda internet olmasa da ekip iletişimi sorunsuz.", 5)
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(background)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 64.dp, bottom = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Hero() }
            items(features) { FeatureRow(it) }
            item { Section("Kullanıcı Geri Bildirimleri") }
            items(reviews) { ReviewCard(it) }
            item { Section("Nasıl Çalışır?") }
            item { FlowSketch() }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            StickyCtaButton(onClick = onFinish)
        }
    }
}

@Composable
private fun Hero() {
    Column(
        modifier = Modifier.padding(top = 40.dp, bottom = 40.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("HayatKurtar: İnternetsiz Dahi İletişim", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Text(
            "Yakındaki kişilerle Bluetooth üzerinden mesajlaşın, cihazları hızla keşfedin ve acil durumlarda bağlantıda kalın.",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun FeatureRow(feature: Feature) {
    Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        feature.icon()
        Spacer(Modifier.width(20.dp))
        Column {
            Text(feature.title, fontWeight = FontWeight.SemiBold, color = Color.White)
            Text(feature.description, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.7f))
        }
    }
}

@Composable
private fun Section(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.padding(top = 40.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
    )
}

@Composable
private fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(review.rating) {
                    Icon(Icons.Filled.Bluetooth, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(text = "\"${review.text}\"", style = MaterialTheme.typography.bodyMedium, color = Color.White)
            Spacer(Modifier.height(12.dp))
            Text("${review.name}, ${review.role}", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.7f))
        }
    }
}

@Composable
private fun FlowSketch() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.04f)),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Akış Özeti", color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Canvas(modifier = Modifier.fillMaxWidth().height(140.dp)) {
                val stroke = Stroke(width = 6f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f))
                drawLine(Color.White.copy(alpha = 0.6f), start = Offset(60f, 40f), end = Offset(size.width - 60f, 40f), strokeWidth = 4f)
                drawLine(Color.White.copy(alpha = 0.6f), start = Offset(60f, 80f), end = Offset(size.width - 60f, 80f), strokeWidth = 4f)
                drawLine(Color.White.copy(alpha = 0.6f), start = Offset(60f, 120f), end = Offset(size.width - 60f, 120f), strokeWidth = 4f)

                // Düğümler
                drawCircle(Color.White, radius = 10f, center = Offset(60f, 40f), style = stroke)
                drawCircle(Color.White, radius = 10f, center = Offset(size.width - 60f, 80f), style = stroke)
                drawCircle(Color.White, radius = 10f, center = Offset(60f, 120f), style = stroke)
            }
        }
    }
}

@Composable
private fun StickyCtaButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("Haydi Başlayalım", color = Color(0xFF0D1117), fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
private fun IconContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) { content() }
}


