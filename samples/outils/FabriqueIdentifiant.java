package twiskIG.outils;

public class FabriqueIdentifiant {
    private int noEtape = 0;
    private static FabriqueIdentifiant instance;

    //private FabriqueIdentifiant() {}

    public static FabriqueIdentifiant getInstance() {
        if (instance == null) {
            instance = new FabriqueIdentifiant();
        }
        return instance;
    }

    public String getIdentifiantEtape() {
        return "ID-" + (++noEtape);
    }

}
