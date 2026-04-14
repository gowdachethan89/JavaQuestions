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

    public Map<Integer, Integer> getDuePayments(){
        Map<Integer, Integer> dues = new HashMap<>();
        for(Integer memberId: workoutDataMap.keySet()){
            List<Workout> workouts = workoutDataMap.get(memberId);
            int totalWorkoutInHours = (int)workouts.stream()
                    .map(workout ->
                            Math.ceil((double) (workout.getEndTime() - workout.getStartTime()) /60)
                    )
                    .mapToDouble(duration -> duration).sum();

            Optional<Member> member = members.stream().filter(m -> m.memberId == memberId).findFirst();
            if(member.isPresent()){
                if(member.get().membershipStatus == MembershipStatus.BRONZE){
                    dues.put(memberId, (totalWorkoutInHours - 1) * 10);
                }
                if(member.get().membershipStatus == MembershipStatus.SILVER){
                    dues.put(memberId, (totalWorkoutInHours - 3) * 8);
                }
                if(member.get().membershipStatus == MembershipStatus.GOLD) {
                    dues.put(memberId, (totalWorkoutInHours - 5) * 6);
                }
            }
        }
        return dues;
    }
}
