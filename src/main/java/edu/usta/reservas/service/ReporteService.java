package edu.usta.reservas.service;

import edu.usta.reservas.model.Reserva;
import edu.usta.reservas.repository.ReservaRepository;
import edu.usta.reservas.repository.SalaRepository;

public class ReporteService {
    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;

    public ReporteService(SalaRepository salaRepository, ReservaRepository reservaRepository) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
    }

    public String generarReporteGeneral() {
        // Adaptado a obtenerTodasLasSalas() y obtenerTodasLasReservas()
        long totalAulas = salaRepository.obtenerTodasLasSalas().stream().filter(s -> s.getTipo().equals("AULA")).count();
        long totalLaboratorios = salaRepository.obtenerTodasLasSalas().stream().filter(s -> s.getTipo().equals("LABORATORIO")).count();
        long totalAuditorios = salaRepository.obtenerTodasLasSalas().stream().filter(s -> s.getTipo().equals("AUDITORIO")).count();

        long activas = reservaRepository.obtenerTodasLasReservas().stream().filter(r -> !r.isCancelada()).count();
        long canceladas = reservaRepository.obtenerTodasLasReservas().stream().filter(Reserva::isCancelada).count();

        StringBuilder sb = new StringBuilder();
        sb.append("Reporte general\n-------------\n");
        sb.append("Total de salas: ").append(salaRepository.obtenerTodasLasSalas().size()).append("\n");
        sb.append("Aulas: ").append(totalAulas).append("\n");
        sb.append("Laboratorios: ").append(totalLaboratorios).append("\n");
        sb.append("Auditorios: ").append(totalAuditorios).append("\n");
        sb.append("Total de reservas: ").append(reservaRepository.obtenerTodasLasReservas().size()).append("\n");
        sb.append("Reservas activas: ").append(activas).append("\n");
        sb.append("Reservas canceladas: ").append(canceladas).append("\n");

        return sb.toString();
    }
}