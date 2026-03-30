package com.javalearning.collections;

import java.util.Arrays;

public class FindMedian {

    public static void main(String[] args) {
        int[] nums1 = {1, 3};
        int[] nums2 = {2};

        FindMedian solution = new FindMedian();
        double median = solution.findMedianSortedArrays(nums1, nums2);
        System.out.println("Median: " + median);
    }
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merged = new int[nums1.length + nums2.length];
        int i = 0, j = 0, k = 0;

//        while(i < nums1.length && j < nums2.length){
//            if(nums1[i] < nums2[j]){
//                merged[k++] = nums1[i++];
//            } else {
//                merged[k++] = nums2[j++];
//            }
//        }

        while(i < nums1.length) merged[k++] = nums1[i++];
        while(j < nums2.length) merged[k++] = nums2[j++];
        Arrays.sort(merged);
        return (merged.length % 2 == 0) ? (merged[merged.length / 2 - 1] + merged[merged.length / 2]) / 2.0 : merged[merged.length / 2];
    }
}
