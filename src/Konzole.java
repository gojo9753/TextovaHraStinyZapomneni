import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Třída Konzole zajišťuje hlavní herní smyčku textové hry "Stíny Zapomnění".
 * Komunikuje s hráčem, zpracovává příkazy a zobrazuje výstupy.
 */
class Konzole {
    private MapaSveta mapaSveta;
    private HashMap<String, Prikaz> prikazy;
    private Scanner scanner;
    boolean konecHry;
    private HashMap<String, Predmet> inventar;
    private Stack<Integer> historieLokaci;
    private HashMap<String, Quiz> kvizy;


    public Konzole(MapaSveta mapaSveta) {
        this.mapaSveta = mapaSveta;
        this.prikazy = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.konecHry = false;
        this.inventar = new HashMap<>();
        this.historieLokaci = new Stack<>();
        this.kvizy = new HashMap<>();
        inicializujPrikazy();
        inicializujKvizy();
    }

    private void inicializujPrikazy() {
        prikazy.put("jdi", new Jdi(mapaSveta, historieLokaci));
        prikazy.put("prozkoumej", new Prozkoumej(mapaSveta));
        prikazy.put("konec", new Konec(this));
        prikazy.put("pouzij", new Pouzij(mapaSveta, inventar));
        prikazy.put("vezmi", new Vezmi(mapaSveta, inventar));
        prikazy.put("mluv", new Mluv(mapaSveta));
        prikazy.put("odpovez", new Odpovez(mapaSveta, inventar, kvizy));
    }

    private void inicializujKvizy() {
        // Hádanka od Bloudícího dítěte
        kvizy.put("BloudícíDítě", new Quiz(
            "Nejsem živý, ale rostu; nemám plíce, ale potřebuji vzduch; nemám ústa, ale voda mě zabije. Co jsem?",
            "oheň",
            "BloudícíDítě",
            "Amulet světla"
        ));
        
        // Kvíz od Kováře Ulricha
        kvizy.put("KovářUlrich", new Quiz(
            "Jsem most zlomený, pomoz mi opravit srdce mého i mostu. Najdi co spojuje minulost a budoucnost, není to železo, ale je to silnější než ocel.",
            "vzpomínky",
            "KovářUlrich",
            "opravený most"
        ));
        
        // Kvíz od Mistra Harlana
        kvizy.put("MistrHarlan", new Quiz(
            "Abych ti dal elixír pravdy, musíš nejprve dokázat svou moudrost. Co je to, co chodí po čtyřech ráno, po dvou v poledne a po třech večer?",
            "člověk",
            "MistrHarlan",
            "Elixír vědění"
        ));
        
        // Hádanka od Ducha knihovníka
        kvizy.put("DuchKnihovníka", new Quiz(
            "V prachu času leží odpověď. Najdi první písmeno nejstaršího svitku, poslední písmeno zapomnění a prostřední písmeno tajemství. Jaké slovo vytvoří?",
            "stp",
            "DuchKnihovníka",
            "Klíč"
        ));
    }

    public void hraj() {
        mapaSveta.nactiMapu();
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        historieLokaci.push(mapaSveta.getAktualniLokaceId());
        System.out.println("\n==========================================================");
        System.out.println("             STÍNY ZAPOMNĚNÍ - TEXTOVÁ HRA                ");
        System.out.println("==========================================================\n");
        System.out.println("Vítejte ve hře Stíny Zapomnění!");
        System.out.println("Začínáte v místnosti: " + aktualniLokace.getNazev());
        System.out.println(aktualniLokace.getPopis());
        zobrazPrikazy();
        zobrazInventar();
        zobrazCesty();
        while (!konecHry) {
            System.out.println("\n----------------------------------------------------------");
            System.out.print("> ");
            String vstup = scanner.nextLine();
            String[] slova = vstup.split(" ");
            if (slova.length > 0) {
                String nazevPrikazu = slova[0].toLowerCase();
                String[] parametry = new String[slova.length - 1];
                for (int i = 1; i < slova.length; i++) {
                    parametry[i - 1] = slova[i];
                }
                provedPrikaz(nazevPrikazu, parametry);
                System.out.println("\n==========================================================");
            } else {
                System.out.println("Musíš zadat nějaký příkaz.");
                System.out.println("\n==========================================================");
            }
            if (konecHry) {
                break;
            }
            zobrazCesty();
            zobrazInventar();
            zobrazPrikazy();
            zobrazPostavy();
            zobrazHadanky();
        }
    }

    private void zobrazHadanky() {
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        for (Postava postava : aktualniLokace.getPostavy().values()) {
            String nazevPostavy = postava.getNazev();
            Quiz kviz = kvizy.get(nazevPostavy);
            if (kviz != null && !kviz.isVyreseno()) {
                System.out.println("Hádanka od " + nazevPostavy + ": " + kviz.getOtazka());
                System.out.println("(Použij příkaz: odpovez " + nazevPostavy + " <tvá odpověď>)");
            }
        }
    }

    private void zobrazPostavy() {
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        for (Postava postava : aktualniLokace.getPostavy().values()) {
            System.out.println(postava.getNazev() + ": " + postava.getDialog());
        }

    }

    private void provedPrikaz(String nazevPrikazu, String[] parametry) {
        Prikaz prikaz = prikazy.get(nazevPrikazu);
        if (prikaz != null) {
            prikaz.proved(parametry);
        } else {
            System.out.println("Neznámý příkaz: " + nazevPrikazu);
        }
    }
    private void zobrazPrikazy() {
        System.out.println("Dostupné příkazy:");
        for (Prikaz prikaz : prikazy.values()) {
            System.out.println(prikaz.getNazev() + " - " + prikaz.getPopis());
        }
    }

    private void zobrazInventar() {
        if (inventar.isEmpty()) {
            System.out.println("Tvůj inventář je prázdný.");
        } else {
            System.out.println("Tvůj inventář:");
            for (Predmet predmet : inventar.values()) {
                System.out.println("- " + predmet.getNazev() + ": " + predmet.getPopis());
            }
        }
    }
    private void zobrazCesty() {
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        if (aktualniLokace != null) {
            System.out.println("Aktuální místnost: " + aktualniLokace.getNazev());
            System.out.println("Dostupné předměty:");
            for (Predmet predmet : aktualniLokace.getPredmety().values()) {
                System.out.println("- " + predmet.getNazev() + ": " + predmet.getPopis());
            }
            System.out.println("Dostupná místa:");
            for (int cislo : aktualniLokace.getMozneCesty()) {
                Lokace lokace = mapaSveta.getMapa().get(cislo);
                 System.out.println("- " + lokace.getNazev() + ": " + lokace.getPopis());
            }
        }
    }
    public int getAktualniLokaceId() {
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        if (mapaSveta != null && mapaSveta.getMapa() != null) {
            for (HashMap.Entry<Integer, Lokace> entry : mapaSveta.getMapa().entrySet()) {
                if (entry.getValue().equals(aktualniLokace)) {
                    return entry.getKey();
                }
            }
        }
        return -1;
    }
}
