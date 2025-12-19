package com.example.gamecollection.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gamecollection.R
import com.example.gamecollection.data.StatutJeu

/**
 * Barre de filtres horizontale pour filtrer par statut.
 *
 * @param statutSelectionne Le statut actuellement sélectionné (null = tous)
 * @param onStatutChange Callback appelé lors du changement de filtre
 * @param modifier Modifier pour personnaliser l'apparence
 */
@Composable
fun FiltresStatut(
    statutSelectionne: StatutJeu?,
    onStatutChange: (StatutJeu?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Filtre "Toutes"
        FilterChip(
            selected = statutSelectionne == null,
            onClick = { onStatutChange(null) },
            label = { Text(stringResource(R.string.filtre_toutes)) }
        )

        // Filtres par statut
        StatutJeu.entries.forEach { statut ->
            FilterChip(
                selected = statutSelectionne == statut,
                onClick = { onStatutChange(statut) },
                label = { Text(statut.label) }
            )
        }
    }
}