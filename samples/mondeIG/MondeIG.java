package twiskIG.mondeIG;

import twiskIG.exceptions.ExceptionArcIG;
import twiskIG.outils.TailleComposants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG> {

    private HashMap<String, EtapeIG> etapes;
    private ArrayList<ArcIG> arcs;
    private ArrayList<PointDeControleIG> pdcs;

    public MondeIG() {
        super();
        this.etapes = new HashMap<>();
        this.arcs = new ArrayList<>();
        this.pdcs = new ArrayList<>();
        TailleComposants tComp = TailleComposants.getInstance();
        ActiviteIG activite = new ActiviteIG("Activite", tComp.getLargeurBox(), tComp.getHauteurBox());
        this.ajouter(activite);
    }

    public void ajouter(EtapeIG etape) {
        etapes.put(etape.getID(), etape);
        this.notifierObservateur();
    }

    public void ajouter(PointDeControleIG pt1, PointDeControleIG pt2) throws ExceptionArcIG {
        verifierArc(pt1, pt2);
        ArcIG arc = new ArcIG(pt1, pt2);
        arcs.add(arc);
        this.notifierObservateur();
    }

    private void verifierArc(PointDeControleIG p1, PointDeControleIG p2) throws ExceptionArcIG {
        ArcIG arc = new ArcIG(p1, p2);
        if(p1.memeEtapeQue(p2)){
            p1.setEstSelectionne();
            p2.setEstSelectionne();
            throw new ExceptionArcIG("Impossible de creer un arc sur la meme etape");
        }

        for (ArcIG arcIG : arcs) {
            if (arcIG.aCommeDebut(arc.getEtapeDebut()) && arcIG.aCommeFin(arc.getEtapeFin())) {
                p1.setEstSelectionne();
                p2.setEstSelectionne();
                throw new ExceptionArcIG("Impossible de créer cet arc, il existe déjà un arc qui relie ces deux étapes");
            }
            if (arcIG.aCommeDebut(arc.getEtapeFin()) && arcIG.aCommeFin(arc.getEtapeDebut())) {
                p1.setEstSelectionne();
                p2.setEstSelectionne();
                throw new ExceptionArcIG("Impossible de créer cet arc, deux arcs sont symétriques");
            }

        }
    }

    public void supprimerLaSelection() {
        for(EtapeIG e : this) {
            if (e.estSelectionnee()) {
                e.setEstAffiche(false);
                Iterator<ArcIG> iterateur = this.iteratorArcIG();
                for(PointDeControleIG p : e) {
                    while(iterateur.hasNext()) {
                        ArcIG arc = iterateur.next();
                        String id = p.getID();
                        if (arc.getDebut().getID().equals(id) || arc.getFin().getID().equals(id)) {
                            arc.setAffiche(false);
                            arc.setEstSelectionne();
                            arc.getDebut().setEstSelectionne();
                            arc.getFin().setEstSelectionne();
                            iterateur.remove();
                        }
                    }
                }

                etapes.remove(e);
            }
        }
        Iterator<ArcIG> itArc = this.iteratorArcIG();
        while(itArc.hasNext()) {
            ArcIG arc = itArc.next();
            if (arc.getSelectionne()) {
                arc.setAffiche(false);
                arc.setEstSelectionne();
                itArc.remove();
                for(PointDeControleIG p : arc.getEtapeDebut()) {
                    if (p.isSelected()) {
                        p.setEstSelectionne();
                    }
                }
                for(PointDeControleIG p : arc.getEtapeFin()) {
                    if (p.isSelected()) {
                        p.setEstSelectionne();
                    }
                }
            }
        }
        this.notifierObservateur();
    }

    public Iterator<ArcIG> iteratorArcIG() {
        return arcs.iterator();
    }

    public Iterator<EtapeIG> iterator() {
        return etapes.values().iterator();
    }

    public void renommerEtape(String nom) {
        for(EtapeIG e : this) {
            if (e.estSelectionnee()) {
                e.renommer(nom);
                e.setSelectionne();
            }
        }
        this.notifierObservateur();
    }

    public void pointDeControleSelectionne(PointDeControleIG pdc) throws ExceptionArcIG {
        pdcs.add(pdc);
        if(pdcs.size() == 2) {
            PointDeControleIG p1 = pdcs.get(0);
            PointDeControleIG p2 = pdcs.get(1);
            ajouter(p1, p2);
            pdcs.clear();
        }
        this.notifierObservateur();
    }

    public void viderPointDeControle() {
        pdcs.clear();
    }
}
