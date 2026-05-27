package edu.usta.reservas.repository;

import edu.usta.reservas.model.Reserva;
import java.util.List;

public interface ReservaRepository {
    void guardar(Reserva reserva);
    Reserva buscarReservaPorId(String id);
    List<Reserva> obtenerTodasLasReservas(); // Nombre específico para evitar choques
}
