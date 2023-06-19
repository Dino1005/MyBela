package com.example.mybela.screens.game

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mybela.R
import com.example.mybela.data.NotificationService
import com.example.mybela.screens.stats.StatsViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar


@Composable
fun GameScreen(
    selectedPlayer1 : String?,
    selectedPlayer2 : String?,
    selectedPlayer3 : String?,
    selectedPlayer4 : String?,
    selectedLength : String?,
    navController: NavController,
    gameViewModel: GameViewModel,
    statsViewModel: StatsViewModel,
) {
    val gameState: List<Game> by gameViewModel.state.collectAsState()
    statsViewModel.getData()
    val games = statsViewModel.state.value
    val context = LocalContext.current

    val totalMiPoints = gameState.sumOf { it.miPoints } + gameState.sumOf { it.miTricks }
    val totalViPoints = gameState.sumOf { it.viPoints } + gameState.sumOf { it.viTricks }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveGame(){
        val db = Firebase.firestore
        val id = games.size

        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            formatter.format(Calendar.getInstance().time )
        } else {
            "Unknown"
        }

        db.collection("games").document("$id").set(hashMapOf(
            "mi1" to selectedPlayer1,
            "mi2" to selectedPlayer2,
            "vi1" to selectedPlayer3,
            "vi2" to selectedPlayer4,
            "miPoints" to gameState.sumOf { it.miPoints },
            "viPoints" to gameState.sumOf { it.viPoints },
            "miTricks" to gameState.sumOf { it.miTricks },
            "viTricks" to gameState.sumOf { it.viTricks },
            "length" to selectedLength,
            "date" to date
        ))
    }

    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.CHANNEL_ID,
                "Game End",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Notifies user when game ends"

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    if(totalMiPoints > selectedLength!!.toInt() || totalViPoints > selectedLength.toInt()) {
        if(totalMiPoints > totalViPoints) {
            createNotificationChannel()
            val service = NotificationService(context)
            service.showNotification("MI")
        } else {
            createNotificationChannel()
            val service = NotificationService(context)
            service.showNotification("VI")
        }
        saveGame()
        gameViewModel.resetGame()
        navController.popBackStack()
    }

    Box(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
            .background(colorResource(R.color.teal)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, bottom = 40.dp)
                .background(colorResource(R.color.teal), shape = RoundedCornerShape(20.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.mi),
                    color = colorResource(R.color.white),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 0.dp)
                )

                Text(
                    text = selectedPlayer1 ?: "",
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 0.dp)
                )

                Text(
                    text = selectedPlayer2 ?: "",
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 0.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                )

                Text(
                    text = stringResource(R.string.app_name),
                    color = colorResource(R.color.white),
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.vi),
                    color = colorResource(R.color.white),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 0.dp)
                )

                Text(
                    text = selectedPlayer3 ?: "",
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 0.dp)
                )

                Text(
                    text = selectedPlayer4 ?: "",
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(6.dp, 2.dp, 6.dp, 0.dp)
                )
            }

        }
    }

    Card(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.ghost_white)),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
        LazyColumn(
            content = {
                items(gameState.size) { i ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 2.dp, 8.dp, 2.dp)
                            .clickable {
                                navController.navigate("round/${gameState[i].id}")
                                       },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment= Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth(0.5f)
                                .background(colorResource(R.color.ghost_white))
                        ) {
                            Text(
                                text = (gameState[i].miPoints + gameState[i].miTricks).toString(),
                                color = colorResource(R.color.teal),
                                fontSize = 40.sp,
                                fontWeight = FontWeight.ExtraBold,
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment= Alignment.CenterHorizontally,
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth(1f)
                                .background(colorResource(R.color.ghost_white))
                        ) {
                            Text(
                                text = (gameState[i].viPoints + gameState[i].viTricks).toString(),
                                color = colorResource(R.color.teal),
                                fontSize = 40.sp,
                                fontWeight = FontWeight.ExtraBold,
                            )
                        }
                    }

                    Row {
                        Divider(color = colorResource(R.color.teal), thickness = 1.dp)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp)
        )

        Card(
            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.teal)),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 16.dp, 4.dp)
                    .fillMaxHeight(0.5f)
            ) {
                Text(
                    text = totalMiPoints.toString(),
                    color = colorResource(R.color.white),
                    fontSize = 50.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = selectedLength,
                    color = colorResource(R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(35.dp, 0.dp, 35.dp, 0.dp)
                )

                Text(
                    text = totalViPoints.toString(),
                    color = colorResource(R.color.white),
                    fontSize = 50.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Button(
                onClick = { navController.navigate("round/0") },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 20.dp, 0.dp)
            ) {
                Text(
                    text = stringResource(R.string.new_game),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.teal),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}