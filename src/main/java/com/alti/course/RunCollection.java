package com.alti.course;

import java.util.*;

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
                    .mapToInt(r->r.obstacleTimes.get(index))
                    .min()
                    .orElse(0);
            totalBest += minForObstacle;
        }
        return totalBest;
    }

    public double chanceOfPersonalBest(Run run) {
        int pb = personalBest();
        if(pb == Integer.MAX_VALUE) return 1.0;

        Map<Integer, List<Integer>> pool = new HashMap<>();
        for (int i = 0; i < course.obstacleCount; i++) {
            pool.put(i, new ArrayList<>());
        }

        for(Run r: runs) {
            for(int i = 0; i < r.obstacleTimes.size(); i++) {
                pool.get(i).add(r.obstacleTimes.get(i));
            }
        }

        int successCount = 0;
        int trials = 10000;
        Random rand = new Random();
        int currentRunTime = run.getRunTime();
        int startIdx = run.obstacleTimes.size();

        for(int t = 0; t < trials; t++) {
            int simulatedTotal = currentRunTime;
            for(int i = startIdx; i < course.obstacleCount; i++) {
                List<Integer> options = pool.get(i);
                if(options.isEmpty()){
                    simulatedTotal += 0;
                } else {
                    simulatedTotal += options.get(rand.nextInt(options.size()));
                }
            }
            if(simulatedTotal <= pb){
                successCount++;
            }
        }
        return (double)successCount/trials;
    }
}
