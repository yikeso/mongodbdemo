package com.china.ciic.mongodemo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

    static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private static final Logger log = LoggerFactory.getLogger(MyPasswordEncoder.class);

    /**
     * 密码加密两次
     */
    @Override
    public String encode(CharSequence charSequence) {
        String pwd = charSequence.toString();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");  //MD5 算法
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] pwdBytes = pwd.getBytes("UTF-8");
            md5.update(pwdBytes);
            byte[] firt = md5.digest();
            byte[] second = new byte[firt.length + pwdBytes.length];
            int i = 0;
            for(;i < firt.length;i++){
                second[i] = firt[i];
            }
            int j = 0;
            for(;i < second.length;i++,j++){
                second[i] = pwdBytes[j];
            }
            sha256.update(second);
            second = sha256.digest();
            pwd = toHex(second);
        }catch (NoSuchAlgorithmException e){
            log.error("密码明文加密错误",e);
        }catch (UnsupportedEncodingException e) {
            log.error("密码明文加密错误",e);
        }
        return pwd;
    }

    /**
     * 将数组转换为16进制字符串
     * @param bytes
     * @return
     */
    static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    /**
     * 校对密码是否一致
     * @param charSequence
     * @param s
     * @return
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        s = s.toLowerCase();
        String str = charSequence.toString().toLowerCase();
        return s.endsWith(str);
    }
}
