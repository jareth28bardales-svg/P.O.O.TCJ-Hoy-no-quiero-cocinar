package MODELO;

public class Repartidor extends Usuario {

    public Repartidor() { super(); }

    public Repartidor(String nombreUsuario, String contrasena) {
        super(nombreUsuario, contrasena, "REPARTIDOR", "", "", "", "");
    }

    @Override
    public void mostrarMenu() { System.out.println("Menu de Repartidor"); }
}