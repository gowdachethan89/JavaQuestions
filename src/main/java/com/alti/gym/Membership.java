package com.alti.gym;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Membership {
    /*
        Data for managing a gym membership, and methods which staff can
        use to perform any queries or updates.
    */
    public List<Member> members;

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

    }

    public Map<Integer, Double> getAverageWorkoutDurations() {
        return null;
    }

    public Map<Integer, Integer> getDuePayments(){

        return null;
    }
}
