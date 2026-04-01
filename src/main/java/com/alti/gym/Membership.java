package com.alti.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Membership {
    /*
        Data for managing a gym membership, and methods which staff can
        use to perform any queries or updates.
    */
    public List<Member> members;
    // Add this field to store workouts per member
    private final Map<Integer, List<Workout>> memberWorkouts = new HashMap<>();

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
        //Check if the member exists
        boolean memberExists = false;
        for(Member member : members){
            if(member.memberId == id){
                memberExists = true;
                break;
            }
        }
        // If member exists, add the workout to their list
        if (memberExists){
            memberWorkouts.computeIfAbsent(id, k -> new ArrayList<>()).add(workout);
        }
        // If not, ignore as per requirements
    }

    public Map<Integer, Double> getAverageWorkoutDurations() {
        Map<Integer, Double> averageDurations = new HashMap<>();
        for (Map.Entry<Integer, List<Workout>> entry : memberWorkouts.entrySet()) {
            List<Workout> workouts = entry.getValue();
            if (!workouts.isEmpty()) {
                double totalDuration = 0.0;
                for (Workout workout : workouts) {
                    totalDuration += workout.getDuration();
                }
                double averageDuration = totalDuration / workouts.size();
                averageDurations.put(entry.getKey(), averageDuration);
            }
        }
        return averageDurations;
    }
}
