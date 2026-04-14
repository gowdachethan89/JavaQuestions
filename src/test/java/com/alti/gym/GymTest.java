package com.alti.gym;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;


public class GymTest {
    /*
    This is not a complete test suite, but tests some basic functionality of
    the code and shows how to use it.
*/
    @Test
    public void testMember() {
        System.out.println("Running testMember");
        Member testMember = new Member(1, "John Doe", MembershipStatus.BRONZE);
        assert testMember.memberId == 1 :
                "member ID should be 1, was " + testMember.memberId;
        assert testMember.name.equals("John Doe") :
                "member name should be \"John Doe\", was \"" + testMember.name + "\"";
        assert testMember.membershipStatus == MembershipStatus.BRONZE :
                "membership status should be BRONZE, was " + testMember.membershipStatus;
    }

    @Test
    public void testMembership() {
        System.out.println("Running testMembership");
        Membership testMembership = new Membership();
        Member testMember = new Member(1, "John Doe", MembershipStatus.BRONZE);
        testMembership.addMember(testMember);
        assert testMembership.members.size() == 1 :
                "members size should be 1, was " + testMembership.members.size();
        assert testMembership.members.get(0).equals(testMember) :
                "first member should equal testMember";

        testMembership.updateMembership(1, MembershipStatus.SILVER);
        assert testMembership.members.get(0).membershipStatus == MembershipStatus.SILVER :
                "membership status should be SILVER, was " + testMembership.members.get(0).membershipStatus;

        Member testMember2 = new Member(2, "Alex C", MembershipStatus.BRONZE);
        testMembership.addMember(testMember2);

        Member testMember3 = new Member(3, "Marie C", MembershipStatus.GOLD);
        testMembership.addMember(testMember3);

        Member testMember4 = new Member(4, "Joe D", MembershipStatus.SILVER);
        testMembership.addMember(testMember4);

        Member testMember5 = new Member(5, "June R", MembershipStatus.BRONZE);
        testMembership.addMember(testMember5);

        MembershipStatistics attendanceStats = testMembership.getMembershipStatistics();
        assert attendanceStats.totalMembers == 5 :
                "total members should be 5, was " + attendanceStats.totalMembers;
        assert attendanceStats.totalPaidMembers == 3 :
                "total paid members should be 3, was " + attendanceStats.totalPaidMembers;
        assert Math.abs(attendanceStats.conversionRate - 60.00) < 0.1 :
                "conversion rate should be 60.00, was " + attendanceStats.conversionRate;
    }

    @Test
    public void testGetAverageWorkoutDurations() {
        System.out.println("Running testGetAverageWorkoutDurations");
        Membership testMembership = new Membership();
        Member testMember1 = new Member(12, "John Doe", MembershipStatus.SILVER);
        testMembership.addMember(testMember1);

        Member testMember2 = new Member(22, "Alex Cleeve", MembershipStatus.BRONZE);
        testMembership.addMember(testMember2);

        Member testMember3 = new Member(31, "Marie Cardiff", MembershipStatus.GOLD);
        testMembership.addMember(testMember3);

        Member testMember4 = new Member(37, "George Costanza", MembershipStatus.SILVER);
        testMembership.addMember(testMember4);

        Workout testWorkout1 = new Workout(11, 10, 20);
        Workout testWorkout2 = new Workout(24, 15, 35);
        Workout testWorkout3 = new Workout(32, 45, 90);
        Workout testWorkout4 = new Workout(47, 100, 155);
        Workout testWorkout5 = new Workout(56, 120, 200);
        Workout testWorkout6 = new Workout(62, 300, 400);
        Workout testWorkout7 = new Workout(78, 1000, 1010);
        Workout testWorkout8 = new Workout(80, 1010, 1045);

        testMembership.addWorkout(12, testWorkout1);
        testMembership.addWorkout(22, testWorkout2);
        testMembership.addWorkout(31, testWorkout3);
        testMembership.addWorkout(12, testWorkout4);
        testMembership.addWorkout(22, testWorkout5);
        testMembership.addWorkout(31, testWorkout6);
        testMembership.addWorkout(12, testWorkout7);
        testMembership.addWorkout(4, testWorkout8);

        Map<Integer, Double> averageDurations = testMembership.getAverageWorkoutDurations();
        assert Math.abs(averageDurations.get(12) - 25.0) < 0.1 :
                "average duration for member 12 should be 25.0, was " + averageDurations.get(12);
        assert Math.abs(averageDurations.get(22) - 50.0) < 0.1 :
                "average duration for member 22 should be 50.0, was " + averageDurations.get(22);
        assert Math.abs(averageDurations.get(31) - 72.5) < 0.1 :
                "average duration for member 31 should be 72.5, was " + averageDurations.get(31);
        assertFalse(averageDurations.containsKey(4));
    }

    @Test
    public void testGetDuePayments() {
        System.out.println("Running testGetDuePayments");
        // Test get_due_payments function
        Membership testMembership = new Membership();
        testMembership.addMember(new Member(1, "John Doe", MembershipStatus.BRONZE));
        testMembership.addMember(new Member(2, "Alex C", MembershipStatus.SILVER));
        testMembership.addMember(new Member(3, "Marie C", MembershipStatus.GOLD));

        // Add workouts for members
        Map<Integer, List<Workout>> memberWorkouts = new HashMap<>();
        memberWorkouts.put(1, Arrays.asList(
                new Workout(1, 500, 700), new Workout(10, 300, 350), new Workout(12, 10, 20),
                new Workout(3, 50, 90), new Workout(6, 130, 150), new Workout(15, 900, 920)
        ));
        memberWorkouts.put(2, Arrays.asList(
                new Workout(13, 510, 540), new Workout(14, 600, 700), new Workout(2, 15, 35),
                new Workout(4, 100, 155), new Workout(18, 200, 225), new Workout(8, 1050, 1155)
        ));
        memberWorkouts.put(3, Arrays.asList(
                new Workout(5, 120, 135), new Workout(17, 140, 190), new Workout(9, 210, 255),
                new Workout(11, 400, 450), new Workout(16, 910, 940), new Workout(7, 1000, 1100)
        ));

        for (Map.Entry<Integer, List<Workout>> entry : memberWorkouts.entrySet()) {
            int memberId = entry.getKey();
            List<Workout> workoutList = entry.getValue();
            for (Workout workout : workoutList) {
                testMembership.addWorkout(memberId, workout);
            }
        }

        Map<Integer, Integer> duePayments = testMembership.getDuePayments();
        assert Math.abs(duePayments.get(1) - 50.0) < 0.1 :
                "due payment for member 1 should be 50.0, was " + duePayments.get(1);
        assert Math.abs(duePayments.get(2) - 32.0) < 0.1 :
                "due payment for member 2 should be 32.0, was " + duePayments.get(2);
        assert Math.abs(duePayments.get(3) - 6.0) < 0.1 :
                "due payment for member 3 should be 6.0, was " + duePayments.get(3);

        // Test member with no workouts
        testMembership.addMember(new Member(4, "Ron Burgundy", MembershipStatus.SILVER));
        Map<Integer, Integer> duePayments2 = testMembership.getDuePayments();
        assert Math.abs(duePayments2.get(4) - 0.0) < 0.1 :
                "due payment for member 4 should be 0.0, was " + duePayments2.get(4);
    }
}
