package com.alti.gym;

class Workout {
    /**
     * This class represents a single workout session for a member.
     * Each object of the Workout class has a unique ID, as well as
     * a start time and end time that are represented in the number
     * of minutes spent from the start of the day.
     */

    private int id;
    private int startTime;
    private int endTime;

    public Workout(int id, int startTime, int endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return endTime - startTime;
    }
}
