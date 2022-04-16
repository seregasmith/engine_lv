package ru.smith.engine_lv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.smith.engine_lv.api.dto.common.Vehicle;
import ru.smith.engine_lv.api.dto.common.response.ErrorCode;
import ru.smith.engine_lv.api.dto.common.response.ErrorPayload;
import ru.smith.engine_lv.api.dto.common.response.Response;
import ru.smith.engine_lv.service.VehicleService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


//todo get method to get vehicle by type(path parameterised) and by id
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
}
