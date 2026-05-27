package edu.usta.reservas.rules;

import edu.usta.reservas.model.Laboratorio;
import edu.usta.reservas.model.Reserva;
import edu.usta.reservas.model.Sala;

public class ReglaLaboratorio implements ReglaReserva {
    @Override
    public OperationResult validar(Reserva reserva, Sala sala) {
        if (sala instanceof Laboratorio) {
            if (!reserva.getResponsable().equalsIgnoreCase("Docente")) {
                return OperationResult.fail("Error: Solo los docentes pueden reservar laboratorios.");
            }
        }
        return OperationResult.ok();
    }
}
