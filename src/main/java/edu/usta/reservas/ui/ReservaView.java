package edu.usta.reservas.ui;

import edu.usta.reservas.service.ReporteService;
import edu.usta.reservas.service.ReservaService;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ReservaView {
    private final ReservaService reservaService;
    private final ReporteService reporteService;

    private TextField txtSalaId, txtSalaNombre, txtSalaTipo, txtSalaCapacidad, txtSalaUbicacion;
    private TextField txtReservaId, txtReservaSalaId, txtFecha, txtHoraInicio, txtHoraFin, txtTipoActividad, txtResponsable, txtAsistentes;
    private TextArea txtResultado;

    public ReservaView(ReservaService reservaService, ReporteService reporteService) {
        this.reservaService = reservaService;
        this.reporteService = reporteService;
    }

    public Parent crearVista() {
        Label titulo = new Label("Sistema de Reserva de Salas");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        txtResultado = new TextArea();
        txtResultado.setEditable(false);
        txtResultado.setPrefHeight(200);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                titulo,
                new Label("GESTIÓN DE SALAS"), crearFormularioSalas(), crearBotonesSalas(),
                new Label("GESTIÓN DE RESERVAS"), crearFormularioReservas(), crearBotonesReservas(),
                new Label("RESULTADOS Y REPORTES"), txtResultado
        );
        return layout;
    }

    private GridPane crearFormularioSalas() {
        GridPane grid = new GridPane(); grid.setHgap(10); grid.setVgap(5);
        txtSalaId = new TextField(); txtSalaNombre = new TextField(); txtSalaTipo = new TextField(); txtSalaCapacidad = new TextField(); txtSalaUbicacion = new TextField();
        grid.add(new Label("ID Sala:"), 0, 0); grid.add(txtSalaId, 1, 0);
        grid.add(new Label("Nombre:"), 2, 0); grid.add(txtSalaNombre, 3, 0);
        grid.add(new Label("Tipo (AULA/LABORATORIO/AUDITORIO):"), 0, 1); grid.add(txtSalaTipo, 1, 1);
        grid.add(new Label("Capacidad:"), 2, 1); grid.add(txtSalaCapacidad, 3, 1);
        grid.add(new Label("Ubicación:"), 0, 2); grid.add(txtSalaUbicacion, 1, 2);
        return grid;
    }

    private GridPane crearFormularioReservas() {
        GridPane grid = new GridPane(); grid.setHgap(10); grid.setVgap(5);
        txtReservaId = new TextField(); txtReservaSalaId = new TextField(); txtFecha = new TextField(); txtHoraInicio = new TextField(); txtHoraFin = new TextField(); txtTipoActividad = new TextField(); txtResponsable = new TextField(); txtAsistentes = new TextField();
        grid.add(new Label("ID Reserva:"), 0, 0); grid.add(txtReservaId, 1, 0);
        grid.add(new Label("ID Sala:"), 2, 0); grid.add(txtReservaSalaId, 3, 0);
        grid.add(new Label("Fecha (AAAA-MM-DD):"), 0, 1); grid.add(txtFecha, 1, 1);
        grid.add(new Label("Hora Inicio (0-24):"), 2, 1); grid.add(txtHoraInicio, 3, 1);
        grid.add(new Label("Hora Fin (0-24):"), 0, 2); grid.add(txtHoraFin, 1, 2);
        grid.add(new Label("Tipo Actividad:"), 2, 2); grid.add(txtTipoActividad, 3, 2);
        grid.add(new Label("Responsable:"), 0, 3); grid.add(txtResponsable, 1, 3);
        grid.add(new Label("Asistentes:"), 2, 3); grid.add(txtAsistentes, 3, 3);
        return grid;
    }

    private HBox crearBotonesSalas() {
        Button btnRegistrar = new Button("Registrar Sala"); Button btnListar = new Button("Listar Salas");
        btnRegistrar.setOnAction(e -> registrarSala()); btnListar.setOnAction(e -> txtResultado.setText(reservaService.listarSalas()));
        return new HBox(10, btnRegistrar, btnListar);
    }

    private HBox crearBotonesReservas() {
        Button btnCrear = new Button("Crear Reserva"); Button btnConsultar = new Button("Consultar Reserva"); Button btnCancelar = new Button("Cancelar Reserva"); Button btnListar = new Button("Listar Reservas"); Button btnReporte = new Button("Generar Reporte"); Button btnLimpiar = new Button("Limpiar Campos");
        btnCrear.setOnAction(e -> crearReserva()); btnConsultar.setOnAction(e -> txtResultado.setText(reservaService.consultarReserva(txtReservaId.getText()))); btnCancelar.setOnAction(e -> txtResultado.setText(reservaService.cancelarReserva(txtReservaId.getText()))); btnListar.setOnAction(e -> txtResultado.setText(reservaService.listarReservas())); btnReporte.setOnAction(e -> txtResultado.setText(reporteService.generarReporteGeneral())); btnLimpiar.setOnAction(e -> limpiarCampos());
        return new HBox(10, btnCrear, btnConsultar, btnCancelar, btnListar, btnReporte, btnLimpiar);
    }

    private void registrarSala() {
        try {
            txtResultado.setText(reservaService.registrarSala(txtSalaId.getText(), txtSalaNombre.getText(), txtSalaTipo.getText(), Integer.parseInt(txtSalaCapacidad.getText()), txtSalaUbicacion.getText()));
        } catch (Exception e) { txtResultado.setText("Error: Capacidad inválida."); }
    }

    private void crearReserva() {
        try {
            txtResultado.setText(reservaService.crearReserva(txtReservaId.getText(), txtReservaSalaId.getText(), txtFecha.getText(), Integer.parseInt(txtHoraInicio.getText()), Integer.parseInt(txtHoraFin.getText()), txtTipoActividad.getText(), txtResponsable.getText(), Integer.parseInt(txtAsistentes.getText())));
        } catch (Exception e) { txtResultado.setText("Error: Datos numéricos inválidos."); }
    }

    private void limpiarCampos() {
        txtSalaId.clear(); txtSalaNombre.clear(); txtSalaTipo.clear(); txtSalaCapacidad.clear(); txtSalaUbicacion.clear();
        txtReservaId.clear(); txtReservaSalaId.clear(); txtFecha.clear(); txtHoraInicio.clear(); txtHoraFin.clear(); txtTipoActividad.clear(); txtResponsable.clear(); txtAsistentes.clear(); txtResultado.clear();
    }
}
