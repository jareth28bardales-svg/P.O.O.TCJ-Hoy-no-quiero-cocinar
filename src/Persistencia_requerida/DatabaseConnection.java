package Persistencia_requerida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import MODELO.*;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:restaurante.db";
    private static Connection connection = null;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
                System.out.println("Conectado a la base de datos");
                crearTablasSiNoExisten();
            } catch (SQLException e) {
                System.out.println("Error de conexion: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Crea las tablas si no existen en la base de datos.
     */
    private static void crearTablasSiNoExisten() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS restaurante (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "direccion TEXT," +
                    "eslogan TEXT," +
                    "descripcion TEXT," +
                    "logo TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre_usuario TEXT UNIQUE NOT NULL," +
                    "contrasena TEXT NOT NULL," +
                    "rol TEXT NOT NULL," +
                    "nombre_completo TEXT NOT NULL," +
                    "direccion TEXT," +
                    "telefono TEXT," +
                    "email TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS combos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "descripcion TEXT," +
                    "precio REAL NOT NULL," +
                    "imagen TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS pedidos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_cliente INTEGER NOT NULL," +
                    "id_combo INTEGER NOT NULL," +
                    "id_repartidor INTEGER," +
                    "fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "fecha_entrega TIMESTAMP," +
                    "estado TEXT DEFAULT 'PENDIENTE'," +
                    "FOREIGN KEY (id_cliente) REFERENCES usuarios(id)," +
                    "FOREIGN KEY (id_combo) REFERENCES combos(id)," +
                    "FOREIGN KEY (id_repartidor) REFERENCES usuarios(id))");
            System.out.println("Tablas verificadas correctamente");
            insertarDatosEjemplo();
        } catch (SQLException e) {
            System.out.println("Error al crear tablas: " + e.getMessage());
        }
    }

    /**
     * Inserta datos de ejemplo si la base de datos esta vacia.
     */
    private static void insertarDatosEjemplo() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM usuarios");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Insertando datos de ejemplo...");
                stmt.execute("INSERT INTO restaurante (nombre, direccion, eslogan, descripcion) VALUES " +
                        "('Insert Into Hunger', 'Av. Tecnologia #123', 'El mejor codigo sabe rico!', 'Restaurante tematico de programacion')");
                stmt.execute("INSERT INTO usuarios (nombre_usuario, contrasena, rol, nombre_completo) VALUES ('admin', 'admin123', 'ADMIN', 'Administrador')");
                stmt.execute("INSERT INTO usuarios (nombre_usuario, contrasena, rol, nombre_completo) VALUES ('gerente', 'gerente123', 'GERENTE', 'Gerente General')");
                stmt.execute("INSERT INTO usuarios (nombre_usuario, contrasena, rol, nombre_completo) VALUES ('repartidor', 'repartidor123', 'REPARTIDOR', 'Repartidor 1')");
                stmt.execute("INSERT INTO usuarios (nombre_usuario, contrasena, rol, nombre_completo, direccion, telefono, email) VALUES ('cliente1', 'cliente123', 'CLIENTE', 'Cliente Uno', 'Calle Principal 123', '555-0101', 'cliente1@email.com')");
                stmt.execute("INSERT INTO combos (nombre, descripcion, precio) VALUES ('Combo Hello World', 'Hamburguesa con queso, papas y refresco', 120.00)");
                stmt.execute("INSERT INTO combos (nombre, descripcion, precio) VALUES ('Combo Debugger', 'Hot dog especial con tocino y bebida', 95.00)");
                stmt.execute("INSERT INTO combos (nombre, descripcion, precio) VALUES ('Combo Full Stack', 'Pizza familiar con 3 ingredientes', 280.00)");
                stmt.execute("INSERT INTO combos (nombre, descripcion, precio) VALUES ('Combo Clean Code', 'Ensalada Cesar con pollo', 110.00)");
                System.out.println("Datos de ejemplo insertados");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar datos: " + e.getMessage());
        }
    }

    /**
     * Cierra la conexion a la base de datos.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexion cerrada");
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Valida las credenciales del usuario.
     * @param username Nombre de usuario
     * @param password Contrasena
     * @return Usuario si es valido, null si no existe
     */
    public static Usuario login(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Cliente user = new Cliente();
                user.setId(rs.getInt("id"));
                user.setNombreUsuario(rs.getString("nombre_usuario"));
                user.setContrasena(rs.getString("contrasena"));
                user.setRol(rs.getString("rol"));
                user.setNombreCompleto(rs.getString("nombre_completo"));
                user.setDireccion(rs.getString("direccion"));
                user.setTelefono(rs.getString("telefono"));
                user.setEmail(rs.getString("email"));
                System.out.println("Login exitoso: " + username);
                return user;
            } else {
                System.out.println("Login fallido: " + username);
            }
        } catch (SQLException e) {
            System.out.println("Error login: " + e.getMessage());
        }
        return null;
    }

    /**
     * Registra un nuevo cliente en la base de datos.
     * @param cliente Cliente a registrar
     * @return true si fue exitoso
     */
    public static boolean registrarCliente(Usuario cliente) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, rol, nombre_completo, direccion, telefono, email) VALUES (?, ?, 'CLIENTE', ?, ?, ?, ?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombreUsuario());
            pstmt.setString(2, cliente.getContrasena());
            pstmt.setString(3, cliente.getNombreCompleto());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setString(6, cliente.getEmail());
            pstmt.executeUpdate();
            GestorUsuarios.agregarCredencial(
                    cliente.getNombreUsuario(),
                    cliente.getContrasena(),
                    "CLIENTE");
            System.out.println("Registro exitoso: " + cliente.getNombreUsuario());
            return true;
        } catch (SQLException e) {
            System.out.println("Error registro: " + e.getMessage());
            return false;
        }
    }
    /**
     * Obtiene todos los combos del menu.
     * @return Lista de combos
     */
    public static List<Combo> getCombos() {
        List<Combo> combos = new ArrayList<>();
        String sql = "SELECT * FROM combos";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Combo combo = new Combo();
                combo.setId(rs.getInt("id"));
                combo.setNombre(rs.getString("nombre"));
                combo.setDescripcion(rs.getString("descripcion"));
                combo.setPrecio(rs.getDouble("precio"));
                combo.setImagen(rs.getString("imagen"));
                combos.add(combo);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return combos;
    }
    /**
     * Crea un nuevo pedido en la base de datos.
     * @param idCliente Id del cliente
     * @param idCombo   Id del combo
     * @return true si fue exitoso
     */
    public static boolean crearPedido(int idCliente, int idCombo) {
        String sql = "INSERT INTO pedidos (id_cliente, id_combo, estado) VALUES (?, ?, 'PENDIENTE')";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            pstmt.setInt(2, idCombo);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    /**
     * Obtiene los pedidos de un cliente especifico.
     * @param idCliente Id del cliente
     * @return Lista de pedidos
     */
    public static List<Pedido> getPedidosPorCliente(int idCliente) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre_completo AS nombre_cliente, " +
                "c.nombre AS nombre_combo " +
                "FROM pedidos p " +
                "JOIN usuarios u ON p.id_cliente = u.id " +
                "JOIN combos c ON p.id_combo = c.id " +
                "WHERE p.id_cliente = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setIdCliente(rs.getInt("id_cliente"));
                pedido.setIdCombo(rs.getInt("id_combo"));
                pedido.setIdRepartidor(rs.getObject("id_repartidor") != null
                        ? rs.getInt("id_repartidor") : null);
                pedido.setFechaPedido(rs.getTimestamp("fecha_pedido"));
                pedido.setFechaEntrega(rs.getTimestamp("fecha_entrega"));
                pedido.setEstado(rs.getString("estado"));
                pedido.setNombreCliente(rs.getString("nombre_cliente"));
                pedido.setNombreCombo(rs.getString("nombre_combo"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    /**
     * Obtiene cuantas veces ha pedido un cliente un combo especifico.
     * @param idCliente Id del cliente
     * @param idCombo   Id del combo
     * @return Cantidad de veces pedido
     */
    public static int contarPedidosCombo(int idCliente, int idCombo) {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE id_cliente = ? AND id_combo = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            pstmt.setInt(2, idCombo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
    /**
     * Actualiza los datos de perfil de un usuario.
     * @param usuario Usuario con los datos actualizados
     * @return true si fue exitoso
     */
    public static boolean actualizarPerfil(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre_completo = ?, direccion = ?, " +
                "telefono = ?, email = ?, contrasena = ? WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombreCompleto());
            pstmt.setString(2, usuario.getDireccion());
            pstmt.setString(3, usuario.getTelefono());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getContrasena());
            pstmt.setInt(6, usuario.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar perfil: " + e.getMessage());
            return false;
        }
    }
    /**
     * Agrega un nuevo combo al menu.
     * @param combo Combo a agregar
     * @return true si fue exitoso
     */
    public static boolean agregarCombo(Combo combo) {
        String sql = "INSERT INTO combos (nombre, descripcion, precio, imagen) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, combo.getNombre());
            pstmt.setString(2, combo.getDescripcion());
            pstmt.setDouble(3, combo.getPrecio());
            pstmt.setString(4, combo.getImagen());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar combo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Agrega un nuevo repartidor al sistema.
     * @param repartidor Usuario con rol REPARTIDOR
     * @return true si fue exitoso
     */
    public static boolean agregarRepartidor(Usuario repartidor) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, rol, nombre_completo) " +
                "VALUES (?, ?, 'REPARTIDOR', ?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, repartidor.getNombreUsuario());
            pstmt.setString(2, repartidor.getContrasena());
            pstmt.setString(3, repartidor.getNombreCompleto());
            pstmt.executeUpdate();
            GestorUsuarios.agregarCredencial(
                    repartidor.getNombreUsuario(),
                    repartidor.getContrasena(),
                    "REPARTIDOR");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar repartidor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los pedidos del sistema con nombre de cliente y combo.
     * @return Lista de todos los pedidos
     */
    public static List<Pedido> getTodosLosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre_completo AS nombre_cliente, " +
                "c.nombre AS nombre_combo " +
                "FROM pedidos p " +
                "JOIN usuarios u ON p.id_cliente = u.id " +
                "JOIN combos c ON p.id_combo = c.id " +
                "ORDER BY p.fecha_pedido DESC";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setIdCombo(rs.getInt("id_combo"));
                p.setIdRepartidor(rs.getObject("id_repartidor") != null
                        ? rs.getInt("id_repartidor") : null);
                p.setFechaPedido(rs.getTimestamp("fecha_pedido"));
                p.setFechaEntrega(rs.getTimestamp("fecha_entrega"));
                p.setEstado(rs.getString("estado"));
                p.setNombreCliente(rs.getString("nombre_cliente"));
                p.setNombreCombo(rs.getString("nombre_combo"));
                pedidos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return pedidos;
    }

    /**
     * Obtiene todos los repartidores del sistema.
     * @return Lista de repartidores
     */
    public static List<Usuario> getRepartidores() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol = 'REPARTIDOR'";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MODELO.Repartidor r = new MODELO.Repartidor();
                r.setId(rs.getInt("id"));
                r.setNombreUsuario(rs.getString("nombre_usuario"));
                r.setNombreCompleto(rs.getString("nombre_completo"));
                lista.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Asigna un repartidor a un pedido y cambia su estado a EN_CAMINO.
     * @param idPedido     Id del pedido
     * @param idRepartidor Id del repartidor
     * @return true si fue exitoso
     */
    public static boolean asignarRepartidor(int idPedido, int idRepartidor) {
        String sql = "UPDATE pedidos SET id_repartidor = ?, estado = 'EN_CAMINO' WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idRepartidor);
            pstmt.setInt(2, idPedido);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al asignar repartidor: " + e.getMessage());
            return false;
        }
    }
    /**
     * Obtiene el pedido asignado a un repartidor que este pendiente de entrega.
     * @param idRepartidor Id del repartidor
     * @return Pedido asignado o null si no tiene
     */
    public static Pedido getPedidoAsignado(int idRepartidor) {
        String sql = "SELECT p.*, u.nombre_completo AS nombre_cliente, " +
                "c.nombre AS nombre_combo " +
                "FROM pedidos p " +
                "JOIN usuarios u ON p.id_cliente = u.id " +
                "JOIN combos c ON p.id_combo = c.id " +
                "WHERE p.id_repartidor = ? AND p.estado = 'EN_CAMINO'";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idRepartidor);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setIdCliente(rs.getInt("id_cliente"));
                p.setIdCombo(rs.getInt("id_combo"));
                p.setIdRepartidor(idRepartidor);
                p.setFechaPedido(rs.getTimestamp("fecha_pedido"));
                p.setEstado(rs.getString("estado"));
                p.setNombreCliente(rs.getString("nombre_cliente"));
                p.setNombreCombo(rs.getString("nombre_combo"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Marca un pedido como entregado y registra la fecha de entrega.
     * @param idPedido Id del pedido a marcar
     * @return true si fue exitoso
     */
    public static boolean marcarComoEntregado(int idPedido) {
        String sql = "UPDATE pedidos SET estado = 'ENTREGADO', " +
                "fecha_entrega = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, idPedido);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    /**
     * Obtiene la informacion del restaurante.
     * @return Objeto RestauranteInfo o null si no existe
     */
    public static RestauranteInfo getInfoRestaurante() {
        String sql = "SELECT * FROM restaurante LIMIT 1";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                RestauranteInfo info = new RestauranteInfo();
                info.setId(rs.getInt("id"));
                info.setNombre(rs.getString("nombre"));
                info.setDireccion(rs.getString("direccion"));
                info.setEslogan(rs.getString("eslogan"));
                info.setDescripcion(rs.getString("descripcion"));
                info.setLogo(rs.getString("logo"));
                return info;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza la informacion del restaurante.
     * @param info Objeto con los datos actualizados
     * @return true si fue exitoso
     */
    public static boolean actualizarInfoRestaurante(RestauranteInfo info) {
        String sql = "UPDATE restaurante SET nombre = ?, direccion = ?, " +
                "eslogan = ?, descripcion = ? WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, info.getNombre());
            pstmt.setString(2, info.getDireccion());
            pstmt.setString(3, info.getEslogan());
            pstmt.setString(4, info.getDescripcion());
            pstmt.setInt(5, info.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Agrega un nuevo gerente al sistema.
     * @param gerente Usuario con rol GERENTE
     * @return true si fue exitoso
     */
    public static boolean agregarGerente(Usuario gerente) {
        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, rol, nombre_completo) " +
                "VALUES (?, ?, 'GERENTE', ?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, gerente.getNombreUsuario());
            pstmt.setString(2, gerente.getContrasena());
            pstmt.setString(3, gerente.getNombreCompleto());
            pstmt.executeUpdate();
            GestorUsuarios.agregarCredencial(
                    gerente.getNombreUsuario(),
                    gerente.getContrasena(),
                    "GERENTE");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar gerente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los clientes registrados.
     * @return Lista de clientes
     */
    public static List<Usuario> getClientes() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol = 'CLIENTE'";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombreUsuario(rs.getString("nombre_usuario"));
                c.setNombreCompleto(rs.getString("nombre_completo"));
                c.setDireccion(rs.getString("direccion"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene todos los gerentes registrados.
     * @return Lista de gerentes
     */
    public static List<Usuario> getGerentes() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol = 'GERENTE'";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Gerente g = new Gerente();
                g.setId(rs.getInt("id"));
                g.setNombreUsuario(rs.getString("nombre_usuario"));
                g.setNombreCompleto(rs.getString("nombre_completo"));
                lista.add(g);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lista;
    }
    /**
     * Actualiza la imagen de un combo por su nombre.
     * @param nombreCombo  Nombre del combo a actualizar
     * @param nombreImagen Nombre del archivo de imagen en Recursos
     */
    public static void actualizarImagenCombo(String nombreCombo, String nombreImagen) {
        String sql = "UPDATE combos SET imagen = ? WHERE nombre = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, nombreImagen);
            pstmt.setString(2, nombreCombo);
            pstmt.executeUpdate();
            System.out.println("Imagen actualizada: " + nombreCombo
                    + " -> " + nombreImagen);
        } catch (SQLException e) {
            System.out.println("Error al actualizar imagen: " + e.getMessage());
        }
    }
}