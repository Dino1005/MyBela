package com.example.mybela.screens.stats

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mybela.R

@Composable
fun StatScreen(
    navController: NavController,
    statsViewModel: StatsViewModel,
) {
    statsViewModel.getData()
    val games = statsViewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.teal)),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = stringResource(R.string.stats),
            color = colorResource(R.color.white),
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(0.dp, 6.dp, 0.dp, 0.dp)
        )
        Card(
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.ghost_white)),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
        ) {
            LazyColumn(
                content = {
                    items(games.size) { i ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp, 2.dp, 2.dp, 2.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = games[i].date,
                                color = colorResource(R.color.teal),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp, 2.dp, 8.dp, 2.dp)
                                .clickable {
                                    //TODO in version 2.0
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .height(90.dp)
                                    .fillMaxWidth(0.45f)
                                    .background(colorResource(R.color.ghost_white))
                            ) {
                                Text(
                                    text = (games[i].miPoints + games[i].miTricks).toString(),
                                    color = colorResource(R.color.teal),
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                )

                                Text(
                                    text = "(${games[i].miTricks})",
                                    color = colorResource(R.color.teal),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        text = games[i].mi1,
                                        color = colorResource(R.color.teal),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                    )

                                    Text(
                                        text = "&",
                                        color = colorResource(R.color.teal),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(4.dp, 0.dp, 4.dp, 0.dp)
                                    )

                                    Text(
                                        text = games[i].mi2,
                                        color = colorResource(R.color.teal),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .height(90.dp)
                                    .fillMaxWidth(0.2f)
                                    .background(colorResource(R.color.ghost_white))
                            ) {
                                Text(
                                    text = games[i].length,
                                    color = colorResource(R.color.teal),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .height(90.dp)
                                    .fillMaxWidth(1f)
                                    .background(colorResource(R.color.ghost_white))
                            ) {
                                Text(
                                    text = (games[i].viPoints + games[i].viTricks).toString(),
                                    color = colorResource(R.color.teal),
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                )

                                Text(
                                    text = "(${games[i].viTricks})",
                                    color = colorResource(R.color.teal),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        text = games[i].vi1,
                                        color = colorResource(R.color.teal),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                    )

                                    Text(
                                        text = "&",
                                        color = colorResource(R.color.teal),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(4.dp, 0.dp, 4.dp, 0.dp)
                                    )

                                    Text(
                                        text = games[i].vi2,
                                        color = colorResource(R.color.teal),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }

                        Row {
                            Divider(color = colorResource(R.color.teal), thickness = 1.dp)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxHeight(0.85f)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text(
                    text = "Back",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}