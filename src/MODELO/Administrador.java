package MODELO;

/**
 * Representa al Administrador del sistema "Hoy No Quiero Cocinar".
 * Posee el nivel de acceso más alto del sistema, con capacidades
 * para gestionar usuarios, configuraciones globales y supervisar
 * todas las operaciones del restaurante.
 *
 * <p>El administrador hereda de la clase base {@link Usuario}
 * y define su comportamiento específico mediante la
 * implementación de sus métodos abstractos.</p>
 *
 * @author Jorge Enrique Pérez Ortiz — Informática Administrativa
 */
public class Administrador extends Usuario {

    /**
     * Constructor por defecto que inicializa un administrador
     * sin datos específicos. Delega en el constructor sin
     * parámetros de la clase padre {@link Usuario}.
     */
    public Administrador() {
        super();
    }

    /**
     * Constructor que inicializa un administrador con credenciales
     * básicas de acceso al sistema.
     *
     * @param nombreUsuario Alias único para iniciar sesión
     * @param contrasena    Contraseña del administrador (debe ser hasheada)
     */
    public Administrador(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, "ADMIN", "", "", "", "");
    }

    /**
     * Muestra el menú de opciones específico para el rol de administrador.
     * Este método sobrescribe el comportamiento definido en la clase padre
     * {@link Usuario} para proporcionar las opciones administrativas
     * como gestión de usuarios, reportes globales y configuración del sistema.
     */
    @Override
    public void mostrarMenu() {
        System.out.println("Menu de Administrador");
    }
}
