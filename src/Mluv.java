import java.util.HashMap;
/**
 * Třída Mluv implementuje příkaz "mluv", který umožňuje hráči mluvit s postavami
 * ve stejné lokaci. Pokud hráč zadá jméno existující postavy, zobrazí se její dialog.
 */
class Mluv implements Prikaz {
    private MapaSveta mapaSveta;

    public Mluv(MapaSveta mapaSveta) {
        this.mapaSveta = mapaSveta;
    }

    @Override
    public String getNazev() {
        return "mluv";
    }

    @Override
    public String getPopis() {
        return "Mluví s postavou v lokaci. Použití: mluv <nazev_postavy>";
    }

    @Override
    public void proved(String[] parametry) {
        if (parametry.length != 1) {
            System.out.println("Chybné použití příkazu mluv. Správné použití: mluv <nazev_postavy>");
            return;
        }
        String nazevPostavy = parametry[0];
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        HashMap<String, Postava> postavyVLokaci = aktualniLokace.getPostavy();

        if (postavyVLokaci != null && postavyVLokaci.containsKey(nazevPostavy)) {
            Postava postava = postavyVLokaci.get(nazevPostavy);
            System.out.println(postava.getDialog());
        } else {
            System.out.println("V této lokaci není žádná postava s názvem " + nazevPostavy + ".");
        }
    }
}
