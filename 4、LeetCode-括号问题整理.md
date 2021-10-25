# LeetCode-括号问题整理

LeetCode 上与“括号”有关的问题整理。

## 问题列表

[20. 有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)

[22. 括号生成](https://leetcode-cn.com/problems/generate-parentheses/)

[32. 最长有效括号](https://leetcode-cn.com/problems/longest-valid-parentheses/)

[301. 删除无效的括号](https://leetcode-cn.com/problems/remove-invalid-parentheses/)



[678. 有效的括号字符串](https://leetcode-cn.com/problems/valid-parenthesis-string/)

[856. 括号的分数](https://leetcode-cn.com/problems/score-of-parentheses/)

[921. 使括号有效的最少添加](https://leetcode-cn.com/problems/minimum-add-to-make-parentheses-valid/)

[1111. 有效括号的嵌套深度](https://leetcode-cn.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/)

[1249. 移除无效的括号](https://leetcode-cn.com/problems/minimum-remove-to-make-valid-parentheses/)

[1614. 括号的最大嵌套深度](https://leetcode-cn.com/problems/maximum-nesting-depth-of-the-parentheses/)

## [20. 有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)

### 问题描述

给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
有效字符串需满足：

- 左括号必须用相同类型的右括号闭合。
- 左括号必须以正确的顺序闭合。

**示例 1：**

```tex
输入：s = "()"
输出：true
```

### 解题思路

根据问题要求，有效的括号具备以下特征：

- 每个左括号都有一个右括号与之匹配
- 匹配的括号之间，不会有其他的半括号

我们可以遍历字符串，使用一个栈来保存左括号，每匹配完一对有效括号，就从栈中消除该左括号，这样当遇到右括号时，可以直接判断该右括号是否与栈顶元素匹配。过程如下：

- 如果是左括号，就 push 到栈中；
- 如果是右括号，旧 pop 出栈顶字符，判断与当前右括号是否能够组成闭合括号；

当字符串遍历完成，栈不为空，说明字符串不是有效括号。

### 代码实现

```java
public class Solution {

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // 是左括号就 push 进栈中
            if (isLeft(ch)) {
                stack.push(ch);
            } else {
                // 特殊情况：栈中没有前置字符，说明只有右括号没有左括号
                if (stack.isEmpty()) {
                    return false;
                }
                // 判断是否是闭合的括号
                if (!isClosed(stack.pop(), ch)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private boolean isLeft(char ch) {
        return ch == '(' || ch == '[' || ch == '{';
    }

    private boolean isClosed(char left, char right) {
        return (left == '(' && right == ')')
                || (left == '[' && right == ']')
                || (left == '{' && right == '}');
    }
}
```

## [22. 括号生成](https://leetcode-cn.com/problems/generate-parentheses/)

> 相同问题：[剑指 Offer II 085. 生成匹配的括号](https://leetcode-cn.com/problems/IDBivT/)、[面试题 08.09. 括号](https://leetcode-cn.com/problems/bracket-lcci/)

### 问题描述

数字 `n` 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 **有效的** 括号组合。

有效括号组合需满足：左括号必须以正确的顺序闭合。

**示例 1：**

```
输入：n = 3
输出：["((()))","(()())","(())()","()(())","()()()"]
```

**示例 2：**

```
输入：n = 1
输出：["()"]
```

### 解题思路

#### 暴力计算

根据下面的定义，长的有效括号组合可以由较短的有效括号获得，如下图所示：

![image.png](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/a2f4d6904ef31e4686260cc359b10b19414b5a0192e13537e99124cf0952b96a-image.png)

<center>来自题解：https://leetcode-cn.com/problems/generate-parentheses/solution/python-dong-tai-gui-hua-yi-chong-jian-dan-de-si-lu/</center>

所以，`n` 对括号组成的有效括号组合可以由 `n-1`对括号组成的有效括号组合得到，依次类推，直到 `n == 1`。

- `n == 1`，有效括号组合为：“()"；
- `n == 2` 的有效括号组合，通过在 `n == 1` 的组合中元素的不同位置插入 “()” 得到，比如在前面插入、中间插入、后面插入，分别得到：“()()"、"(())"、"()()"；

使用这个方法，可以暴力求解出所有有效括号组合。

#### 回溯

这是在给定的元素集合：左括号“(”、右括号")"，找到满足条件的所有组合，可以使用回溯求解“寻找组合”类的问题。

“回溯算法”的求解步骤如下：

**1、选择列表**

可供选择的元素只有“左括号、右括号”，所以选择列表是："(",")"。

**2、路径**

记录已经选择的左右括号，要求路径中的符号能够组成有效的括号对。

**3、结束条件**

* 当路径中“左括号”和“右括号”的个数都等于 `n` 时，此时无法添加更多的符号，不需要再向下搜索。
* 从 [20. 有效的括号](https://leetcode-cn.com/problems/valid-parentheses/) 中我们知道，当路径中“右括号”个数大于“左括号”时，路径中的符号无法组成有效的括号对，不需要再继续搜索。

**4、选择**

什么条件下把“左括号”和“右括号”添加进路径中？

* 如果路径中“左括号”个数小于 `n`，把“左括号”添加进路径；
* 如果路径中“右括号”个数小于“左括号”个数，且“右括号”个数小于 `n`，把“右括号”添加进路径；

**5、空间状态树**

定义函数 $backtrack(res,path,left,right)$，当前路径`path` 中已经有 `left`个“左括号” 和 `right`个“右括号”，继续搜索有效括号组合。

下图显示了 `n=2` 时构建的“空间状态树”：

![image-20211012174224994](https://cdn.jsdelivr.net/gh/shimengjie/image-repo/img/image-20211012174224994.png)

### 代码实现

#### 暴力计算

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        res.add("()");

        for (int i = 2; i <= n; i++) {
            Set<String> tmpSet = new HashSet<>();
            List<String> tmpList = new LinkedList<>();
            
            for (String param : res) {
                for (int j = 0; j < param.length(); j++) {
                    String tmp = new StringBuilder(param).insert(j, "()").toString();
                    if (tmpSet.add(tmp)) {
                        tmpList.add(tmp);
                    }
                }
            }
            res = tmpList;
        }
        return res;
    }
}
```

#### 回溯

```JAVA
class Solution {
    int n;

    public List<String> generateParenthesis(int n) {
        this.n = n;
        List<String> res = new LinkedList<>();
        backtrack(res, new StringBuilder(2 * n), 0, 0);
        return res;
    }

    private void backtrack(List<String> res, StringBuilder path, int left, int right) {
        if (left == n && right == n) {
            res.add(path.toString());
            return;
        }

        if (right > left) {
            return;
        }

        if (left < n) {
            path.append('(');
            // 递归
            backtrack(res, path, left + 1, right);
            // 撤销选择
            path.deleteCharAt(path.length() - 1);
        }
        if (right < left && right < n) {
            path.append(')');
            // 递归
            backtrack(res, path, left, right + 1);
            // 撤销选择
            path.deleteCharAt(path.length() - 1);
        }
    }
}
```

## [32. 最长有效括号](https://leetcode-cn.com/problems/longest-valid-parentheses/)

### 问题描述

给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。

**示例 1：**

```
输入：s = "(()"
输出：2
解释：最长有效括号子串是 "()"
```

### 解题思路

要找到的是“格式正确且连续”的括号字符串最大长度，最直觉的做法是使用动态规划来求解，困难之处在于：怎么确定状态和转移方程。



动态规划中的”状态“是什么？有以下两种理解。

#### 第一种

”状态“是：到字符串下标 $i$ 处为止，最大的连续括号子串长度；

基于这个状态，定义数组 $dp[n]$，$dp[i]$ 表示：在字符串 $s[0,...,i]$ 中，最大的连续括号子串长度；

状态转移方程：

- 如果 $s[i] = ')'$，且 $s[i-1] = '('$，那么 $dp[i] = dp[i-1] + 2$；
- 如果 $s[i] = ')'$，但 $s[i-1] = ')'$，需要找到与 $s[i]$ 匹配的字符下标，因为 $dp[i-1]$ 表示的是 “$s[0,...,i-1]$ 中最大的括号子串长度”，无法确定有效括号子串的下标范围，所以无法找到与 $s[i]$ 匹配的字符下标，也就无法写出转移方程；

因此，这种“状态“是无效的。



#### 第二种

“状态”是：以字符串下标 $i$ 处结尾的，最大的连续括号子串长度；

基于这个状态，定义数组 $dp[n]$，$dp[i]$ 表示：以字符 $s[i]$ 结尾的最大括号子串长度，子串 $s[i - dp[i] + 1,...,i]$ 是一个有效的括号子串；

状态转移方程：

- 如果 $s[i] = ')'$，且 $s[i-1] = '('$，那么 $dp[i] = dp[i-1] + 2$；
- 如果 $s[i] = ')'$，但 $s[i-1] = ')'$，那么 $s[i - dp[i-1],..., i-1]$ 是一个有效或无效的子串，$s[i]$ 匹配的字符位置下标为：$preIdx = i - 1 - dp[i-1] - 1$；如果 $s[preIdx] = '('$，那么 $dp[i] = dp[preIdx - 1] + dp[i - 1] + 2$；

因此，这种情况下的“状态”定义是有效的。



从上面的过程可以看出，关键是要找到与 $s[i]$ 匹配的字符下标。

### 代码实现

```java
public class Solution {
    
    public int longestValidParentheses(String s) {
        int res = 0, preIdx = 0;
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    if (i >= 2) {
                        dp[i] = dp[i - 2] + 2;
                    } else {
                        dp[i] = 2;
                    }
                } else {
                    // preIdx : s[i] 对应的下标
                    preIdx = i - dp[i - 1] - 1;
                    // s[preIdx] 存在且是左括号
                    if (preIdx >= 0 && s.charAt(preIdx) == '(') {
                        // preIdx - 1 存在
                        if (preIdx - 1 >= 0) {
                            dp[i] = dp[preIdx - 1] + dp[i - 1] + 2;
                        } else {
                            dp[i] = dp[i - 1] + 2;
                        }
                    }
                }
                res = Math.max(res, dp[i]);
            }
        }
        return res;
    }
}
```

## [301. 删除无效的括号](https://leetcode-cn.com/problems/remove-invalid-parentheses/)

### 问题描述

给你一个由若干括号和字母组成的字符串 `s` ，删除最小数量的无效括号，使得输入的字符串有效。

返回所有可能的结果。答案可以按 **任意顺序** 返回。

**示例 1：**

```
输入：s = "()())()"
输出：["(())()","()()()"]
```

### 解题思路

