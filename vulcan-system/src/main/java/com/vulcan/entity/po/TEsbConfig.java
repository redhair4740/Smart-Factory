package com.vulcan.entity.po;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity.po
 * @name: TEsbConfig
 * @Date: 2024/4/11  下午4:26
 * @Description //TODO
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
