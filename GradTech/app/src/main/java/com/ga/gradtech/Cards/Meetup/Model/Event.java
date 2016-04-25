package com.ga.gradtech.Cards.Meetup.Model;

/**
 * This class models the JSON object returned by the Meetup API call.
 * Created by leisforkokomo on 4/21/16.
 */
public class Event {
    private Venue venue;
    private String description;
    private String event_url;
    private String name;
    private long time;
    private Group group;

    /**
     * These methods are getters and setters for the class's private variables.
     * @return
     */
    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvent_url() {
        return event_url;
    }

    public void setEvent_url(String event_url) {
        this.event_url = event_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
