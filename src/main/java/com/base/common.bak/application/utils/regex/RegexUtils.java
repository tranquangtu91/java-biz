package com.base.common.application.utils.regex;

import java.util.regex.Pattern;

public class RegexUtils {

    public static Pattern globToRegExp(String glob) {
        String _glob = glob.replaceAll("\\/\\*\\*$", "**");
        StringBuilder out = new StringBuilder("^");
        for (int i = 0; i < _glob.length(); ++i) {
            final char c = _glob.charAt(i);
            switch (c) {
                case '*':
                    out.append(".*");
                    break;
                case '?':
                    out.append('.');
                    break;
                case '.':
                    out.append("\\.");
                    break;
                case '\\':
                    out.append("\\\\");
                    break;
                default:
                    out.append(c);
            }
        }
        out.append('$');

        String regex = out.toString();
        Pattern pattern = Pattern.compile(regex);
        return pattern;
    }
}
