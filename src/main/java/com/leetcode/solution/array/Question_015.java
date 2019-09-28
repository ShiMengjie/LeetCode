package com.leetcode.solution.array;

import org.junit.Test;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Question_015 {
    @Test
    public void test() {

        int[] nums = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = threeSum(nums);
        System.out.printf("==========");

    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums); // 升序排列

        int target; // 第一个数A的相反数
        int j; // 第二个数B的下标
        int k; // 第三个数C的下标，k从后向前遍历，这样只需要一次遍历就可以完成对剩下所有数据的遍历
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            target = -nums[i];
            j = i + 1;
            k = nums.length - 1;
            while (j < k) {
                if (nums[j] + nums[k] == target) {
                    List<Integer> list = new ArrayList<>(3);
                    list.add(nums[i]);
                    list.add(nums[j]);
                    list.add(nums[k]);
                    result.add(list);
                    j++;
                    k--;
                    // 为了避免B和C的重复
                    while (j < k && nums[j] == nums[j - 1]) {
                        j++;
                    }
                    while (j < k && nums[k] == nums[k + 1]) {
                        k--;
                    }
                } else if (nums[j] + nums[k] < target) {
                    // 因为数组已经排好序了，如果两个数的和小于目标值，就增加j的值
                    j++;
                } else {
                    // 如果两个数的和大于目标值，就减小K的值
                    k--;
                }
            }
        }
        return result;
    }
}
