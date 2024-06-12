package twiskIG.outils;

public class TailleComposants {
    private static final TailleComposants INSTANCE = new TailleComposants();

    private double tailleTitre;
    private double tailleBouton;
    private double tailleHBox;
    private int largeurEcran;
    private int hauteurEcran;
    private int largeurBox;
    private int hauteurBox;

    private TailleComposants() {
        tailleTitre = 30;
        tailleBouton = 20;
        tailleHBox = 50;
        largeurEcran = 600;
        hauteurEcran = 550;
        largeurBox = 150;
        hauteurBox = 137;
    }

    public static TailleComposants getInstance() {
        return INSTANCE;
    }
    public double getTailleTitre() {
        return tailleTitre;
    }

    public int largeurFenetre() {
        return largeurEcran;
    }

    public int hauteurFenetre() {
        return hauteurEcran;
    }

    public void setTailleTitre(double tailleTitre) {
        this.tailleTitre = tailleTitre;
    }

    public double getTailleBouton() {
        return tailleBouton;
    }

    public void setTailleBouton(double tailleBouton) {
        this.tailleBouton = tailleBouton;
    }

    public double getTailleHBox() {
        return tailleHBox;
    }

    public int getLargeurBox() { return largeurBox; }

    public int getHauteurBox() { return hauteurBox; }

    public void setTailleHBox(double tailleHBox) {
        this.tailleHBox = tailleHBox;
    }
}
