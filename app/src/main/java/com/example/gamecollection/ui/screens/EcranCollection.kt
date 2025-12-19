package com.example.gamecollection.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gamecollection.R
import com.example.gamecollection.data.Jeu
import com.example.gamecollection.data.StatutJeu
import com.example.gamecollection.ui.components.BarreRecherche
import com.example.gamecollection.ui.components.CarteJeu
import com.example.gamecollection.ui.components.FiltresStatut

/**
 * Écran principal affichant la collection de jeux.
 * S'adapte à l'orientation de l'écran (portrait/paysage).
 *
 * @param jeux Liste des jeux à afficher
 * @param onJeuClick Callback appelé lors du clic sur un jeu
 * @param onAjouterJeu Callback appelé lors de l'ajout d'un jeu
 * @param modifier Modifier pour personnaliser l'apparence
 */
@Composable
fun EcranCollection(
    jeux: List<Jeu>,
    onJeuClick: (Jeu) -> Unit = {},
    onAjouterJeu: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // États locaux pour la recherche et les filtres
    var texteRecherche by rememberSaveable { mutableStateOf("") }
    var statutFiltre by rememberSaveable { mutableStateOf<StatutJeu?>(null) }

    // Filtrer les jeux selon la recherche et le statut
    val jeuxFiltres = jeux.filter { jeu ->
        val correspondRecherche = jeu.titre.contains(texteRecherche, ignoreCase = true)
        val correspondStatut = statutFiltre == null || jeu.statut == statutFiltre
        correspondRecherche && correspondStatut
    }

    // Détecter l'orientation
    val configuration = LocalConfiguration.current
    val estPaysage = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(modifier = modifier.fillMaxSize()) {
        if (estPaysage) {
            // Layout paysage : filtres à gauche, liste à droite
            ContenuPaysage(
                texteRecherche = texteRecherche,
                onRechercheChange = { texteRecherche = it },
                statutFiltre = statutFiltre,
                onStatutChange = { statutFiltre = it },
                jeuxFiltres = jeuxFiltres,
                onJeuClick = onJeuClick
            )
        } else {
            // Layout portrait : tout en colonne
            ContenuPortrait(
                texteRecherche = texteRecherche,
                onRechercheChange = { texteRecherche = it },
                statutFiltre = statutFiltre,
                onStatutChange = { statutFiltre = it },
                jeuxFiltres = jeuxFiltres,
                onJeuClick = onJeuClick
            )
        }

        // Bouton flottant pour ajouter un jeu
        FloatingActionButton(
            onClick = onAjouterJeu,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.ajouter_jeu)
            )
        }
    }
}

/**
 * Contenu de l'écran en mode portrait.
 */
@Composable
private fun ContenuPortrait(
    texteRecherche: String,
    onRechercheChange: (String) -> Unit,
    statutFiltre: StatutJeu?,
    onStatutChange: (StatutJeu?) -> Unit,
    jeuxFiltres: List<Jeu>,
    onJeuClick: (Jeu) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Titre
        Text(
            text = stringResource(R.string.titre_collection),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Barre de recherche
        BarreRecherche(
            valeur = texteRecherche,
            onValeurChange = onRechercheChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Filtres par statut
        FiltresStatut(
            statutSelectionne = statutFiltre,
            onStatutChange = onStatutChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Liste des jeux
        ListeJeux(
            jeux = jeuxFiltres,
            onJeuClick = onJeuClick,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Contenu de l'écran en mode paysage.
 */
@Composable
private fun ContenuPaysage(
    texteRecherche: String,
    onRechercheChange: (String) -> Unit,
    statutFiltre: StatutJeu?,
    onStatutChange: (StatutJeu?) -> Unit,
    jeuxFiltres: List<Jeu>,
    onJeuClick: (Jeu) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Colonne gauche : titre, recherche et filtres
        Column(
            modifier = Modifier
                .width(250.dp)
                .padding(end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.titre_collection),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            BarreRecherche(
                valeur = texteRecherche,
                onValeurChange = onRechercheChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Filtres en colonne verticale pour le mode paysage
            FiltresStatutVertical(
                statutSelectionne = statutFiltre,
                onStatutChange = onStatutChange
            )
        }

        // Colonne droite : liste des jeux
        ListeJeux(
            jeux = jeuxFiltres,
            onJeuClick = onJeuClick,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Filtres de statut affichés verticalement (pour mode paysage).
 */
@Composable
private fun FiltresStatutVertical(
    statutSelectionne: StatutJeu?,
    onStatutChange: (StatutJeu?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        androidx.compose.material3.FilterChip(
            selected = statutSelectionne == null,
            onClick = { onStatutChange(null) },
            label = { Text(stringResource(R.string.filtre_toutes)) }
        )

        StatutJeu.entries.forEach { statut ->
            androidx.compose.material3.FilterChip(
                selected = statutSelectionne == statut,
                onClick = { onStatutChange(statut) },
                label = { Text(statut.label) }
            )
        }
    }
}

/**
 * Liste scrollable des jeux.
 */
@Composable
private fun ListeJeux(
    jeux: List<Jeu>,
    onJeuClick: (Jeu) -> Unit,
    modifier: Modifier = Modifier
) {
    if (jeux.isEmpty()) {
        // Message si aucun jeu
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.aucun_jeu),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 80.dp) // Espace pour le FAB
        ) {
            items(jeux, key = { it.id }) { jeu ->
                CarteJeu(
                    jeu = jeu,
                    onClick = { onJeuClick(jeu) }
                )
            }
        }
    }
}