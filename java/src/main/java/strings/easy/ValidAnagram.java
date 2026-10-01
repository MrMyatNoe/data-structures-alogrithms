package strings.easy;

import java.util.HashMap;

public class ValidAnagram {

    // Time: O(n)  Space: O(1)  (n = s.length(); fixed 26-slot count array, lowercase a-z only;
    // toCharArray() adds a temporary O(n) copy, so charAt in an index loop would be strictly O(1))
    public static boolean solve(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }

        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
            System.out.printf("first word:s=%s t=%s c=%c count=%s%n", s, t, c, java.util.Arrays.toString(count));
        } 
        for (char c : t.toCharArray())  { 
            count[c - 'a']--;
            System.out.printf("second word: s=%s t=%s c=%c count=%s%n", s, t, c, java.util.Arrays.toString(count));
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                System.out.printf("MISMATCH letter=%c diff=%d%n", (char) ('a' + i), count[i]);
                return false;
            }
        }
        return true;    
    }
}
