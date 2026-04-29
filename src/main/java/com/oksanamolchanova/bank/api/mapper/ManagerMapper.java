package com.oksanamolchanova.bank.api.mapper;

import com.oksanamolchanova.bank.api.dto.ManagerAfterCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerDto;
import com.oksanamolchanova.bank.api.entity.Manager;
import com.oksanamolchanova.bank.api.entity.enums.ManagerStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR, imports = {LocalDateTime.class, ManagerStatus.class})
public interface ManagerMapper {
    ManagerDto toDto(Manager manager);

    List<ManagerDto> toListDto(List<Manager> managers);

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(ManagerStatus.PENDING)")
    Manager toCreateEntity(ManagerCreateDto managerDto);

    ManagerAfterCreateDto toAfterCreateDto(Manager manager);
}
