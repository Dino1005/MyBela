package com.example.mybela.screens.register

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mybela.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.registerState.collectAsState(initial = null)

    Box(
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
            .background(colorResource(R.color.teal)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(R.string.app_name),
            color = colorResource(R.color.white),
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(bottom = 40.dp)
        )
    }

    Card(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.ghost_white)),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 180.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
            )

            OutlinedTextField(
                value = email,
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon", tint = colorResource(R.color.teal)) },
                onValueChange = { email = it },
                singleLine = true,
                shape = shapes.large,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 4.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = colorResource(R.color.gray),containerColor = colorResource(R.color.white)),
                label = { Text(stringResource(R.string.email), color = colorResource(R.color.gray)) },
                isError = state.value?.isError?.isNotEmpty() == true,
            )

            OutlinedTextField(
                value = password,
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "passwordIcon", tint = colorResource(R.color.teal)) },
                onValueChange = { password = it },
                singleLine = true,
                shape = shapes.large,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 4.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = colorResource(R.color.gray),containerColor = colorResource(R.color.white)),
                label = { Text(stringResource(R.string.password), color = colorResource(R.color.gray)) },
                isError = state.value?.isError?.isNotEmpty() == true,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (state.value?.isLoading == true) {
                    CircularProgressIndicator(modifier = Modifier.size(30.dp), color = colorResource(R.color.teal))
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.teal)),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    scope.launch {
                        viewModel.registerUser(email, password)
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.white),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
        LaunchedEffect(key1 = state.value?.isSuccess){
            scope.launch {
                if(state.value?.isSuccess?.isNotEmpty() == true){
                    val success = state.value?.isSuccess
                    Toast.makeText(context, success, Toast.LENGTH_LONG).show()

                    val db = Firebase.firestore
                    val name = email.split("@")[0]
                    db.collection("users").add(hashMapOf("name" to name))

                    navController.popBackStack()
                }
            }
        }

        LaunchedEffect(key1 = state.value?.isError){
            scope.launch {
                if(state.value?.isError?.isNotEmpty() == true){
                    val error = state.value?.isError
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show()

                }
            }
        }
}