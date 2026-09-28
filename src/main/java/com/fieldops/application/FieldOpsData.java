package com.fieldops.application;

import com.fieldops.service.EmployeeService;
import com.fieldops.service.TeamService;
import com.fieldops.service.VehicleService;

// Agrupa os servicos principais para entregar todos eles para a interface.
public record FieldOpsData(
        EmployeeService employeeService,
        VehicleService vehicleService,
        TeamService teamService
) {
}
