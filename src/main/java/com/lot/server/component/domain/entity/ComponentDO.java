package com.lot.server.component.domain.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * ComponentDO
 * 对应数据库中的 "component" 表 (示例名称，可自行调整)
 */
@Entity
@Table(name = "component")
@Data
public class ComponentDO {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 组件名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 组件描述
     */
    @Column(name = "description")
    private String description;
}
