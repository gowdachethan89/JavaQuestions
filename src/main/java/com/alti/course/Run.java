package com.alti.course;

import java.util.ArrayList;
import java.util.List;

class Run {
    /* Data and methods about a single run of the obstacle course */
    public Course course; // The Course object this run is for
    public boolean complete; // true if the run is a full run of the course
    // false if the run is in progress or was aborted
    public List<Integer> obstacleTimes; // The times it took to complete each obstacle

    public Run(Course runCourse) {
        course = runCourse;
        complete = false;
        obstacleTimes = new ArrayList<>();
    }

    public void addObstacleTime(int obstacleTime) {
        // When an obstacle is completed, add the time to the current run.
        // Params:
        //   obstacleTime: the time in seconds it took to complete the obstacle
        if(complete) {
            throw new IllegalStateException("Cannot add obstacle to complete run");
        }
        obstacleTimes.add(obstacleTime);
        if(obstacleTimes.size() == course.obstacleCount) {
            complete = true;
        }
    }

    public int getRunTime() {
        // Returns the total time this run has taken.
        // If the run is not complete, it returns the time taken so far.
        return obstacleTimes.stream().mapToInt(Integer::intValue).sum();
    }
}
