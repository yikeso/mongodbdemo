package com.china.ciic.mongodemo.services;

import com.china.ciic.mongodemo.mongo.po.User;
import com.china.ciic.mongodemo.mongo.repositories.UserRepository;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;

@Service
public class UserService implements UserDetailsService {

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    UserRepository userRepository;
    /**
     * 用户集合
     */
    static final String USER_CLLECTION = "user";
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByName(s);
    }

    /**
     * 根据id修改用户
     * @param user
     */
    public void updateById(User user){
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        updateSetField(update,user);
        mongoTemplate.updateFirst(query,update,USER_CLLECTION);
    }

    private void updateSetField(Update update, Object o) {
        //反射属性及父类属性
        for(Class oClass = o.getClass(),rootClass = Object.class;oClass != rootClass;oClass = oClass.getSuperclass()){
            reflectClass(update, o, oClass);
        }
    }

    private void reflectClass(Update update, Object o, Class oClass) {
        Field[] fields = oClass.getDeclaredFields();
        if (fields == null || fields.length < 1) {
            return;
        }
        try {
            Object value = null;
            Field field = null;
            String fieldName = null;
            for (int i = 0, size = fields.length; i < size; i++) {
                field = fields[i];
                fieldName = field.getName();
                if ("id".equals(fieldName)){
                    continue;
                }
                field.setAccessible(true);
                value = field.get(o);
                if(value == null){
                    continue;
                }
                update.set(fieldName,value);
            }
        } catch (IllegalAccessException e) {
            log.error("更新user反射更新字段失败",e);
        }
    }
}
