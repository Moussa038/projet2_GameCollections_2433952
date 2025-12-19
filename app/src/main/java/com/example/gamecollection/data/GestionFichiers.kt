package com.example.gamecollection.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

/**
 * Nom du fichier JSON pour sauvegarder la collection de jeux.
 */
private const val NOM_FICHIER = "collection_jeux.json"

/**
 * Tag pour les logs.
 */
private const val TAG = "GestionFichiers"

/**
 * Sauvegarde la liste des jeux dans un fichier JSON.
 *
 * @param jeux Liste des jeux à sauvegarder
 * @param nomFichier Nom du fichier (par défaut: collection_jeux.json)
 */
fun Context.sauvegarderJeux(jeux: List<Jeu>, nomFichier: String = NOM_FICHIER) {
    try {
        val fichier = File(this.filesDir, nomFichier)
        val jsonString = Gson().toJson(jeux)
        fichier.writeText(jsonString)
        Log.d(TAG, "Jeux sauvegardés: ${jeux.size} jeux dans $nomFichier")
    } catch (e: Exception) {
        Log.e(TAG, "Erreur lors de la sauvegarde des jeux", e)
    }
}

/**
 * Charge la liste des jeux depuis un fichier JSON.
 *
 * @param nomFichier Nom du fichier (par défaut: collection_jeux.json)
 * @return Liste des jeux chargés, ou liste vide si le fichier n'existe pas
 */
fun Context.chargerJeux(nomFichier: String = NOM_FICHIER): List<Jeu> {
    val fichier = File(this.filesDir, nomFichier)

    return if (fichier.exists()) {
        try {
            val jsonString = fichier.readText()
            val typeListeJeux = object : TypeToken<List<Jeu>>() {}.type
            val jeux: List<Jeu> = Gson().fromJson(jsonString, typeListeJeux)
            Log.d(TAG, "Jeux chargés: ${jeux.size} jeux depuis $nomFichier")
            jeux
        } catch (e: Exception) {
            Log.e(TAG, "Erreur lors du chargement des jeux", e)
            emptyList()
        }
    } else {
        Log.d(TAG, "Fichier $nomFichier non trouvé, retour liste vide")
        emptyList()
    }
}

/**
 * Vérifie si le fichier de sauvegarde existe.
 *
 * @param nomFichier Nom du fichier (par défaut: collection_jeux.json)
 * @return true si le fichier existe, false sinon
 */
fun Context.fichierSauvegardeExiste(nomFichier: String = NOM_FICHIER): Boolean {
    return File(this.filesDir, nomFichier).exists()
}