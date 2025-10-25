package com.example.uniforlibrary.relatoriosAdm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uniforlibrary.acervoAdm.AcervoAdm_Activity
import com.example.uniforlibrary.exposicoesAdm.ExposicoesAdm_Activity
import com.example.uniforlibrary.homeAdm.HomeAdm_Activity
import com.example.uniforlibrary.reservasAdm.ReservasADM_activity
import com.example.uniforlibrary.ui.theme.UniforLibraryTheme

class RelatoriosAdm_Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniforLibraryTheme {
                RelatoriosAdmScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatoriosAdmScreen() {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Relatórios", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            AdminBottomNav(context = context, selectedItemIndex = 4)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Tela de Relatórios")
        }
    }
}

@Composable
fun AdminBottomNav(context: Context, selectedItemIndex: Int) {
    val navigationItems = listOf(
        AdminBottomNavItem("Home", Icons.Default.Home, 0) { context.startActivity(Intent(context, HomeAdm_Activity::class.java)) },
        AdminBottomNavItem("Acervo", Icons.AutoMirrored.Filled.MenuBook, 1) { context.startActivity(Intent(context, AcervoAdm_Activity::class.java)) },
        AdminBottomNavItem("Exposições", Icons.Default.PhotoLibrary, 2) { context.startActivity(Intent(context, ExposicoesAdm_Activity::class.java)) },
        AdminBottomNavItem("Reservas", Icons.Default.Bookmark, 3) { context.startActivity(Intent(context, ReservasADM_activity::class.java)) },
        AdminBottomNavItem("Relatórios", Icons.Default.Assessment, 4) { /* Já está aqui */ }
    )

    Surface(tonalElevation = 0.dp, shadowElevation = 16.dp, color = Color.White) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier.height(80.dp).padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            navigationItems.forEach { item ->
                NavigationBarItem(
                    selected = selectedItemIndex == item.index,
                    onClick = item.onClick,
                    label = { Text(item.label, fontSize = 9.sp, maxLines = 2, textAlign = TextAlign.Center, lineHeight = 11.sp, fontWeight = if (selectedItemIndex == item.index) FontWeight.Bold else FontWeight.Medium) },
                    icon = { Icon(imageVector = item.icon, contentDescription = item.label, modifier = Modifier.size(24.dp)) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color(0xFF666666),
                        unselectedTextColor = Color(0xFF666666),
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

data class AdminBottomNavItem(val label: String, val icon: ImageVector, val index: Int, val onClick: () -> Unit)

@Preview(showBackground = true)
@Composable
fun RelatoriosAdmScreenPreview() {
    UniforLibraryTheme {
        RelatoriosAdmScreen()
    }
}
