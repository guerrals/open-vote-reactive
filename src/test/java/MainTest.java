import com.github.javafaker.Faker;
import models.ElectionZone;
import models.Elector;
import models.Urn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void main() {
        Faker faker = Faker.instance();
        /*Array estático de 4 candidatos, incluindo o voto em branco.
        Os candidatos são representados pelos nomes.
        */
        String[] candidates = {new Elector(true).getName(), new Elector(true).getName(), new Elector(true).getName(), "Branco"};

        // Map de resultados geral da eleição
        Map<String, Integer> results = new HashMap<String, Integer>();

        // Fluxo de zonas eleitorais da votação
        Flux<ElectionZone> electionZoneFlux = Flux.range(0, 2).map(integer -> {
            return new ElectionZone(String.valueOf(integer));
        });

        // Declaração do método executado na subscrição do fluxo de zonas
        electionZoneFlux.subscribe(electionZone -> {
            // É criado um fluxo de urnas e um fluxo de eleitores
            Flux<Urn> urnFlux = Flux.range(0, 4).map(Urn::new);
            Flux<Elector> electorFlux = Flux.range(0, 10).map(number -> {
                /*
                Declara um eleitor e assinala seu voto de forma aleatória
                para um integrante do array de candidatos
                */

                Elector elector = new Elector(false);
                elector.setElectionChoice(candidates[faker.random().nextInt(candidates.length)]);
                return elector;
                // Filtra os eleitores de acordo com a situação eleitoral
            }).filter(elector -> Objects.equals(elector.getSituation(), "Regular"));

            StepVerifier.create(electorFlux).assertNext(Assertions::assertNotNull).expectComplete();

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
            StepVerifier.create(urnFlux).assertNext(Assertions::assertNotNull).expectComplete();;

        });
        StepVerifier.create(electionZoneFlux).assertNext(Assertions::assertNotNull).expectComplete();
    }
}