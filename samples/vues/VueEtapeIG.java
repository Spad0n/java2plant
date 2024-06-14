package twiskIG.vues;

import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import twiskIG.mondeIG.EtapeIG;
import twiskIG.mondeIG.MondeIG;

public abstract class VueEtapeIG extends VBox implements Observateur {
    protected MondeIG monde;
    protected Label labelTitre;
    protected EtapeIG etape;

    public VueEtapeIG(MondeIG monde, EtapeIG etape) {
        this.labelTitre = new Label(etape.getNom());
        this.etape = etape;
        this.monde = monde;

        this.setPrefWidth(etape.getLargeur());
        this.setPrefHeight(etape.getHauteur());

        if (this.etape.estSelectionnee()) {
            this.setStyle("-fx-border-color: Blue;" +
                    "-fx-background-insets: 0 0 -1 0, 0, 1, 2;" +
                    "-fx-border-radius: 3px;" +
                    "-fx-border-width: 3px;"
            );
        } else {
            this.setStyle("-fx-border-color: Black;" +
                    "-fx-background-insets: 0 0 -1 0, 0, 1, 2;" +
                    "-fx-border-radius: 3px;" +
                    "-fx-border-width: 2px;"
            );
        }

        this.setOnMouseClicked(event -> {
            this.etape.setSelectionne();
            this.monde.notifierObservateur();
        });

        this.setOnDragDetected(event -> {
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(this.etape.getID());
            db.setContent(content);

            event.consume();
        });

        this.getChildren().add(labelTitre);
        this.relocate(this.etape.getPosX(), etape.getPosY());
    }
}
