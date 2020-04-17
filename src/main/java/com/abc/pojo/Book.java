package com.abc.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="Book对象", description="")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("cid")
    private Integer cid;

    @ApiModelProperty(value = "0:未上架；1：已上架；2：已售卖")
    @TableField(value = "book_type",fill = FieldFill.INSERT)
    private Integer bookType;

    @TableField("price")
    private Double price;

    @TableField("original_price")
    private Double originalPrice;

    @TableField("uid")
    private Integer uid;

    @TableField("author")
    private String author;

    @TableField("press")
    private String press;

    @TableField("version")
    private String version;

    @TableField("degree")
    private Double degree;

    @TableField("publish_date")
    private String publishDate;

    @TableField("description")
    private String description;

    @TableField(value = "date",fill = FieldFill.INSERT)
    private LocalDateTime date;

    @TableField(value = "status",fill = FieldFill.INSERT)
    @TableLogic
    private Integer status;


}
