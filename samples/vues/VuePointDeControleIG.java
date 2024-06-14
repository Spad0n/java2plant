package twiskIG.vues;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twiskIG.exceptions.TwiskException;
import twiskIG.mondeIG.MondeIG;
import twiskIG.mondeIG.PointDeControleIG;

public class VuePointDeControleIG extends Circle implements Observateur {
    private MondeIG monde;
    private PointDeControleIG pdc;
    public VuePointDeControleIG(PointDeControleIG pdc, MondeIG monde) {
        super(pdc.getPosX(), pdc.getPosY(), 5);
        this.pdc = pdc;
        this.monde = monde;

        this.setOnMouseClicked(event -> {
            try {
                monde.pointDeControleSelectionne(pdc);
                pdc.setEstSelectionne();
                monde.notifierObservateur();
            } catch (TwiskException e) {
                pdc.setEstSelectionne();
                monde.viderPointDeControle();
                monde.notifierObservateur();
            }
        });

        if (pdc.isSelected()) {
            this.setFill(Color.RED);
        } else {
            this.setFill(Color.PINK);
        }
    }

    public void reagir() {

    }
}
