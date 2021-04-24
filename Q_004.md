# 4. Median of Two Sorted Arrays

> Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, return **the median** of the two sorted arrays.
>
> The overall run time complexity should be `O(log (m+n))`.

**Example：**

```java
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.
```

## 解法1

在一个有序数组中找中位数，只需要找到中间坐标位置上的元素，因此可以把题目分解成两步：

1、把两个有序数组合并成一个有序数组

2、从合并后的有序数组中找到中位数

### 代码实现

```java
/**
 * 在两个有序数组中，寻找中位数
 *
 * @param nums1 有序数组1
 * @param nums2 有序数组2
 * @return 中位数
 */
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int[] l = mergeArray(nums1, nums2);
    if (l.length % 2 == 0) {
        return (l[result.length / 2] + l[l.length / 2 - 1]) / 2.0;
    } else {
        return l[l.length / 2];
    }
}

/**
 * 合并两个有序数组为一个有序数组
 *
 * @param nums1 有序数组1
 * @param nums2 有序数组2
 * @return 合并后的有序数组
 */
private int[] mergeArray(int[] nums1, int[] nums2) {
    int len1 = nums1.length;
    int len2 = nums2.length;

    int i = 0;
    int j = 0;
    int k = 0;
    int[] result = new int[len1 + len2];
    while (i < len1 && j < len2) {
        if (nums1[i] < nums2[j]) {
            result[k] = nums1[i];
            i++;
        } else {
            result[k] = nums2[j];
            j++;
        }
        k++;
    }
    while (i < len1) {
        result[k++] = nums1[i++];
    }
    while (j < len2) {
        result[k++] = nums2[j++];
    }
    return result;
}
```

需要遍历两个数组复制到另一个数组中，时间复杂度和空间复杂度都是：$O(m + n)$。

## 解法2

假设合并后的有序数组长度为 L（L= m + n），那么中位数就是找第K个数：

* 如果 (m + n) 是偶数，K = L/2、K = L/2 + 1，中位数就是这两个数的均值
* 如果 (m + n) 是奇数，K = L/2

问题就变成了：在两个有序数组中，找到第K小的数。问题成两步：

1、遍历数组A和数组B，比较两个数组的元素值，较小元素下标向后移，记录已经遍历过的元素个数k

2、当遍历的个数等于K，得到中位数

### 代码实现

```java
/**
 * 在两个有序数组中，寻找中位数
 *
 * @param nums1 有序数组1
 * @param nums2 有序数组2
 * @return 中位数
 */
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int l = nums1.length + nums2.length;
    if (l % 2 == 1) {
        int k = l / 2; // k是下标，k+1才是要找的数
        return getKthElement(nums1, nums2, k + 1);
    } else {
        int k1 = l / 2, k2 = l / 2 - 1;
        return (getKthElement(nums1, nums2, k1 + 1) + getKthElement(nums1, nums2, k2 + 1)) / 2.0;
    }
}

/**
 * 在两个有序数组中，寻找第K个数
 *
 * @param nums1 有序数组1
 * @param nums2 有序数组2
 * @param k     第K个数，不是下标
 * @return 返回第K个数
 */
private double getKthElement(int[] nums1, int[] nums2, int k) {
    int length1 = nums1.length, length2 = nums2.length;
    int index1 = 0, index2 = 0;
    // 已经遍历的元素个数
    int l = 0;
    // 目标元素值
    int targetNum = 0;
    while (index1 < nums1.length && index2 < nums2.length) {
        if (nums1[index1] < nums2[index2]) {
            index1++;
            l++;
            if (l == k) {
                targetNum = nums1[index1 - 1];
            }
        } else {
            index2++;
            l++;
            if (l == k) {
                targetNum = nums2[index2 - 1];
            }
        }
    }
    while (index1 < nums1.length) {
        index1++;
        l++;
        if (l == k) {
            targetNum = nums1[index1 - 1];
        }
    }
    while (index2 < nums2.length) {
        index2++;
        l++;
        if (l == k) {
            targetNum = nums2[index2 - 1];
        }
    }
    return targetNum;
}
```

只进行了一次遍历，且没有使用额外的空间，空间复杂度是：$O(1)$，时间复杂度：$O(m+n)$。

## 解法3

怎么降低时间复杂度，达到题目要求的$log(m+n)$？

前面两种方法本质上是相同的，都是在合并后的有序数组中寻找中位数，没有排序算法可以在$log(m+n)$时间复杂度内把两个数组合并成一个有序数组，所以“解法1“和”解法2”的思路已经行不通了。

常见的时间复杂度是$log(m+n)$的算法主要有：二叉树、二分法，前者需要先建立一棵树，复杂度是$O(n log n)$，那么只有使用二分法。

**怎么在这里使用二分法？突破点在哪？**

在“解法1“和”解法2”中，每次比较两个数组中的元素，都只把下标向后移动一位，只能排除一个元素。如果每次数值比较后能够排除多个元素，甚至是成比例地排除元素个数，就能降低时间复杂度。

**下面是官方题解的思路：**

已知我们要排除掉 $K-1$个数找到第$K$个数，如果每次排除的元素个数是$\frac{K}{2}$、$\frac{K}{4}$、$\frac{K}{8}$...就能达到题目要求的时间复杂度$log(m+n)$。

因为数据A和B都是有序的，所以比较$A[\frac{K}{2}-1]$和$B[\frac{K}{2}-1]$的大小，如果$A[\frac{K}{2}-1] \le B[\frac{K}{2}-1]$ ，就有下面的关系：

$$A[0,...,\frac{K}{2}-2] \le A[\frac{K}{2}-1] \le B[\frac{K}{2}-1]$$

如果$B[0,...,\frac{K}{2}-2]$比也小于$A[\frac{K}{2}-1]$，那么比$A[\frac{K}{2}-1]$小的数最多只有$K-2$个，$A[\frac{K}{2}-1]$就不是第$K$个数，可以排除掉$A[0,...,\frac{K}{2}-1]$共的$\frac{K}{2}$个元素。

可以看到，比较$A[\frac{K}{2}-1]$和$B[\frac{K}{2}-1]$之后，可以排除掉$\frac{K}{2}$个不可能是第$K$个元素的数，查找范围缩小了一半。我们只需要在排除后的新数组上继续进行二分查找，并根据我们排除的个数，减少$K$的值。

有以下三种特殊情况：

* 如果$A[\frac{K}{2}-1]$和$B[\frac{K}{2}-1]$越界，那么可以选取对应数组中的最后一个元素，并根据实际的值减少$K$的值，而不是直接减去$\frac{K}{2}$
* 如果一个数组为空，说明该数组中所有元素都被遍历排除了，我们可以直接返回另一个数组中第$K$个元素
* 如果$K==1$，返回两个数组当前元素的最小值

### 代码实现

```java
/**
 * 在两个有序数组中，寻找中位数
 *
 * @param nums1 有序数组1
 * @param nums2 有序数组2
 * @return 中位数
 */
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int l = nums1.length + nums2.length;
    if (l % 2 == 1) {
        int k = l / 2; // k是下标，k+1才是要找的数
        return getKthElement(nums1, nums2, k + 1);
    } else {
        int k1 = l / 2, k2 = l / 2 - 1;
        return (getKthElement(nums1, nums2, k1 + 1) + getKthElement(nums1, nums2, k2 + 1)) / 2.0;
    }
}

/**
 * 在两个有序数组中，寻找第K个数
 *
 * @param nums1 有序数组1
 * @param nums2 有序数组2
 * @param k     第K个数，不是下标
 * @return 返回第K个数
 */
public int getKthElement(int[] nums1, int[] nums2, int k) {
    int lenth1 = nums1.length, length2 = nums2.length;
    // 数组1和数组2当前开始的下标
    int index1 = 0, index2 = 0;

    while (true) {
        // 如果某个数组的起始下标已经等于数组的长度，那么说明该数组已经遍历完了，从另一个数组中寻找第k个数
        if (index1 == lenth1) {
            return nums2[index2 + k - 1];
        }
        if (index2 == length2) {
            return nums1[index1 + k - 1];
        }
        // 如果k值为1，表示只找第一个数，返回两个数组中的较小值
        if (k == 1) {
            return Math.min(nums1[index1], nums2[index2]);
        }
        // 一般情况，取出一半的k作为偏移量，分别比较两个数组对应的下标的值
        int half = k / 2;
        int newIndex1 = Math.min(index1 + half, lenth1) - 1;
        int newIndex2 = Math.min(index2 + half, length2) - 1;
        if (nums1[newIndex1] <= nums2[newIndex2]) {
            // K 减去 newIndex1 和 index1 之间的元素个数，得到下一个目标K
            k -= (newIndex1 - index1 + 1);
            // 更新起始下标为下一个数
            index1 = newIndex1 + 1;
        } else {
            k -= (newIndex2 - index2 + 1);
            index2 = newIndex2 + 1;
        }
    }
}
```

每一轮循环可以将查找范围减少一半，所以时间复杂度是：$O(log(n+n))$。