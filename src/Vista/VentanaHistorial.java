package Vista;

import Persistencia_requerida.DatabaseConnection;
import MODELO.Usuario;
import MODELO.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Ventana que muestra el historial de pedidos del cliente.
 * Incluye pedidos en proceso, pedidos entregados y grafica de consumo.
 *
 * @author Hector Jareth Flores Bardales
 */
public class VentanaHistorial extends JFrame {

    private Usuario usuarioActual;

    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color AZUL_OSCURO    = new Color(27, 54, 93);
    private static final Color BLANCO         = Color.WHITE;
    private static final Color GRIS_FONDO     = new Color(245, 247, 250);
    private static final Color GRIS_BORDE     = new Color(220, 220, 220);
    private static final Color GRIS_TEXTO     = new Color(130, 130, 130);
    private static final Color VERDE          = new Color(39, 174, 96);
    private static final Color NARANJA        = new Color(230, 126, 34);

    /**
     * Constructor de la ventana de historial.
     * @param usuario Usuario cliente activo
     */
    public VentanaHistorial(Usuario usuario) {
        this.usuarioActual = usuario;
        construirVentana();
    }

    /**
     * Construye y organiza todos los componentes de la ventana.
     */
    private void construirVentana() {
        setTitle("Insert Into Hunger - Mis Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GRIS_FONDO);

        add(crearFranja(), BorderLayout.NORTH);
        add(crearPanelContenido(), BorderLayout.CENTER);
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
            Image imagen = icono.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(imagen));
        } catch (Exception e) {
            lblLogo = new JLabel("🌮");
            lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        }

        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBackground(AZUL_OSCURO);

        JLabel lblNombre = new JLabel("Insert Into Hunger");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(BLANCO);

        JLabel lblEslogan = new JLabel("El sabor que nunca da error");
        lblEslogan.setFont(new Font("Arial", Font.ITALIC, 11));
        lblEslogan.setForeground(new Color(180, 200, 220));

        panelTexto.add(Box.createVerticalGlue());
        panelTexto.add(lblNombre);
        panelTexto.add(lblEslogan);
        panelTexto.add(Box.createVerticalGlue());

        panelLogo.add(lblLogo);
        panelLogo.add(panelTexto);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 12));
        panelBotones.setBackground(AZUL_OSCURO);

        JButton btnMenu   = crearBotonFranja("Menu");
        JButton btnPerfil = crearBotonFranja("Mi perfil");
        JButton btnCerrar = crearBotonFranjaRojo("Cerrar sesion");

        btnMenu.addActionListener(e -> {
            dispose();
            new VentanaMenu(usuarioActual).setVisible(true);
        });
        btnCerrar.addActionListener(e -> {
            dispose();
            new VentanaLogin().setVisible(true);
        });

        btnPerfil.addActionListener(e -> {
            dispose();
            new VentanaPerfil(usuarioActual).setVisible(true);
        });

        panelBotones.add(btnMenu);
        panelBotones.add(btnPerfil);
        panelBotones.add(btnCerrar);

        franja.add(panelLogo,    BorderLayout.WEST);
        franja.add(panelBotones, BorderLayout.EAST);
        return franja;
    }

    /**
     * Crea el contenido principal con pestanas.
     * @return Panel con pestanas
     */
    private JPanel crearPanelContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Mis Pedidos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(AZUL_OSCURO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("Arial", Font.BOLD, 13));
        pestanas.setBackground(BLANCO);

        pestanas.addTab("⏳  En Proceso", crearPanelEnProceso());
        pestanas.addTab("✅  Entregados",  crearPanelEntregados());

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(pestanas,  BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de pedidos en proceso.
     * @return Panel de pedidos pendientes
     */
    private JPanel crearPanelEnProceso() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        List<Pedido> todos = DatabaseConnection.getPedidosPorCliente(usuarioActual.getId());

        String[] columnas = {"#", "Combo", "Fecha del pedido", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        int contador = 1;
        for (Pedido p : todos) {
            if (!p.getEstado().equals("ENTREGADO")) {
                String fecha = p.getFechaPedido() != null
                        ? p.getFechaPedido().toString().substring(0, 16) : "—";
                modelo.addRow(new Object[]{
                        contador++,
                        p.getNombreCombo(),
                        fecha,
                        p.getEstado()
                });
            }
        }

        if (modelo.getRowCount() == 0) {
            JPanel panelVacio = new JPanel(new GridBagLayout());
            panelVacio.setBackground(BLANCO);
            JLabel lbl = new JLabel("No tienes pedidos en proceso 🎉");
            lbl.setFont(new Font("Arial", Font.ITALIC, 16));
            lbl.setForeground(GRIS_TEXTO);
            panelVacio.add(lbl);
            panel.add(panelVacio, BorderLayout.CENTER);
            return panel;
        }

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(AZUL_OSCURO);
        tabla.getTableHeader().setForeground(BLANCO);
        tabla.setSelectionBackground(new Color(210, 230, 255));
        tabla.setGridColor(GRIS_BORDE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_BORDE));

        // Tarjeta de resumen
        long totalEnProceso = todos.stream()
                .filter(p -> !p.getEstado().equals("ENTREGADO")).count();
        JPanel panelResumen = crearTarjetaResumen(
                "Pedidos en camino", String.valueOf(totalEnProceso), NARANJA);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(BLANCO);
        panelSuperior.add(panelResumen);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel de pedidos entregados con grafica.
     * @return Panel de entregados
     */
    private JPanel crearPanelEntregados() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        List<Pedido> todos = DatabaseConnection.getPedidosPorCliente(usuarioActual.getId());

        String[] columnas = {"#", "Combo", "Fecha pedido", "Fecha entrega", "Precio", "Veces pedido"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        Map<String, Integer> conteoPorCombo = new HashMap<>();
        double totalGastado = 0;
        int contador = 1;

        for (Pedido p : todos) {
            if (p.getEstado().equals("ENTREGADO")) {
                String fechaPedido  = p.getFechaPedido()  != null
                        ? p.getFechaPedido().toString().substring(0, 16)  : "—";
                String fechaEntrega = p.getFechaEntrega() != null
                        ? p.getFechaEntrega().toString().substring(0, 16) : "—";

                int vecesCombo = DatabaseConnection.contarPedidosCombo(
                        usuarioActual.getId(), p.getIdCombo());

                // Obtener precio del combo
                double precio = 0;
                for (MODELO.Combo c : DatabaseConnection.getCombos()) {
                    if (c.getId() == p.getIdCombo()) { precio = c.getPrecio(); break; }
                }
                totalGastado += precio;

                modelo.addRow(new Object[]{
                        contador++,
                        p.getNombreCombo(),
                        fechaPedido,
                        fechaEntrega,
                        "L. " + precio,
                        vecesCombo + "x"
                });

                conteoPorCombo.merge(p.getNombreCombo(), 1, Integer::sum);
            }
        }

        // Panel izquierdo: tabla + resumen
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(BLANCO);

        if (modelo.getRowCount() == 0) {
            JPanel panelVacio = new JPanel(new GridBagLayout());
            panelVacio.setBackground(BLANCO);
            JLabel lbl = new JLabel("Aun no tienes pedidos entregados");
            lbl.setFont(new Font("Arial", Font.ITALIC, 16));
            lbl.setForeground(GRIS_TEXTO);
            panelVacio.add(lbl);
            panel.add(panelVacio, BorderLayout.CENTER);
            return panel;
        }

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(AZUL_OSCURO);
        tabla.getTableHeader().setForeground(BLANCO);
        tabla.setSelectionBackground(new Color(210, 230, 255));
        tabla.setGridColor(GRIS_BORDE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_BORDE));

        JPanel panelResumen = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelResumen.setBackground(BLANCO);
        panelResumen.add(crearTarjetaResumen("Total entregados",
                String.valueOf(modelo.getRowCount()), VERDE));
        panelResumen.add(crearTarjetaResumen("Total gastado",
                "L. " + String.format("%.2f", totalGastado), AZUL_PRINCIPAL));

        panelIzquierdo.add(panelResumen, BorderLayout.NORTH);
        panelIzquierdo.add(scroll,       BorderLayout.CENTER);

        // Panel derecho: grafica de barras
        PanelGrafica grafica = new PanelGrafica(conteoPorCombo);
        grafica.setPreferredSize(new Dimension(300, 0));
        grafica.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                "Combos mas pedidos"));

        panel.add(panelIzquierdo, BorderLayout.CENTER);
        panel.add(grafica,        BorderLayout.EAST);
        return panel;
    }

    /**
     * Crea una tarjeta de resumen con titulo y valor.
     * @param titulo Titulo de la tarjeta
     * @param valor  Valor a mostrar
     * @param color  Color del valor
     * @return Panel de la tarjeta
     */
    private JPanel crearTarjetaResumen(String titulo, String valor, Color color) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTitulo.setForeground(GRIS_TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 26));
        lblValor.setForeground(color);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(lblValor);
        return tarjeta;
    }

    /**
     * Crea un boton para la franja de navegacion.
     * @param texto Texto del boton
     * @return Boton estilizado
     */
    private JButton crearBotonFranja(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setForeground(BLANCO);
        btn.setBackground(AZUL_OSCURO);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return btn;
    }

    /**
     * Crea un boton rojo para la franja.
     * @param texto Texto del boton
     * @return Boton rojo
     */
    private JButton crearBotonFranjaRojo(String texto) {
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

    // ---------------------------------------------------------------
    // Clase interna para la grafica de barras
    // ---------------------------------------------------------------

    /**
     * Panel que dibuja una grafica de barras con los combos mas pedidos.
     */
    private static class PanelGrafica extends JPanel {

        private final Map<String, Integer> datos;

        public PanelGrafica(Map<String, Integer> datos) {
            this.datos = datos;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (datos == null || datos.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int margenIzq  = 20;
            int margenSup  = 30;
            int margenInf  = 60;
            int anchoPanel = getWidth()  - margenIzq - 20;
            int altoPanel  = getHeight() - margenSup - margenInf;

            int maxValor = datos.values().stream().max(Integer::compareTo).orElse(1);
            int anchoBarra = anchoPanel / datos.size() - 10;

            Color[] colores = {
                    new Color(41, 128, 185),
                    new Color(39, 174, 96),
                    new Color(230, 126, 34),
                    new Color(142, 68, 173),
                    new Color(192, 57, 43)
            };

            int i = 0;
            for (Map.Entry<String, Integer> entrada : datos.entrySet()) {
                int x = margenIzq + i * (anchoBarra + 10);
                int altoBarra = (int) ((double) entrada.getValue() / maxValor * altoPanel);
                int y = margenSup + altoPanel - altoBarra;

                g2.setColor(colores[i % colores.length]);
                g2.fillRoundRect(x, y, anchoBarra, altoBarra, 8, 8);

                // Valor encima de la barra
                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                String val = String.valueOf(entrada.getValue());
                int anchoTexto = g2.getFontMetrics().stringWidth(val);
                g2.drawString(val, x + anchoBarra / 2 - anchoTexto / 2, y - 5);

                // Nombre debajo (rotado si es largo)
                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                String nombre = entrada.getKey().length() > 12
                        ? entrada.getKey().substring(0, 12) + "…"
                        : entrada.getKey();
                int anchoNombre = g2.getFontMetrics().stringWidth(nombre);
                g2.drawString(nombre,
                        x + anchoBarra / 2 - anchoNombre / 2,
                        margenSup + altoPanel + 15);
                i++;
            }
        }
    }
}
