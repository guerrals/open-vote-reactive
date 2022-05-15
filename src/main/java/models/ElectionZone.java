package models;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

public class ElectionZone {

    private String zone;
    private Map<String, Integer> zoneResults = new HashMap<String, Integer>();

    public ElectionZone(String urnNumber) {
        this.zone = urnNumber;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    /* Printa de forma mais humana o resultado da zona eleitoral */
    public void printZoneResults() {
        // Para cada candidato printa no terminal 'nome do candidato'
        for (String candidate : this.zoneResults.keySet()) {
            System.out.println(candidate + ": " + this.zoneResults.get(candidate));
        }
    }

    /* Atualiza o 'zoneResults' candidato a candidato dentro de um for */
    public void setZoneResults(Map<String, Integer> urnResults) {
        // Para cada candidato no array de candidatos (chaves do map urnResults)
        for (String candidate : urnResults.keySet()) {
            // Caso o 'zoneResults' tenha o candidato como chave
            if (this.zoneResults.containsKey(candidate)) {
                // Atualiza o candidato no map com o número de votos da urna + os votos atuais
                this.zoneResults.put(candidate, urnResults.get(candidate) + this.zoneResults.get(candidate));
            } else {
                // Cria o candidatao no 'zoneResults' inserindo o número de votos da urna como valor
                this.zoneResults.put(candidate, urnResults.get(candidate));
            }
        }
    }
}
