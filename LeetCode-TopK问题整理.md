# LeetCode-TopK问题整理



https://mp.weixin.qq.com/s/M_xtSEtj69f_xmleRXgFqQ

## [链表中倒数第k个节点](https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/)

相同的问题：[返回倒数第 k 个节点](https://leetcode-cn.com/problems/kth-node-from-end-of-list-lcci/)

两次循环：

```java
class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode node = head;
        int len = 0;
        while (node.next != null) {
            len++;
            node = node.next;
        }
        int target = len - k + 1;
        node = head;
        for (int i = 0; i < target; i++) {
            node = node.next;
        }
        return node;
    }
}
```

一次循环 + 快慢指针：

最后一个下标为 n-1，倒数第k个节点下标为：n - 1 - k + 1 = n - k。

当 node 指向最后一个节点时，slow 应该指向 n - k，两者差值为 k - 1。

```java
class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode node = head, slow = head;
        int idx = 0;
        while (node.next != null) {
            idx++;
            node = node.next;
            if (idx >= k) {
                slow = slow.next;
            }
        }
        return slow;
    }
}
```

## [19. 删除链表的倒数第 N 个结点](https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/)

相同的问题：[删除链表的倒数第 n 个结点](https://leetcode-cn.com/problems/SLwz0R/)





## [215. 数组中的第K个最大元素](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)

如果输入是整个数组，那么用一个效率比较高的排序算法，然后取第K个元素；如果数组中的元素是依次给的，那么用堆排序更高效，每新增一个元素的复杂度是 log n。

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}
```

