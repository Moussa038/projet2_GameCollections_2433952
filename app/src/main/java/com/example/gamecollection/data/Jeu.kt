package com.example.gamecollection.data

import java.util.UUID

/**
 * Énumération des plateformes de jeu disponibles.
 *
 * @property label Nom affiché de la plateforme
 */
enum class Plateforme(val label: String) {
    PS5("PlayStation 5"),
    PS4("PlayStation 4"),
    XBOX_SERIES("Xbox Series X|S"),
    XBOX_ONE("Xbox One"),
    SWITCH("Nintendo Switch"),
    PC("PC"),
    MOBILE("Mobile")
}

/**
 * Énumération des genres de jeux vidéo.
 *
 * @property label Nom affiché du genre
 */
enum class Genre(val label: String) {
    ACTION("Action"),
    AVENTURE("Aventure"),
    RPG("RPG"),
    FPS("FPS"),
    SPORT("Sport"),
    COURSE("Course"),
    STRATEGIE("Stratégie"),
    SIMULATION("Simulation"),
    HORREUR("Horreur"),
    PUZZLE("Puzzle"),
    COMBAT("Combat"),
    PLATEFORME("Plateforme"),
    AUTRE("Autre")
}

/**
 * Énumération des statuts de progression d'un jeu.
 *
 * @property label Nom affiché du statut
 */
enum class StatutJeu(val label: String) {
    A_JOUER("À jouer"),
    EN_COURS("En cours"),
    TERMINE("Terminé"),
    ABANDONNE("Abandonné")
}

/**
 * Classe représentant un jeu vidéo dans la collection.
 *
 * @property id Identifiant unique du jeu
 * @property titre Titre du jeu
 * @property plateforme Plateforme sur laquelle le jeu est possédé
 * @property genre Genre du jeu
 * @property anneeSortie Année de sortie du jeu
 * @property statut Statut de progression du joueur
 * @property note Note personnelle du joueur (0-5 étoiles)
 * @property tempsJeu Temps de jeu en minutes
 * @property commentaires Commentaires personnels sur le jeu
 */
data class Jeu(
    val id: String = UUID.randomUUID().toString(),
    val titre: String,
    val plateforme: Plateforme,
    val genre: Genre,
    val anneeSortie: Int,
    val statut: StatutJeu = StatutJeu.A_JOUER,
    val note: Int = 0,
    val tempsJeu: Int = 0,
    val commentaires: String = ""
) {
    /**
     * Retourne le temps de jeu formaté en heures et minutes.
     *
     * @return String formatée (ex: "24h 30min")
     */
    fun obtenirTempsFormate(): String {
        val heures = tempsJeu / 60
        val minutes = tempsJeu % 60
        return when {
            heures > 0 && minutes > 0 -> "${heures}h ${minutes}min"
            heures > 0 -> "${heures}h"
            minutes > 0 -> "${minutes}min"
            else -> "0min"
        }
    }
}