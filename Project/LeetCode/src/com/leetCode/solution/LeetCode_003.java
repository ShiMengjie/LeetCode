package com.leetCode.solution;

import java.util.HashMap;
import java.util.Map;

public class LeetCode_003 {

    public static int lengthOfLongestSubstring(String s) {
        int ans = 0;
        int i = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int j = 0; j < s.length(); j++) {
            char ch = s.charAt(j);
            if (map.containsKey(ch)) {
                i = Math.max(map.get(ch), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(ch, j + 1);
        }
        return ans;
    }
}
