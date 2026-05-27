package edu.usta.reservas.service;

import edu.usta.reservas.model.*;
import edu.usta.reservas.repository.ReservaRepository;
import edu.usta.reservas.repository.SalaRepository;
import edu.usta.reservas.rules.*;

import java.util.ArrayList;
import java.util.List;

public class ReservaService {
    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;
    private final List<ReglaReserva> reglas;

    public ReservaService(SalaRepository salaRepository, ReservaRepository reservaRepository) {
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
        this.reglas = new ArrayList<>();
        this.reglas.add(new ReglaAuditorio());
        this.reglas.add(new ReglaLaboratorio());
    }

    public String registrarSala(String id, String nombre, String tipo, int capacidad, String ubicacion) {
        if (id == null || id.trim().isEmpty()) return "Error: el ID de la sala no puede estar vacío.";
        if (nombre == null || nombre.trim().isEmpty()) return "Error: el nombre de la sala no puede estar vacío.";
        if (tipo == null || tipo.trim().isEmpty()) return "Error: el tipo de sala no puede estar vacío.";
        if (capacidad <= 0) return "Error: la capacidad debe ser mayor que cero.";
        if (salaRepository.buscarSalaPorId(id) != null) return "Error: ya existe una sala con ese ID.";

        Sala sala;
        switch (tipo.toUpperCase()) {
            case "AULA": sala = new Aula(id, nombre, capacidad, ubicacion); break;
            case "LABORATORIO": sala = new Laboratorio(id, nombre, capacidad, ubicacion); break;
            case "AUDITORIO": sala = new Auditorio(id, nombre, capacidad, ubicacion); break;
            default: return "Error: tipo de sala no válido. Use AULA, LABORATORIO o AUDITORIO.";
        }

        salaRepository.guardar(sala);
        return "Sala registrada correctamente.";
    }

    public String crearReserva(String id, String salaId, String fecha, int horaInicio, int horaFin,
                               String tipoActividad, String responsable, int cantidadAsistentes) {
        if (id == null || id.trim().isEmpty()) return "Error: el ID de la reserva no puede estar vacío.";

        Sala sala = salaRepository.buscarSalaPorId(salaId);
        if (sala == null) return "Error: la sala no existe.";
        if (!sala.isActiva()) return "Error: la sala no está activa.";
        if (cantidadAsistentes > sala.getCapacidad()) return "Error: la cantidad de asistentes supera la capacidad de la sala.";
        if (horaInicio < 0 || horaInicio > 24 || horaFin < 0 || horaFin > 24) return "Error: horas no válidas.";
        if (horaInicio >= horaFin) return "Error: la hora de inicio debe ser menor que la hora de fin.";


        for (Reserva r : reservaRepository.obtenerTodasLasReservas()) {
            if (!r.isCancelada() && r.getSalaId().equals(salaId) && r.getFecha().equals(fecha)) {
                if (!(horaFin <= r.getHoraInicio() || horaInicio >= r.getHoraFin())) {
                    return "Error: la sala ya está reservada en ese horario.";
                }
            }
        }

        Reserva nuevaReserva = new Reserva(id, salaId, fecha, horaInicio, horaFin, tipoActividad, responsable, cantidadAsistentes);

        for (ReglaReserva regla : reglas) {
            OperationResult resultado = regla.validar(nuevaReserva, sala);
            if (!resultado.isSuccess()) {
                return resultado.getMessage();
            }
        }

        reservaRepository.guardar(nuevaReserva);
        return "Reserva creada correctamente.";
    }

    public String consultarReserva(String id) {
        Reserva r = reservaRepository.buscarReservaPorId(id);
        if (r == null) return "Error: la reserva no existe.";

        Sala s = salaRepository.buscarSalaPorId(r.getSalaId());
        String estado = r.isCancelada() ? "CANCELADA" : "ACTIVA";

        return "Reserva: " + r.getId() + "\n" +
                "Sala: " + (s != null ? s.getNombre() : "Desconocida") + " (" + (s != null ? s.getTipo() : "-") + ")\n" +
                "Fecha: " + r.getFecha() + " | Horario: " + r.getHoraInicio() + "h a " + r.getHoraFin() + "h\n" +
                "Responsable: " + r.getResponsable() + " | Asistentes: " + r.getCantidadAsistentes() + "\n" +
                "Estado: " + estado;
    }

    public String cancelarReserva(String id) {
        Reserva r = reservaRepository.buscarReservaPorId(id);
        if (r == null) return "Error: la reserva no existe.";
        if (r.isCancelada()) return "Error: la reserva ya estaba cancelada.";

        r.setCancelada(true);
        return "Reserva cancelada correctamente.";
    }

    public String listarSalas() {
        // Cambiado a obtenerTodasLasSalas()
        List<Sala> todas = salaRepository.obtenerTodasLasSalas();
        if (todas.isEmpty()) return "No hay salas registradas.";

        StringBuilder sb = new StringBuilder("=== LISTADO DE SALAS ===\n");
        for (Sala s : todas) {
            sb.append("[").append(s.getId()).append("] ").append(s.getNombre())
                    .append(" - Tipo: ").append(s.getTipo())
                    .append(" - Capacidad: ").append(s.getCapacidad())
                    .append(" - Ubicación: ").append(s.getUbicacion())
                    .append(" - Estado: ").append(s.isActiva() ? "Activa" : "Inactiva")
                    .append("\n");
        }
        return sb.toString();
    }

    public String listarReservas() {

        List<Reserva> todas = reservaRepository.obtenerTodasLasReservas();
        if (todas.isEmpty()) return "No hay reservas registradas.";

        StringBuilder sb = new StringBuilder("=== LISTADO DE RESERVAS ===\n");
        for (Reserva r : todas) {
            Sala s = salaRepository.buscarSalaPorId(r.getSalaId());
            String salaNom = (s != null) ? s.getNombre() : "ID:" + r.getSalaId();

            sb.append("[").append(r.getId()).append("] Sala: ").append(salaNom)
                    .append(" | Fecha: ").append(r.getFecha())
                    .append(" | Horario: ").append(r.getHoraInicio()).append("-").append(r.getHoraFin())
                    .append(" | Resp: ").append(r.getResponsable())
                    .append(" | Estado: ").append(r.isCancelada() ? "CANCELADA" : "ACTIVA")
                    .append("\n");
        }
        return sb.toString();
    }
}