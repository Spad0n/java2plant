package twiskIG.mondeIG;

public class PointDeControleIG {
    private double x;
    private double y;
    private String id;
    private EtapeIG etape;
    private boolean estSelectionne;
    public PointDeControleIG(double x, double y, String id, EtapeIG etape) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.etape = etape;
        this.estSelectionne = false;
    }

    public String getID() {
        return this.id;
    }

    public double getPosX() {
        return x;
    }

    public double getPosY() {
        return y;
    }

    public boolean isSelected() {
        return estSelectionne;
    }

    public void setEstSelectionne() {
        estSelectionne = !estSelectionne;
    }

    public EtapeIG getEtape() {
        return this.etape;
    }

    public boolean memeEtapeQue(PointDeControleIG p2) {
        return this.etape.getID().equals(p2.getEtape().getID());
    }
}
