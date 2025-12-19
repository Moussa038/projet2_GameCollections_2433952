package com.example.gamecollection.ui.screens

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gamecollection.R
import com.example.gamecollection.data.Jeu
import com.example.gamecollection.ui.components.BadgeStatut
import com.example.gamecollection.ui.components.EtoilesNotation

/**
 * Écran affichant les détails d'un jeu.
 *
 * @param jeu Le jeu à afficher
 * @param onRetour Callback pour revenir à l'écran précédent
 * @param onModifier Callback pour modifier le jeu
 * @param onSupprimer Callback pour supprimer le jeu
 * @param modifier Modifier pour personnaliser l'apparence
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcranDetails(
    jeu: Jeu,
    onRetour: () -> Unit = {},
    onModifier: (Jeu) -> Unit = {},
    onSupprimer: (Jeu) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val estPaysage = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(modifier = modifier.fillMaxSize()) {
        // Barre supérieure
        TopAppBar(
            title = { Text(stringResource(R.string.titre_details)) },
            navigationIcon = {
                IconButton(onClick = onRetour) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.annuler)
                    )
                }
            },
            actions = {
                IconButton(onClick = { onModifier(jeu) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.modifier)
                    )
                }
                IconButton(onClick = { onSupprimer(jeu) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.supprimer)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        // Contenu
        if (estPaysage) {
            ContenuDetailsPaysage(jeu = jeu)
        } else {
            ContenuDetailsPortrait(jeu = jeu)
        }
    }
}

/**
 * Contenu des détails en mode portrait.
 */
@Composable
private fun ContenuDetailsPortrait(jeu: Jeu) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Image placeholder
        ImageJeu()

        Spacer(modifier = Modifier.height(16.dp))

        // Titre du jeu
        Text(
            text = jeu.titre,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Infos principales
        CartesInfoPrincipales(jeu = jeu)

        Spacer(modifier = Modifier.height(16.dp))

        // Statut
        SectionStatut(jeu = jeu)

        Spacer(modifier = Modifier.height(16.dp))

        // Note personnelle
        SectionNote(jeu = jeu)

        Spacer(modifier = Modifier.height(16.dp))

        // Temps de jeu
        SectionTempsJeu(jeu = jeu)

        Spacer(modifier = Modifier.height(16.dp))

        // Commentaires
        SectionCommentaires(jeu = jeu)
    }
}

/**
 * Contenu des détails en mode paysage.
 */
@Composable
private fun ContenuDetailsPaysage(jeu: Jeu) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Colonne gauche : image et infos principales
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            ImageJeu()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = jeu.titre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            CartesInfoPrincipales(jeu = jeu)
        }

        // Colonne droite : statut, note, commentaires
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SectionStatut(jeu = jeu)

            Spacer(modifier = Modifier.height(12.dp))

            SectionNote(jeu = jeu)

            Spacer(modifier = Modifier.height(12.dp))

            SectionTempsJeu(jeu = jeu)

            Spacer(modifier = Modifier.height(12.dp))

            SectionCommentaires(jeu = jeu)
        }
    }
}

/**
 * Placeholder pour l'image du jeu.
 */
@Composable
private fun ImageJeu() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "🎮",
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

/**
 * Cartes affichant plateforme, genre et année.
 */
@Composable
private fun CartesInfoPrincipales(jeu: Jeu) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CarteInfo(
            label = stringResource(R.string.plateforme),
            valeur = jeu.plateforme.label,
            modifier = Modifier.weight(1f)
        )
        CarteInfo(
            label = stringResource(R.string.genre),
            valeur = jeu.genre.label,
            modifier = Modifier.weight(1f)
        )
        CarteInfo(
            label = stringResource(R.string.annee),
            valeur = jeu.anneeSortie.toString(),
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Carte d'information individuelle.
 */
@Composable
private fun CarteInfo(
    label: String,
    valeur: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = valeur,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Section affichant le statut du jeu.
 */
@Composable
private fun SectionStatut(jeu: Jeu) {
    Text(
        text = stringResource(R.string.statut),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    BadgeStatut(statut = jeu.statut)
}

/**
 * Section affichant la note personnelle.
 */
@Composable
private fun SectionNote(jeu: Jeu) {
    Text(
        text = stringResource(R.string.note_personnelle),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    EtoilesNotation(note = jeu.note)
}

/**
 * Section affichant le temps de jeu.
 */
@Composable
private fun SectionTempsJeu(jeu: Jeu) {
    Text(
        text = stringResource(R.string.temps_de_jeu),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "🕐 ${jeu.obtenirTempsFormate()}",
        style = MaterialTheme.typography.bodyLarge
    )
}

/**
 * Section affichant les commentaires.
 */
@Composable
private fun SectionCommentaires(jeu: Jeu) {
    Text(
        text = stringResource(R.string.commentaires),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = jeu.commentaires.ifEmpty { "Aucun commentaire" },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(12.dp),
            color = if (jeu.commentaires.isEmpty()) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}