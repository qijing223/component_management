package com.lot.server.component.repository;

import com.lot.server.component.model.ComponentDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ComponentRepository
 * 负责与数据库进行增删改查等交互 (JPA 会自动生成常用的SQL)
 */
@Repository
public interface ComponentRepository extends JpaRepository<ComponentDO, Long> {

    // 如果有自定义查询需求，可以在这里定义方法并使用Spring Data JPA的命名规则或@Query注解
    // 例如: List<ComponentDO> findByName(String name);
}
