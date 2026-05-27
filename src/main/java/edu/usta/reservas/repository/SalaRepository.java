package edu.usta.reservas.repository;

import edu.usta.reservas.model.Sala;
import java.util.List;

public interface SalaRepository {
    void guardar(Sala sala);
    Sala buscarSalaPorId(String id);
    List<Sala> obtenerTodasLasSalas(); // Nombre específico para evitar choques
}