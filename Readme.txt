Le projet a été réalisé en tres peu de temps et date de plus de 10 ans.

Au niveau du jeu, le projet contient :
- des animations d'intro, de fin, d'update de score, de passage vers des puyos suivants
- des effets de clignotements rétro (en pointillé jaune)
- un effet d'explosion de puyos
- des difficultés (ajout de N ligne(s) de puyos ou quelques puyos)
- de la physique (chute des puyos, deplacement, vitesse/accéleration)
- la notion du temps (24h = 24 minutes)


Au niveau du code, on retrouve :
- une asbtraction des ressources (PuyoRessources)
- un découpage en plusieurs classes (Puyo est un élement graphique, PuyoGfx contient les FX, PuyoAction sont les actions possibles, PuyoGAmeControls gére les inputs avec une notion de degrée pression comme sur console, PuyoFont gére la font avec un style 24h chrono, ...)

Au niveau du scénario :
- Jack Bauer doit déjouer l'attaque des puyos mais la technologie actuelle ne permet pas de les stopper.
- Jack est obligé de retourner dans le passé et d'utilisé une vielle machine des années 70 (à J-1 de la date Epoch) pour avoir une chance de sauver le monde
- Il n'a que 24 heures... Le compte à rebours a déjà commencé! 

ps : le jeu fonctionnait sans probleme sur mon Pentium III 500Mhz mais possede des problemes sur le chargement des ressources au debut de la partie. ca arrive qu'une des images des puyos n'affiche. Il faudra investigure ca

Mustapha Tachouct 