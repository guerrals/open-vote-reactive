package models;

import java.util.*;

public class Urn {
    public void calculateResults(List<String> candidatesNames, List<String> electionChoices){
        Map<String, String> results = new HashMap<String, String>();
        for (String candidateName : candidatesNames) {
            int voteFrequency = Collections.frequency(electionChoices, candidateName);
            results.put(candidateName, String.valueOf(voteFrequency));
        }

        System.out.println("Resultado das eleições: " + results);
    }
}
