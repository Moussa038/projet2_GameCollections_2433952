package com.example.gamecollection.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.gamecollection.data.Jeu
import com.example.gamecollection.data.StatutJeu
import com.example.gamecollection.ui.theme.StatusAbandoned
import com.example.gamecollection.ui.theme.StatusCompleted
import com.example.gamecollection.ui.theme.StatusInProgress
import com.example.gamecollection.ui.theme.StatusToPlay

/**
 * Carte affichant les informations résumées d'un jeu.
 *
 * @param jeu Le jeu à afficher
 * @param onClick Callback appelé lors du clic sur la carte
 * @param modifier Modifier pour personnaliser l'apparence
 */
@Composable
fun CarteJeu(
    jeu: Jeu,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder pour l'image du jeu
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🎮",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Informations du jeu
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Titre
                Text(
                    text = jeu.titre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Plateforme
                Text(
                    text = jeu.plateforme.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Statut
                BadgeStatut(statut = jeu.statut)

                // Note (étoiles)
                if (jeu.note > 0) {
                    EtoilesNotation(note = jeu.note)
                }
            }
        }
    }
}

/**
 * Badge coloré affichant le statut d'un jeu.
 *
 * @param statut Le statut du jeu
 * @param modifier Modifier pour personnaliser l'apparence
 */
@Composable
fun BadgeStatut(
    statut: StatutJeu,
    modifier: Modifier = Modifier
) {
    val couleur = when (statut) {
        StatutJeu.A_JOUER -> StatusToPlay
        StatutJeu.EN_COURS -> StatusInProgress
        StatutJeu.TERMINE -> StatusCompleted
        StatutJeu.ABANDONNE -> StatusAbandoned
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = couleur
    ) {
        Text(
            text = statut.label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = androidx.compose.ui.graphics.Color.White
        )
    }
}

/**
 * Affiche une notation sous forme d'étoiles.
 *
 * @param note La note (0 à 5)
 * @param modifier Modifier pour personnaliser l'apparence
 */
@Composable
fun EtoilesNotation(
    note: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            Text(
                text = if (index < note) "★" else "☆",
                color = if (index < note) {
                    com.example.gamecollection.ui.theme.StarFilled
                } else {
                    com.example.gamecollection.ui.theme.StarEmpty
                }
            )
        }
    }
}