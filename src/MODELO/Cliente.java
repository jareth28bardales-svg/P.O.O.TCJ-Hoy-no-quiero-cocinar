package MODELO;

public class Cliente extends Usuario {

    public Cliente() { super(); }

    public Cliente(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, "CLIENTE", "", "", "", "");
    }

    @Override
    public void mostrarMenu() { System.out.println("Menu de Cliente"); }
}