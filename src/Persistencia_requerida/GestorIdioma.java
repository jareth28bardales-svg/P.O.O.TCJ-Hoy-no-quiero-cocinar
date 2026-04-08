package Persistencia_requerida;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona la carga de textos segun el idioma seleccionado.
 * Lee los archivos de idioma desde la carpeta datos/idiomas.
 *
 * @author TuNombre
 * @version 1.0
 */
public class GestorIdioma {

    /** Idioma activo en el sistema */
    private static String idiomaActivo = "es";

    /** Mapa con las claves y textos del idioma activo */
    private static Map<String, String> textos = new HashMap<>();

    /**
     * Carga el archivo de idioma seleccionado.
     * @param codigoIdioma Codigo del idioma: "es" o "en"
     */
    public static void cargarIdioma(String codigoIdioma) {
        idiomaActivo = codigoIdioma;
        textos.clear();

        String ruta = "datos/idiomas/" + codigoIdioma + ".txt";
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            System.out.println("Archivo de idioma no encontrado: " + ruta);
            return;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || !linea.contains("=")) continue;
                String[] partes = linea.split("=", 2);
                textos.put(partes[0].trim(), partes[1].trim());
            }
            System.out.println("Idioma cargado: " + codigoIdioma);
        } catch (IOException e) {
            System.out.println("Error al cargar idioma: " + e.getMessage());
        }
    }

    /**
     * Obtiene el texto correspondiente a una clave.
     * @param clave Clave del texto
     * @return Texto en el idioma activo, o la clave si no existe
     */
    public static String get(String clave) {
        return textos.getOrDefault(clave, clave);
    }

    /**
     * Retorna el codigo del idioma activo.
     * @return Codigo del idioma activo
     */
    public static String getIdiomaActivo() {
        return idiomaActivo;
    }
}
