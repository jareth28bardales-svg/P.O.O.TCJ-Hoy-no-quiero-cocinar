package MODELO;

/**
 * Representa al Cliente en el sistema "Hoy No Quiero Cocinar".
 * Es el usuario final que consulta el catálogo de combos,
 * realiza pedidos y realiza seguimiento a sus entregas.
 *
 * <p>El cliente hereda de la clase base {@link Usuario}
 * y define su comportamiento específico mediante la
 * implementación de sus métodos abstractos. Cada cliente
 * puede tener un solo pedido activo a la vez.</p>
 *
 * @author Jorge Enrique Pérez Ortiz — Informática Administrativa
 */
public class Cliente extends Usuario {

    /**
     * Constructor por defecto que inicializa un cliente
     * sin datos específicos. Delega en el constructor sin
     * parámetros de la clase padre {@link Usuario}.
     */
    public Cliente() { super(); }

    /**
     * Constructor que inicializa un cliente con credenciales
     * básicas de acceso al sistema.
     *
     * @param nombreUsuario Alias único para iniciar sesión
     * @param contrasena    Contraseña del cliente (debe ser hasheada)
     */
    public Cliente(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, "CLIENTE", "", "", "", "");
    }

    /**
     * Muestra el menú de opciones específico para el rol de cliente.
     * Este método sobrescribe el comportamiento definido en la clase padre
     * {@link Usuario} para proporcionar las opciones del catálogo
     * de combos, carrito de compras y seguimiento de pedidos.
     */
    @Override
    public void mostrarMenu() {
        System.out.println("Menu de Cliente");
    }
}
