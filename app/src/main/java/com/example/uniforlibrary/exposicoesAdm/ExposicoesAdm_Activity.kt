package com.example.uniforlibrary.exposicoesAdm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uniforlibrary.R
import com.example.uniforlibrary.components.AdminBottomNav
import com.example.uniforlibrary.exposicoes.ReadingActivity
import com.example.uniforlibrary.profile.EditProfileActivity
import com.example.uniforlibrary.ui.theme.UniforLibraryTheme

class ExposicoesAdm_Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniforLibraryTheme {
                ExposicoesAdmScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposicoesAdmScreen() {
    val context = LocalContext.current

    val submissions = remember {
        mutableStateListOf(
            Submission("PathExileLORE", "Narak - 2020", "★5", "Aprovado"),
            Submission("WOW", "Aliens - 1977", "★4.8", "Reprovado"),
            Submission("How to build your upper/lower exercises", "JohnDoe - 2025", "★4.1", "Pendente")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.drawable.logo_branca), contentDescription = "Logo Unifor", modifier = Modifier.size(50.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Exposições", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Notificações */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificações", tint = Color.White)
                    }
                    IconButton(onClick = { navigateToProfile(context) }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            AdminBottomNav(context = context, selectedItemIndex = 2)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Gerencie as submissões dos alunos que desejam expor seus trabalhos para avaliação e validação no nosso acervo.", fontSize = 14.sp)
            }
            items(submissions) { submission ->
                SubmissionCard(
                    submission = submission,
                    onStatusChange = { newStatus ->
                        val index = submissions.indexOf(submission)
                        if (index != -1) {
                            submissions[index] = submission.copy(status = newStatus)
                        }
                    },
                    onViewClick = { navigateToExposicaoDetailAdm(context) }
                )
            }
        }
    }
}

@Composable
fun SubmissionCard(submission: Submission, onStatusChange: (String) -> Unit, onViewClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Book, contentDescription = "Book Icon", modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    submission.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(submission.author, color = Color.Gray, fontSize = 14.sp)
                Text(submission.rating, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
            }
            Column(horizontalAlignment = Alignment.End) {
                val statusColor = when (submission.status) {
                    "Aprovado" -> Color(0xFF388E3C)
                    "Reprovado" -> Color.Red
                    else -> Color.Gray
                }
                Text(submission.status, color = statusColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    TextButton(onClick = onViewClick) { Text("Ver") }
                    TextButton(onClick = { onStatusChange("Aprovado") }) { Text("Aprovar", color = Color(0xFF388E3C)) }
                    TextButton(onClick = { onStatusChange("Reprovado") }) { Text("Reprovar", color = Color.Red) }
                }
            }
        }
    }
}


// --- Modelos e Navegação ---
data class Submission(val title: String, val author: String, val rating: String, val status: String)

private fun navigateToProfile(context: Context) {
    context.startActivity(Intent(context, EditProfileActivity::class.java))
}

private fun navigateToExposicaoDetailAdm(context: Context) {
    context.startActivity(Intent(context, ExposicaoDetailAdm_Activity::class.java))
}


@Preview(showBackground = true)
@Composable
fun ExposicoesAdmScreenPreview() {
    UniforLibraryTheme {
        ExposicoesAdmScreen()
    }
}
