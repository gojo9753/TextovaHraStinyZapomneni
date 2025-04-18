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
            System.out.println();
            System.out.println("*************************************************************");
            System.out.println("*                    VÍTĚZSTVÍ!                             *");
            System.out.println("*************************************************************");
            System.out.println("Gratuluji! Dokončil jsi své putování ve hře Stíny Zapomnění.");
            System.out.println("Nalezl jsi ztracený artefakt a odhalil jsi tajemství města.");
            System.out.println("Temnota ustupuje, stíny se rozplývají a město začíná znovu ožívat.");
            System.out.println("Obyvatelé se pomalu vracejí a v jejich očích vidíš vděčnost.");
            System.out.println();
            System.out.println("Tvé jméno bude navždy součástí legend tohoto místa.");
            System.out.println("Děkujeme, že jsi hrál Stíny Zapomnění!");
            System.out.println("*************************************************************");
            konzole.konecHry = true;
        } else {
            System.out.println("Tento příkaz můžeš použít pouze v místnosti, která je koncem hry.");
        }
    }
}
