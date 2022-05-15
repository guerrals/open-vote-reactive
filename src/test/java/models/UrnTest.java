package models;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UrnTest {

    @Test
    public void onNext() {
        Flux<Elector> electorFlux = Flux.just(new Elector(false), new Elector(false), new Elector(false));
        StepVerifier.create(electorFlux)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }
    @Test
    void onError() {
        Faker faker = Faker.instance();
        String[] candidates = {new Elector(true).getName(), new Elector(true).getName(), new Elector(true).getName(), "Branco"};
        Flux<Urn> urnFlux = Flux.range(0, 2).map(Urn::new).concatWith(Flux.error(new RuntimeException("Exception UrnFlux")));
        Flux<Elector> electorFlux = Flux.range(0, 5).map(number -> {
                /*
                Declara um eleitor e assinala seu voto de forma aleatória
                para um integrante do array de candidatos
                */

            Elector elector = new Elector(false);
            elector.setElectionChoice(candidates[faker.random().nextInt(candidates.length)]);
            return elector;
            // Filtra os eleitores de acordo com a situação eleitoral
        }).filter(elector -> Objects.equals(elector.getSituation(), "Regular")).concatWith(Flux.error(new RuntimeException("Exception ElectorFlux")));

        StepVerifier.create(urnFlux)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .expectErrorMessage("Exception UrnFlux");

        StepVerifier.create(electorFlux)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .assertNext(Assertions::assertNotNull)
                .expectErrorMessage("Exception ElectorFlux");
    }
}