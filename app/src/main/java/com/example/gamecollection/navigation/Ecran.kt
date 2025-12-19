package com.example.gamecollection.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gamecollection.R

/**
 * Classe scellée définissant les écrans de l'application.
 *
 * @property route Identifiant de la route pour la navigation
 * @property titreResId ID de la ressource string pour le titre
 * @property icone Icône affichée dans la barre de navigation
 */
sealed class Ecran(
    val route: String,
    val titreResId: Int,
    val icone: ImageVector
) {
    /** Écran principal affichant la collection de jeux. */
    object Collection : Ecran(
        route = "collection",
        titreResId = R.string.nav_collection,
        icone = Icons.Default.Home
    )

    /** Écran affichant les statistiques de la collection. */
    object Stats : Ecran(
        route = "stats",
        titreResId = R.string.nav_stats,
        icone = Icons.Default.Star
    )

    /** Écran des paramètres de l'application. */
    object Parametres : Ecran(
        route = "parametres",
        titreResId = R.string.nav_parametres,
        icone = Icons.Default.Settings
    )

    /** Écran de détails d'un jeu. */
    object Details : Ecran(
        route = "details/{jeuId}",
        titreResId = R.string.titre_details,
        icone = Icons.Default.Info
    ) {
        /**
         * Crée la route avec l'ID du jeu.
         *
         * @param jeuId Identifiant du jeu
         * @return Route formatée
         */
        fun creerRoute(jeuId: String): String = "details/$jeuId"
    }

    companion object {
        /** Liste des écrans affichés dans la barre de navigation. */
        val items = listOf(Collection, Stats, Parametres)
    }
}