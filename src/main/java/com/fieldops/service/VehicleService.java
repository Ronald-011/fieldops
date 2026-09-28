package com.fieldops.service;

import com.fieldops.model.Vehicle;
import com.fieldops.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

// Servico responsavel pelas regras de negocio de veiculos.
public class VehicleService {
    // O servico usa o repositorio para salvar e consultar dados.
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        if (vehicleRepository == null) {
            throw new IllegalArgumentException("Vehicle repository is required.");
        }
        this.vehicleRepository = vehicleRepository;
    }

    // Cadastra um veiculo depois de validar as regras da aplicacao.
    public void register(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle is required.");
        }
        if (vehicleRepository.findById(vehicle.getId()).isPresent()) {
            throw new IllegalArgumentException("Vehicle ID already exists: " + vehicle.getId());
        }
        if (vehicleRepository.findByLicensePlate(vehicle.getLicensePlate()).isPresent()) {
            throw new IllegalArgumentException("Vehicle license plate already exists: " + vehicle.getLicensePlate());
        }

        vehicleRepository.save(vehicle);
    }

    // Lista todos os veiculos cadastrados.
    public List<Vehicle> listAll() {
        return vehicleRepository.findAll();
    }

    // Busca um veiculo pelo ID.
    public Optional<Vehicle> findById(long id) {
        return vehicleRepository.findById(id);
    }
}
