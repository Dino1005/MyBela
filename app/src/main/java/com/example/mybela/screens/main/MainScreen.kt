package com.example.mybela.screens.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mybela.R

@Composable
fun MainScreen(
    name : String?,
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val context = LocalContext.current
    var users by remember { mutableStateOf(listOf<String>()) }
    users = userViewModel.state.value

    var selectedPlayer1 by remember { mutableStateOf("") }
    var selectedPlayer2 by remember { mutableStateOf("") }
    var selectedPlayer3 by remember { mutableStateOf("") }
    var selectedPlayer4 by remember { mutableStateOf("") }

    fun updateSelectedUsers(){
        val selectedUsers = listOf(selectedPlayer1, selectedPlayer2, selectedPlayer3, selectedPlayer4)
        users = userViewModel.removeSelectedUsers(selectedUsers)
    }

    val onPlayer1Selected: (String) -> Unit = { item ->
        selectedPlayer1 = item
        updateSelectedUsers()
    }

    val onPlayer2Selected: (String) -> Unit = { item ->
        selectedPlayer2 = item
        updateSelectedUsers()
    }

    val onPlayer3Selected: (String) -> Unit = { item ->
        selectedPlayer3 = item
        updateSelectedUsers()
    }

    val onPlayer4Selected: (String) -> Unit = { item ->
        selectedPlayer4 = item
        updateSelectedUsers()
    }

    var selectedLength by remember { mutableStateOf("") }
    val onLengthSelected: (String) -> Unit = { item ->
        selectedLength = item
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
                .padding(16.dp, bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
            )

            Text(
                text = stringResource(R.string.app_name),
                color = colorResource(R.color.white),
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.logged_in),
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                )
                Text(
                    text = name ?: "",
                    color = colorResource(R.color.white),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.mi),
                        color = colorResource(R.color.teal),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(16.dp)
                    )

                    DropDownMenu(
                        labelText = stringResource(R.string.player1),
                        users,
                        onItemSelected = onPlayer1Selected
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    DropDownMenu(
                        labelText = stringResource(R.string.player2),
                        users,
                        onItemSelected = onPlayer2Selected
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.vi),
                        color = colorResource(R.color.teal),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(16.dp)
                    )

                    DropDownMenu(
                        labelText = stringResource(R.string.player1),
                        users,
                        onItemSelected = onPlayer3Selected
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    DropDownMenu(
                        labelText = stringResource(R.string.player2),
                        users,
                        onItemSelected = onPlayer4Selected
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            DropDownMenu(
                labelText = stringResource(R.string.length),
                listOf("1001", "701", "501", "301"),
                onItemSelected = onLengthSelected
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    if (selectedPlayer1.isNotEmpty() &&
                        selectedPlayer2.isNotEmpty() &&
                        selectedPlayer3.isNotEmpty() &&
                        selectedPlayer4.isNotEmpty() &&
                        selectedLength.isNotEmpty()
                    ) {
                        navController.navigate("game/$selectedPlayer1/$selectedPlayer2/$selectedPlayer3/$selectedPlayer4/$selectedLength")
                    } else {
                        Toast.makeText(context, "Select all fields!", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.start_new_game),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    navController.navigate("stats")
                }
            ) {
                Text(
                    text = stringResource(R.string.stats),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    labelText: String,
    items: List<String>,
    onItemSelected: (String) -> Unit
){
    var isExpanded by remember { mutableStateOf(false) }
    var selection by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
    ) {
        OutlinedTextField(
            value = selection,
            onValueChange = {},
            readOnly = true,
            label = { Text(labelText, color = colorResource(R.color.gray)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = colorResource(R.color.gray),containerColor = colorResource(R.color.white)),
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            items.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label, color = colorResource(R.color.black)) },
                    onClick = {
                        selection = label
                        isExpanded = false
                        onItemSelected(label)
                    })
            }
        }
    }
}