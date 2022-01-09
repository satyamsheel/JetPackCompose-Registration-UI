package com.connectors.jet

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RegistrationScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationScreen() {
    val context = LocalContext.current

    val name = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val countryCode = remember { mutableStateOf(TextFieldValue()) }
    val mobileNo = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val panCard = remember { mutableStateOf(TextFieldValue()) }
    val selectedGender = remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap =  remember { mutableStateOf<Bitmap?>(null) }
    var resultUri by remember { mutableStateOf<Uri?>(null) }

    val nameErrorState = remember { mutableStateOf(false) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val panCardErrorState = remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
    ) {

        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("Stash")
            }
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("fin")
            }
        }, fontSize = 30.sp)
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = {
                if (nameErrorState.value) {
                    nameErrorState.value = false
                }
                name.value = it
            },

            modifier = Modifier.fillMaxWidth(),
            isError = nameErrorState.value,
            label = {
                Text(text = "Name*")
            },
        )
        if (nameErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }

        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                email.value = it
            },

            modifier = Modifier.fillMaxWidth(),
            isError = emailErrorState.value,
            label = {
                Text(text = "Email*")
            },
        )
        if (emailErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }
        if(panCardErrorState.value){
            Text(text = "Required", color = Color.Red)
        }

        Spacer(modifier = Modifier.size(16.dp))
        Row() {
            OutlinedTextField(
                value = countryCode.value,
                onValueChange = {

                    countryCode.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    autoCorrect = false
                ),
                modifier = Modifier.fillMaxWidth(0.3f),
                label = {
                    Text(text = "Code")
                },
            )
            Spacer(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = mobileNo.value,
                onValueChange = {

                    mobileNo.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    autoCorrect = false
                ),
                label = {
                    Text(text = "Mobile No")
                },
            )
        }

        Spacer(Modifier.size(16.dp))
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                if (passwordErrorState.value) {
                    passwordErrorState.value = false
                }
                password.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Password*")
            },
            isError = passwordErrorState.value,
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = Color.Red
                    )
                }
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (passwordErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }

        Spacer(Modifier.size(16.dp))
        val cPasswordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = panCard.value,
            onValueChange = {
                if (panCardErrorState.value) {
                    panCardErrorState.value = false
                }
                panCard.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            isError = panCardErrorState.value,
            label = {
                Text(text = "Pan Card*")
            },
            trailingIcon = {
                IconButton(onClick = {
                    cPasswordVisibility.value = !cPasswordVisibility.value
                }) {
                    Icon(
                        imageVector = if (cPasswordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = Color.Red
                    )
                }
            },
            visualTransformation = if (cPasswordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (panCardErrorState.value) {
            val msg = if (panCard.value.text.isEmpty()) {
                "Required"
            } else {
                ""
            }
            Text(text = msg, color = Color.Red)
        }

        Spacer(Modifier.size(16.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Select Gender")
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                RadioButton(selected = selectedGender.value == Gender.male, onClick = {
                    selectedGender.value = Gender.male
                },colors = RadioButtonDefaults.colors(Color.Red))

                Spacer(modifier = Modifier.size(16.dp))
                Text(Gender.male)
                Spacer(modifier = Modifier.size(16.dp))
                RadioButton(selected = selectedGender.value == Gender.female, onClick = {
                    selectedGender.value = Gender.female
                },colors = RadioButtonDefaults.colors(Color.Magenta))

                Spacer(modifier = Modifier.size(16.dp))
                Text(Gender.female)

                Spacer(modifier = Modifier.size(16.dp))
                RadioButton(selected = selectedGender.value == Gender.others, onClick = {
                    selectedGender.value = Gender.others
                },colors = RadioButtonDefaults.colors(Color.Blue))
                Spacer(modifier = Modifier.size(16.dp))
                Text(Gender.others)
            }
        }

        Spacer(Modifier.size(16.dp))
        val launcher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.GetContent()) { uri :Uri? ->
            imageUri = uri
        }

        val launchers = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.GetContent()) { uri: Uri? ->
            resultUri = uri
        }

        Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Button(onClick = {
                launcher.launch("image/*")
            }, colors = buttonColors(backgroundColor = Color.Red)) {
                Text(text = "Pick image", color = Color.White)
            }
            Button(onClick = {
                launchers.launch("*/*")
            }, colors = buttonColors(backgroundColor = Color.Red)) {
                Text(text = "Upload PDF", color = Color.White)
            }
        }

            Spacer(modifier = Modifier.height(12.dp))
            Column() {
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver,it)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver,it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
                bitmap.value?.let {  btm ->
                    Image(bitmap = btm.asImageBitmap(),
                        contentDescription =null,
                        modifier = Modifier.size(400.dp))
                }
            }
        }



        Spacer(Modifier.size(16.dp))
        Button(
            onClick = {
                when {
                    name.value.text.isEmpty() -> {
                        nameErrorState.value = true
                    }
                    email.value.text.isEmpty() -> {
                        emailErrorState.value = true
                    }
                    password.value.text.isEmpty() -> {
                        passwordErrorState.value = true
                    }
                    panCard.value.text.isEmpty() -> {
                        panCardErrorState.value = true
                    }
                    selectedGender.value.isEmpty() ->{
                        Toast.makeText(
                            context,
                            "Select A Gendre",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    panCard.value.text.length != 10 ->{
                        Toast.makeText(
                                    context,
                                    "PanCard Format Error",
                                    Toast.LENGTH_SHORT
                                    ).show()
                    }
                    mobileNo.value.text.length != 10 ->{
                        Toast.makeText(
                            context,
                            "Mobile No Format Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.text).matches() ->
                        Toast.makeText(
                            context,
                            "Email Format Error",
                            Toast.LENGTH_SHORT
                        ).show()
                    else -> {
                        Toast.makeText(
                            context,
                            "Registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            content = {
                Text(text = "Register", color = Color.White)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = buttonColors(backgroundColor = Color.Red)
        )
        Spacer(Modifier.size(16.dp))

    }
}

object Gender {
    const val male = "Male"
    const val female = "Female"
    const val others = "Others"
}

