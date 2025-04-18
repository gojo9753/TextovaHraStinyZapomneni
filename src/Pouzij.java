import java.util.HashMap;
/**
 * Třída Pouzij implementuje příkaz "pouzij", který umožňuje hráči použít předmět
 * z inventáře. Chování závisí na konkrétním předmětu (např. klíč, lektvar).
 */
class Pouzij implements Prikaz {
    private final MapaSveta mapaSveta;
    private final HashMap<String, Predmet> inventar;

    public Pouzij(MapaSveta mapaSveta, HashMap<String, Predmet> inventar) {
        this.mapaSveta = mapaSveta;
        this.inventar = inventar;
    }

    @Override
    public String getNazev() {
        return "pouzij";
    }

    @Override
    public String getPopis() {
        return "Použije předmět z inventáře. Použití: pouzij <nazev_predmetu>";
    }

    @Override
    public void proved(String[] parametry) {
        if (parametry.length != 1) {
            System.out.println("Chybné použití příkazu pouzij. Správné použití: pouzij <nazev_predmetu>");
            return;
        }

        String nazevPredmetu = parametry[0];
        if (inventar.containsKey(nazevPredmetu)) {
            Predmet predmet = inventar.get(nazevPredmetu);
            System.out.println("Používáš předmět: " + predmet.getNazev());
            if (nazevPredmetu.equals("Klíč")) {
                System.out.println("Otvíráš dveře do tajné místnosti! Zkontroluj si, kam mužes jit.");
                mapaSveta.getAktualniLokace().getMozneCesty().add(4);
            } else if (nazevPredmetu.equals("Elixír vědění")) {
                System.out.println("Používáš elixír vědění! Artefakt je v místnosti Strážní věž!");
            } else {
                System.out.println("S tímto předmětem se nedá nic dělat.");
            }
        } else {
            System.out.println("Předmět " + nazevPredmetu + " nemáš v inventáři.");
        }
    }
}
