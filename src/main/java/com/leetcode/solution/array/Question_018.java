package com.leetcode.solution.array;

import java.util.*;

public class Question_018 {

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return result;
        }
        int len = nums.length;
        Arrays.sort(nums);
        for (int i = 0; i < len - 3; i++) {
            // 如果最小的4个数和大于目标数或者最大的4个数和小于目标数，结束运行
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target
                    || nums[len - 1] + nums[len - 2] + nums[len - 3] + nums[len - 4] < target) {
                break;
            }
            // 如果当前数与最大的3个数之和小于目标值，则跳过
            if (nums[i] + nums[len - 1] + nums[len - 2] + nums[len - 3] < target) {
                continue;
            }
            // 避免重复值
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 指定第一个数，计算第二个目标数
            int target2 = target - nums[i];
            for (int j = i + 1; j < len - 2; j++) {
                // 在第二层遍历中，同样要进行第一轮类似的判断
                if (nums[j] + nums[j + 1] + nums[j + 2] > target2
                        || nums[len - 1] + nums[len - 2] + nums[len - 3] < target2) {
                    break;
                }
                if (nums[j] + nums[len - 1] + nums[len - 2] < target2) {
                    continue;
                }
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // 指定第二个数，计算第三个目标数
                int target3 = target2 - nums[j];
                // 在剩下的数中，寻找和为target3的两个数
                int left = j + 1, right = len - 1;
                while (left < right) {
                    int sum = nums[left] + nums[right];
                    if (sum == target3) {
                        // 如果和等于目标3，那么把所有数都添加进列表中
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        // 避免left和right的数重复
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    } else if (sum < target3) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return result;
    }

}


