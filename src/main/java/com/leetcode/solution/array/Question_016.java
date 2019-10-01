package com.leetcode.solution.array;

import java.util.Arrays;

public class Question_016 {

    public int threeSumClosest(int[] nums, int target) {
        int result = 0;
        if (nums == null || nums.length == 0) {
            return result;
        }
        if (nums.length <= 3) {
            return nums[0] + nums[1] + nums[2];
        }
        Arrays.sort(nums);
        result = nums[0] + nums[1] + nums[nums.length - 1]; // 随机选择3个数求和作为初始值
        int i = 0;
        int j;
        int k;
        int sum;
        for (; i < nums.length - 2; i++) {
            if (i > 0 && nums[i - 1] == nums[i])
                continue;
            j = i + 1;
            k = nums.length - 1;
            while (j < k) {
                sum = nums[i] + nums[j] + nums[k];
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    j++;
                } else {
                    k--;
                }
                // 更新result的值
                result = Math.abs(sum - target) < Math.abs(target - result) ? sum : result;
            }
        }
        return result;
    }

}
