package models;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.*;

public class Urn implements Subscriber<Elector> {
    private Map<String, Integer> results = new HashMap<String, Integer>();

    public Map<String, Integer> getResults() {
        return results;
    }

    public void setResults(Elector elector) {
        if (this.results.containsKey(elector.getElectionChoice())) {
            int qtdVotes = this.results.get(elector.getElectionChoice());
            qtdVotes = qtdVotes + 1;
            this.results.put(elector.getElectionChoice(), qtdVotes);
        } else {
            this.results.put(elector.getElectionChoice(), 1);
        }
    }


    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Elector elector) {
        setResults(elector);
        System.out.println("Informativo:\nEleitor " + elector.getName() + " presente na votação\nVoto computado para candidato: " + elector.getElectionChoice() + "\nResultado parcial:");
        for (String candidate :
                results.keySet()) {
            System.out.println(candidate + ": " + results.get(candidate) + ";");
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Throwable: " + t);
    }

    @Override
    public void onComplete() {
        results.remove("Branco");
        String winner = "";
        Integer votes = 0;
        for (String candidate : results.keySet()) {
            if (results.get(candidate) > votes ) {
                votes = results.get(candidate);
                winner = candidate;
            }
        }
        System.out.println("Vencedor por maioria: " + winner);
    }
}
