package com.example.gamecollection.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamecollection.data.Jeu
import com.example.gamecollection.data.JeuRepository
import com.example.gamecollection.data.chargerJeux
import com.example.gamecollection.data.fichierSauvegardeExiste
import com.example.gamecollection.data.sauvegarderJeux
import com.example.gamecollection.ui.components.DialogueJeu
import com.example.gamecollection.ui.screens.EcranCollection
import com.example.gamecollection.ui.screens.EcranDetails
import com.example.gamecollection.ui.screens.EcranParametres
import com.example.gamecollection.ui.screens.EcranStats

/**
 * Composant principal gérant la navigation de l'application.
 * Gère l'état global des jeux et le transmet aux écrans enfants.
 *
 * @param themeSombre Indique si le thème sombre est activé
 * @param onChangerTheme Callback pour changer le thème
 */
@Composable
fun NavigationPrincipale(
    themeSombre: Boolean,
    onChangerTheme: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val routeActuelle = navBackStackEntry?.destination?.route
    val context = LocalContext.current

    // État global de la liste des jeux
    var listeJeux by rememberSaveable { mutableStateOf<List<Jeu>>(emptyList()) }

    // État pour indiquer si les données sont chargées
    var donneesChargees by rememberSaveable { mutableStateOf(false) }

    // État de la langue sélectionnée
    var langueSelectionnee by rememberSaveable { mutableStateOf("fr") }

    // États pour les dialogues
    var afficherDialogueAjout by rememberSaveable { mutableStateOf(false) }
    var jeuAModifier by rememberSaveable { mutableStateOf<Jeu?>(null) }

    // Charger les jeux au démarrage
    LaunchedEffect(Unit) {
        if (!donneesChargees) {
            listeJeux = if (context.fichierSauvegardeExiste()) {
                context.chargerJeux()
            } else {
                JeuRepository.obtenirJeuxExemples()
            }
            donneesChargees = true
        }
    }

    // Sauvegarder automatiquement quand la liste change
    LaunchedEffect(listeJeux) {
        if (donneesChargees && listeJeux.isNotEmpty()) {
            context.sauvegarderJeux(listeJeux)
        }
    }

    // Dialogue d'ajout de jeu
    if (afficherDialogueAjout) {
        DialogueJeu(
            jeuExistant = null,
            onValider = { nouveauJeu ->
                listeJeux = listeJeux + nouveauJeu
                afficherDialogueAjout = false
            },
            onAnnuler = { afficherDialogueAjout = false }
        )
    }

    // Dialogue de modification de jeu
    jeuAModifier?.let { jeu ->
        DialogueJeu(
            jeuExistant = jeu,
            onValider = { jeuModifie ->
                listeJeux = listeJeux.map {
                    if (it.id == jeuModifie.id) jeuModifie else it
                }
                jeuAModifier = null
            },
            onAnnuler = { jeuAModifier = null }
        )
    }

    // Vérifier si on est sur l'écran de détails (pour cacher la bottom bar)
    val afficherBottomBar = routeActuelle in Ecran.items.map { it.route }

    Scaffold(
        bottomBar = {
            if (afficherBottomBar) {
                NavigationBar {
                    Ecran.items.forEach { ecran ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    ecran.icone,
                                    contentDescription = stringResource(ecran.titreResId)
                                )
                            },
                            label = { Text(stringResource(ecran.titreResId)) },
                            selected = routeActuelle == ecran.route,
                            onClick = {
                                navController.navigate(ecran.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Ecran.Collection.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Écran Collection
            composable(Ecran.Collection.route) {
                EcranCollection(
                    jeux = listeJeux,
                    onJeuClick = { jeu ->
                        navController.navigate(Ecran.Details.creerRoute(jeu.id))
                    },
                    onAjouterJeu = {
                        afficherDialogueAjout = true
                    }
                )
            }

            // Écran Stats
            composable(Ecran.Stats.route) {
                EcranStats(jeux = listeJeux)
            }

            // Écran Paramètres
            composable(Ecran.Parametres.route) {
                EcranParametres(
                    themeSombre = themeSombre,
                    onChangerTheme = onChangerTheme,
                    langueSelectionnee = langueSelectionnee,
                    onChangerLangue = { langueSelectionnee = it }
                )
            }

            // Écran Détails
            composable(
                route = Ecran.Details.route,
                arguments = listOf(
                    navArgument("jeuId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val jeuId = backStackEntry.arguments?.getString("jeuId")
                val jeu = listeJeux.find { it.id == jeuId }

                if (jeu != null) {
                    EcranDetails(
                        jeu = jeu,
                        onRetour = { navController.popBackStack() },
                        onModifier = { jeuAEditer ->
                            jeuAModifier = jeuAEditer
                        },
                        onSupprimer = { jeuASupprimer ->
                            listeJeux = listeJeux.filter { it.id != jeuASupprimer.id }
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}