import java.util.HashMap;
/**
 * Třída Pouzij implementuje příkaz "pouzij", který umožňuje hráči použít předmět
 * z inventáře. Chování závisí na konkrétním předmětu (např. klíč, lektvar).
 */
class Pouzij implements Prikaz {
    private final MapaSveta mapaSveta;
    private final HashMap<String, Predmet> inventar;

    public Pouzij(MapaSveta mapaSveta, HashMap<String, Predmet> inventar) {
        this.mapaSveta = mapaSveta;
        this.inventar = inventar;
    }

    @Override
    public String getNazev() {
        return "pouzij";
    }

    @Override
    public String getPopis() {
        return "Použije předmět z inventáře. Použití: pouzij <nazev_predmetu>";
    }

    @Override
    public void proved(String[] parametry) {
        if (parametry.length != 1) {
            System.out.println("Chybné použití příkazu pouzij. Správné použití: pouzij <nazev_predmetu>");
            return;
        }

        String nazevPredmetu = parametry[0];
        if (inventar.containsKey(nazevPredmetu)) {
            Predmet predmet = inventar.get(nazevPredmetu);
            System.out.println("Používáš předmět: " + predmet.getNazev());
            
            switch (nazevPredmetu) {
                case "Klíč":
                    System.out.println("Otvíráš dveře do tajné místnosti! Zkontroluj si, kam můžeš jít.");
                    mapaSveta.getAktualniLokace().pridejCestu(4);
                    break;
                    
                case "AmuletSvětla":
                    System.out.println("Amulet začíná zářit jasným světlem a odhání temné síly z místnosti.");
                    if (mapaSveta.getAktualniLokaceId() == 4) {
                        System.out.println("Duchové ustupují a odhalují tajný průchod!");
                        mapaSveta.getAktualniLokace().pridejCestu(5);
                    } else {
                        System.out.println("Amulet svítí, ale zdá se, že zde nejsou žádné temné síly k odehnání.");
                    }
                    break;
                    
                case "Svitky":
                    System.out.println("Rozbaluješ staré svitky a pečlivě je studuješ...");
                    System.out.println("Svitky obsahují starou mapu města a zmiňují se o artefaktu ukrytém ve Strážní věži.");
                    System.out.println("Také naznačují, že k opravě mostu je potřeba vzpomínka na lepší časy.");
                    break;
                    
                case "Meč":
                    System.out.println("Tasíš meč, připraven k boji!");
                    if (mapaSveta.getAktualniLokaceId() == 8) {
                        System.out.println("Mečem jsi zničil starou bariéru, která blokovala přístup k zadní části tržiště.");
                        System.out.println("Našel jsi skrytý předmět!");
                        Predmet stribrna = new Predmet("StříbrnáMince", "Může být směněna za důležité informace na tržišti.");
                        inventar.put("StříbrnáMince", stribrna);
                    } else {
                        System.out.println("Mácháš mečem ve vzduchu, ale není tu nic, proti čemu by se dal použít.");
                    }
                    break;
                    
                case "KřišťálováDýka":
                    System.out.println("Křišťálová dýka se rozsvítí a odhaluje neviditelné entity kolem tebe.");
                    if (mapaSveta.getAktualniLokaceId() == 4 || mapaSveta.getAktualniLokaceId() == 10) {
                        System.out.println("Vidíš duchy, kteří ti ukazují směr k tajnému pokladu!");
                        System.out.println("Duchové ti našeptávají: 'Hledej v Opatství, tam najdeš to, co hledáš.'");
                    } else {
                        System.out.println("Zdá se, že v této místnosti nejsou žádné neviditelné bytosti.");
                    }
                    break;
                    
                case "StříbrnáMince":
                    System.out.println("Prohlížíš si stříbrnou minci, na které je vyobrazeno staré město.");
                    if (mapaSveta.getAktualniLokaceId() == 8) {
                        System.out.println("Ukazuješ minci starému obchodníkovi na tržišti.");
                        System.out.println("Obchodník: 'Ah, vidím, že jsi našel starou relikvii. Výměnou za ni ti povím tajemství.'");
                        System.out.println("Obchodník ti šeptá: 'Elixír vědění najdeš v Opatství. Klíč k tajné komnatě se nachází v Podzemní svatyni.'");
                        inventar.remove("StříbrnáMince");
                    } else {
                        System.out.println("Tady není nikdo, komu bys mohl minci nabídnout.");
                    }
                    break;
                    
                case "ElixírVědění":
                    System.out.println("Piješ Elixír vědění! Tvá mysl se rozjasňuje a vidíš věci, které jsi předtím neviděl.");
                    System.out.println("Nyní víš, že artefakt je ukrytý v místnosti Strážní věž!");
                    System.out.println("Také zjišťuješ, že některé postavy ti mohou položit hádanky, které musíš vyřešit pomocí příkazu 'odpovez'.");
                    inventar.remove("ElixírVědění");
                    break;
                    
                default:
                    System.out.println("S tímto předmětem se nedá nic dělat.");
                    break;
            }
        } else {
            System.out.println("Předmět " + nazevPredmetu + " nemáš v inventáři.");
        }
    }
}
