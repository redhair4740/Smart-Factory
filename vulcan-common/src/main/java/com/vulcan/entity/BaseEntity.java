package com.vulcan.entity;

import jakarta.persistence.Column;
import lombok.Data;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity
 * @name: BaseEntity
 * @Date: 2024/4/12  下午1:56
 * @Description //TODO
 */
@Data
public class BaseEntity {

    //创建者
    @Column(name = "create_by")
    private String createBy;

    //创建时间
    @Column(name = "create_time")
    private String createTime;

    //更新者
    @Column(name = "update_by")
    private String updateBy;

    //更新时间
    @Column(name = "update_time")
    private String updateTime;

    //备注
    @Column(name = "remark")
    private String remark;

    //状态（0正常 1停用）
    @Column(name = "status")
    private String status;

    //删除标志（0代表存在 1代表删除）
    @Column(name = "del_flag")
    private String delFlag;

    //版本号
    @Column(name = "version")
    private String version;

}
