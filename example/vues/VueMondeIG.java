package twiskIG.vues;

import javafx.scene.layout.Pane;
import twiskIG.mondeIG.ArcIG;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.mondeIG.PointDeControleIG;

import java.util.Iterator;

public class VueMondeIG extends Pane implements Observateur {
    private MondeIG monde;

    public VueMondeIG(MondeIG monde) {
        this.monde = monde;
        monde.ajouterObservateur(this);
    }

    public void reagir() {
        this.getChildren().clear();

        Iterator<ArcIG> iteratorArc = monde.iteratorArcIG();
        while (iteratorArc.hasNext()) {
            ArcIG arcIG = iteratorArc.next();
            if (arcIG.getAffiche()) {
                System.out.println("testarc");
                VueArcIG vueArcIG = new VueArcIG(monde, arcIG);
                this.getChildren().add(vueArcIG);
            }
        }

        for (EtapeIG etape : this.monde) {
            if (etape.getEstAffiche()) {
                VueActiviteIG vueActiviteIG = new VueActiviteIG(monde, etape);
                this.getChildren().add(vueActiviteIG);
                for (PointDeControleIG pdc : etape) {
                    VuePointDeControleIG vuePointDeControleIG = new VuePointDeControleIG(pdc, monde);
                    this.getChildren().add(vuePointDeControleIG);
                }
            }
        }
    }
}
