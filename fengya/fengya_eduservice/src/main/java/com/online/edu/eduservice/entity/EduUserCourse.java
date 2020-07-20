package com.online.edu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhouzhou
 * @since 2020-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduUserCourse对象", description="")
public class EduUserCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.ID_WORKER_STR)
    private String uid;

    private String cid;

    private String orderid;

    private int status;




}
