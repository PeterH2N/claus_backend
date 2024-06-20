package claus.backend.DBObjects.teams;

import claus.backend.DBObjects.DBObject;

import java.util.ArrayList;
import java.util.UUID;

public class Team
{
    UUID id;
    String name;

    ArrayList<User> coaches;

    ArrayList<User> gymnasts;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getCoaches() {
        return coaches;
    }

    public void setCoaches(ArrayList<User> coaches) {
        this.coaches = coaches;
    }

    public void addCoach(User coach) {
        coaches.add(coach);
    }

    void removeCoach(User coach) {
        coaches.remove(coach);
    }

    public ArrayList<User> getGymnasts() {
        return gymnasts;
    }

    public void setGymnasts(ArrayList<User> gymnasts) {
        this.gymnasts = gymnasts;
    }

    public void addGymnast(User gymnast) {
        gymnasts.add(gymnast);
    }

    void removeGymnast(User gymnast) {
        gymnasts.remove(gymnast);
    }
}
