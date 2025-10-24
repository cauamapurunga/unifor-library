package com.example.uniforlibrary.notification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uniforlibrary.ui.theme.UniforLibraryTheme

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniforLibraryTheme {
                NotificationScreen(
                    notifications = sampleNotifications,
                    onBackClick = { finish() }
                )
            }
        }
    }
}


enum class NotificationType {
    LATE_RETURN,
    RESERVATION_CONFIRMED
}

data class NotificationItem(
    val id: Int,
    val type: NotificationType,
    val title: String,
    val description: String,
    val time: String,
    val isRead: Boolean = false
)

val sampleNotifications = listOf(
    NotificationItem(
        id = 1,
        type = NotificationType.LATE_RETURN,
        title = "Devolução atrasada",
        description = "O livro 'PathExileLORE' deve ser devolvido imediatamente.",
        time = "2h",
        isRead = false
    ),
    NotificationItem(
        id = 2,
        type = NotificationType.RESERVATION_CONFIRMED,
        title = "Reserva confirmada",
        description = "Sua reserva do livro 'How to build you upper/lower exercises' foi confirmada.",
        time = "3h"
    ),
    NotificationItem(
        id = 3,
        type = NotificationType.RESERVATION_CONFIRMED,
        title = "Reserva disponível",
        description = "O livro 'Design Patterns' está pronto para retirada.",
        time = "1d"
    ),
    NotificationItem(
        id = 4,
        type = NotificationType.LATE_RETURN,
        title = "Devolução atrasada",
        description = "O livro 'Clean Architecture' está com 5 dias de atraso.",
        time = "5d",
        isRead = true
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    notifications: List<NotificationItem>,
    onBackClick: () -> Unit
) {
    var notificationList by remember { mutableStateOf(notifications) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificações") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        // Ação para marcar todas como lidas
                        notificationList = notificationList.map { it.copy(isRead = true) }
                    }) {
                        Text("Marcar todas como lidas")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(notificationList, key = { it.id }) { notification ->
                NotificationCard(item = notification)
            }
        }
    }
}

@Composable
fun NotificationCard(item: NotificationItem) {
    val icon: ImageVector
    val iconColor: Color

    when (item.type){
        NotificationType.LATE_RETURN -> {
            icon = Icons.Default.Error
            iconColor = MaterialTheme.colorScheme.error
        }
        NotificationType.RESERVATION_CONFIRMED -> {
            icon = Icons.Default.CheckCircle
            iconColor = MaterialTheme.colorScheme.primary
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = item.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.align(Alignment.Top)
        ) {
            Text(
                text = item.time,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (!item.isRead) {
                Spacer(modifier = Modifier.height(8.dp))
                // Ponto azul para indicar notificação não lida
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Tela de Notificações")
@Composable
fun NotificationScreenPreview() {
    UniforLibraryTheme {
        NotificationScreen(
            notifications = sampleNotifications,
            onBackClick = {}
        )
    }
}