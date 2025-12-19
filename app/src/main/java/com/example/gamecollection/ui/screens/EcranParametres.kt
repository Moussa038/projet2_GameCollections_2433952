package com.example.gamecollection.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gamecollection.R

/**
 * Écran des paramètres de l'application.
 * Permet de changer le thème et la langue.
 *
 * @param themeSombre Indique si le thème sombre est activé
 * @param onChangerTheme Callback appelé lors du changement de thème
 * @param langueSelectionnee Code de la langue sélectionnée
 * @param onChangerLangue Callback appelé lors du changement de langue
 * @param modifier Modifier pour personnaliser l'apparence
 */
@Composable
fun EcranParametres(
    themeSombre: Boolean,
    onChangerTheme: (Boolean) -> Unit = {},
    langueSelectionnee: String,
    onChangerLangue: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val estPaysage = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (estPaysage) {
        ContenuParametresPaysage(
            themeSombre = themeSombre,
            onChangerTheme = onChangerTheme,
            langueSelectionnee = langueSelectionnee,
            onChangerLangue = onChangerLangue,
            modifier = modifier
        )
    } else {
        ContenuParametresPortrait(
            themeSombre = themeSombre,
            onChangerTheme = onChangerTheme,
            langueSelectionnee = langueSelectionnee,
            onChangerLangue = onChangerLangue,
            modifier = modifier
        )
    }
}

/**
 * Contenu de l'écran paramètres en mode portrait.
 */
@Composable
private fun ContenuParametresPortrait(
    themeSombre: Boolean,
    onChangerTheme: (Boolean) -> Unit,
    langueSelectionnee: String,
    onChangerLangue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Titre
        Text(
            text = stringResource(R.string.titre_parametres),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section Apparence
        SectionApparence(
            themeSombre = themeSombre,
            onChangerTheme = onChangerTheme
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section Langue
        SectionLangue(
            langueSelectionnee = langueSelectionnee,
            onChangerLangue = onChangerLangue
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section À propos
        SectionAPropos()
    }
}

/**
 * Contenu de l'écran paramètres en mode paysage.
 */
@Composable
private fun ContenuParametresPaysage(
    themeSombre: Boolean,
    onChangerTheme: (Boolean) -> Unit,
    langueSelectionnee: String,
    onChangerLangue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Colonne gauche
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.titre_parametres),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionApparence(
                themeSombre = themeSombre,
                onChangerTheme = onChangerTheme
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionAPropos()
        }

        // Colonne droite
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            SectionLangue(
                langueSelectionnee = langueSelectionnee,
                onChangerLangue = onChangerLangue
            )
        }
    }
}

/**
 * Section pour le changement de thème (clair/sombre).
 */
@Composable
private fun SectionApparence(
    themeSombre: Boolean,
    onChangerTheme: (Boolean) -> Unit
) {
    Text(
        text = stringResource(R.string.apparence),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🌙")
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.theme_sombre),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Switch(
                checked = themeSombre,
                onCheckedChange = onChangerTheme
            )
        }
    }
}

/**
 * Section pour le changement de langue.
 */
@Composable
private fun SectionLangue(
    langueSelectionnee: String,
    onChangerLangue: (String) -> Unit
) {
    val langues = listOf(
        "fr" to "🇫🇷 Français",
        "en" to "🇬🇧 English",
        "es" to "🇪🇸 Español"
    )

    Text(
        text = stringResource(R.string.langue),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            langues.forEach { (code, label) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = langueSelectionnee == code,
                            onClick = { onChangerLangue(code) },
                            role = Role.RadioButton
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = langueSelectionnee == code,
                        onClick = null
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

/**
 * Section À propos de l'application.
 */
@Composable
private fun SectionAPropos() {
    Text(
        text = stringResource(R.string.a_propos),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.version),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "1.0.0",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.developpeur),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.nom_developpeur),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}