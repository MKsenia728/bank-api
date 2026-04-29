package com.oksanamolchanova.bank.api.mapper;

import com.oksanamolchanova.bank.api.dto.ManagerAfterCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerDto;
import com.oksanamolchanova.bank.api.entity.Manager;
import com.oksanamolchanova.bank.api.util.DtoCreator;
import com.oksanamolchanova.bank.api.util.EntityCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Test class ManagerMapper")
class ManagerMapperTest {
    private final ManagerMapper managerMapper = new ManagerMapperImpl();

    private final Manager manager = EntityCreator.getManagerEntity();

    @DisplayName("Positive test. Manager mapper to DTO")
    @Test
    void toDtoTest() {
        ManagerDto expectedManagerDto = DtoCreator.getManagerDto();
        Assertions.assertEquals(expectedManagerDto, managerMapper.toDto(manager));
    }

    @DisplayName("Negative test. Manager null mapper to DTO")
    @Test
    void toDtoNullTest() {
        Assertions.assertNull(managerMapper.toDto(null));
    }

    @DisplayName("Positive test. Managers list mapper to list DTO")
    @Test
    void toListDtoTest() {
        List<Manager> managerList = new ArrayList<>();
        managerList.add(manager);
        List<ManagerDto> expectedManagerDtoList = new ArrayList<>();
        expectedManagerDtoList.add(DtoCreator.getManagerDto());

        Assertions.assertEquals(expectedManagerDtoList, managerMapper.toListDto(managerList));
    }

    @DisplayName("Negative test. Managers list null mapper to list DTO")
    @Test
    void toListDtoNullTest() {
        Assertions.assertNull(managerMapper.toListDto(null));
    }

    @DisplayName("Positive test. To client for create")
    @Test
    void toCreateEntityTest() {
        Manager expectedManager = EntityCreator.getCreateManagerEntity();
        ManagerCreateDto managerCreateDto = DtoCreator.getManagerCreateDto();
        Assertions.assertEquals(expectedManager, managerMapper.toCreateEntity(managerCreateDto));
    }

    @DisplayName("Negative test. To client for create DTO null")
    @Test
    void toCreateEntityNullTest() {
        Assertions.assertNull(managerMapper.toCreateEntity(null));
    }

    @DisplayName("Positive test. Manager mapper to DTO after create")
    @Test
    void toAfterCreateDtoTest() {
        Manager expectedManager = EntityCreator.getCreateManagerEntity();
        ManagerAfterCreateDto expectedManagerAfterCreateDto = DtoCreator.getManagerAfterCreateDto();
        Assertions.assertEquals(expectedManagerAfterCreateDto, managerMapper.toAfterCreateDto(expectedManager));
    }

    @DisplayName("Negative test. Manager null mapper to DTO after create")
    @Test
    void toAfterCreateDtoNullTest() {
        Assertions.assertNull(managerMapper.toAfterCreateDto(null));
    }
}