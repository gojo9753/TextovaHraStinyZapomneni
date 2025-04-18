/**
 * Třída Prozkoumej implementuje příkaz "prozkoumej", který zobrazí
 * název a popis aktuální lokace, ve které se hráč právě nachází.
 */
class Prozkoumej implements Prikaz {
    private MapaSveta mapaSveta;

    public Prozkoumej(MapaSveta mapaSveta) {
        this.mapaSveta = mapaSveta;
    }

    @Override
    public String getNazev() {
        return "prozkoumej";
    }

    @Override
    public String getPopis() {
        return "Zobrazí popis aktuální mistnosti.";
    }

    @Override
    public void proved(String[] parametry) {
        Lokace aktualniLokace = mapaSveta.getAktualniLokace();
        System.out.println("Jsme v místnosti: " + aktualniLokace.getNazev());
        System.out.println("Popis místnosti: " + aktualniLokace.getPopis());
        System.out.println("Mozné cesty: " + aktualniLokace.getMozneCesty());
        System.out.println("Dostupné predmety: " + aktualniLokace.getPredmety());
        System.out.println("Dostupné postavy: " + aktualniLokace.getPostavy());
    }
}
