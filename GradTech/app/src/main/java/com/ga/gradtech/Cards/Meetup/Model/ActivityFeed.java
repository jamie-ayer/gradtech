package com.ga.gradtech.Cards.Meetup.Model;

/**
 * This class models the JSON object returned by the Meetup API call.
 * Created by leisforkokomo on 4/21/16.
 */
public class ActivityFeed {
    Event[] results;

    /**
     * The following methods are getters and setters for the class's private variables.
     * @return
     */
    public Event[] getResults() {
        return results;
    }

    public void setResults(Event[] results) {
        this.results = results;
    }
}
