import models.Elector;
import models.Urn;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.github.javafaker.Faker;
public class Main {
    public static void main(String[] args) {
        Faker faker = Faker.instance();
        String[] candidates = {new Elector(true).getName(), new Elector(true).getName(), new Elector(true).getName(), "Branco"};
        Flux.range(0, 100).map(integer -> {
            Elector elector = new Elector(false);
            elector.setElectionChoice(candidates[faker.random().nextInt(candidates.length)]);
            return elector;
        }).filter(elector -> Objects.equals(elector.getSituation(), "Regular")).subscribe(new Urn());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            System.out.println("");
        }
    }
}
