package com.fieldops.service;

import com.fieldops.model.Employee;
import com.fieldops.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

// Servico responsavel pelas regras de negocio de colaboradores.
public class EmployeeService {
    // O servico usa o repositorio para salvar e consultar dados.
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        if (employeeRepository == null) {
            throw new IllegalArgumentException("Employee repository is required.");
        }
        this.employeeRepository = employeeRepository;
    }

    // Cadastra um colaborador depois de validar as regras da aplicacao.
    public void register(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee is required.");
        }
        if (employeeRepository.findById(employee.getId()).isPresent()) {
            throw new IllegalArgumentException("Employee ID already exists: " + employee.getId());
        }
        if (employeeRepository.findByRegistration(employee.getRegistration()).isPresent()) {
            throw new IllegalArgumentException("Employee registration already exists: " + employee.getRegistration());
        }

        employeeRepository.save(employee);
    }

    // Lista todos os colaboradores cadastrados.
    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    // Busca um colaborador pelo ID.
    public Optional<Employee> findById(long id) {
        return employeeRepository.findById(id);
    }
}
