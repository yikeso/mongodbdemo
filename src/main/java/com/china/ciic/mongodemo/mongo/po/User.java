package com.china.ciic.mongodemo.mongo.po;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * User实体
 */
@Document
public class User extends IdEntity implements UserDetails {

    /**
     * 用户名
     * 为用户名字段建立唯一索引
     */
    @Indexed(unique = true)
    String name;
    /**
     * 密码
     */
    String password;

    /**
     * 盐值
     */
    String salt;
    /**
     * 邮箱
     */
    String email;

    /**
     * 权限
     */
    List<String> authorities = new ArrayList<String>();

    /**
     * 角色
     */
    List<String> roles = new ArrayList<String>();

    /**
     * 账号过期时间
     */
    Long accountExpiredTime = Long.MAX_VALUE;

    /**
     * 凭据过期时间
     */
    Long credentialsExpiredTime = Long.MAX_VALUE;

    public User() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAccountExpiredTime() {
        return accountExpiredTime;
    }

    public void setAccountExpiredTime(Long accountExpiredTime) {
        this.accountExpiredTime = accountExpiredTime;
    }

    public Long getCredentialsExpiredTime() {
        return credentialsExpiredTime;
    }

    public void setCredentialsExpiredTime(Long credentialsExpiredTime) {
        this.credentialsExpiredTime = credentialsExpiredTime;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = null;
        if (CollectionUtils.isEmpty(authorities) && CollectionUtils.isEmpty(roles)) {
            return grantedAuthorities;
        }
        int i,size;
        if(!CollectionUtils.isEmpty(authorities)){
            for (i = 0,size = authorities.size();i < size;i++){
                grantedAuthorities.add(new UserGrantedAuthority(authorities.get(i)));
            }
        }
        if(!CollectionUtils.isEmpty(roles)){
            for (i = 0,size = roles.size();i < size;i++){
                grantedAuthorities.add(new UserGrantedAuthority(roles.get(i)));
            }
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return System.currentTimeMillis() < accountExpiredTime;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isCredentialsNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return System.currentTimeMillis() < credentialsExpiredTime;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 用户权限
     */
    class UserGrantedAuthority implements GrantedAuthority {

        /**
         * 权限
         */
        String authority;

        public UserGrantedAuthority() {
        }

        public UserGrantedAuthority(String authority) {
            this.authority = authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("UserGrantedAuthority{");
            sb.append("authority='").append(authority).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
