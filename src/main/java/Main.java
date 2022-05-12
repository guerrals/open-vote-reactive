import models.ElectionZone;
import models.Elector;
import models.Urn;
import reactor.core.publisher.Flux;
import com.github.javafaker.Faker;

import java.time.Duration;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Faker faker = Faker.instance();
        String[] candidates = {new Elector(true).getName(), new Elector(true).getName(), new Elector(true).getName(), "Branco"};

        Flux.range(0, 10).map(integer -> new Urn())
                .subscribe(Flux.range(0, 100).map(integer -> {
                    Elector elector = new Elector(false);
                    elector.setElectionChoice(candidates[faker.random().nextInt(candidates.length)]);
                    return elector;
                }).filter(elector -> Objects.equals(elector.getSituation(), "Regular"))::subscribe);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            System.out.println("");
        }
    }
}
