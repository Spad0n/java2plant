package twiskIG.vues;

import javafx.scene.layout.HBox;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;

public class VueActiviteIG extends VueEtapeIG implements Observateur {
    private HBox clients;

    public VueActiviteIG(MondeIG monde, EtapeIG etape) {
        super(monde, etape);
        this.clients = new HBox();
        this.getChildren().add(clients);
        this.clients.setStyle("-fx-border-color: #0059FF;" +
                "-fx-background-insets:0 0 -1 0, 0, 1, 2;" +
                "-fx-background-radius: 3px, 3px, 2px, 1px;" +
                "-fx-background-color: grey;");
        this.clients.setPrefSize(etape.getHauteur(), etape.getLargeur());
        //this.relocate(this.etape.getPosX(), etape.getPosY());
    }

    @Override
    public void reagir() {

    }
}
