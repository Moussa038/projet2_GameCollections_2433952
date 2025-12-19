package com.example.gamecollection.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gamecollection.R
import com.example.gamecollection.data.Jeu
import com.example.gamecollection.data.Plateforme
import com.example.gamecollection.data.StatutJeu
import com.example.gamecollection.ui.theme.PrimaryPurple
import com.example.gamecollection.ui.theme.StatusAbandoned
import com.example.gamecollection.ui.theme.StatusCompleted
import com.example.gamecollection.ui.theme.StatusInProgress
import com.example.gamecollection.ui.theme.StatusToPlay

/**
 * Écran affichant les statistiques de la collection.
 *
 * @param jeux Liste des jeux pour calculer les statistiques
 * @param modifier Modifier pour personnaliser l'apparence
 */
@Composable
fun EcranStats(
    jeux: List<Jeu>,
    modifier: Modifier = Modifier
) {
    // Calculs des statistiques
    val totalJeux = jeux.size
    val jeuxAvecNote = jeux.filter { it.note > 0 }
    val noteMoyenne = if (jeuxAvecNote.isNotEmpty()) {
        jeuxAvecNote.map { it.note }.average()
    } else {
        0.0
    }
    val tempsTotal = jeux.sumOf { it.tempsJeu } / 60

    // Comptage par statut
    val enCours = jeux.count { it.statut == StatutJeu.EN_COURS }
    val termines = jeux.count { it.statut == StatutJeu.TERMINE }
    val aJouer = jeux.count { it.statut == StatutJeu.A_JOUER }
    val abandonnes = jeux.count { it.statut == StatutJeu.ABANDONNE }

    // Comptage par plateforme
    val parPlateforme = Plateforme.entries
        .map { p -> p to jeux.count { it.plateforme == p } }
        .filter { it.second > 0 }
        .sortedByDescending { it.second }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Titre
        Text(
            text = stringResource(R.string.titre_statistiques),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cartes de résumé
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CarteStatistique(
                titre = stringResource(R.string.total_jeux),
                valeur = "$totalJeux",
                sousTexte = "jeux",
                couleur = PrimaryPurple,
                modifier = Modifier.weight(1f)
            )
            CarteStatistique(
                titre = stringResource(R.string.note_moyenne),
                valeur = String.format("%.1f", noteMoyenne),
                sousTexte = "★★★★☆",
                couleur = StatusInProgress,
                modifier = Modifier.weight(1f)
            )
            CarteStatistique(
                titre = stringResource(R.string.temps_total),
                valeur = "${tempsTotal}h",
                sousTexte = "de jeu",
                couleur = StatusCompleted,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Répartition par statut
        Text(
            text = stringResource(R.string.repartition_statut),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LigneStatut(label = "En cours", count = enCours, couleur = StatusInProgress)
                LigneStatut(label = "Terminés", count = termines, couleur = StatusCompleted)
                LigneStatut(label = "À jouer", count = aJouer, couleur = StatusToPlay)
                LigneStatut(label = "Abandonnés", count = abandonnes, couleur = StatusAbandoned)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Jeux par plateforme
        Text(
            text = stringResource(R.string.jeux_par_plateforme),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (parPlateforme.isEmpty()) {
                    Text(
                        text = stringResource(R.string.aucun_jeu),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    parPlateforme.forEach { (plateforme, count) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = plateforme.label)
                            Text(
                                text = "$count",
                                fontWeight = FontWeight.Bold,
                                color = PrimaryPurple
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Carte individuelle de statistique.
 */
@Composable
private fun CarteStatistique(
    titre: String,
    valeur: String,
    sousTexte: String,
    couleur: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = couleur)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = titre,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
            )
            Text(
                text = valeur,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = sousTexte,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

/**
 * Ligne affichant un statut et son comptage.
 */
@Composable
private fun LigneStatut(
    label: String,
    count: Int,
    couleur: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(
                text = "● ",
                color = couleur
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = "$count",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}