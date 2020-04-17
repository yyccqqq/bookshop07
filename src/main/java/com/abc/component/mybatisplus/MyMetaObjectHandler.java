package com.abc.component.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "date", LocalDateTime.class,LocalDateTime.now());
        this.strictInsertFill(metaObject, "status", Integer.class, 0);
        this.strictInsertFill(metaObject, "time",LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "type", Integer.class, 0);
        this.strictInsertFill(metaObject, "bookType", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
