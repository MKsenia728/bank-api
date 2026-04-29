package com.oksanamolchanova.bank.api.controller;

import com.oksanamolchanova.bank.api.dto.ManagerAfterCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerDto;
import com.oksanamolchanova.bank.api.service.exceptions.DataAlreadyExistException;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.service.exceptions.ErrorMessage;
import com.oksanamolchanova.bank.api.service.interf.ManagerService;
import com.oksanamolchanova.bank.api.util.DtoCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ManagerController.class)
@DisplayName("ManagerController test class")
class ManagerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ManagerService service;

    @DisplayName("Positive test. Status 200, JSON response. Controller for find manager by ID")
    @Test
    void getManagerByIdTest() throws Exception {
        String id = "1";
        ManagerDto expectManager = DtoCreator.getManagerDto();
        Mockito.when(service.getManagerById(id)).thenReturn(expectManager);

        mockMvc.perform(get("/managers/id/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectManager.id()))
                .andExpect(jsonPath("$.firstName").value(expectManager.firstName()))
                .andExpect(jsonPath("$.lastName").value(expectManager.lastName()))
                .andExpect(jsonPath("$.status").value(expectManager.status()))
                .andExpect(jsonPath("$.createdAt").value(expectManager.createdAt().toString().substring(0, 10)))
                .andExpect(jsonPath("$.updatedAt").value(expectManager.updatedAt().toString().substring(0, 10)));

        Mockito.verify(service).getManagerById(id);
    }

    @DisplayName("Negative test. Invalid ID. Controller for find manager by ID")
    @Test
    void getManagerByIdInvalidIdTest() throws Exception {
        String id = "id";
        mockMvc.perform(
                        get("/managers/id/" + id)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value("Validation failed"))
                .andExpect(jsonPath("$.errorExtensions[0].code").value("Value must be integer and positive"))
                .andExpect(jsonPath("$.errorExtensions[0].message").value("Variable for path is invalid"));
    }

    @DisplayName("Negative test. Id does not exist. Controller for find manager by ID")
    @Test
    void getManagerByIdNotExistIdTest() throws Exception {
        String id = "1";
        Mockito.when(service.getManagerById(id)).thenThrow(new DataNotFoundException(ErrorMessage.MANAGER_NOT_FOUND));
        mockMvc.perform(get("/managers/id/" + id)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.MANAGER_NOT_FOUND));
    }

    @DisplayName("Positive test. Status 200, JSON response. Controller for find managers with clients")
    @Test
    void getAllManagersWithClientsTest() throws Exception {
        List<ManagerDto> managerDtoList = new ArrayList<>();
        managerDtoList.add(DtoCreator.getManagerDto());

        Mockito.when(service.getAllManagersWithClients()).thenReturn(managerDtoList);

        mockMvc.perform(
                        get("/managers/all/withClients")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(managerDtoList.get(0).id()))
                .andExpect(jsonPath("$[0].firstName").value(managerDtoList.get(0).firstName()))
                .andExpect(jsonPath("$[0].lastName").value(managerDtoList.get(0).lastName()))
                .andExpect(jsonPath("$[0].status").value(managerDtoList.get(0).status()))
                .andExpect(jsonPath("$[0].createdAt").value(managerDtoList.get(0).createdAt().toString().substring(0, 10)))
                .andExpect(jsonPath("$[0].updatedAt").value(managerDtoList.get(0).updatedAt().toString().substring(0, 10)));
        Mockito.verify(service).getAllManagersWithClients();
    }

    @DisplayName("Negative test. Managers does not exist. Controller for find managers with clients")
    @Test
    void getAllManagersWithClientsNotExistTest() throws Exception {
        Mockito.when(service.getAllManagersWithClients()).thenThrow(new DataNotFoundException(ErrorMessage.MANAGERS_NOT_FOUND));
        mockMvc.perform(get("/managers/all/withClients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.MANAGERS_NOT_FOUND));
    }

    @DisplayName("Positive test. JSON response. Controller for create manager")
    @Test
    void createNewManagerTest() throws Exception {
        ManagerCreateDto managerCreateDto = DtoCreator.getManagerCreateDto();
        ManagerAfterCreateDto managerAfterCreateDto = DtoCreator.getManagerAfterCreateDto();

        Mockito.when(service.managerNewCreate(managerCreateDto)).thenReturn(managerAfterCreateDto);
        mockMvc.perform(post("/managers/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(managerCreateDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(managerAfterCreateDto.id()))
                .andExpect(jsonPath("$.firstName").value(managerAfterCreateDto.firstName()))
                .andExpect(jsonPath("$.lastName").value(managerAfterCreateDto.lastName()))
                .andExpect(jsonPath("$.status").value(managerAfterCreateDto.status()))
                .andExpect(jsonPath("$.createdAt").value(managerAfterCreateDto.createdAt().toString().substring(0, 10)))
                .andExpect(jsonPath("$.updatedAt").value(managerAfterCreateDto.updatedAt().toString().substring(0, 10)));

        Mockito.verify(service).managerNewCreate(managerCreateDto);
    }

    @DisplayName("Negative test. Manager already exists. Controller for find managers with clients")
    @Test
    void createNewManagerAlreadyExistManagerTest() throws Exception {
        ManagerCreateDto managerCreateDto = DtoCreator.getManagerCreateDto();
        Mockito.when(service.managerNewCreate(managerCreateDto)).thenThrow(new DataAlreadyExistException(ErrorMessage.MANAGER_ALREADY_EXISTS));
        mockMvc.perform(
                        post("/managers/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(managerCreateDto))
                )
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("406"))
                .andExpect(jsonPath("$.message").value(ErrorMessage.MANAGER_ALREADY_EXISTS));
    }

    @DisplayName("Negative test. Invalid input data. Controller for find managers with clients")
    @Test
    void createNewManagerNotEnoughInputDataTest() throws Exception {
        ManagerCreateDto managerCreateDto = new ManagerCreateDto("firstname1", "lastname2");
        mockMvc.perform(
                        post("/managers/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(managerCreateDto))
                )
                .andExpect(status().isBadRequest());

    }
}