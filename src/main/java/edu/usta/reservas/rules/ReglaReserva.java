package edu.usta.reservas.rules;

import edu.usta.reservas.model.Reserva;
import edu.usta.reservas.model.Sala;

public interface ReglaReserva {
    OperationResult validar(Reserva reserva, Sala sala);
}
