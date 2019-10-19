package com.leetcode.algorithms;

public class Permutation {

    public static void nextPermutation(int[] arr) {
        int len = arr.length;
        // 从后向前找，找到第一组逆序的数，等价于从前向后找到最后一组正序
        int k = len - 2;
        while (k >= 0 && arr[k] >= arr[k + 1]) {
            k--;
        }
        // 如果k小于0，表示没有后续序列了，对整个进行翻转，形成最开始的序列
        if (k < 0) {
            reverse(arr, 0, len - 1);
            return;
        }
        // 找到大于arr[k]的最小的数
        int i = len - 1;
        while (i >= k + 1 && arr[i] <= arr[k]) {
            i--;
        }
        swap(arr, k, i);
        // 再对arr[k+1]~arr[n-1]进行翻转
        reverse(arr, k + 1, len - 1);
    }

    private static void reverse(int[] arr, int begin, int end) {
        while (begin < end) {
            swap(arr, begin, end);
            begin++;
            end--;
        }
    }

    private static void swap(int[] nums, int idx1, int idx2) {
        int tmp = nums[idx1];
        nums[idx1] = nums[idx2];
        nums[idx2] = tmp;
    }
}
