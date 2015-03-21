package com.quizme.api.util;

import java.util.StringTokenizer;

/**
 * Created by jbeale on 3/7/15.
 */
public class WordUtils {

    public static String getFirstNWords(String str, int n) {
        StringTokenizer st = new StringTokenizer(str);
        String retStr = "";
        for (int i = 0; i < n && st.hasMoreTokens(); i++) {
            retStr += st.nextToken()+" ";
        }
        if (st.hasMoreTokens()) {
            retStr += "...";
        }
        return retStr;
    }
}
