package org.srmzhk;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexHelper {
    private final Pattern integerPattern = Pattern.compile("^[-+]?\\d+$");
    private final Pattern floatsPattern = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$");
    private final Pattern txtPattern = Pattern.compile(".*\\.txt$");

    public boolean isTxt(String s) {
        return match(txtPattern, s);
    }

    public boolean isInteger(String s) {
        return match(integerPattern, s.trim());
    }

    public boolean isFloat(String s) {
        return match(floatsPattern, s.trim());
    }

    private static boolean match(Pattern pattern, String s) {
        if (s == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
