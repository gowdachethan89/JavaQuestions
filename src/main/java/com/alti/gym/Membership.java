package com.alti.gym;

import java.util.*;

class Membership {
    /*
        Data for managing a gym membership, and methods which staff can
        use to perform any queries or updates.
    */
    public List<Member> members;

    public Map<Integer, List<Workout>> workoutDataMap = new HashMap<>();

    public Membership() {
        members = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void updateMembership(int memberId, MembershipStatus membershipStatus) {
        for (Member member : members) {
            if (member.memberId == memberId) {
                member.membershipStatus = membershipStatus;
                break;
            }
        }
    }

    public MembershipStatistics getMembershipStatistics() {
        int totalMembers = members.size();
        int totalPaidMembers = 0;
        for (Member member : members) {
            System.out.println(member.membershipStatus);
            if (member.membershipStatus == MembershipStatus.GOLD  || member.membershipStatus == MembershipStatus.SILVER) {
                totalPaidMembers++;
            }
        }
        System.out.println(totalPaidMembers);


        double conversionRate = (totalPaidMembers / (double) totalMembers) * 100.0;
        return new MembershipStatistics(totalMembers, totalPaidMembers, conversionRate);
    }
    public void addWorkout(int id, Workout workout){
        boolean memberExists = members.stream().map(member -> member.memberId).toList().contains(id);
        if(memberExists) {
            workoutDataMap.putIfAbsent(id, new ArrayList<>());
            workoutDataMap.get(id).add(workout);
        }
    }

    public Map<Integer, Double> getAverageWorkoutDurations() {
        Map<Integer, Double> averageWorkouts = new HashMap<>();
        for(Integer memberId: workoutDataMap.keySet()){
            List<Workout> workouts = workoutDataMap.get(memberId);
            double totalWorkout = workouts.stream()
                    .map(workout -> workout.getEndTime() - workout.getStartTime())
                    .mapToDouble(duration -> duration).sum();
            double averageDuration = totalWorkout/workouts.size();
            averageWorkouts.put(memberId, averageDuration);
        }
        return averageWorkouts;
    }

    /**
     * Calculates due payments for each member based on their membership tier
     * and workout history (ordered by workout ID).
     */
    public Map<Integer, Integer> getDuePayments() {
        Map<Integer, Integer> result = new HashMap<>();

        for (Member member : members) {
            int memberId = member.memberId;
            // Get a copy of workouts to sort without affecting the original list
            List<Workout> workouts = new ArrayList<>(workoutDataMap.getOrDefault(memberId, new ArrayList<>()));

            // Requirement: Workouts are ordered by their ID
            workouts.sort(Comparator.comparingInt(Workout::getId));

            int freeWorkoutQuota = 0;
            int hourlyRate = 0;

            switch (member.membershipStatus) {
                case BRONZE:
                    freeWorkoutQuota = 1;
                    hourlyRate = 10;
                    break;
                case SILVER:
                    freeWorkoutQuota = 3;
                    hourlyRate = 8;
                    break;
                case GOLD:
                    freeWorkoutQuota = 5;
                    hourlyRate = 6;
                    break;
            }

            int totalCost = 0;
            for (int i = 0; i < workouts.size(); i++) {
                // Skip the initial free workouts based on membership tier
                if (i < freeWorkoutQuota) continue;

                Workout w = workouts.get(i);
                int durationMinutes = w.getDuration();

                // Requirement: Duration rounded up to the nearest hour
                int hoursCharged = (int) Math.ceil(durationMinutes / 60.0);
                totalCost += hoursCharged * hourlyRate;
            }

            result.put(memberId, totalCost);
        }

        return result;
    }
}
