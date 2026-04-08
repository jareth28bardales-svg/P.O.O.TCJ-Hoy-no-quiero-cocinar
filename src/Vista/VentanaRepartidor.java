package Vista;

import Persistencia_requerida.DatabaseConnection;
import MODELO.Usuario;
import MODELO.Pedido;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana del modulo de Repartidor.
 * Permite ver el pedido asignado y marcarlo como entregado.
 *
 * @author Antony Hernandez - 20232100056 - Programacion Intermedia - DIA065-0800
 */
public class VentanaRepartidor extends JFrame {

    private Usuario usuarioActual;
    private JPanel panelCentral;

    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color AZUL_OSCURO    = new Color(27, 54, 93);
    private static final Color BLANCO         = Color.WHITE;
    private static final Color GRIS_FONDO     = new Color(245, 247, 250);
    private static final Color GRIS_BORDE     = new Color(220, 220, 220);
    private static final Color GRIS_TEXTO     = new Color(130, 130, 130);
    private static final Color VERDE          = new Color(39, 174, 96);
    private static final Color NARANJA        = new Color(230, 126, 34);

    /**
     * Constructor de la ventana del repartidor.
     * @param usuario Repartidor que inicio sesion
     */
    public VentanaRepartidor(Usuario usuario) {
        this.usuarioActual = usuario;
        construirVentana();
    }

    /**
     * Construye y organiza todos los componentes de la ventana.
     */
    private void construirVentana() {
        setTitle("Insert Into Hunger - Repartidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GRIS_FONDO);

        add(crearFranja(), BorderLayout.NORTH);

        panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(GRIS_FONDO);
        add(panelCentral, BorderLayout.CENTER);

        cargarEstadoEntrega();
    }

    /**
     * Crea la franja de navegacion superior.
     * @return Panel de la franja
     */
    private JPanel crearFranja() {
        JPanel franja = new JPanel(new BorderLayout());
        franja.setBackground(AZUL_OSCURO);
        franja.setPreferredSize(new Dimension(0, 65));
        franja.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelLogo.setBackground(AZUL_OSCURO);

        JLabel lblLogo;
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/Recursos/LOG.png"));
            Image img = icono.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo = new JLabel("🌮");
            lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        }

        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBackground(AZUL_OSCURO);

        JLabel lblNombre = new JLabel("Insert Into Hunger  —  Repartidor");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(BLANCO);

        JLabel lblSub = new JLabel("Bienvenido, " + usuarioActual.getNombreCompleto());
        lblSub.setFont(new Font("Arial", Font.ITALIC, 11));
        lblSub.setForeground(new Color(180, 200, 220));

        panelTexto.add(Box.createVerticalGlue());
        panelTexto.add(lblNombre);
        panelTexto.add(lblSub);
        panelTexto.add(Box.createVerticalGlue());

        panelLogo.add(lblLogo);
        panelLogo.add(panelTexto);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 12));
        panelBotones.setBackground(AZUL_OSCURO);

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnRefrescar.setForeground(BLANCO);
        btnRefrescar.setBackground(AZUL_PRINCIPAL);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btnRefrescar.addActionListener(e -> cargarEstadoEntrega());

        JButton btnCerrar = crearBotonRojo("Cerrar sesion");
        btnCerrar.addActionListener(e -> {
            dispose();
            new VentanaLogin().setVisible(true);
        });

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);

        franja.add(panelLogo,    BorderLayout.WEST);
        franja.add(panelBotones, BorderLayout.EAST);
        return franja;
    }

    /**
     * Consulta si hay una entrega asignada y actualiza el panel central.
     */
    private void cargarEstadoEntrega() {
        panelCentral.removeAll();

        Pedido pedido = DatabaseConnection.getPedidoAsignado(usuarioActual.getId());

        if (pedido == null) {
            mostrarSinEntrega();
        } else {
            mostrarEntregaAsignada(pedido);
        }

        panelCentral.revalidate();
        panelCentral.repaint();
    }

    /**
     * Muestra el estado cuando no hay entregas asignadas.
     */
    private void mostrarSinEntrega() {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(50, 60, 50, 60)
        ));

        JLabel lblIcono = new JLabel("🛵", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Sin entregas por ahora");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(AZUL_OSCURO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Cuando el gerente te asigne un pedido, aparecera aqui.");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSub.setForeground(GRIS_TEXTO);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRefrescar = new JButton("Verificar nuevamente");
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRefrescar.setBackground(AZUL_PRINCIPAL);
        btnRefrescar.setForeground(BLANCO);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRefrescar.addActionListener(e -> cargarEstadoEntrega());

        tarjeta.add(lblIcono);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(lblSub);
        tarjeta.add(Box.createVerticalStrut(30));
        tarjeta.add(btnRefrescar);

        panelCentral.add(tarjeta);
    }

    /**
     * Muestra la tarjeta con los datos de la entrega asignada.
     * @param pedido Pedido asignado al repartidor
     */
    private void mostrarEntregaAsignada(Pedido pedido) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        // Encabezado con estado
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEstado.setBackground(NARANJA);
        panelEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel lblEstado = new JLabel("● EN CAMINO");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstado.setForeground(BLANCO);
        panelEstado.add(lblEstado);

        JLabel lblIcono = new JLabel("📦", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Tienes una entrega asignada");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(AZUL_OSCURO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(400, 1));
        sep.setForeground(GRIS_BORDE);

        // Detalles del pedido
        JPanel panelDetalles = new JPanel(new GridLayout(0, 2, 10, 8));
        panelDetalles.setBackground(BLANCO);
        panelDetalles.setMaximumSize(new Dimension(420, 200));

        agregarFila(panelDetalles, "N° de pedido:",  "#" + pedido.getId());
        agregarFila(panelDetalles, "Cliente:",        pedido.getNombreCliente());
        agregarFila(panelDetalles, "Combo:",          pedido.getNombreCombo());
        agregarFila(panelDetalles, "Fecha de pedido:",
                pedido.getFechaPedido() != null
                        ? pedido.getFechaPedido().toString().substring(0, 16) : "—");

        // Boton de confirmar entrega
        JButton btnEntregado = new JButton("✓  Marcar como entregado");
        btnEntregado.setFont(new Font("Arial", Font.BOLD, 15));
        btnEntregado.setBackground(VERDE);
        btnEntregado.setForeground(BLANCO);
        btnEntregado.setBorderPainted(false);
        btnEntregado.setFocusPainted(false);
        btnEntregado.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntregado.setMaximumSize(new Dimension(300, 48));
        btnEntregado.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntregado.addActionListener(e -> confirmarEntrega(pedido.getId()));

        tarjeta.add(panelEstado);
        tarjeta.add(Box.createVerticalStrut(25));
        tarjeta.add(lblIcono);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(sep);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(panelDetalles);
        tarjeta.add(Box.createVerticalStrut(30));
        tarjeta.add(btnEntregado);

        panelCentral.add(tarjeta);
    }

    /**
     * Agrega una fila de etiqueta y valor al panel de detalles.
     * @param panel  Panel donde agregar la fila
     * @param label  Texto de la etiqueta
     * @param valor  Valor a mostrar
     */
    private void agregarFila(JPanel panel, String label, String valor) {
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 13));
        lblLabel.setForeground(GRIS_TEXTO);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 13));
        lblValor.setForeground(AZUL_OSCURO);

        panel.add(lblLabel);
        panel.add(lblValor);
    }

    /**
     * Confirma la entrega del pedido asignado.
     * @param idPedido Id del pedido a marcar como entregado
     */
    private void confirmarEntrega(int idPedido) {
        int opcion = JOptionPane.showConfirmDialog(this,
                "Confirmas que el pedido #" + idPedido + " fue entregado?",
                "Confirmar entrega",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            if (DatabaseConnection.marcarComoEntregado(idPedido)) {
                JOptionPane.showMessageDialog(this,
                        "Entrega confirmada correctamente.",
                        "Entregado", JOptionPane.INFORMATION_MESSAGE);
                cargarEstadoEntrega();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al confirmar la entrega.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Crea un boton rojo para la franja.
     * @param texto Texto del boton
     * @return Boton rojo
     */
    private JButton crearBotonRojo(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(BLANCO);
        btn.setBackground(new Color(192, 57, 43));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return btn;
    }
}
