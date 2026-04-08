//@author Jorge Enrique Pérez Ortiz — Informática Administrativa
package Persistencia_requerida;
import java.io.*;

/*
 Gestiona la persistencia de la sesión activa.
 Guarda el usuario logueado en un archivo para
 que persista aunque el programa se cierre.*/

public class GestorSesion {

    /** Ruta del archivo de sesión */
    private static final String RUTA_SESION = "datos/sesion.txt";

    public void guardarSesion(String nombreUsuario, String perfil) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_SESION))) {
            escritor.println(nombreUsuario);
            escritor.println(perfil);
        } catch (IOException e) {
            System.err.println("Error al guardar sesión: " + e.getMessage());
        }
    }

    public String[] cargarSesion() {
        File archivo = new File(RUTA_SESION);
        if (!archivo.exists()) return null;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String usuario = lector.readLine();
            String perfil  = lector.readLine();
            if (usuario != null && perfil != null) {
                return new String[]{usuario, perfil};
            }
        } catch (IOException e) {
            System.err.println("Error al cargar sesión: " + e.getMessage());
        }
        return null;
    }

    public void cerrarSesion()
    {
        new File(RUTA_SESION).delete();
    }
    public boolean haySesionActiva() {
        return new File(RUTA_SESION).exists();
    }
}
