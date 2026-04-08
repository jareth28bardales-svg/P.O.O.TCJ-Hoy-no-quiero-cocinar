package MODELO;

public abstract class Usuario {

    protected int id;
    protected String nombreUsuario;
    protected String contrasena;
    protected String rol;
    protected String nombreCompleto;
    protected String direccion;
    protected String telefono;
    protected String email;

    public Usuario() {}

    public Usuario(String nombreUsuario, String contrasena, String rol,
                   String nombreCompleto, String direccion, String telefono, String email) {
        this.nombreUsuario  = nombreUsuario;
        this.contrasena     = contrasena;
        this.rol            = rol;
        this.nombreCompleto = nombreCompleto;
        this.direccion      = direccion;
        this.telefono       = telefono;
        this.email          = email;
    }

    public abstract void mostrarMenu();

    public int getId()                              { return id; }
    public void setId(int id)                       { this.id = id; }

    public String getNombreUsuario()                    { return nombreUsuario; }
    public void setNombreUsuario(String n)              { this.nombreUsuario = n; }

    public String getContrasena()                   { return contrasena; }
    public void setContrasena(String c)             { this.contrasena = c; }

    public String getRol()                          { return rol; }
    public void setRol(String rol)                  { this.rol = rol; }

    public String getPerfil()                       { return rol; }
    public void setPerfil(String perfil)            { this.rol = perfil; }

    public String getNombreCompleto()                       { return nombreCompleto; }
    public void setNombreCompleto(String n)                 { this.nombreCompleto = n; }

    public String getDireccion()                    { return direccion; }
    public void setDireccion(String d)              { this.direccion = d; }

    public String getTelefono()                     { return telefono; }
    public void setTelefono(String t)               { this.telefono = t; }

    public String getEmail()                        { return email; }
    public void setEmail(String e)                  { this.email = e; }
}