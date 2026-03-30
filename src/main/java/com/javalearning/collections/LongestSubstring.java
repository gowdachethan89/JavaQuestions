package com.javalearning.collections;

import java.util.HashMap;

public class LongestSubstring {
    public static void main(String[] args) {
        String s = "abcdeabcbb";
        LongestSubstring solution = new LongestSubstring();
        int result = solution.lengthOfLongestSubstring(s);
        System.out.println("Length of the longest substring without repeating characters: " + result);
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        // HashMap to store the last position of each character seen
        HashMap<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);

            // If we have seen the character before and it is inside the current window
            if (map.containsKey(currentChar) && map.get(currentChar) >= left) {
                left = map.get(currentChar) + 1;
            }
            // Update the last seen position of the current character
            map.put(currentChar, right);

            // Calculate the max length of the substring
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}