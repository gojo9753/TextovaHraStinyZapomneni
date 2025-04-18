import java.util.ArrayList;
import java.util.Stack;

/**
 * Třída Jdi implementuje příkaz "jdi", který umožňuje hráči přesouvat se mezi lokacemi
 * v textové adventuře. Hráč může jít do jiné lokace podle jejího ID nebo se vrátit zpět
 * do předchozí lokace pomocí "jdi zpet".
 */

class Jdi implements Prikaz {
    private MapaSveta mapaSveta;
    private Stack<Integer> historieLokaci;

    public Jdi(MapaSveta mapaSveta, Stack<Integer> historieLokaci) {
        this.mapaSveta = mapaSveta;
        this.historieLokaci = historieLokaci;
    }

    @Override
    public String getNazev() {
        return "jdi";
    }

    @Override
    public String getPopis() {
        return "Přejde do jiného místa. Použití: jdi <místo> nebo jdi zpet";
    }

    @Override
    public void proved(String[] parametry) {
        if (parametry.length != 1) {
            System.out.println("Chybné použití příkazu jdi. Správné použití: jdi <id_lokace> nebo jdi zpet");
            return;
        }

        if (parametry[0].equalsIgnoreCase("zpet")) {
            if (historieLokaci.size() > 1) {
                historieLokaci.pop();
                int idPredchoziLokace = historieLokaci.peek();
                Lokace predchoziLokace = mapaSveta.getLokace(idPredchoziLokace);
                mapaSveta.setAktualniLokace(predchoziLokace);
                System.out.println("Přešel jsi do: " + predchoziLokace.getNazev());
                System.out.println(predchoziLokace.getPopis());
            } else {
                System.out.println("Už jsi na začátku, nemůžeš jít zpět.");
            }
        } else {
            int idLokace = mapaSveta.najdiLokaci(parametry[0]);
            if (idLokace == -1) {
                System.out.println("Místo s názvem " + parametry[0] + " neexistuje.");
                return;
            }
            Lokace aktualniLokace = mapaSveta.getAktualniLokace();
            ArrayList<Integer> mozneCesty = aktualniLokace.getMozneCesty();

            if (mozneCesty.contains(idLokace)) {
                Lokace novaLokace = mapaSveta.getLokace(idLokace);
                if (novaLokace != null) {
                    historieLokaci.push(mapaSveta.getAktualniLokaceId());
                    mapaSveta.setAktualniLokace(novaLokace);
                    System.out.println("Přešel jsi do místa: " + novaLokace.getNazev());
                    System.out.println(novaLokace.getPopis());
                } else {
                    System.out.println("Místo s ID " + idLokace + " neexistuje.");
                }
            } else {
                System.out.println("Z tohoto místa se do místa  " + mapaSveta.getLokace(idLokace).getNazev() + " nedá jít.");
            }
        }
    }
}

