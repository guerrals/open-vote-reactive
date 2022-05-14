import models.Elector;
import models.Urn;
import reactor.core.publisher.Flux;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Faker faker = Faker.instance();
        String[] candidates = {new Elector(true).getName(), new Elector(true).getName(), new Elector(true).getName(), "Branco"};

        Map<String, Integer> results = new HashMap<String, Integer>();
        Flux<Urn> urnFlux = Flux.range(0, 5).map(Urn::new);

        urnFlux.subscribe(urn -> {
            Flux<Elector> electorFlux = Flux.range(0, 100).map(number -> {
                Elector elector = new Elector(false);
                elector.setElectionChoice(candidates[faker.random().nextInt(candidates.length)]);
                return elector;
            }).filter(elector -> Objects.equals(elector.getSituation(), "Regular"));
            electorFlux.subscribe(urn);

            for (String candidate : urn.getUrnResults().keySet()) {
                if (results.containsKey(candidate)) {
                    results.put(candidate, urn.getUrnResults().get(candidate) + results.get(candidate));
                } else {
                    results.put(candidate, urn.getUrnResults().get(candidate));
                }
            }
        });

        results.remove("Branco");
        String winner = "";
        Integer votes = 0;
        for (String candidate : results.keySet()) {
            if (results.get(candidate) > votes ) {
                votes = results.get(candidate);
                winner = candidate;
            }
        }
        System.out.println("\nResultado das eleições:\nCandidato eleito: " + winner +"\n" + winner + " Eleito(a) com " + votes + " votos");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            System.out.println("");
        }
    }
}
