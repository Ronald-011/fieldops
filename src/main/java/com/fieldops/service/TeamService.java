package com.fieldops.service;

import com.fieldops.model.Team;
import com.fieldops.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

// Servico responsavel pelas regras de negocio de equipes.
public class TeamService {
    // O servico usa o repositorio para salvar e consultar dados.
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        if (teamRepository == null) {
            throw new IllegalArgumentException("Team repository is required.");
        }
        this.teamRepository = teamRepository;
    }

    // Cadastra uma equipe depois de validar as regras da aplicacao.
    public void register(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team is required.");
        }
        if (teamRepository.findById(team.getId()).isPresent()) {
            throw new IllegalArgumentException("Team ID already exists: " + team.getId());
        }
        if (teamRepository.findByName(team.getName()).isPresent()) {
            throw new IllegalArgumentException("Team name already exists: " + team.getName());
        }

        teamRepository.save(team);
    }

    // Lista todas as equipes cadastradas.
    public List<Team> listAll() {
        return teamRepository.findAll();
    }

    // Busca uma equipe pelo ID.
    public Optional<Team> findById(long id) {
        return teamRepository.findById(id);
    }
}
