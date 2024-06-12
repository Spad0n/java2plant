package twiskIG.vues;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import twiskIG.mondeIG.MondeIG;

public class VueMenu extends MenuBar implements Observateur {
    private MondeIG monde;
    public VueMenu(MondeIG monde) {
        super();
        this.monde = monde;

        // Menu fichier
        Menu fichier = new Menu("Fichier");
        MenuItem quitter = new MenuItem("Quitter");
        fichier.getItems().addAll(quitter);

        quitter.setOnAction(event -> Platform.exit());

        // Menu edition
        Menu edition = new Menu("_Edition");
        MenuItem renommer = new MenuItem("Renommer la sélection");
        MenuItem supprimer = new MenuItem("Supprimer");

        renommer.setOnAction(event -> {
            TextInputDialog textDialog = new TextInputDialog();
            textDialog.setHeaderText("Renommer");
            textDialog.showAndWait();
            String nom = textDialog.getEditor().getText();
            monde.renommerEtape(nom);
        });

        supprimer.setOnAction(event -> {
            monde.supprimerLaSelection();
        });
        edition.getItems().addAll(renommer, supprimer);

        this.getMenus().addAll(fichier, edition);
        monde.ajouterObservateur(this);
    }

    @Override
    public void reagir() {

    }
}
