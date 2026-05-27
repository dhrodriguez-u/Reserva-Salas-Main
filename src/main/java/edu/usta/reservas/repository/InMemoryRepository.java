package edu.usta.reservas.repository;

import edu.usta.reservas.model.Reserva;
import edu.usta.reservas.model.Sala;
import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements SalaRepository, ReservaRepository {
    private final List<Sala> salas = new ArrayList<>();
    private final List<Reserva> reservas = new ArrayList<>();

    @Override
    public void guardar(Sala sala) {
        salas.add(sala);
    }

    @Override
    public Sala buscarSalaPorId(String id) {
        return salas.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Sala> obtenerTodasLasSalas() {
        return new ArrayList<>(salas);
    }

    @Override
    public void guardar(Reserva reserva) {
        reservas.add(reserva);
    }

    @Override
    public Reserva buscarReservaPorId(String id) {
        return reservas.stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Reserva> obtenerTodasLasReservas() {
        return new ArrayList<>(reservas);
    }
}