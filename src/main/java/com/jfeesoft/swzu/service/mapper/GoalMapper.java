package com.jfeesoft.swzu.service.mapper;

import com.jfeesoft.swzu.domain.Goal;
import com.jfeesoft.swzu.service.dto.GoalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Goal and its DTO GoalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoalMapper extends EntityMapper<GoalDTO, Goal> {


    @Mapping(target = "tasks", ignore = true)
    Goal toEntity(GoalDTO goalDTO);

    default Goal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Goal goal = new Goal();
        goal.setId(id);
        return goal;
    }
}
