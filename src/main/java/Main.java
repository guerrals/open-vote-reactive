import models.ElectionZone;
import models.Elector;
import models.Urn;
import reactor.core.publisher.Flux;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
/*
* Nome: Jefferson William de Paiva Guerra
* Matrícula: 20180122400
* */
public class Main {
    public static void main(String[] args) {
        Faker faker = Faker.instance();
        /*Array estático de 4 candidatos, incluindo o voto em branco.
        Os candidatos são representados pelos nomes.
        */
        String[] candidates = {new Elector(true).getName(), new Elector(true).getName(), new Elector(true).getName(), "Branco"};

        // Map de resultados geral da eleição
        Map<String, Integer> results = new HashMap<String, Integer>();

        // Fluxo de zonas eleitorais da votação
        Flux<ElectionZone> electionZoneFlux = Flux.range(0, 5).map(integer -> {
            return new ElectionZone(String.valueOf(integer));
        });

        // Declaração do método executado na subscrição do fluxo de zonas
        electionZoneFlux.subscribe(electionZone -> {
            // É criado um fluxo de urnas e um fluxo de eleitores
            Flux<Urn> urnFlux = Flux.range(0, 5).map(Urn::new);
            Flux<Elector> electorFlux = Flux.range(0, 50).map(number -> {
                /*
                Declara um eleitor e assinala seu voto de forma aleatória
                para um integrante do array de candidatos
                */

                Elector elector = new Elector(false);
                elector.setElectionChoice(candidates[faker.random().nextInt(candidates.length)]);
                return elector;
                // Filtra os eleitores de acordo com a situação eleitoral
            }).filter(elector -> Objects.equals(elector.getSituation(), "Regular"));

            // Declaração do método executado na subscrição do fluxo de urnas
            urnFlux.subscribe(urn -> {
                // Subscree a urna no fluxo de eleitores
                electorFlux.subscribe(urn);

                // Atualiza o resultado da zona urna a urna
                electionZone.setZoneResults(urn.getUrnResults());

                // Popula o array de resultados geral
                urn.getUrnResults().keySet().forEach(candidate -> {
                    if (results.containsKey(candidate)) {
                        results.put(candidate, urn.getUrnResults().get(candidate) + results.get(candidate));
                    } else {
                        results.put(candidate, urn.getUrnResults().get(candidate));
                    }
                });
            });

            System.out.println("\nResultados da zona eleitoral: " + electionZone.getZone());
            electionZone.printZoneResults();
        });
        electionZoneFlux.subscribe();

        results.remove("Branco");
        String winner = "";
        Integer votes = 0;
        for (String candidate : results.keySet()) {
            if (results.get(candidate) > votes ) {
                votes = results.get(candidate);
                winner = candidate;
            }
        }
        System.out.println("\nResultado das eleições:\nCandidato eleito: " + winner +"\n" + winner + " Candidato(a) com " + votes + " votos");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            System.out.println("");
        }
    }
}
