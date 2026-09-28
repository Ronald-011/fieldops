package com.fieldops.repository;

import com.fieldops.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Repositorio em memoria responsavel por armazenar equipes.
public class TeamRepository {
    // Lista temporaria: os dados existem apenas enquanto o programa esta rodando.
    private final List<Team> teams = new ArrayList<>();

    // Salva uma equipe na lista.
    public void save(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team is required.");
        }
        teams.add(team);
    }

    // Retorna uma copia da lista para proteger o armazenamento interno.
    public List<Team> findAll() {
        return List.copyOf(teams);
    }

    // Procura uma equipe pelo ID.
    public Optional<Team> findById(long id) {
        return teams.stream()
                .filter(team -> team.getId() == id)
                .findFirst();
    }

    // Procura uma equipe pelo nome.
    public Optional<Team> findByName(String name) {
        if (name == null) {
            return Optional.empty();
        }

        String normalizedName = name.strip();
        return teams.stream()
                .filter(team -> team.getName().equalsIgnoreCase(normalizedName))
                .findFirst();
    }
}
