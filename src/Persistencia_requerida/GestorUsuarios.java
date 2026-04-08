package Persistencia_requerida;

import MODELO.*;

import java.io.*;

/**
 * Gestiona la validacion de credenciales de usuarios.
 * Lee las credenciales desde un archivo de texto externo
 * y delega la carga del perfil completo a la base de datos.
 *
 * @author Jorge Enrique Pérez Ortiz — Informática Administrativa
 */
public class GestorUsuarios {

    /** Ruta del archivo de credenciales */
    private static final String RUTA_CREDENCIALES = "src/Datos/credenciales.txt";

    /**
     * Valida las credenciales del usuario comparando contra
     * el archivo de texto y la base de datos.
     * @param nombreUsuario Nombre de usuario ingresado
     * @param contrasena    Contrasena ingresada
     * @return Usuario valido o null si las credenciales son incorrectas
     */
    public Usuario validarCredenciales(String nombreUsuario, String contrasena) {
        // Primero verificar contra el archivo de texto
        if (!verificarEnArchivo(nombreUsuario, contrasena)) {
            // Si no esta en el archivo, verificar en la BD
            // (para clientes registrados por la app)
            Usuario usuario = DatabaseConnection.login(nombreUsuario, contrasena);
            if (usuario == null) return null;
            return crearUsuarioSegunPerfil(usuario);
        }

        // Si esta en el archivo, cargar perfil completo desde BD
        Usuario usuario = DatabaseConnection.login(nombreUsuario, contrasena);
        if (usuario == null) return null;
        return crearUsuarioSegunPerfil(usuario);
    }

    /**
     * Verifica si las credenciales existen en el archivo de texto.
     * @param nombreUsuario Nombre de usuario a buscar
     * @param contrasena    Contrasena a verificar
     * @return true si las credenciales coinciden en el archivo
     */
    private boolean verificarEnArchivo(String nombreUsuario, String contrasena) {
        File archivo = buscarArchivo();

        if (archivo == null) {
            System.out.println("Archivo de credenciales no encontrado," +
                    " usando solo BD.");
            return false;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;

                String[] partes = linea.split(",");
                if (partes.length < 2) continue;

                String usuario = partes[0].trim();
                String clave   = partes[1].trim();

                if (usuario.equals(nombreUsuario) && clave.equals(contrasena)) {
                    System.out.println("Credenciales verificadas en archivo: "
                            + nombreUsuario);
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer credenciales: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca el archivo de credenciales en varias rutas posibles.
     * @return Archivo encontrado o null si no existe
     */
    private File buscarArchivo() {
        String[] rutas = {
                RUTA_CREDENCIALES,
                "Datos/credenciales.txt",
                System.getProperty("user.dir") + "/src/Datos/credenciales.txt"
        };

        for (String ruta : rutas) {
            File f = new File(ruta);
            if (f.exists()) {
                System.out.println("Archivo de credenciales encontrado: " + ruta);
                return f;
            }
        }
        return null;
    }

    /**
     * Agrega un nuevo usuario al archivo de credenciales.
     * Se usa al registrar clientes nuevos o agregar empleados.
     * @param nombreUsuario Nombre de usuario
     * @param contrasena    Contrasena
     * @param rol           Rol del usuario
     */
    public static void agregarCredencial(String nombreUsuario,
                                         String contrasena, String rol) {
        File archivo = new File(RUTA_CREDENCIALES);

        // Crear carpeta si no existe
        if (!archivo.getParentFile().exists()) {
            archivo.getParentFile().mkdirs();
        }

        try (PrintWriter escritor = new PrintWriter(
                new FileWriter(archivo, true))) {
            escritor.println(nombreUsuario + "," + contrasena + "," + rol);
            System.out.println("Credencial agregada al archivo: "
                    + nombreUsuario);
        } catch (IOException e) {
            System.out.println("Error al guardar credencial: "
                    + e.getMessage());
        }
    }

    /**
     * Crea el objeto de usuario correcto segun su perfil.
     * @param usuario Usuario base obtenido de la BD
     * @return Usuario con el tipo correcto segun su rol
     */
    private Usuario crearUsuarioSegunPerfil(Usuario usuario) {
        Usuario usuarioFinal;
        switch (usuario.getPerfil().toUpperCase()) {
            case "ADMIN":
                usuarioFinal = new Administrador(
                        usuario.getNombreUsuario(), usuario.getContrasena());
                break;
            case "GERENTE":
                usuarioFinal = new Gerente(
                        usuario.getNombreUsuario(), usuario.getContrasena());
                break;
            case "REPARTIDOR":
                usuarioFinal = new Repartidor(
                        usuario.getNombreUsuario(), usuario.getContrasena());
                break;
            default:
                usuarioFinal = new Cliente(
                        usuario.getNombreUsuario(), usuario.getContrasena());
                break;
        }
        usuarioFinal.setId(usuario.getId());
        usuarioFinal.setNombreCompleto(usuario.getNombreCompleto());
        usuarioFinal.setDireccion(usuario.getDireccion());
        usuarioFinal.setTelefono(usuario.getTelefono());
        usuarioFinal.setEmail(usuario.getEmail());
        return usuarioFinal;
    }
}
