package com.alti.course;

import java.util.ArrayList;
import java.util.List;

class RunCollection {
    public Course course; // the Course this RunCollection is for
    public List<Run> runs;  // the Run objects for this particular course

    public RunCollection(Course collectionCourse) {
        course = collectionCourse;
        runs = new ArrayList<>();
    }

    public int getNumRuns() {
        // Returns the number of runs in this collection
        return runs.size();
    }

    public void addRun(Run run) {
        // Adds a run to this collection
        if(!run.course.equals(course)) {
            throw new IllegalArgumentException("run's Course is not the same as the RunCollection's");
        }
        runs.add(run);
    }

    public int personalBest() {
        // Returns the best finish time achieved in this RunCollection
        return runs.stream().filter(run -> run.complete).mapToInt(v -> v.getRunTime()).min().orElse(Integer.MAX_VALUE);
    }

    public int bestOfBests() {
        int totalBest = 0;
        for(int i = 0; i < course.obstacleCount; i++){
            final int index = i;
            int minForObstacle = runs.stream()
                    .filter(r -> r.obstacleTimes.size() > index)
                    .mapToInt(r -> r.obstacleTimes.get(index))
                    .min()
                    .orElse(0);
            totalBest += minForObstacle;
        }
        return totalBest;
    }

    public double chanceOfPersonalBest(Run run) {
        return 0.0;
    }
}
