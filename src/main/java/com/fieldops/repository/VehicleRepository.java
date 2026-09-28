package com.fieldops.repository;

import com.fieldops.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repositorio em memoria responsavel por armazenar veiculos.
public class VehicleRepository {
    // Lista temporaria: os dados existem apenas enquanto o programa esta rodando.
    private final List<Vehicle> vehicles = new ArrayList<>();

    // Salva um veiculo na lista.
    public void save(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required.");
        }
        vehicles.add(vehicle);
    }

    // Retorna uma copia da lista para proteger o armazenamento interno.
    public List<Vehicle> findAll() {
        return List.copyOf(vehicles);
    }

    // Procura um veiculo pelo ID.
    public Optional<Vehicle> findById(long id) {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getId() == id)
                .findFirst();
    }

    // Procura um veiculo pela placa.
    public Optional<Vehicle> findByLicensePlate(String licensePlate) {
        if (licensePlate == null) {
            return Optional.empty();
        }

        String normalizedLicensePlate = licensePlate.strip();
        return vehicles.stream()
                .filter(vehicle -> vehicle.getLicensePlate().equalsIgnoreCase(normalizedLicensePlate))
                .findFirst();
    }
}
