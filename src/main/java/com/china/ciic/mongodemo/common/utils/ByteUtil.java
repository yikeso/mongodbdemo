package com.china.ciic.mongodemo.common.utils;

/**
 * byte工具
 */
public class ByteUtil {

    static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    /**
     * 将数组转换为16进制字符串
     * @param bytes
     * @return
     */
    public static String bytes2HexStr(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}
