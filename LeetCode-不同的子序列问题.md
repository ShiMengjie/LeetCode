# LeetCode-不同的子序列问题

## [115. 不同的子序列](https://leetcode-cn.com/problems/distinct-subsequences/)



## [940. 不同的子序列 II](https://leetcode-cn.com/problems/distinct-subsequences-ii/)



```java
public int distinctSubseqII(String s) {
    int N = s.length(), MOD = 1_000_000_007;
    int[] dp = new int[N + 1], last = new int[26];
    dp[0] = 1;
    Arrays.fill(last, -1);

    for (int i = 0; i < N; i++) {
        dp[i + 1] = dp[i] * 2 % MOD;

        int idx = s.charAt(i) - 'a';
        if (last[idx] >= 0) {
            dp[i + 1] -= dp[last[idx]];
        }
        dp[i + 1] %= MOD;
        last[idx] = i;
    }
    // 减去 空字符串个数
    dp[N]--;
    if(dp[N] < 0) {
        dp[N] += MOD;
    }
    return dp[N];
}
```





## [5857. 不同的好子序列数目](https://leetcode-cn.com/problems/number-of-unique-good-subsequences/)

```java
public int numberOfUniqueGoodSubsequences(String binary) {
    int mod = (int) 1e9 + 7;
    long zero = 0, one = 0, ans = 0;
    // 以前是否出现过 0
    boolean hasZero = false;
    for (char ch : binary.toCharArray()) {
        // 以 0 结尾的不同的好子序列数目
        if (ch == '0') {
            zero = ans;
            if (!hasZero) {
                ++zero;
                hasZero = true;
            }
            zero %= mod;
        } else {
            // 以 1 结尾的不同的好子序列数目
            one = ans;
            if (!hasZero) ++one;
            one %= mod;
        }
        // 所有的不同的好子序列数目
        ans = one + zero;
        ans %= mod;
    }
    return (int) ans;
}
```

