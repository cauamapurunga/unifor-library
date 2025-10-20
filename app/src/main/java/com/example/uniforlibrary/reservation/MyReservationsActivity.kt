package com.example.uniforlibrary.reservation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uniforlibrary.ui.theme.MyPrimary
import com.example.uniforlibrary.ui.theme.ScreenBackground
import com.example.uniforlibrary.ui.theme.UniforLibraryTheme

class AcervoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniforLibraryTheme {
                AcervoScreen()
            }
        }
    }
}

@Composable
fun AcervoScreen() {
    var currentScreen by remember { mutableStateOf("list") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    when (currentScreen) {
        "list" -> AcervoListScreen(
            onAddClick = { currentScreen = "add" },
            onEditClick = { currentScreen = "edit" },
            onDeleteClick = {
                dialogMessage = "Tem certeza que deseja remover a obra do acervo?"
                showDialog = true
            }
        )

        "add" -> AddEditScreen(
            title = "Adicionar Obra",
            onBack = { currentScreen = "list" },
            onConfirm = {
                dialogMessage = "Tem certeza que deseja adicionar a obra ao acervo?"
                showDialog = true
            }
        )

        "edit" -> AddEditScreen(
            title = "Editar Obra",
            onBack = { currentScreen = "list" },
            onConfirm = {
                dialogMessage = "Tem certeza que deseja editar a obra do acervo?"
                showDialog = true
            }
        )
    }

    if (showDialog) {
        ConfirmDialog(
            message = dialogMessage,
            onDismiss = { showDialog = false },
            onConfirm = { showDialog = false }
        )
    }
}

@Composable
fun AcervoListScreen(
    onAddClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Scaffold(
        containerColor = ScreenBackground,
        bottomBar = { BottomNavBar(selected = "acervo") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MyPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                "|  Gerenciar Acervo",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MyPrimary
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                placeholder = { Text("Pesquisar por título, autor ou ISBN") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Cards simulando livros
            BookCard("PathExileLORE", "Marak - 2020", "★5", onEditClick, onDeleteClick)
            Spacer(Modifier.height(8.dp))
            BookCard("WOW", "Aliens - 1977", "★4.8", onEditClick, onDeleteClick)
        }
    }
}

@Composable
fun BookCard(title: String, subtitle: String, rating: String, onEdit: () -> Unit, onRemove: () -> Unit) {
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(subtitle, color = Color.Gray, fontSize = 14.sp)
            Text(rating, color = MyPrimary, fontWeight = FontWeight.SemiBold)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onEdit) { Text("Editar") }
                TextButton(onClick = onRemove) { Text("Remover") }
            }
        }
    }
}

@Composable
fun AddEditScreen(title: String, onBack: () -> Unit, onConfirm: () -> Unit) {
    Scaffold(
        containerColor = ScreenBackground,
        bottomBar = { BottomNavBar(selected = "acervo") }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "|  Gerenciar Acervo",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MyPrimary
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Título") })
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Autor") })
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Ano") })
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = false, onCheckedChange = {})
                Text("Digital")
                Spacer(Modifier.width(16.dp))
                Checkbox(checked = false, onCheckedChange = {})
                Text("Físico")
            }
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = "", onValueChange = {}, label = { Text("Descrição") })

            Spacer(Modifier.height(32.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Voltar")
                }
                Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(containerColor = MyPrimary)) {
                    Text(if (title.contains("Editar")) "Editar" else "Adicionar")
                }
            }
        }
    }
}

@Composable
fun ConfirmDialog(message: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(message, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Sim", color = MyPrimary) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Não") }
        },
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun BottomNavBar(selected: String) {
    NavigationBar(containerColor = Color.White) {
        val items = listOf("Home", "Acervo", "Empréstimos", "Reservas", "Relatórios")
        items.forEach {
            NavigationBarItem(
                selected = it.lowercase() == selected,
                onClick = {},
                label = { Text(it) },
                icon = {}
            )
        }
    }
}
