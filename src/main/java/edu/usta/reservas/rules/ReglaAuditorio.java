package edu.usta.reservas.rules;

import edu.usta.reservas.model.Auditorio;
import edu.usta.reservas.model.Reserva;
import edu.usta.reservas.model.Sala;

public class ReglaAuditorio implements ReglaReserva {
    @Override
    public OperationResult validar(Reserva reserva, Sala sala) {
        if (sala instanceof Auditorio) {
            if (reserva.getCantidadAsistentes() < 30) {
                return OperationResult.fail("Error: Las reservas de auditorio deben ser para al menos 30 asistentes.");
            }
        }
        return OperationResult.ok();
    }
}
