package com.fieldops.model;

import java.util.List;

// Entidade que representa uma equipe formada por pessoas e um veiculo.
public class Team {
    // Atributos principais da equipe.
    private final long id;
    private final String name;
    private final Employee leader;
    private final List<Employee> members;
    private final Vehicle vehicle;

    public Team(long id, String name, Employee leader, List<Employee> members, Vehicle vehicle) {
        // Validacoes basicas para impedir a criacao de uma equipe invalida.
        if (id <= 0) {
            throw new IllegalArgumentException("Team ID must be positive.");
        }
        if (leader == null) {
            throw new IllegalArgumentException("Team leader is required.");
        }
        if (members == null || members.isEmpty()) {
            throw new IllegalArgumentException("Team members are required.");
        }
        if (members.stream().anyMatch(member -> member == null)) {
            throw new IllegalArgumentException("Team members must not contain null values.");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Team vehicle is required.");
        }

        // A lista de membros e copiada para proteger os dados internos da equipe.
        this.id = id;
        this.name = requireText(name, "Team name");
        this.leader = leader;
        this.members = List.copyOf(members);
        this.vehicle = vehicle;
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

    public Employee getLeader() {
        return leader;
    }

    public List<Employee> getMembers() {
        return members;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
