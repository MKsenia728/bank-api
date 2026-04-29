package com.oksanamolchanova.bank.api.service.impl;

import com.oksanamolchanova.bank.api.dto.ManagerAfterCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerDto;
import com.oksanamolchanova.bank.api.entity.Manager;
import com.oksanamolchanova.bank.api.mapper.ManagerMapper;
import com.oksanamolchanova.bank.api.repository.ManagerRepository;
import com.oksanamolchanova.bank.api.service.exceptions.DataAlreadyExistException;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.util.DtoCreator;
import com.oksanamolchanova.bank.api.util.EntityCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Manager service test class")
@ExtendWith(MockitoExtension.class)
class ManagerServiceImpTest {

    @Mock
    ManagerRepository managerRepository;

    @Mock
    ManagerMapper managerMapper;

    @InjectMocks
    ManagerServiceImpl service;

    private final Manager manager = EntityCreator.getManagerEntity();

    @DisplayName("Positive test. Get manager by Id")
    @Test
    void getManagerByIdTest() {
        ManagerDto managerDto = DtoCreator.getManagerDto();

        Mockito.when(managerRepository.findManagerById(manager.getId())).thenReturn(Optional.of(manager));
        Mockito.when(managerMapper.toDto(manager)).thenReturn(managerDto);

        service.getManagerById(manager.getId().toString());

        Mockito.verify(managerRepository).findManagerById(manager.getId());
        Mockito.verify(managerMapper).toDto(manager);
    }

    @DisplayName("Negative test. Manager does not exist. Get manager by Id")
    @Test
    void getManagerByIdNotExistTest() {
        String id = "1";
        assertThrows(DataNotFoundException.class, () -> service.getManagerById(id));
    }

    @DisplayName("Positive test. Get all managers with clients")
    @Test
    void getAllManagersWithClientsTest() {
        List<Manager> managerList = new ArrayList<>();
        managerList.add(manager);
        List<ManagerDto> managerDtoList = new ArrayList<>();
        managerDtoList.add(DtoCreator.getManagerDto());

        Mockito.when(managerRepository.getAllByClientsNotNull()).thenReturn(managerList);
        Mockito.when(managerMapper.toListDto(managerList)).thenReturn(managerDtoList);

        service.getAllManagersWithClients();

        Mockito.verify(managerRepository).getAllByClientsNotNull();
        Mockito.verify(managerMapper).toListDto(managerList);
    }

    @DisplayName("Negative test. The managers with clients are absent. Get all managers with clients")
    @Test
    void getAllManagersWithClientsNotExistTest() {
        assertThrows(DataNotFoundException.class, () -> service.getAllManagersWithClients());
    }

    @DisplayName("Positive test. Create new manager")
    @Test
    void managerNewCreateTest() {
        ManagerCreateDto managerCreateDto = DtoCreator.getManagerCreateDto();
        ManagerAfterCreateDto managerAfterCreateDto = DtoCreator.getManagerAfterCreateDto();
        List<Manager> managerList = new ArrayList<>();

        Mockito.when(managerRepository.findAll()).thenReturn(managerList);
        Mockito.when(managerMapper.toCreateEntity(managerCreateDto)).thenReturn(manager);
        Mockito.when(managerMapper.toAfterCreateDto(manager)).thenReturn(managerAfterCreateDto);
        Mockito.when(managerRepository.save(manager)).thenReturn(manager);

        service.managerNewCreate(managerCreateDto);

        Mockito.verify(managerRepository).findAll();
        Mockito.verify(managerMapper).toCreateEntity(managerCreateDto);
        Mockito.verify(managerMapper).toAfterCreateDto(manager);
        Mockito.verify(managerRepository).save(manager);
    }

    @DisplayName("Negative test. Such manager already exist. Create new manager")
    @Test
    void managerNewCreateAlreadyExistTest() {
        ManagerCreateDto managerCreateDto = DtoCreator.getManagerCreateDto();
        List<Manager> managerList = new ArrayList<>();
        managerList.add(manager);

        Mockito.when(managerMapper.toCreateEntity(managerCreateDto)).thenReturn(manager);
        Mockito.when(managerRepository.findAll()).thenReturn(managerList);
        assertThrows(DataAlreadyExistException.class, () -> service.managerNewCreate(managerCreateDto));
    }
}