import java.util.HashMap;

/**
 * Třída Quiz implementuje systém hádanek a kvízů v textové hře.
 * Umožňuje vytvářet a řešit různé typy hádanek a kvízů, které jsou
 * prezentovány postavami v herním světě.
 */
class Quiz {
    private String otazka;
    private String odpoved;
    private String nazevPostavy;
    private String odmena;
    private boolean vyreseno;

    public Quiz(String otazka, String odpoved, String nazevPostavy, String odmena) {
        this.otazka = otazka;
        this.odpoved = odpoved;
        this.nazevPostavy = nazevPostavy;
        this.odmena = odmena;
        this.vyreseno = false;
    }

    public String getOtazka() {
        return otazka;
    }

    public String getOdmena() {
        return odmena;
    }

    public String getNazevPostavy() {
        return nazevPostavy;
    }

    public boolean isVyreseno() {
        return vyreseno;
    }

    public boolean zkontrolujOdpoved(String pokus) {
        if (pokus.toLowerCase().equals(odpoved.toLowerCase())) {
            vyreseno = true;
            return true;
        }
        return false;
    }
}