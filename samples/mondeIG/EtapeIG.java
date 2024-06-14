package twiskIG.mondeIG;

import twiskIG.outils.FabriqueIdentifiant;
import twiskIG.outils.TailleComposants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    private String nom;
    private String identifiant;
    private int posX;
    private int posY;
    private int largeur;
    private int hauteur;
    private boolean selectionnee;
    private boolean affiche;
    private ArrayList<PointDeControleIG> pdcIG;

    public EtapeIG(String nom, int larg, int haut) {
        TailleComposants composants = TailleComposants.getInstance();
        FabriqueIdentifiant fabId = FabriqueIdentifiant.getInstance();
        Random rand = new Random();
        this.posX = rand.nextInt(composants.largeurFenetre() - composants.getLargeurBox());
        this.posY = rand.nextInt(composants.hauteurFenetre() - composants.getHauteurBox());
        this.nom = nom;
        this.largeur = larg;
        this.hauteur = haut;
        this.selectionnee = false;
        this.affiche = true;
        this.identifiant = fabId.getIdentifiantEtape();

        this.pdcIG = new ArrayList<>();
        pdcIG.add(new PointDeControleIG(posX, posY + (hauteur/2), "PC1", this));
        pdcIG.add(new PointDeControleIG(posX + (largeur/2), posY, "PC2", this));
        pdcIG.add(new PointDeControleIG(posX + (largeur/2), posY + hauteur, "PC3", this));
        pdcIG.add(new PointDeControleIG(posX + largeur, posY + (hauteur/2), "PC4", this));
    }

    public String getID() {
        return this.identifiant;
    }

    public String getNom() {
        return this.nom;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getHauteur() { return this.hauteur; }
    public int getLargeur() { return this.largeur; }

    public boolean estSelectionnee() {
        return selectionnee;
    }

    public void setEstAffiche(boolean b) {
        this.affiche = b;
    }

    @Override
    public Iterator<PointDeControleIG> iterator() {
        return pdcIG.iterator();
    }

    public void setSelectionne() {
        this.selectionnee = !this.selectionnee;
    }

    public void renommer(String nom) {
        this.nom = nom;
    }

    public boolean getEstAffiche() {
        return this.affiche;
    }
}
