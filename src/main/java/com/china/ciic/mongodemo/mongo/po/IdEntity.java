package com.china.ciic.mongodemo.mongo.po;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * id
 */
public class IdEntity {

    @Id
    String id;
    /**
     * 创建时间
     */
    Date createTime;
    /**
     * 修改时间
     */
    Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.updateTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
