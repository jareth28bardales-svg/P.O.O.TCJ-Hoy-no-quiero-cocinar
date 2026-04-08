package MODELO;

public class RestauranteInfo {
    private int id;
    private String nombre;
    private String direccion;
    private String eslogan;
    private String descripcion;
    private String logo;

    public RestauranteInfo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getEslogan() { return eslogan; }
    public void setEslogan(String eslogan) { this.eslogan = eslogan; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
}