package com.vulcan.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity
 * @name: BaseEntity
 * @Date: 2024/4/12 下午1:56
 * @Description 基础实体类，提供所有实体共用的基本属性和字段
 */
@Data
@MappedSuperclass
public class BaseEntity {

    // 创建者
    @Column(name = "create_by")
    private String createBy;

    // 创建时间
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    // 更新者
    @Column(name = "update_by")
    private String updateBy;

    // 更新时间
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 状态（0正常 1停用）
    @Column(name = "status")
    private String status;

    // 删除标志（0代表存在 1代表删除）
    @Column(name = "del_flag")
    private String delFlag;

    // 版本号
    @Column(name = "version")
    private String version;

}
