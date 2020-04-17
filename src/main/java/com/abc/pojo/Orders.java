package com.abc.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yan
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Orders对象", description="")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("order_id")
    private String orderId;

    @TableField("total_price")
    private Double totalPrice;

    @TableField("buyer_id")
    private Integer buyerId;

    @TableField("seller_id")
    private Integer sellerId;

    @ApiModelProperty(value = "0：未付款；1：待发货；2：已发货；3：交易成功；4：交易取消")
    @TableField(value = "type",fill = FieldFill.INSERT)
    private Integer type;

    @TableField(value = "date",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @TableField(value = "status",fill = FieldFill.INSERT)
    @TableLogic
    private Integer status;


}
