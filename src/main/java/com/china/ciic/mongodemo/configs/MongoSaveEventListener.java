package com.china.ciic.mongodemo.configs;

import com.china.ciic.mongodemo.common.utils.UuidUtil;
import com.china.ciic.mongodemo.mongo.po.IdEntity;
import com.china.ciic.mongodemo.mongo.repositories.UserRepository;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class MongoSaveEventListener extends AbstractMongoEventListener<IdEntity> {

    private static final DateTimeFormatter dtf = DateTimeFormatter.BASIC_ISO_DATE;
    private static final Logger log = LoggerFactory.getLogger(MongoSaveEventListener.class);

    /**
     * 在保存前如果没有id则生成一个id
     * @param event
     */
    @Override
    public void onBeforeSave(BeforeSaveEvent<IdEntity> event) {
        IdEntity entity = event.getSource();
        if(StringUtils.isEmpty(entity.getId())){
            entity.setId(generateID());
            event.getDBObject().put("_id",entity.getId());
        }
        log.debug("存入mongoDb的DBObject:{}",event.getDBObject());
    }

    /**
     * 生成id
     * id由年月日加uuid组成
     * @return
     */
    private String generateID(){
        return LocalDate.now().format(dtf) + UuidUtil.randomUuidString();
    }
}
