import java.util.HashMap;

/**
 * Třída Odpovez implementuje příkaz "odpovez", který umožňuje hráči 
 * odpovídat na hádanky a kvízy, které jsou prezentovány postavami v herním světě.
 */
class Odpovez implements Prikaz {
    private MapaSveta mapaSveta;
    private HashMap<String, Predmet> inventar;
    private HashMap<String, Quiz> kvizy;

    public Odpovez(MapaSveta mapaSveta, HashMap<String, Predmet> inventar, HashMap<String, Quiz> kvizy) {
        this.mapaSveta = mapaSveta;
        this.inventar = inventar;
        this.kvizy = kvizy;
    }

    @Override
    public String getNazev() {
        return "odpovez";
    }

    @Override
    public String getPopis() {
        return "Odpovídá na hádanku nebo kvíz. Použití: odpovez <postava> <odpoved>";
    }

    @Override
    public void proved(String[] parametry) {
        if (parametry.length < 2) {
            System.out.println("Chybné použití příkazu odpovez. Správné použití: odpovez <postava> <odpoved>");
            return;
        }
        
        String nazevPostavy = parametry[0];
        
        StringBuilder odpoved = new StringBuilder();
        for (int i = 1; i < parametry.length; i++) {
            odpoved.append(parametry[i]).append(" ");
        }
        String odpovedText = odpoved.toString().trim();
        
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        HashMap<String, Postava> postavyVLokaci = aktualniLokace.getPostavy();
        
        if (postavyVLokaci == null || !postavyVLokaci.containsKey(nazevPostavy)) {
            System.out.println("V této lokaci není žádná postava s názvem " + nazevPostavy + ".");
            return;
        }
        
        Quiz kviz = kvizy.get(nazevPostavy);
        if (kviz == null) {
            System.out.println(nazevPostavy + " nemá pro tebe žádnou hádanku.");
            return;
        }
        
        if (kviz.isVyreseno()) {
            System.out.println("Už jsi tuto hádanku vyřešil.");
            return;
        }
        
        boolean jeSpravne = kviz.zkontrolujOdpoved(odpovedText);
        if (jeSpravne) {
            System.out.println("Správná odpověď! " + nazevPostavy + " ti dává " + kviz.getOdmena() + ".");
            String odmena = kviz.getOdmena();
            if (odmena.equals("opravený most")) {
                Lokace most = mapaSveta.getLokace(9);
                if (most != null && !most.getMozneCesty().contains(10)) {
                    most.pridejCestu(10);
                    System.out.println("Most byl opraven! Nyní můžeš pokračovat dál do Podzemní svatyně.");
                }
            } else {
                Predmet predmet = new Predmet(odmena, "Získáno jako odměna za vyřešení hádanky od " + nazevPostavy);
                inventar.put(odmena, predmet);
            }
        } else {
            System.out.println("To není správná odpověď. Zkus to znovu.");
        }
    }
}