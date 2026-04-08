package MODELO;

public class Administrador extends Usuario {

    public Administrador() { super(); }

    public Administrador(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, "ADMIN", "", "", "", "");
    }

    @Override
    public void mostrarMenu() { System.out.println("Menu de Administrador"); }
}