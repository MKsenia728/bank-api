package com.oksanamolchanova.bank.api.service.interf;

import com.oksanamolchanova.bank.api.dto.ManagerAfterCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerDto;

import java.util.List;

public interface ManagerService {

    ManagerDto getManagerById(String id);

    List<ManagerDto> getAllManagersWithClients();

    ManagerAfterCreateDto managerNewCreate(ManagerCreateDto managerCreateDto);
}
