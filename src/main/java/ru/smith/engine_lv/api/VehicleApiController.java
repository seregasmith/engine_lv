package ru.smith.engine_lv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smith.engine_lv.api.dto.common.Vehicle;
import ru.smith.engine_lv.api.dto.common.VehicleType;
import ru.smith.engine_lv.api.dto.common.response.ErrorCode;
import ru.smith.engine_lv.api.dto.common.response.ErrorPayload;
import ru.smith.engine_lv.api.dto.common.response.Response;
import ru.smith.engine_lv.service.VehicleService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestController
public class VehicleApiController {
    private VehicleService vehicleService;
    private ErrorHandler errorHandler;

    @Autowired
    public VehicleApiController(VehicleService vehicleService,
                                ErrorHandler errorHandler) {
        this.vehicleService = vehicleService;
        this.errorHandler = errorHandler;
    }

    @PostMapping("/register")
    public ResponseEntity createAccount(@RequestBody List<Vehicle> rqData) {
        List<Vehicle> notRegVehicles = vehicleService.registerNewVehicles(rqData);
        Response res = Response.asSuccess(null);
        if (!notRegVehicles.isEmpty()) {
            String idsStr = notRegVehicles
                    .stream()
                    .map(Vehicle::getId)
                    .map(Objects::toString)
                    .collect(Collectors.joining(", "));

            String msg = String.format("Vehicles already exists with ids: %s", idsStr);
            res = Response.asError(new ErrorPayload(ErrorCode.ALREADY_EXIST, msg));
        }
        if (res.hasError()) {
            return errorHandler.handleError(res);
        }
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/{type}")
    public ResponseEntity getVehicle(@PathVariable String type, @RequestParam Long id) {
        VehicleType vehicleType = VehicleType.fromString(type);
        List<Vehicle> vehicles = Collections.emptyList();
        if (nonNull(vehicleType)) {
            vehicles = vehicleService.getVehicles(vehicleType, id);
        }

        Response res = null;
        if (vehicles.isEmpty()) {
            String msg = String.format("No vehicles found with type %s with id %d", type, id);
            res = Response.asError(new ErrorPayload(ErrorCode.NOT_FOUND, msg));
        }

        if (vehicles.size() > 1) {
            String msg = String.format("More than 1 vehicle found with type %s with id %d", vehicleType, id);
            res = Response.asError(new ErrorPayload(ErrorCode.UNKNOWN, msg));
        }

        if (nonNull(res) && res.hasError()) {
            return errorHandler.handleError(res);
        }

        res = Response.asSuccess(vehicles.get(0));
        return ResponseEntity.ok().body(res);
    }
}
