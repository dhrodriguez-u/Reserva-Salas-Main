package edu.usta.reservas.ui;

import edu.usta.reservas.repository.InMemoryRepository;
import edu.usta.reservas.service.ReporteService;
import edu.usta.reservas.service.ReservaService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        InMemoryRepository repository = new InMemoryRepository();
        ReservaService reservaService = new ReservaService(repository, repository);
        ReporteService reporteService = new ReporteService(repository, repository);

        cargarDatosIniciales(reservaService);

        ReservaView reservaView = new ReservaView(reservaService, reporteService);
        Scene scene = new Scene(reservaView.crearVista(), 980, 680);

        stage.setTitle("Reserva de Salas - Proyecto Refactorizado SOLID");
        stage.setScene(scene);
        stage.show();
    }

    private void cargarDatosIniciales(ReservaService reservaService) {
        reservaService.registrarSala("S001", "Aula 101", "AULA", 35, "Bloque A");
        reservaService.registrarSala("S002", "Laboratorio de Sistemas", "LABORATORIO", 25, "Bloque B");
        reservaService.registrarSala("S003", "Auditorio Principal", "AUDITORIO", 120, "Bloque Central");

        reservaService.crearReserva("R001", "S001", "2026-05-25", 8, 10, "CLASE", "Ing. Pérez", 30);
        reservaService.crearReserva("R002", "S002", "2026-05-25", 10, 12, "PRACTICA", "Docente", 20);
    }

    public static void main(String[] args) { launch(args); }
}
