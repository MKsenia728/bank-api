package com.oksanamolchanova.bank.api.dto;

import java.util.List;

public record ErrorResponseDto(String errorCode, List<ErrorExtensionDto> errorExtensions) {
}
