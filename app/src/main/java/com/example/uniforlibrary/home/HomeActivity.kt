package com.example.uniforlibrary.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uniforlibrary.reservation.MyReservationsActivity
import com.example.uniforlibrary.ui.theme.UniforLibraryTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniforLibraryTheme {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var selectedItemIndex by remember { mutableStateOf(0) }

    val navigationItems = listOf(
        NavigationItem("Home", Icons.Default.Home) { /* Fica na home */ },
        NavigationItem("Acervo", Icons.Default.Search) { /* Navegar para Acervo */ },
        NavigationItem("Empréstimos", Icons.Default.Book) { /* Navegar para Empréstimos */ },
        NavigationItem("Reservas", Icons.Default.Bookmark) {
            navigateToReservations(context)
        },
        NavigationItem("Produzir", Icons.Default.Create) { /* Navegar para Produzir */ },
        NavigationItem("Exposições", Icons.Default.PhotoLibrary) { /* Navegar para Exposições */ }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Início") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            item.onClick()
                        },
                        label = { Text(item.label) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedTextColor = MaterialTheme.colorScheme.background,
                            selectedTextColor = MaterialTheme.colorScheme.background,
                            unselectedIconColor = MaterialTheme.colorScheme.background,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            SearchBarSection()
            Spacer(modifier = Modifier.height(24.dp))
            QuickAccessSection(
                onReservationsClick = { navigateToReservations(context) }
            )
        }
    }
}

// Função para navegar para a tela de Reservas
private fun navigateToReservations(context: Context) {
    val intent = Intent(context, MyReservationsActivity::class.java)
    context.startActivity(intent)
}

@Composable
fun SearchBarSection() {
    var searchQuery by remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        label = { Text("Procure por livros ou produções.") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Ícone de pesquisa") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun QuickAccessSection(onReservationsClick: () -> Unit) {
    val quickAccessItems = listOf(
        QuickAccessItem("Consultar acervo", Icons.Default.MenuBook) { /* Navegar para Acervo */ },
        QuickAccessItem("Meus Empréstimos", Icons.Default.CollectionsBookmark) { /* Navegar para Empréstimos */ },
        QuickAccessItem("Reservas", Icons.Default.BookmarkAdd, onReservationsClick),
        QuickAccessItem("Submeter Produção", Icons.Default.FileUpload) { /* Navegar para Produzir */ },
        QuickAccessItem("Exposições", Icons.Default.PhotoAlbum) { /* Navegar para Exposições */ }
    )

    Column {
        Text(
            "Acesso Rápido",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(quickAccessItems) { item ->
                QuickAccessCard(item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickAccessCard(item: QuickAccessItem) {
    Card(
        onClick = item.onClick,
        modifier = Modifier.height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.label,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// --- Modelos de Dados Atualizados ---

data class NavigationItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

data class QuickAccessItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun HomeScreenPreview() {
    UniforLibraryTheme {
        HomeScreen()
    }
}