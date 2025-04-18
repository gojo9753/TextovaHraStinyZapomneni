import java.util.ArrayList;
import java.util.HashMap;
/**
 * Třída Lokace reprezentuje jedno místo (místnost, lokaci) v herním světě.
 * Obsahuje informace o názvu, popisu, přístupných sousedních lokacích,
 * předmětech a postavách, které se v lokaci nacházejí.
 */
class Lokace {
    private String nazev;
    private String popis;
    private ArrayList<Integer> mozneCesty;
    private HashMap<String, Predmet> predmety;
    private HashMap<String, Postava> postavy;

    public Lokace(String nazev, String popis, ArrayList<Integer> mozneCesty, HashMap<String, Predmet> predmety, HashMap<String, Postava> postavy) {
        this.nazev = nazev;
        this.popis = popis;
        this.mozneCesty = mozneCesty;
        this.predmety = predmety;
        this.postavy = postavy;
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    public ArrayList<Integer> getMozneCesty() {
        return mozneCesty;
    }

    public HashMap<String, Predmet> getPredmety() {
        return predmety;
    }

    public Predmet odeberPredmet(String nazevPredmetu) {
        return predmety.remove(nazevPredmetu);
    }

    public HashMap<String, Postava> getPostavy() {
        return postavy;
    }
    
    public void pridejCestu(int idCile) {
        if (!mozneCesty.contains(idCile)) {
            mozneCesty.add(idCile);
        }
    }
}
