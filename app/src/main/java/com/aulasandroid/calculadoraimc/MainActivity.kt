package com.aulasandroid.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aulasandroid.calculadoraimc.ui.theme.CalculadoraIMCTheme
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IMCScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun IMCScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        var altura by remember {
            mutableStateOf("")
        }

        var peso by remember {
            mutableStateOf("")
        }

        var imc: Double by remember {
            mutableStateOf(0.0)
        }

        fun cardImc(imc: Double): androidx.compose.ui.graphics.Color{
            return when {
                imc == 0.0 -> Color(0xFFA9A9A9)
                imc >= 18.5 && imc < 25 -> Color(0xFF027003)
                imc >= 25 && imc < 30 -> Color(0xFFFFA500)
                else -> Color(0xFFFF0000)
            }
        }

        Column(
            modifier = Modifier
                .background(color = colorResource(R.color.cor_app))
                .fillMaxWidth()
                .height(160.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.bmi),
                contentDescription = "Icon IMC",
                modifier = Modifier
                    .size(80.dp)
                    .padding(16.dp)
            )

            Text(
                text = "Calculadora IMC",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .offset(y = (-30).dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9F6F6)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Seus Dados",
                        color = colorResource(R.color.cor_app),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = altura,
                        onValueChange = {
                            altura = it
                        },

                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(
                                text = "Altura"
                            )
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = peso,
                        onValueChange = {
                            peso = it
                        },

                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = {
                            Text(
                                text = "Peso"
                            )
                        },
                        singleLine = true
                    )

                    Button(
                        onClick = {
                            val alturaMetros = altura.toDouble() / 100

                            if (alturaMetros > 0 && peso.toDouble() > 0){
                                imc = peso.toDouble() / (alturaMetros * alturaMetros)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.cor_app),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Calcular",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                colors = CardDefaults.cardColors(
                    containerColor = cardImc(imc),
                    contentColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "%.1f".format(imc),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (imc == 0.0){
                        Text(
                            text = "Calcule seu IMC!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }else if (imc < 18.5){
                        Text(
                            text = "Abaixo do peso.",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }else if (imc < 25){
                        Text(
                            text = "Peso ideal.",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }else if (imc < 30){
                        Text(
                            text = "Levemente acima do peso.",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }else if (imc < 35){
                        Text(
                            text = "Obesidade grau 1.",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }else if (imc < 40){
                        Text(
                            text = "Obesidade grau 2.",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }else{
                        Text(
                            text = "Obesidade grau 3.",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}