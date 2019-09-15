package com.leetCode.solution;

public class LeetCode_002 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        int current = 0;
        ListNode result = new ListNode(0);
        ListNode pointer = result;

        while (l1 != null || l2 != null || current > 0) {
            if (l1 != null) {
                current += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                current += l2.val;
                l2 = l2.next;
            }
            result.val = current % 10;
            current /= 10;
            if (l1 != null || l2 != null || current > 0) {
                result.next = new ListNode(0);
                result = result.next;
            }
        }
        return pointer;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
