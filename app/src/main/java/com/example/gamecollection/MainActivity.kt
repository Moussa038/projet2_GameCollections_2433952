package com.example.gamecollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.gamecollection.navigation.NavigationPrincipale
import com.example.gamecollection.ui.theme.GameCollectionTheme

/**
 * Activité principale de l'application GameCollection.
 * Point d'entrée de l'application Android.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // État du thème sombre
            var themeSombre by rememberSaveable { mutableStateOf(false) }

            GameCollectionTheme(darkTheme = themeSombre) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationPrincipale(
                        themeSombre = themeSombre,
                        onChangerTheme = { themeSombre = it }
                    )
                }
            }
        }
    }
}