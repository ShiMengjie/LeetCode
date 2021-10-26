# LeetCode-下一个更大元素问题整理

## [496. 下一个更大元素 I](https://leetcode-cn.com/problems/next-greater-element-i/)

### 问题描述

给你两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。

请你找出 nums1 中每个元素在 nums2 中的下一个比其大的值。

nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。

**示例 1:**

```
输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
输出: [-1,3,-1]
解释:
    对于 num1 中的数字 4 ，你无法在第二个数组中找到下一个更大的数字，因此输出 -1 。
    对于 num1 中的数字 1 ，第二个数组中数字1右边的下一个较大数字是 3 。
    对于 num1 中的数字 2 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
```

### 解题思路

#### 方法1

最直接的方法是暴力求解：先遍历数组 nums1 的每个元素，然后在 nums2 中确定元素所在位置，再遍历 nums2 中后续位置的元素。

这样做每次都需要定位 nums1 中元素在 nums2 中的下标，我们可以使用一个 hashMap 记录 nums2 中每个元素的下标，减少定位的过程。

#### 方法2

我们需要知道的其实是，nums2 中每个元素之后，比它大的元素是多少。

我们可以从后向前遍历 nums2 的元素，用一个栈来保存已经遍历过的元素，如果栈顶元素比当前元素值小，就 pop 出栈，然后把当前元素 push 进栈。

再判断栈是否为空，如果不为空，栈顶元素就是比当前元素大第一个元素。

### 代码实现

#### 方法1

```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>(nums2.length);
        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i], i);
        }

        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            int idx = map.get(nums1[i]);
            if(idx == nums2.length - 1) {
                res[i] = -1;
            }else {
                for (int j = idx + 1; j < nums2.length; j++) {
                    if (nums2[j] > nums1[i]) {
                        res[i] = nums2[j];
                        break;
                    }
                    res[i] = -1;
                }
            }
        }
        return res;
    }
}
```

#### 方法2

```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>(nums2.length);
        Stack<Integer> stack = new Stack<>();

        for (int i = nums2.length - 1; i >= 0; i--) {
            int num = nums2[i];
            while (!stack.isEmpty() && num >= stack.peek()) {
                stack.pop();
            }
            // stack 为空，表示已经没有比 num 更大的数了
            map.put(num, stack.isEmpty() ? -1 : stack.peek());
            stack.push(num);
        }

        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; ++i) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }

}
```

## [503. 下一个更大元素 II](https://leetcode-cn.com/problems/next-greater-element-ii/)



## [556. 下一个更大元素 III](https://leetcode-cn.com/problems/next-greater-element-iii/)





