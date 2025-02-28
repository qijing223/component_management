package com.lot.server.component.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * ComponentDTO
 * 用于与前端进行数据交互（避免直接暴露数据库实体）
 */
@Data
@Schema(description = "Component DTO")
public class ComponentDTO {

    @Schema(description = "ComponentID")
    private Long id;

    @Schema(description = "ComponentName")
    private String name;

    @Schema(description = "ComponentDesc")
    private String description;
}
