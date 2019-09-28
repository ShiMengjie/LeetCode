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

    /**
     * 第二种解法：使用二分查找确定第3个数，效率不如上一个方法
     */
    public List<List<Integer>> threeSum2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        Arrays.sort(nums); // 升序排列

        int target;
        int i; // 第一个数的下标
        int j; // 第二个数B的下标
        int k; // 第三个数C的下标
        for (i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            j = i + 1;
            while (j < nums.length) {
                int[] subNums = new int[nums.length - j - 1];
                target = -(nums[i] + nums[j]);
                k = binarySearch(nums, j + 1, nums.length, target);
                if (k >j) {
                    List<Integer> list = new ArrayList<>(3);
                    list.add(nums[i]);
                    list.add(nums[j]);
                    list.add(nums[k]);
                    result.add(list);
                }
                j++;
                while (j < nums.length && nums[j] == nums[j - 1]) {
                    j++;
                }
            }
        }
        return result;
    }

    // 二分查找
    private int binarySearch(int[] a, int i, int j, int key) {
        int low = i;
        int high = j - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = a[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }
}
