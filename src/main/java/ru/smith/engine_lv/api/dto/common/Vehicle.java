package ru.smith.engine_lv.api.dto.common;

public class Vehicle {
    private Long id;
    private VehicleType type;
    private String model;

    public Vehicle() {
    }

    //region GETTER AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    //endregion
}
