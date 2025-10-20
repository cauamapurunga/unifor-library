package com.example.uniforlibrary.reservation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uniforlibrary.ui.theme.UniforLibraryTheme



data class BookReservation(
    val id: Int,
    val title: String,
    val status: String,
    val details: String,
    val canRenew: Boolean
)

class MyReservationsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniforLibraryTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Reservas", fontWeight = FontWeight.Bold) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* Ação do sino */ }) {
                                    Icon(Icons.Default.Notifications, "Notificações")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    MyReservationsScreen(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
fun MyReservationsScreen(modifier: Modifier = Modifier) {
    val allReservations = remember {
        mutableStateListOf(
            BookReservation(1, "How to build you upper/lower exercises", "Aguardando", "Posição na Fila: 2", canRenew = false),
            BookReservation(2, "WOW", "Devolvida", "Devolvido: 20/09/2025", canRenew = true),
            BookReservation(3, "O Poder do Hábito", "Disponível", "Aguardando Retirada", canRenew = false),
            BookReservation(4, "O Guia do Mochileiro das Galáxias", "Aguardando", "Posição na Fila: 5", canRenew = false)
        )
    }

    var showCancelDialog by remember { mutableStateOf(false) }
    var reservationToCancel by remember { mutableStateOf<BookReservation?>(null) }

    val tabs = listOf("Todas", "Disponíveis", "Aguardando", "Devolvidas")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val filteredReservations = remember(selectedTabIndex, allReservations.size) {
        when (tabs[selectedTabIndex]) {
            "Disponíveis" -> allReservations.filter { it.status == "Disponível" }
            "Aguardando" -> allReservations.filter { it.status == "Aguardando" }
            "Devolvidas" -> allReservations.filter { it.status == "Devolvida" }
            else -> allReservations
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex, containerColor = Color.Transparent) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = (index == selectedTabIndex),
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 13.sp,
                            fontWeight = if (index == selectedTabIndex) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredReservations) { reservation ->
                ReservationCard(
                    reservation = reservation,
                    onCancelClick = {
                        reservationToCancel = reservation
                        showCancelDialog = true
                    },
                    onRenewClick = { /* Lógica para renovar */ }
                )
            }
        }
    }

    if (showCancelDialog && reservationToCancel != null) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Atenção") },
            text = { Text("Tem certeza que deseja cancelar a sua reserva?") },
            confirmButton = {
                TextButton(onClick = {
                    reservationToCancel?.let {
                        allReservations.remove(it)
                    }
                    showCancelDialog = false
                }) { Text("Sim") }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) { Text("Não") }
            }
        )
    }
}

@Composable
fun ReservationCard(
    reservation: BookReservation,
    onCancelClick: () -> Unit,
    onRenewClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp, 80.dp)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = "Capa do livro: ${reservation.title}",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Gray
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reservation.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))

                Text(
                    text = reservation.details,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            if (reservation.canRenew) {
                Button(onClick = onRenewClick, shape = RoundedCornerShape(8.dp)) {
                    Text("Renovar")
                }
            } else {
                if (reservation.status != "Devolvida") {
                    TextButton(onClick = onCancelClick) {
                        Text("X Cancelar")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun MyReservationsScreenPreview() {
    UniforLibraryTheme {
        Surface {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Reservas", fontWeight = FontWeight.Bold) },
                        navigationIcon = { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar") },
                        actions = { IconButton(onClick = {}) { Icon(Icons.Default.Notifications, "Notificações") } }
                    )
                }
            ) { paddingValues ->
                MyReservationsScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}
