package com.oksanamolchanova.bank.api.controller;

import com.oksanamolchanova.bank.api.dto.*;
import com.oksanamolchanova.bank.api.dto.ManagerAfterCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerCreateDto;
import com.oksanamolchanova.bank.api.dto.ManagerDto;
import com.oksanamolchanova.bank.api.entity.Manager;
import com.oksanamolchanova.bank.api.service.interf.ManagerService;
import com.oksanamolchanova.bank.api.validation.annotation.PositiveInteger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/managers")
@RequiredArgsConstructor
@Tag(name = "Managers", description = "Controller for work with managers")
public class ManagerController {
    public final ManagerService managerService;

    @GetMapping(value = "/id/{managerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Request for find manager by ID",
            description = "If id exists - returns all info about manager. " +
                    "If id does not exist - returns exception")
    @ApiResponse(responseCode = "200", description = "Successfully returned manager by id", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Manager.class)))
    })
    public ManagerDto getManagerById(@PositiveInteger
                                     @Parameter(description = "Unique id, format integer")
                                     @PathVariable("managerId") String managerId) {
        return managerService.getManagerById(managerId);
    }

    @GetMapping(value = "/all/withClients")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Request for find managers which have clients",
            description = "If managers exist - returns list of managers. " +
                    "If id does not exist - returns exception")
    @ApiResponse(responseCode = "200", description = "Successfully returned list of managers which have clients", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ManagerDto.class)))
    })
    public List<ManagerDto> getAllManagersWithClients() {
        return managerService.getAllManagersWithClients();
    }

    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Request for create Manager",
            description = "If manager does not exist and input JSON data is valid - creates and returns manager" +
                    "If manager already exists or data invalid - returns server status code 400-406")
    @ApiResponse(responseCode = "200", description = "Successfully created account", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ManagerAfterCreateDto.class)))
    })
    public ManagerAfterCreateDto createNewManager(@Valid @RequestBody ManagerCreateDto managerCreateDto) {
        return managerService.managerNewCreate(managerCreateDto);
    }
}
