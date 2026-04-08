package MODELO;

public class Gerente extends Usuario {

    public Gerente() { super(); }

    public Gerente(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, "GERENTE", "", "", "", "");
    }

    @Override
    public void mostrarMenu() { System.out.println("Menu de Gerente"); }
}