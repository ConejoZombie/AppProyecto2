/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package proyecto.pkg2.javafx;

import proyecto.pkg2.javafx.modelo.Tarea;
import proyecto.pkg2.javafx.modelo.Nodo;
import java.io.Console;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.*;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author HEWLETT PACKARD
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TableColumn<?, ?> EstadoColumn;

    @FXML
    private TableColumn<?, ?> IDColumn;

    @FXML
    private TableColumn<?, ?> PrioridadColumn;

    @FXML
    private TableColumn<?, ?> TareaColumn;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private ComboBox<String> cboEstado;

    @FXML
    private ComboBox<String> cboPrioridad;

    @FXML
    private Label label;

    @FXML
    private TableView<?> table;

    @FXML
    private TextField txtTarea;

    @FXML
    void Crear(ActionEvent event) {

    }

    @FXML
    void Editar(ActionEvent event) {

    }

    @FXML
    void Eliminar(ActionEvent event) {

    }
    
    public ObservableList<Tarea> registros = FXCollections.observableArrayList();

    private Nodo cabeza;

    public void ListaTareas() {
        this.cabeza = null;
    }

    public int ObtenerValorPrioridad() {
        int valor = 0;
        switch (cboPrioridad.getValue()) {
            case "Alta":
                valor = 3;
                break;
            case "Media":
                valor = 2;
                break;
            case "Baja":
                valor = 1;
                break;
            default:
                throw new AssertionError();
        }
        return valor;
    }

    public String EstablecerValorPrioridad( int prioridad) {
        String valor = "";
        switch (prioridad) {
            case 1:
                valor = "Baja";
                break;
            case 2:
                valor = "Media";
                break;
            case 3:
                valor = "Alta";
                break;
            default:
                throw new AssertionError();
        }
        return valor;
    }

    @FXML
    void GuardarTest(ActionEvent event) {

        ObtenerValorPrioridad();

        try {
            Tarea tarea = new Tarea(txtTarea.getText(), ObtenerValorPrioridad(), cboEstado.getValue());

            ListaTareas();

            Nodo nuevoNodo = new Nodo(tarea);

            // Si la lista está vacía, el nuevo nodo será la cabeza
            if (cabeza == null) {
                cabeza = nuevoNodo;
            } else {
                Nodo actual = cabeza;
                Nodo anterior = null;

                // Recorremos la lista para encontrar la posición adecuada según la prioridad
                while (actual != null && actual.getTarea().getPrioridad() >= tarea.getPrioridad()) {
                    anterior = actual;
                    actual = actual.getSiguiente();
                }

                // Insertamos el nuevo nodo después del nodo anterior
                nuevoNodo.setSiguiente(actual);
                if (anterior != null) {
                    anterior.setSiguiente(nuevoNodo);
                } else {
                    // Si el nuevo nodo es la nueva cabeza de la lista
                    cabeza = nuevoNodo;
                }
            }

            System.out.println("funciona :)" + cabeza.getTarea());
            EnviarATabla(cabeza.getTarea().getDescripcion(), 0, cabeza.getTarea().getEstado());
            imprimir();

        } catch (Exception e) {
            System.out.println("error :(" + e);
        }
    }
    
    public void EnviarATabla(String descripcion, int estado, String prioridad) {
//        TareaColumn.setCellValueFactory(new PropertyValueFactory<Tarea, String>(descripcion));
//        TareaColumn.setCellFactory((TableColumn.CellDataFeatures<Tarea, String> cellData) -> cellData.getValue().getDescripcion());
//        registros.add(new Tarea(descripcion, estado, prioridad));
//         TareaColumn.setCellValueFactory(
//                (TableColumn.CellDataFeatures<Tarea, String> cellData) -> cellData.getValue().getDescripcion());
        TareaColumn.setCellValueFactory(new PropertyValueFactory<>(descripcion));  
    
    
    

    }

    public void imprimir() {
        Nodo nodoActual = cabeza;
        String contenido = "";
        while (nodoActual != null) {
            contenido += nodoActual.getTarea().getDescripcion();
            nodoActual = nodoActual.getSiguiente();
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Contenido de la lista enlazada");
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
        EnviarATabla(contenido, 1, "Alta");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//Configuracion para el Combo box que muestra el Estado de la tarea.
        String[] opciones = {"Pendiente", "En proceso", "Cancelado", "Terminado"};
        // Este Arraylist hay que pasarla a linkList
        ObservableList<String> items = FXCollections.observableArrayList(opciones);

        cboEstado.setItems(items);
        cboEstado.setValue("Seleccione");

        String[] opcionesPrioridad = {"Alta", "Media", "Baja"};
        // Este Arraylist hay que pasarla a linkList
        ObservableList<String> itemsPrioridad = FXCollections.observableArrayList(opcionesPrioridad);

        cboPrioridad.setItems(itemsPrioridad);
        cboPrioridad.setValue("Seleccione");
    }

}
