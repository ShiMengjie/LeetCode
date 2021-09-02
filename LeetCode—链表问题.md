# LeetCode—链表问题





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

