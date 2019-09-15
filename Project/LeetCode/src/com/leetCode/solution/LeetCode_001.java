package com.leetCode.solution;

import java.util.HashMap;
import java.util.Map;

public class LeetCode_001 {

    public static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<>();
        int number = 0;
        for (int i = 0; i < nums.length; i++) {
            number = target - nums[i];
            if (map.containsKey(number) && map.get(number) != i) {
                return new int[]{map.get(number), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }
}
