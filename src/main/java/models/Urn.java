package models;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.*;

public class Urn implements Subscriber<Elector> {
    private Map<String, Integer> urnResults = new HashMap<String, Integer>();

    public Map<String, Integer> getUrnResults() {
        return urnResults;
    }

    public void setUrnResults(Elector elector) {
        if (this.urnResults.containsKey(elector.getElectionChoice())) {
            int qtdVotes = this.urnResults.get(elector.getElectionChoice());
            qtdVotes = qtdVotes + 1;
            this.urnResults.put(elector.getElectionChoice(), qtdVotes);
        } else {
            this.urnResults.put(elector.getElectionChoice(), 1);
        }
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Elector elector) {
        setUrnResults(elector);
        System.out.println("Informativo:\nEleitor " +
                elector.getName() +
                " presente na sessão de votação\nVoto computado para candidato: " +
                elector.getElectionChoice() + "\nResultado parcial:");
        for (String candidate : urnResults.keySet()) {
            System.out.println(candidate + ": " + urnResults.get(candidate) + ";");
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Throwable: " + t);
    }

    @Override
    public void onComplete() {
        urnResults.remove("Branco");
        String winner = "";
        Integer votes = 0;
        for (String candidate : urnResults.keySet()) {
            if (urnResults.get(candidate) > votes ) {
                votes = urnResults.get(candidate);
                winner = candidate;
            }
        }
        System.out.println("Votação encerrada!\nVencedor por maioria: " + winner);
    }
}
