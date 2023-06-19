package com.example.mybela.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun RoundScreen(
    id : Int?,
    navController: NavController,
    gameViewModel: GameViewModel,
) {
    var isMiSelected by remember { mutableStateOf(true) }

    val gameState: List<Game> by gameViewModel.state.collectAsState()

    val currentGame = if (id != 0) {
        gameState.find { it.id == id }
    } else {
        Game(id = gameState.size + 1)
    }

    var miPointsState by remember { mutableStateOf(currentGame!!.miPoints) }
    var miTricksState by remember { mutableStateOf(currentGame!!.miTricks) }
    var viPointsState by remember { mutableStateOf(currentGame!!.viPoints) }
    var viTricksState by remember { mutableStateOf(currentGame!!.viTricks) }

    fun updatePoints(point: Int){
        if(isMiSelected) {
            miPointsState = (miPointsState.toString() + point.toString()).toInt()
            if(miPointsState > 162) miPointsState = 162
            viPointsState = 162 - miPointsState
        }
        else {
            viPointsState = (viPointsState.toString() + point.toString()).toInt()
            if(viPointsState > 162) viPointsState = 162
            miPointsState = 162 - viPointsState
        }
    }

    Box(
        modifier = Modifier
            .height(240.dp)
            .fillMaxWidth()
            .background(colorResource(R.color.teal)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, bottom = 70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp, 10.dp, 20.dp, 10.dp)
                    .width(140.dp)
                    .background(color = if(isMiSelected) colorResource(R.color.dark_teal) else colorResource(R.color.teal), shape = RoundedCornerShape(20.dp))
                    .clickable {isMiSelected = true},
            ) {
                Text(
                    text = stringResource(R.string.mi),
                    color = colorResource(R.color.white),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(4.dp)
                )

                Text(
                    text = (miPointsState + miTricksState).toString(),
                    color = colorResource(R.color.white),
                    fontSize = 46.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )

                Text(
                    text = "+ $miTricksState",
                    color = colorResource(R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(20.dp, 10.dp, 10.dp, 10.dp)
                    .width(140.dp)
                    .background(color = if(isMiSelected) colorResource(R.color.teal) else colorResource(R.color.dark_teal), shape = RoundedCornerShape(20.dp))
                    .clickable {isMiSelected = false},
            ) {
                Text(
                    text = stringResource(R.string.vi),
                    color = colorResource(R.color.white),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(4.dp)
                )

                Text(
                    text = (viPointsState + viTricksState).toString(),
                    color = colorResource(R.color.white),
                    fontSize = 46.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )

                Text(
                    text = "+ $viTricksState",
                    color = colorResource(R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
    }

    Card(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.ghost_white)),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 180.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Tricks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.teal),
                modifier = Modifier.padding(8.dp)
            )

            Divider(color = colorResource(R.color.teal), thickness = 2.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if(isMiSelected)
                            miTricksState = miTricksState.plus(20)
                        else
                            viTricksState = viTricksState.plus(20)
                              },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "20",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Button(
                    onClick = {
                        if(isMiSelected)
                            miTricksState = miTricksState.plus(50)
                        else
                            viTricksState = viTricksState.plus(50)
                              },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "50",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Button(
                    onClick = {
                        if(isMiSelected)
                            miTricksState = miTricksState.plus(90)
                        else
                            viTricksState = viTricksState.plus(90)
                              },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "90",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = {
                        if(isMiSelected)
                            miTricksState = miTricksState.plus(100)
                        else
                            viTricksState = viTricksState.plus(100)
                              },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "100",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(6.dp, 8.dp, 6.dp, 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if(isMiSelected)
                            miTricksState = miTricksState.plus(150)
                        else
                            viTricksState = viTricksState.plus(150)
                              },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "150",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(6.dp, 8.dp, 6.dp, 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if(isMiSelected)
                            miTricksState = miTricksState.plus(200)
                        else
                            viTricksState = viTricksState.plus(200)
                              },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "200",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        modifier = Modifier.padding(6.dp, 8.dp, 6.dp, 8.dp)
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Points",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.teal),
                modifier = Modifier.padding(8.dp)
            )

            Divider(color = colorResource(R.color.teal), thickness = 2.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { updatePoints(1) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .padding(25.dp, 0.dp, 4.dp, 0.dp)
                ) {
                    Text(
                        text = "1",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }

                Button(
                    onClick = { updatePoints(2) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(12.dp, 0.dp, 12.dp, 0.dp)
                ) {
                    Text(
                        text = "2",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }

                Button(
                    onClick = { updatePoints(3) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(4.dp, 0.dp, 25.dp, 0.dp)
                ) {
                    Text(
                        text = "3",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { updatePoints(4) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .padding(25.dp, 0.dp, 4.dp, 0.dp)
                ) {
                    Text(
                        text = "4",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }

                Button(
                    onClick = { updatePoints(5) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(12.dp, 0.dp, 12.dp, 0.dp)
                ) {
                    Text(
                        text = "5",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }

                Button(
                    onClick = { updatePoints(6) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(4.dp, 0.dp, 25.dp, 0.dp)
                ) {
                    Text(
                        text = "6",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { updatePoints(7) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .padding(25.dp, 0.dp, 4.dp, 0.dp)
                ) {
                    Text(
                        text = "7",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }

                Button(
                    onClick = { updatePoints(8) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(12.dp, 0.dp, 12.dp, 0.dp)
                ) {
                    Text(
                        text = "8",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }

                Button(
                    onClick = { updatePoints(9) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(4.dp, 0.dp, 25.dp, 0.dp)
                ) {
                    Text(
                        text = "9",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                                miPointsState = 0
                                viPointsState = 0
                                miTricksState = 0
                                viTricksState = 0
                              },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .padding(25.dp, 0.dp, 4.dp, 0.dp)
                ) {
                    Text(
                        text = "AC",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }

                Button(
                    onClick = { updatePoints(0) },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(12.dp, 0.dp, 12.dp, 0.dp)
                ) {
                    Text(
                        text = "0",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }

                Button(
                    onClick = {
                        if(isMiSelected) {
                            miPointsState = if (miPointsState.toString().length > 1)
                                miPointsState.toString().dropLast(1).toInt()
                            else
                                0
                            viPointsState = 162 - miPointsState
                        }
                        else {
                            viPointsState = if (viPointsState.toString().length > 1)
                                viPointsState.toString().dropLast(1).toInt()
                            else
                                0
                            miPointsState = 162 - viPointsState
                        }
                              },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.ghost_white)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(4.dp, 0.dp, 25.dp, 0.dp)
                ) {
                    Text(
                        text = "C",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(R.color.teal)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.gray)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(8.dp, 0.dp, 4.dp, 4.dp)
            ) {
                Text(
                    text = "Back",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.black),
                    modifier = Modifier.padding(8.dp)
                )
            }

            Button(
                onClick = {
                    if(miPointsState != 0 || viPointsState != 0) {
                        currentGame!!.miPoints = miPointsState
                        currentGame.viPoints = viPointsState
                        currentGame.miTricks = miTricksState
                        currentGame.viTricks = viTricksState

                        if (id != 0)
                            gameViewModel.updateGame(currentGame)
                        else
                            gameViewModel.addGame(currentGame)
                    }
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp, 0.dp, 4.dp, 4.dp)
            ) {
                Text(
                    text = "Save",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}