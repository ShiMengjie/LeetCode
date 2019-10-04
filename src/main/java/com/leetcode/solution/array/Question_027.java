package com.leetcode.solution.array;

public class Question_027 {
    public int removeElement(int[] nums, int val) {
        int i = 0;
        int j = 1;
        while (i < nums.length) {
            if (nums[j] != val) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }
}
