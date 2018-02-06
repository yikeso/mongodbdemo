package com.china.ciic.mongodemo.common;

import com.china.ciic.mongodemo.common.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

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
            pwd = ByteUtil.bytes2HexStr(second);
        }catch (NoSuchAlgorithmException e){
            log.error("密码明文加密错误",e);
        }catch (UnsupportedEncodingException e) {
            log.error("密码明文加密错误",e);
        }
        return pwd;
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
