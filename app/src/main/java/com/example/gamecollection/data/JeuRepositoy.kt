package com.example.gamecollection.data

/**
 * Repository pour gérer la collection de jeux.
 * Fournit des données de démonstration et des méthodes de gestion.
 */
object JeuRepository {

    /**
     * Retourne une liste de jeux de démonstration pour tester l'application.
     *
     * @return Liste de jeux exemples
     */
    fun obtenirJeuxExemples(): List<Jeu> {
        return listOf(
            Jeu(
                titre = "The Last of Us Part II",
                plateforme = Plateforme.PS5,
                genre = Genre.ACTION,
                anneeSortie = 2020,
                statut = StatutJeu.EN_COURS,
                note = 4,
                tempsJeu = 1470,
                commentaires = "Histoire incroyable, gameplay fluide"
            ),
            Jeu(
                titre = "Elden Ring",
                plateforme = Plateforme.PC,
                genre = Genre.RPG,
                anneeSortie = 2022,
                statut = StatutJeu.TERMINE,
                note = 5,
                tempsJeu = 4800,
                commentaires = "Chef-d'œuvre de FromSoftware"
            ),
            Jeu(
                titre = "Zelda: Breath of the Wild",
                plateforme = Plateforme.SWITCH,
                genre = Genre.AVENTURE,
                anneeSortie = 2017,
                statut = StatutJeu.A_JOUER,
                note = 0,
                tempsJeu = 0,
                commentaires = ""
            ),
            Jeu(
                titre = "God of War Ragnarök",
                plateforme = Plateforme.PS5,
                genre = Genre.ACTION,
                anneeSortie = 2022,
                statut = StatutJeu.TERMINE,
                note = 5,
                tempsJeu = 2400,
                commentaires = "Meilleur jeu de l'année"
            ),
            Jeu(
                titre = "Cyberpunk 2077",
                plateforme = Plateforme.PC,
                genre = Genre.RPG,
                anneeSortie = 2020,
                statut = StatutJeu.ABANDONNE,
                note = 3,
                tempsJeu = 900,
                commentaires = "Trop de bugs au lancement"
            )
        )
    }
}