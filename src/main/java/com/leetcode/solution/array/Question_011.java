package com.leetcode.solution.array;

public class Question_011 {

    public static int maxArea(int[] nums) {
        int left = 0, right = nums.length - 1;
        int height = Math.min(nums[left], nums[right]);
        int area = (right - left) * height;
        while (left < right) {
            while (nums[left] <= height && left < nums.length - 1) {
                left++;
            }
            while (nums[right] <= height && right > 0) {
                right--;
            }
            height = Math.min(nums[left], nums[right]);
            area = Math.max(area, (right - left) * height);
        }
        return area;
    }
}
