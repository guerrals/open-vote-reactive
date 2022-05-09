import models.Voter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class Main {
    public Flux<Voter> votingPersons() {
        return Flux.interval(Duration.ofSeconds(1)).map(s -> {
            Voter voter = new Voter(false);
            System.out.println("Eleitor presente na sala de votação: " + voter.getVoting_room());
            return voter;
        });
    }
    public static void main(String[] args) {
        List<Voter> candidates = List.of(new Voter(true), new Voter(true), new Voter(true));
        Main main = new Main();

        main.votingPersons().subscribe(voter -> {
            System.out.println("Nome do eleitor: " + voter.getName());
        });
    }
}
