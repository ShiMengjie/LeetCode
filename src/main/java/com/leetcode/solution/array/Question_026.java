package com.leetcode.solution.array;

public class Question_026 {

    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }
        if (nums.length < 2) {
            return nums.length;
        }
        int i = 0;
        int j = 1;
        for (; j < nums.length; j++) {
            if (nums[j] == nums[j - 1]) {
                continue;
            }
			nums[++i] = nums[j];
        }
        return i + 1;
    }
}
