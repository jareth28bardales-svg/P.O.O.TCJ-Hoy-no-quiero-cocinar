package MODELO;

import java.sql.Timestamp;

public class Pedido {
    private int id;
    private int idCliente;
    private int idCombo;
    private Integer idRepartidor;
    private Timestamp fechaPedido;
    private Timestamp fechaEntrega;
    private String estado;
    private String nombreCliente;
    private String nombreCombo;

    public Pedido() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public int getIdCombo() { return idCombo; }
    public void setIdCombo(int idCombo) { this.idCombo = idCombo; }
    public Integer getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(Integer idRepartidor) { this.idRepartidor = idRepartidor; }
    public Timestamp getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(Timestamp fechaPedido) { this.fechaPedido = fechaPedido; }
    public Timestamp getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(Timestamp fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public String getNombreCombo() { return nombreCombo; }
    public void setNombreCombo(String nombreCombo) { this.nombreCombo = nombreCombo; }
}