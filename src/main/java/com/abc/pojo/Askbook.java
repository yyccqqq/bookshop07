package com.abc.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Askbook对象", description="")
public class Askbook implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("image")
    private String image;

    @TableField("name")
    private String name;

    @TableField("author")
    private String author;

    @TableField("press")
    private String press;

    @TableField("description")
    private String description;

    @TableField("user_id")
    private Integer userId;

    @TableField("back")
    private String back;

    @TableField(value = "time",fill = FieldFill.INSERT)
    private LocalDateTime time;

    @TableField(value = "status",fill = FieldFill.INSERT)
    @TableLogic
    private Integer status;


}
