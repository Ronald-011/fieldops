package com.fieldops.model;

import com.fieldops.enums.VehicleStatus;
import com.fieldops.enums.VehicleType;

// Entidade que representa um veiculo usado nas operacoes de campo.
public class Vehicle {
    // Atributos principais do veiculo.
    private final long id;
    private final String licensePlate;
    private final String model;
    private final VehicleType type;
    private final VehicleStatus status;

    public Vehicle(long id, String licensePlate, String model, VehicleType type, VehicleStatus status) {
        // Validacoes basicas para impedir a criacao de um veiculo invalido.
        if (id <= 0) {
            throw new IllegalArgumentException("Vehicle ID must be positive.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type is required.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Vehicle status is required.");
        }

        // A placa e salva em maiusculo para manter um padrao unico.
        this.id = id;
        this.licensePlate = requireText(licensePlate, "License plate").toUpperCase();
        this.model = requireText(model, "Model");
        this.type = type;
        this.status = status;
    }

    // Reaproveita a mesma regra para validar textos obrigatorios.
    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank.");
        }
        return value.strip();
    }

    // Getters permitem consultar os dados sem alterar o objeto.
    public long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public VehicleType getType() {
        return type;
    }

    public VehicleStatus getStatus() {
        return status;
    }
}
