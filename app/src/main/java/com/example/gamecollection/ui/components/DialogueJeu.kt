package com.example.gamecollection.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gamecollection.R
import com.example.gamecollection.data.Genre
import com.example.gamecollection.data.Jeu
import com.example.gamecollection.data.Plateforme
import com.example.gamecollection.data.StatutJeu

/**
 * Dialogue pour ajouter ou modifier un jeu.
 *
 * @param jeuExistant Jeu à modifier (null pour un ajout)
 * @param onValider Callback appelé lors de la validation
 * @param onAnnuler Callback appelé lors de l'annulation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogueJeu(
    jeuExistant: Jeu? = null,
    onValider: (Jeu) -> Unit,
    onAnnuler: () -> Unit
) {
    // États du formulaire
    var titre by remember { mutableStateOf(jeuExistant?.titre ?: "") }
    var plateforme by remember { mutableStateOf(jeuExistant?.plateforme ?: Plateforme.PC) }
    var genre by remember { mutableStateOf(jeuExistant?.genre ?: Genre.ACTION) }
    var anneeSortie by remember { mutableStateOf(jeuExistant?.anneeSortie?.toString() ?: "2024") }
    var statut by remember { mutableStateOf(jeuExistant?.statut ?: StatutJeu.A_JOUER) }
    var note by remember { mutableIntStateOf(jeuExistant?.note ?: 0) }
    var tempsJeuHeures by remember { mutableStateOf((jeuExistant?.tempsJeu?.div(60))?.toString() ?: "0") }
    var tempsJeuMinutes by remember { mutableStateOf((jeuExistant?.tempsJeu?.rem(60))?.toString() ?: "0") }
    var commentaires by remember { mutableStateOf(jeuExistant?.commentaires ?: "") }

    // États pour les menus déroulants
    var plateformeExpanded by remember { mutableStateOf(false) }
    var genreExpanded by remember { mutableStateOf(false) }

    val estModification = jeuExistant != null

    AlertDialog(
        onDismissRequest = onAnnuler,
        title = {
            Text(
                text = if (estModification) stringResource(R.string.modifier) else stringResource(R.string.ajouter_jeu)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Titre
                OutlinedTextField(
                    value = titre,
                    onValueChange = { titre = it },
                    label = { Text("Titre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Plateforme
                ExposedDropdownMenuBox(
                    expanded = plateformeExpanded,
                    onExpandedChange = { plateformeExpanded = it }
                ) {
                    OutlinedTextField(
                        value = plateforme.label,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.plateforme)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = plateformeExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = plateformeExpanded,
                        onDismissRequest = { plateformeExpanded = false }
                    ) {
                        Plateforme.entries.forEach { p ->
                            DropdownMenuItem(
                                text = { Text(p.label) },
                                onClick = {
                                    plateforme = p
                                    plateformeExpanded = false
                                }
                            )
                        }
                    }
                }

                // Genre
                ExposedDropdownMenuBox(
                    expanded = genreExpanded,
                    onExpandedChange = { genreExpanded = it }
                ) {
                    OutlinedTextField(
                        value = genre.label,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.genre)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genreExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = genreExpanded,
                        onDismissRequest = { genreExpanded = false }
                    ) {
                        Genre.entries.forEach { g ->
                            DropdownMenuItem(
                                text = { Text(g.label) },
                                onClick = {
                                    genre = g
                                    genreExpanded = false
                                }
                            )
                        }
                    }
                }

                // Année de sortie
                OutlinedTextField(
                    value = anneeSortie,
                    onValueChange = { anneeSortie = it.filter { c -> c.isDigit() }.take(4) },
                    label = { Text(stringResource(R.string.annee)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Statut
                Text(
                    text = stringResource(R.string.statut),
                    style = MaterialTheme.typography.labelMedium
                )
                Column {
                    StatutJeu.entries.forEach { s ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = statut == s,
                                    onClick = { statut = s },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = statut == s,
                                onClick = null
                            )
                            Text(
                                text = s.label,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                // Note
                Text(
                    text = stringResource(R.string.note_personnelle),
                    style = MaterialTheme.typography.labelMedium
                )
                SelecteurNote(
                    note = note,
                    onNoteChange = { note = it }
                )

                // Temps de jeu
                Text(
                    text = stringResource(R.string.temps_de_jeu),
                    style = MaterialTheme.typography.labelMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = tempsJeuHeures,
                        onValueChange = { tempsJeuHeures = it.filter { c -> c.isDigit() }.take(4) },
                        label = { Text("Heures") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = tempsJeuMinutes,
                        onValueChange = { tempsJeuMinutes = it.filter { c -> c.isDigit() }.take(2) },
                        label = { Text("Minutes") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                // Commentaires
                OutlinedTextField(
                    value = commentaires,
                    onValueChange = { commentaires = it },
                    label = { Text(stringResource(R.string.commentaires)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val heures = tempsJeuHeures.toIntOrNull() ?: 0
                    val minutes = tempsJeuMinutes.toIntOrNull() ?: 0
                    val tempsTotal = (heures * 60) + minutes

                    val nouveauJeu = Jeu(
                        id = jeuExistant?.id ?: java.util.UUID.randomUUID().toString(),
                        titre = titre,
                        plateforme = plateforme,
                        genre = genre,
                        anneeSortie = anneeSortie.toIntOrNull() ?: 2024,
                        statut = statut,
                        note = note,
                        tempsJeu = tempsTotal,
                        commentaires = commentaires
                    )
                    onValider(nouveauJeu)
                },
                enabled = titre.isNotBlank()
            ) {
                Text(stringResource(R.string.enregistrer))
            }
        },
        dismissButton = {
            TextButton(onClick = onAnnuler) {
                Text(stringResource(R.string.annuler))
            }
        }
    )
}

/**
 * Sélecteur de note avec étoiles cliquables.
 *
 * @param note Note actuelle (0-5)
 * @param onNoteChange Callback appelé lors du changement de note
 */
@Composable
private fun SelecteurNote(
    note: Int,
    onNoteChange: (Int) -> Unit
) {
    Row {
        repeat(5) { index ->
            TextButton(
                onClick = {
                    // Si on clique sur l'étoile déjà sélectionnée, on désélectionne
                    onNoteChange(if (note == index + 1) 0 else index + 1)
                }
            ) {
                Text(
                    text = if (index < note) "★" else "☆",
                    color = if (index < note) {
                        com.example.gamecollection.ui.theme.StarFilled
                    } else {
                        com.example.gamecollection.ui.theme.StarEmpty
                    },
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}