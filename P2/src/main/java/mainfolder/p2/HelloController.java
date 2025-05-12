package mainfolder.p2;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.time.LocalDate;

public class HelloController {

    @FXML private TextField niaTextField;
    @FXML private TextField nombreTextField;
    @FXML private DatePicker fechaNacPicker;
    @FXML private TableView<Student> tablaEstudiantes;
    @FXML private TableColumn<Student, Integer> columnaNIA;
    @FXML private TableColumn<Student, String> columnaNombre;
    @FXML private TableColumn<Student, LocalDate> columnaFechaNac;
    @FXML private Button insertarButton;
    @FXML private Button guardarButton;

    private final String url = "jdbc:mariadb://localhost:3307/practica2";
    private final String user = "root";
    private final String password = "";
    private boolean enEdicion = false;
    private Student estudianteEditando = null;

    @FXML
    public void initialize() {

        columnaNIA.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getNia()));
        columnaNombre.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getName()));
        columnaFechaNac.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getBornDate()));

        cargarEstudiantes();
        guardarButton.setDisable(true);
        tablaEstudiantes.setOnMouseClicked(this::seleccionarFila);

    }

    private void cargarEstudiantes() {

        ObservableList<Student> lista = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(url, user, password);

             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM estudiantes")) {

            while (resultSet.next()) {

                lista.add(new Student(
                        resultSet.getInt("nia"),
                        resultSet.getString("nombre"),
                        resultSet.getDate("fecha_nacimiento").toLocalDate()));

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        tablaEstudiantes.setItems(lista);

    }

    @FXML
    public void insertar() {

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO estudiantes (nia, nombre, fecha_nacimiento) VALUES (");
        sql.append(niaTextField.getText()).append(", ");
        sql.append("'").append(nombreTextField.getText()).append("', ");
        sql.append("'").append(Date.valueOf(fechaNacPicker.getValue())).append("')");

        try (Connection conn = DriverManager.getConnection(url, user, password);

             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql.toString());

        } catch (SQLException e) {

            e.printStackTrace();

        }

        cargarEstudiantes();
        limpiarCampos();

    }

    @FXML
    public void eliminar() {

        Student seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM estudiantes WHERE nia = ").append(seleccionado.getNia());

            try (Connection conn = DriverManager.getConnection(url, user, password);

                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql.toString());

            } catch (SQLException e) {

                e.printStackTrace();

            }

            cargarEstudiantes();
            limpiarCampos();

        } else {

            System.out.println("No hay ninguna fila seleccionada.");

        }

    }

    @FXML
    public void actualizar() {

        Student seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {

            niaTextField.setText(String.valueOf(seleccionado.getNia()));
            nombreTextField.setText(seleccionado.getName());
            fechaNacPicker.setValue(seleccionado.getBornDate());

            guardarButton.setDisable(false);
            insertarButton.setDisable(true);
            estudianteEditando = seleccionado;
            enEdicion = true;

        } else {

            System.out.println("No hay ninguna fila seleccionada.");

        }

    }

    @FXML
    public void guardar() {

        if (!enEdicion || estudianteEditando == null) return;

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE estudiantes SET ");
        sql.append("nombre = '").append(nombreTextField.getText()).append("', ");
        sql.append("fecha_nacimiento = '").append(Date.valueOf(fechaNacPicker.getValue())).append("' ");
        sql.append("WHERE nia = ").append(estudianteEditando.getNia());

        try (Connection conn = DriverManager.getConnection(url, user, password);

             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql.toString());

        } catch (SQLException e) {

            e.printStackTrace();

        }

        cargarEstudiantes();
        limpiarCampos();
        guardarButton.setDisable(true);
        insertarButton.setDisable(false);
        enEdicion = false;
        estudianteEditando = null;

    }

    private void seleccionarFila(MouseEvent event) {

        if (event.getClickCount() == 1) {

            Student seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();

            if (seleccionado != null) {

                niaTextField.setText(String.valueOf(seleccionado.getNia()));
                nombreTextField.setText(seleccionado.getName());
                fechaNacPicker.setValue(seleccionado.getBornDate());

            }

        }

    }

    private void limpiarCampos() {

        niaTextField.clear();
        nombreTextField.clear();
        fechaNacPicker.setValue(null);

    }

}