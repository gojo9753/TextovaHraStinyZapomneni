public class Main {
    /**
     * V metodě main se spustí hra, která je implementována tridlou Konzole.
     */
    public static void main(String[] args) {
        MapaSveta mapaSveta = new MapaSveta();
        Konzole konzole = new Konzole(mapaSveta);
        konzole.hraj();
    }
}

