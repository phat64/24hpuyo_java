Le projet a �t� r�alis� en tres peu de temps et date de plus de 10 ans.

Au niveau du jeu, le projet contient :
- des animations d'intro, de fin, d'update de score, de passage vers des puyos suivants
- des effets de clignotements r�tro (en pointill� jaune)
- un effet d'explosion de puyos
- des difficult�s (ajout de N ligne(s) de puyos ou quelques puyos)
- de la physique (chute des puyos, deplacement, vitesse/acc�leration)
- la notion du temps (24h = 24 minutes)


Au niveau du code, on retrouve :
- une asbtraction des ressources (PuyoRessources)
- un d�coupage en plusieurs classes (Puyo est un �lement graphique, PuyoGfx contient les FX, PuyoAction sont les actions possibles, PuyoGAmeControls g�re les inputs avec une notion de degr�e pression comme sur console, PuyoFont g�re la font avec un style 24h chrono, ...)

Au niveau du sc�nario :
- Jack Bauer doit d�jouer l'attaque des puyos mais la technologie actuelle ne permet pas de les stopper.
- Jack est oblig� de retourner dans le pass� et d'utilis� une vielle machine des ann�es 70 (� J-1 de la date Epoch) pour avoir une chance de sauver le monde
- Il n'a que 24 heures... Le compte � rebours a d�j� commenc�! 

ps : le jeu fonctionnait sans probleme sur mon Pentium III 500Mhz mais possede des problemes sur le chargement des ressources au debut de la partie. ca arrive qu'une des images des puyos n'affiche. Il faudra investigure ca

Mustapha Tachouct 