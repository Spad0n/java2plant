package twiskIG.vues;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twiskIG.mondeIG.ArcIG;
import twiskIG.mondeIG.MondeIG;

public class VueArcIG extends Pane implements Observateur {

    public VueArcIG(MondeIG monde, ArcIG arc) {
        Line ligne = dessinerLigne(arc);
        Polyline fleche = dessinerTriangle(ligne, arc);
        this.getChildren().add(ligne);
        this.setOnMouseClicked(event -> {
            arc.setSelectionne();
            monde.notifierObservateur();
        });
        if (arc.getSelectionne()) {
            ligne.setFill(Color.RED);
            ligne.setStroke(Color.RED);
            ligne.setStrokeWidth(5);
        }
    }

    private Line dessinerLigne(ArcIG arc) {
        Line ligne = new Line();
        ligne.setStartX(arc.getDebut().getPosX());
        ligne.setStartY(arc.getDebut().getPosY());
        ligne.setEndX(arc.getFin().getPosX());
        ligne.setEndY(arc.getFin().getPosY());
        ligne.setStrokeWidth(3);
        return ligne;
    }

    private Polyline dessinerTriangle(Line ligne, ArcIG arc) {
        Polyline triangle = new Polyline();
        Point2D p1 = new Point2D(ligne.getEndX(), ligne.getEndY() - 10);
        Point2D p2 = new Point2D(ligne.getEndX(), ligne.getEndY() + 10);
        Point2D p3 = new Point2D(ligne.getEndX() + 10, ligne.getEndY());

        triangle.getPoints().addAll(
                p1.getX(), p1.getY(),
                p2.getX(), p2.getY(),
                p3.getX(), p3.getY()
        );

        return triangle;
    }
    @Override
    public void reagir() {

    }
}
