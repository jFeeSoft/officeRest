package com.jfeesoft.offecerest.service.mapper;

import com.jfeesoft.offecerest.domain.*;
import com.jfeesoft.offecerest.service.dto.GoalDTO;

import org.mapstruct.*;

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
