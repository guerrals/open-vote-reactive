package models;

import reactor.core.publisher.Flux;

import java.util.*;

public class ElectionZone {
    private String zone;
    private List<String> sessions = List.of(new String[]{"01", "02", "03", "04", "05"});
    private Map<String, String> electionZoneResults = new HashMap<String, String>();

    public ElectionZone(String zone) {
        this.zone = zone;
    }

    public String getZone() {
        return zone;
    }

    public List<String> getSessions() {
        return sessions;
    }

    public Map<String, String> getElectionZoneResults() {
        return electionZoneResults;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setSessions(List<String> sessions) {
        this.sessions = sessions;
    }

    public void setElectionZoneResults(Map<String, String> electionZoneResults) {
        this.electionZoneResults = electionZoneResults;
    }

    public List<Urn> urns() {
        List<Urn> urnList = new ArrayList<Urn>();
        for (String session : sessions) {
            urnList.add(new Urn());
        }
        return urnList;
    }
}
