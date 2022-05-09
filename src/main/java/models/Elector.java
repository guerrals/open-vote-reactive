package models;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Elector {
    private String id;
    private String name;
    private String age;
    private String situation;
    private String voting_zone;
    private String voting_room;
    private String electionChoice;
    private LocalDateTime voteDateTime;
    private boolean isCandidate = false;

    public String getElectionChoice() {
        return electionChoice;
    }

    public void setElectionChoice(String electionChoice) {
        this.electionChoice = electionChoice;
    }

    public LocalDateTime getVoteDateTime() {
        return voteDateTime;
    }

    public void setVoteDateTime(LocalDateTime voteDateTime) {
        this.voteDateTime = voteDateTime;
    }

    public Elector(boolean candidate) {
        Faker faker = Faker.instance();
        String[] situations = {"Regular", "Irregular"};
        String[] voting_zones = {"72", "77", "79", "29", "00"};
        String[] voting_rooms = {"01", "02", "03", "04", "05"};
        this.id = UUID.randomUUID().toString();
        this.name = faker.name().fullName();
        this.age = String.valueOf(faker.number().numberBetween(18, 60));
        this.situation = situations[faker.random().nextInt(situations.length)];
        this.voting_zone = voting_zones[faker.random().nextInt(voting_zones.length)];
        this.voting_room = voting_rooms[faker.random().nextInt(voting_rooms.length)];
        this.isCandidate = candidate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getVoting_zone() {
        return voting_zone;
    }

    public void setVoting_zone(String voting_zone) {
        this.voting_zone = voting_zone;
    }

    public String getVoting_room() {
        return voting_room;
    }

    public void setVoting_room(String voting_room) {
        this.voting_room = voting_room;
    }

    public boolean isCandidate() {
        return isCandidate;
    }

    public void setCandidate(boolean candidate) {
        isCandidate = candidate;
    }
}

