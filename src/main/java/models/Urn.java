package models;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.*;

public class Urn implements Subscriber<Elector>  {
    private Integer urnNumber;
    private Map<String, Integer> urnResults = new HashMap<String, Integer>();

    public Urn(Integer number) {
        this.urnNumber = number;
    }

    public Integer getUrnNumber() {
        return urnNumber;
    }

    public void setUrnNumber(Integer urnNumber) {
        this.urnNumber = urnNumber;
    }
    public Map<String, Integer> getUrnResults() {
        return urnResults;
    }

    public void setUrnResults(Elector elector) {
        if (this.urnResults.containsKey(elector.getElectionChoice())) {
            this.urnResults.put(elector.getElectionChoice(), this.urnResults.get(elector.getElectionChoice()) + 1);
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
        this.setUrnResults(elector);
        System.out.println("\nInformativo:\nEleitor " +
                elector.getName() +
                " presente na sessão de votação\nVoto computado para candidato: " +
                elector.getElectionChoice() + "\nContagem de votos da urna:");
        for (String candidate : this.urnResults.keySet()) {
            System.out.println(candidate + ": " + this.urnResults.get(candidate));
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("Throwable: " + t);
    }

    @Override
    public void onComplete() {
        System.out.println("Votação na urna " + getUrnNumber() + " encerrada!");
    }
}
