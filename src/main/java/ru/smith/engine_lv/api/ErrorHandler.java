package ru.smith.engine_lv.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.smith.engine_lv.api.dto.common.response.ErrorCode;
import ru.smith.engine_lv.api.dto.common.response.Response;

import java.util.EnumMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Service
public class ErrorHandler {
    Map<ErrorCode, HttpStatus> mapping = new EnumMap<>(ErrorCode.class);

    {
        mapping.put(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);
        mapping.put(ErrorCode.ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        mapping.put(ErrorCode.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity handleError(Response response) {
        HttpStatus httpStatus = ofNullable(response.getError())
                .filter(er -> mapping.containsKey(er.getCode()))
                .map(er -> mapping.get(er.getCode()))
                .orElse(HttpStatus.BAD_GATEWAY);
        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }
}
