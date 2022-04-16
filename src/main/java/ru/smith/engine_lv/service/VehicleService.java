package ru.smith.engine_lv.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.smith.engine_lv.api.dto.common.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Vehicle model abstraction to store vehicles in memory
 */
@Service
public class VehicleService {
    private final Logger LOG = LoggerFactory.getLogger(VehicleService.class);
    private static final List<Vehicle> REGISTER = new ArrayList<>();

    /**
     * @param vehiclesToReg - list of vehicles to register in model
     * @return list of not registered vehicles.
     */
    public List<Vehicle> registerNewVehicles(List<Vehicle> vehiclesToReg) {
        List<Vehicle> errorRegisteredVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehiclesToReg) {
            if (REGISTER.stream()
                    .anyMatch(v -> v.getType().equals(vehicle.getType()) && v.getId().equals(vehicle.getId()))
            ) {
                LOG.error("Vehicle wasn't registered because ID already contains in model. ID ={}", vehicle.getId());
                errorRegisteredVehicles.add(vehicle);
                continue;
            }
            REGISTER.add(vehicle);
        }

        return errorRegisteredVehicles;
    }
}
