package com.example.gamecollection.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gamecollection.R

/**
 * Barre de recherche pour filtrer les jeux.
 *
 * @param valeur Texte actuel de la recherche
 * @param onValeurChange Callback appelé lors du changement de texte
 * @param modifier Modifier pour personnaliser l'apparence
 */
@Composable
fun BarreRecherche(
    valeur: String,
    onValeurChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valeur,
        onValueChange = onValeurChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.rechercher_jeu)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (valeur.isNotEmpty()) {
                IconButton(onClick = { onValeurChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp)
    )
}