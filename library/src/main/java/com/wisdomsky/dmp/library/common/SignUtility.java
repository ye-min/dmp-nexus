package com.wisdomsky.dmp.library.common;

import org.apache.commons.codec.digest.DigestUtils;

public class SignUtility {
    public static String calculateSHA1(String data) {
        return DigestUtils.sha1Hex(data);
    }
}
