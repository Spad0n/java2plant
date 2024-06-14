package twiskIG.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.TilePane;
import twiskIG.mondeIG.ActiviteIG;
import twiskIG.mondeIG.MondeIG;
import twiskIG.outils.TailleComposants;
import twiskIG.outils.FabriqueIdentifiant;

public class VueOutils extends TilePane implements Observateur {
    private MondeIG monde;

    public VueOutils(MondeIG monde) {
        super();
        this.monde = monde;
        this.monde.ajouterObservateur(this);

        Button AjouterActivite = new Button("+");
        this.getChildren().add(AjouterActivite);
        Tooltip tooltip = new Tooltip("Ajouter une Activite");
        Tooltip.install(AjouterActivite, tooltip);

        AjouterActivite.setOnAction(event -> {
            TailleComposants tComp = TailleComposants.getInstance();
            ActiviteIG activiteIG = new ActiviteIG("Activite", tComp.getLargeurBox(), tComp.getHauteurBox());
            this.monde.ajouter(activiteIG);
        });
    }

    public void reagir() {

    }
}
