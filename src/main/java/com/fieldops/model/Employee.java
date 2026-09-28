package com.fieldops.model;

import com.fieldops.enums.EmployeeStatus;
import com.fieldops.enums.JobRole;
import com.fieldops.enums.WorkShift;

// Entidade que representa um colaborador do FieldOps.
public class Employee {
    // Atributos principais do colaborador.
    private final long id;
    private final String name;
    private final String registration;
    private final JobRole role;
    private final WorkShift workShift;
    private final EmployeeStatus status;

    public Employee(long id, String name, String registration, JobRole role,
                    WorkShift workShift, EmployeeStatus status) {
        // Validacoes basicas para impedir a criacao de um colaborador invalido.
        if (id <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive.");
        }
        if (role == null) {
            throw new IllegalArgumentException("Job role is required.");
        }
        if (workShift == null) {
            throw new IllegalArgumentException("Work shift is required.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Employee status is required.");
        }

        // Atribui os valores validados aos atributos do objeto.
        this.id = id;
        this.name = requireText(name, "Name");
        this.registration = requireText(registration, "Registration");
        this.role = role;
        this.workShift = workShift;
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

    public String getName() {
        return name;
    }

    public String getRegistration() {
        return registration;
    }

    public JobRole getRole() {
        return role;
    }

    public WorkShift getWorkShift() {
        return workShift;
    }

    public EmployeeStatus getStatus() {
        return status;
    }
}
