package com.fieldops.application;

import com.fieldops.enums.EmployeeStatus;
import com.fieldops.enums.JobRole;
import com.fieldops.enums.VehicleStatus;
import com.fieldops.enums.VehicleType;
import com.fieldops.enums.WorkShift;
import com.fieldops.model.Employee;
import com.fieldops.model.Team;
import com.fieldops.model.Vehicle;
import com.fieldops.repository.EmployeeRepository;
import com.fieldops.repository.TeamRepository;
import com.fieldops.repository.VehicleRepository;
import com.fieldops.service.EmployeeService;
import com.fieldops.service.TeamService;
import com.fieldops.service.VehicleService;

import java.util.List;

// Cria dados iniciais para a aplicacao abrir com informacoes na tela.
public class SampleData {
    private SampleData() {
        // Classe utilitaria: nao precisa ser instanciada.
    }

    public static FieldOpsData create() {
        // Cria os servicos e seus repositorios em memoria.
        EmployeeService employeeService = new EmployeeService(new EmployeeRepository());
        VehicleService vehicleService = new VehicleService(new VehicleRepository());
        TeamService teamService = new TeamService(new TeamRepository());

        // Cria colaboradores para demonstrar o sistema.
        Employee ana = new Employee(
                1L, "Ana Silva", "00123",
                JobRole.FIELD_TECHNICIAN, WorkShift.MORNING, EmployeeStatus.AVAILABLE
        );
        Employee bruno = new Employee(
                2L, "Bruno Costa", "00124",
                JobRole.TEAM_LEADER, WorkShift.AFTERNOON, EmployeeStatus.TRAINING
        );
        Employee carla = new Employee(
                3L, "Carla Souza", "00125",
                JobRole.SUPERVISOR, WorkShift.MORNING, EmployeeStatus.AVAILABLE
        );
        Employee ronald = new Employee(
                4L, "Ronald Santana", "00126",
                JobRole.SUPERVISOR, WorkShift.MORNING, EmployeeStatus.AVAILABLE
        );

        // Salva os colaboradores usando o servico.
        employeeService.register(ana);
        employeeService.register(bruno);
        employeeService.register(carla);
        employeeService.register(ronald);

        // Cria veiculos para demonstrar o sistema.
        Vehicle van = new Vehicle(
                1L, "FOP-1024", "Fiat Fiorino",
                VehicleType.VAN, VehicleStatus.AVAILABLE
        );
        Vehicle truck = new Vehicle(
                2L, "FOP-2048", "Volkswagen Delivery",
                VehicleType.TRUCK, VehicleStatus.MAINTENANCE
        );

        // Salva os veiculos usando o servico.
        vehicleService.register(van);
        vehicleService.register(truck);

        // Cria uma equipe com lider, membros e veiculo.
        teamService.register(new Team(
                1L, "Equipe Norte",
                bruno,
                List.of(ana, ronald),
                van
        ));

        return new FieldOpsData(employeeService, vehicleService, teamService);
    }
}
