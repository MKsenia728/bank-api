package com.oksanamolchanova.bank.api.service.impl;

import com.oksanamolchanova.bank.api.dto.ManagerAfterCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerDto;
import com.oksanamolchanova.bank.api.entity.Manager;
import com.oksanamolchanova.bank.api.mapper.ManagerMapper;
import com.oksanamolchanova.bank.api.repository.ManagerRepository;
import com.oksanamolchanova.bank.api.service.exceptions.DataAlreadyExistException;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.service.exceptions.ErrorMessage;
import com.oksanamolchanova.bank.api.service.interf.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerServiceImpl implements ManagerService {
    private final ManagerMapper managerMapper;
    private final ManagerRepository managerRepository;

    @Override
    @Transactional
    public ManagerDto getManagerById(String id) {
        log.info("Get manager by id {}", id);
        return managerMapper.toDto(managerRepository.findManagerById(Long.parseLong(id)).orElseThrow(
                ()-> {
                    log.warn(ErrorMessage.MANAGER_NOT_FOUND);
                    throw new DataNotFoundException(ErrorMessage.MANAGER_NOT_FOUND);
                }));
    }
    @Override
    @Transactional
    public List<ManagerDto> getAllManagersWithClients() {
        log.info("Get all managers with clients");
        List<ManagerDto> resultList = new ArrayList<>(managerMapper.toListDto(managerRepository.getAllByClientsNotNull()));
        if (resultList.size() == 0) {
            log.warn(ErrorMessage.MANAGERS_NOT_FOUND);
            throw new DataNotFoundException(ErrorMessage.MANAGERS_NOT_FOUND);
        }
        return resultList;
    }

    @Override
    @Transactional
    public ManagerAfterCreateDto managerNewCreate(ManagerCreateDto managerCreateDto) {
        log.info("Create new manager");
        Manager manager = managerMapper.toCreateEntity(managerCreateDto);
        checkManagerNotExist(manager);
        return  managerMapper.toAfterCreateDto(managerRepository.save(manager));
    }
    
    private void checkManagerNotExist(Manager manager) {
        managerRepository.findAll().forEach(m -> {
            if (m.equals(manager)) {
                log.error(ErrorMessage.MANAGER_ALREADY_EXISTS);
                throw new DataAlreadyExistException(ErrorMessage.MANAGER_ALREADY_EXISTS);
            }
        });
    }
}

