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


    public Konzole(MapaSveta mapaSveta) {
        this.mapaSveta = mapaSveta;
        this.prikazy = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.konecHry = false;
        this.inventar = new HashMap<>();
        this.historieLokaci = new Stack<>();
        inicializujPrikazy();
    }

    private void inicializujPrikazy() {
        prikazy.put("jdi", new Jdi(mapaSveta, historieLokaci));
        prikazy.put("prozkoumej", new Prozkoumej(mapaSveta));
        prikazy.put("konec", new Konec(this));
        prikazy.put("pouzij", new Pouzij(mapaSveta, inventar));
        prikazy.put("vezmi", new Vezmi(mapaSveta, inventar));
        prikazy.put("mluv", new Mluv(mapaSveta));
    }

    private void inicializujInventar() {
    }
    public void hraj() {
        mapaSveta.nactiMapu();
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        historieLokaci.push(mapaSveta.getAktualniLokaceId());
        System.out.println("Vítejte ve hře Stíny Zapomnění!");
        System.out.println("Začínáte v místnosti: " + aktualniLokace.getNazev());
        System.out.println(aktualniLokace.getPopis());
        zobrazPrikazy();
        zobrazInventar();
        zobrazCesty();
        while (!konecHry) {
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
            } else {
                System.out.println("Musíš zadat nějaký příkaz.");
            }
            zobrazCesty();
            zobrazInventar();
            zobrazPrikazy();
            zobrazPostavy();
        }
    }

    private void zobrazPostavy() {
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        for (Postava postava : aktualniLokace.getPostavy().values()) {
            System.out.println(postava.getNazev() + ": " + postava.getDialog());
        }

    }

    // Метод для выполнения команды
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
            System.out.println("Aktualní místnost: " + aktualniLokace.getNazev());
            System.out.println("Dostupné predmety:");
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
