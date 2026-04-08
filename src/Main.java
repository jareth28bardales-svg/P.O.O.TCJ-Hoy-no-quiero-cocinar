// @author Hector Jareth Flores Bardales
import Vista.VentanaIdioma;
import javax.swing.SwingUtilities;

/**
 * Clase principal del sistema de restaurante.
 * Punto de entrada del programa.
 */
public class Main {
    public static void main(String[] args) {
        Persistencia_requerida.DatabaseConnection.getConnection();
        Persistencia_requerida.DatabaseConnection.actualizarImagenCombo(
                "Combo Hello World", "Imagen2.jpeg");
        Persistencia_requerida.DatabaseConnection.actualizarImagenCombo(
                "Combo Debugger", "Imagen3.png");
        Persistencia_requerida.DatabaseConnection.actualizarImagenCombo(
                "Combo Full Stack", "Imagen4.png");
        Persistencia_requerida.DatabaseConnection.actualizarImagenCombo(
                "Combo Clean Code", "Imagen5.png");

        SwingUtilities.invokeLater(() -> {
            new VentanaIdioma().setVisible(true);
        });
    }
}
