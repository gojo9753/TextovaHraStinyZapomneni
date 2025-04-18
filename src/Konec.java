class Konec implements Prikaz {
    private final Konzole konzole;

    public Konec(Konzole konzole) {
        this.konzole = konzole;
    }

    @Override
    public String getNazev() {
        return "konec";
    }

    @Override
    public String getPopis() {
        return "Ukončí hru.";
    }

    @Override
    public void proved(String[] parametry) {
        if (konzole.getAktualniLokaceId() == 5) {
            System.out.println("Konec hry. Díky za hru!");
            konzole.konecHry = true;
        } else {
            System.out.println("Tento příkaz mužete použit pouze v místnosti, která je koncem hry.");
        }
    }
}
