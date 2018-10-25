package com.jfeesoft.offecerest.service.mapper;

import com.jfeesoft.offecerest.domain.*;
import com.jfeesoft.offecerest.service.dto.TaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Task and its DTO TaskDTO.
 */
@Mapper(componentModel = "spring", uses = {GoalMapper.class})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {

    @Mapping(source = "goal.id", target = "goalId")
    @Mapping(source = "goal.name", target = "goalName")
    TaskDTO toDto(Task task);

    @Mapping(source = "goalId", target = "goal")
    Task toEntity(TaskDTO taskDTO);

    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
