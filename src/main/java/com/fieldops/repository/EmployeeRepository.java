package com.fieldops.repository;

import com.fieldops.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repositorio em memoria responsavel por armazenar colaboradores.
public class EmployeeRepository {
    // Lista temporaria: os dados existem apenas enquanto o programa esta rodando.
    private final List<Employee> employees = new ArrayList<>();

    // Salva um colaborador na lista.
    public void save(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee is required.");
        }
        employees.add(employee);
    }

    // Retorna uma copia da lista para proteger o armazenamento interno.
    public List<Employee> findAll() {
        return List.copyOf(employees);
    }

    // Procura um colaborador pelo ID.
    public Optional<Employee> findById(long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }

    // Procura um colaborador pela matricula.
    public Optional<Employee> findByRegistration(String registration) {
        if (registration == null) {
            return Optional.empty();
        }

        String normalizedRegistration = registration.strip();
        return employees.stream()
                .filter(employee -> employee.getRegistration().equalsIgnoreCase(normalizedRegistration))
                .findFirst();
    }
}
