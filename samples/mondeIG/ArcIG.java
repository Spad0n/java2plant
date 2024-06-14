package twiskIG.mondeIG;

public class ArcIG {
    private PointDeControleIG pdc1;
    private PointDeControleIG pdc2;
    private boolean affiche;
    private boolean selectionne;

    public ArcIG(PointDeControleIG pdc1, PointDeControleIG pdc2) {
        //estAffiche = true;
        this.pdc1 = pdc1;
        this.pdc2 = pdc1;
        this.selectionne = false;
    }

    public PointDeControleIG getDebut() {
        return pdc1;
    }

    public PointDeControleIG getFin() {
        return pdc2;
    }

    public EtapeIG getEtapeDebut() {
        return pdc1.getEtape();
    }

    public EtapeIG getEtapeFin() {
        return pdc2.getEtape();
    }

    public void setSelectionne() {
        this.selectionne = !this.selectionne;
    }

    public boolean getSelectionne() {
        return this.selectionne;
    }

    public boolean getAffiche() {
        return affiche;
    }

    public void setAffiche(boolean b) {
        affiche = b;
    }

    public void setEstSelectionne() {
        selectionne = !selectionne;
    }

    public boolean aCommeDebut(EtapeIG etapeFin) {
        String id = etapeFin.getID();
        return getEtapeDebut().getID().equals(id);
    }

    public boolean aCommeFin(EtapeIG etapeDebut) {
        String id = etapeDebut.getID();
        return getEtapeDebut().getID().equals(id);
    }
}
