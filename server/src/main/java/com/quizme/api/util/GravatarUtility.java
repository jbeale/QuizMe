package com.quizme.api.util;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by jbeale on 3/5/15.
 */
public class GravatarUtility {

    public static String getGravatarURI(String email, boolean isThumbnail) {
        Hash emailHash = new Md5Hash(email.trim().toLowerCase());
        StringBuilder sb = new StringBuilder();
        sb.append("http://www.gravatar.com/avatar/");
        sb.append(emailHash.toHex());
        sb.append("?d=mm");
        if (isThumbnail) sb.append("&s=64");
        return sb.toString();
    }
}
