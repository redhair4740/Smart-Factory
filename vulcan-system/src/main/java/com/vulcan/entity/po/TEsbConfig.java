package com.vulcan.entity.po;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 【请填写功能名称】对象 t_esb_config
 * 
 * @author weifu
 * @date 2023-03-08
 */
@Data
@Entity
@Table(name = "t_esb_config")
public class TEsbConfig
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "esb_column")
    private String esbColumn;

    @Column(name = "mom_column")
    private String momColumn;

    @Column(name = "esb_default_value")
    private String esbDefaultValue;

    @Column(name = "remark")
    private String remark;

}
