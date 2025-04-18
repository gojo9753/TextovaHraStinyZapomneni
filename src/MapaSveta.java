import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**
 * Třída MapaSveta spravuje herní svět – tedy všechny lokace, jejich propojení a
 * aktuální pozici hráče. Načítá také data ze souborů jako jsou lokace, postavy a předměty.
 */
class MapaSveta {
    private HashMap<Integer, Lokace> mapa;
    private Lokace aktualniLokace;

    public MapaSveta() {
        this.mapa = new HashMap<>();
        this.aktualniLokace = null;
    }

    public void addLokace(Integer id, Lokace lokace) {
        this.mapa.put(id, lokace);
    }
    public int najdiLokaci(String nazev) {
        nazev = nazev.toLowerCase();
        for (HashMap.Entry<Integer, Lokace> entry : this.mapa.entrySet()) {
            if (entry.getValue().getNazev().toLowerCase().equals(nazev)) {
                return entry.getKey();
            }
        }
        return -1;
    }
    public Lokace getLokace(Integer id) {
        return this.mapa.get(id);
    }

    public HashMap<Integer, Lokace> getMapa() {
        return mapa;
    }

    public void setAktualniLokace(Lokace lokace) {
        this.aktualniLokace = lokace;
    }

    public Lokace getAktualniLokace() {
        return aktualniLokace;
    }

    public int getAktualniLokaceId() {
        if (mapa != null) {
            for (HashMap.Entry<Integer, Lokace> entry : mapa.entrySet()) {
                if (entry.getValue().equals(aktualniLokace)) {
                    return entry.getKey();
                }
            }
        }
        return -1;
    }

    public void nactiMapu() {
        try {
            File soubor = new File("lokace.txt");
            FileReader cteckaSouboru = new FileReader(soubor);
            BufferedReader ctecka = new BufferedReader(cteckaSouboru);
            String radek;
            while ((radek = ctecka.readLine()) != null) {
                String[] udaje = radek.split(";");
                if (udaje.length >= 3) {
                    int id = Integer.parseInt(udaje[0].trim());
                    String nazev = udaje[1].trim();
                    String popis = udaje[2].trim();
                    ArrayList<Integer> mozneCesty = new ArrayList<>();
                    if (udaje.length > 3) {
                        String[] cesty = udaje[3].split(",");
                        for (String cesta : cesty) {
                            mozneCesty.add(Integer.parseInt(cesta.trim()));
                        }
                    }
                    HashMap<String, Predmet> predmety = nactiPredmety(id);
                    HashMap<String, Postava> postavy = nactiPostavy(id);
                    Lokace lokace = new Lokace(nazev, popis, mozneCesty, predmety, postavy);
                    mapa.put(id, lokace);
                } else {
                    System.out.println("Chybný řádek v souboru lokace.txt: " + radek);
                }
            }
            ctecka.close();
        } catch (FileNotFoundException e) {
            System.out.println("Soubor lokace.txt nebyl nalezen.");
            mapa = new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (mapa.containsKey(1)) {
            aktualniLokace = mapa.get(1);
        } else if (!mapa.isEmpty()) {
            aktualniLokace = mapa.values().iterator().next();
            System.out.println("Chyba: Lokace s ID 1 nebyla nalezena. Začínáte v jiné lokaci.");
        } else {
            System.out.println("Chyba: Mapa lokací je prázdná. Hra nemůže pokračovat.");
            System.exit(1);
        }
    }

    private HashMap<String, Postava> nactiPostavy(int idLokace) {
        HashMap<String, Postava> postavy = new HashMap<>();
        try {
            File soubor = new File("postavy.txt");
            Scanner ctecka = new Scanner(soubor);
            while (ctecka.hasNextLine()) {
                String radek = ctecka.nextLine();
                String[] udaje = radek.split(";");
                if (udaje.length == 3) {
                    int idLokacePostavy = Integer.parseInt(udaje[0].trim());
                    String nazev = udaje[1].trim();
                    String dialog = udaje[2].trim();
                    if (idLokacePostavy == idLokace) {
                        Postava postava = new Postava(nazev, dialog);
                        postavy.put(nazev, postava);
                    }
                } else {
                    System.out.println("Chybný řádek v souboru postavy.txt: " + radek);
                }
            }
            ctecka.close();
        } catch (FileNotFoundException e) {
            System.out.println("Soubor postavy.txt nebyl nalezen. Vytvářím prázdný seznam postav.");
        }
        return postavy;
    }
    private HashMap<String, Predmet> nactiPredmety(int idLokace) {
        HashMap<String, Predmet> predmety = new HashMap<>();
        try {
            File soubor = new File("predmety.txt");
            Scanner ctecka = new Scanner(soubor);
            while (ctecka.hasNextLine()) {
                String radek = ctecka.nextLine();
                String[] udaje = radek.split(";");
                if (udaje.length == 3) {
                    int idLokacePredmetu = Integer.parseInt(udaje[0].trim());
                    String nazev = udaje[1].trim();
                    String popis = udaje[2].trim();
                    if (idLokacePredmetu == idLokace) {
                        predmety.put(nazev, new Predmet(nazev, popis));
                    }
                } else {
                    System.out.println("Chybný řádek v souboru predmety.txt: " + radek);
                }
            }
            ctecka.close();
        } catch (FileNotFoundException e) {
            System.out.println("Soubor predmety.txt nebyl nalezen.");
        }
        return predmety;
    }

}
