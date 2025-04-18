import java.util.HashMap;

/**
 * Třída "Vezmi" implementuje příkaz "vezmi", který umožňuje hráči sebrat předmět z lokacet.
 * Zkontroluje, zda sebraný předmět existuje v lokaci. Pokud ne, vypísa se zpráva s nápovědou, jak příkaz správně použít.
 * sebere předmět z lokace a vloží ho do inventáře.
 */
class Vezmi implements Prikaz {
    private MapaSveta mapaSveta;
    private HashMap<String, Predmet> inventar;

    public Vezmi(MapaSveta mapaSveta, HashMap<String, Predmet> inventar) {
        this.mapaSveta = mapaSveta;
        this.inventar = inventar;
    }

    @Override
    public String getNazev() {
        return "vezmi";
    }

    @Override
    public String getPopis() {
        return "Vezme předmět a uloží ho do inventáře. Použití: vezmi <nazev_predmetu>";
    }

    @Override
    public void proved(String[] parametry) {
        if (parametry.length != 1) {
            System.out.println("Chybné použití příkazu vezmi. Správné použití: vezmi <nazev_predmetu>");
            return;
        }
        String nazevPredmetu = parametry[0];
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        HashMap<String, Predmet> predmetyVLokaci = aktualniLokace.getPredmety();

        if (predmetyVLokaci != null && predmetyVLokaci.containsKey(nazevPredmetu)) {
            Predmet predmet = aktualniLokace.odeberPredmet(nazevPredmetu);
            inventar.put(nazevPredmetu, predmet);
            System.out.println("Vzal jsi předmět " + nazevPredmetu + " a vložil jsi ho do inventáře.");
        } else {
            System.out.println("Předmět " + nazevPredmetu + " v této lokaci není.");
        }
    }
}
