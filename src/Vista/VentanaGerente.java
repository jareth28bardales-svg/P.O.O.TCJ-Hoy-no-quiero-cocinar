package Vista;

import Persistencia_requerida.DatabaseConnection;
import MODELO.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Ventana principal del modulo de Gerente.
 * Permite gestionar combos, repartidores, pedidos y ver estadisticas.
 *
 * @author Wilmer Isai Amador Artega
 */
public class VentanaGerente extends JFrame {

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
     * Constructor de la ventana del gerente.
     * @param usuario Gerente que inicio sesion
     */
    public VentanaGerente(Usuario usuario) {
        this.usuarioActual = usuario;
        construirVentana();
    }

    /**
     * Construye y organiza todos los componentes de la ventana.
     */
    private void construirVentana() {
        setTitle("Insert Into Hunger - Gerente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GRIS_FONDO);

        add(crearFranja(),   BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
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

        JLabel lblNombre = new JLabel("Insert Into Hunger  —  Gerente");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(BLANCO);

        JLabel lblUsuario = new JLabel("Bienvenido, " + usuarioActual.getNombreCompleto());
        lblUsuario.setFont(new Font("Arial", Font.ITALIC, 11));
        lblUsuario.setForeground(new Color(180, 200, 220));

        panelTexto.add(Box.createVerticalGlue());
        panelTexto.add(lblNombre);
        panelTexto.add(lblUsuario);
        panelTexto.add(Box.createVerticalGlue());

        panelLogo.add(lblLogo);
        panelLogo.add(panelTexto);

        JButton btnCerrar = crearBotonFranjaRojo("Cerrar sesion");
        btnCerrar.addActionListener(e -> {
            dispose();
            new VentanaLogin().setVisible(true);
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 12));
        panelBotones.setBackground(AZUL_OSCURO);
        panelBotones.add(btnCerrar);

        franja.add(panelLogo,    BorderLayout.WEST);
        franja.add(panelBotones, BorderLayout.EAST);
        return franja;
    }

    /**
     * Crea el contenido principal con pestanas.
     * @return Panel con pestanas
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setFont(new Font("Arial", Font.BOLD, 13));

        pestanas.addTab("🍔  Combos",         crearPanelCombos());
        pestanas.addTab("🛵  Repartidores",   crearPanelRepartidores());
        pestanas.addTab("📋  Pedidos",        crearPanelPedidos());
        pestanas.addTab("📊  Estadisticas",   crearPanelEstadisticas());

        panel.add(pestanas, BorderLayout.CENTER);
        return panel;
    }

    // ================================================================
    // PESTAÑA 1 — COMBOS
    // ================================================================

    /**
     * Crea el panel de gestion de combos del menu.
     * @return Panel de combos
     */
    private JPanel crearPanelCombos() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tabla de combos
        String[] cols = {"ID", "Nombre", "Descripcion", "Precio"};
        DefaultTableModel modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        cargarCombosEnTabla(modeloTabla);

        JTable tabla = new JTable(modeloTabla);
        estilizarTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_BORDE));

        // Formulario para agregar combo
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBackground(BLANCO);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panelForm.setPreferredSize(new Dimension(300, 0));

        JLabel lblTitulo = new JLabel("Agregar nuevo combo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(AZUL_OSCURO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtNombre      = crearCampoForm();
        JTextField txtDescripcion = crearCampoForm();
        JTextField txtPrecio      = crearCampoForm();

        JButton btnAgregar = new JButton("Agregar combo");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAgregar.setBackground(AZUL_PRINCIPAL);
        btnAgregar.setForeground(BLANCO);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String desc   = txtDescripcion.getText().trim();
            String precioStr = txtPrecio.getText().trim();

            if (nombre.isEmpty() || desc.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.",
                        "Campos vacios", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                double precio = Double.parseDouble(precioStr);
                Combo combo = new Combo(nombre, desc, precio, "");
                if (DatabaseConnection.agregarCombo(combo)) {
                    JOptionPane.showMessageDialog(this, "Combo agregado correctamente.",
                            "Exito", JOptionPane.INFORMATION_MESSAGE);
                    txtNombre.setText("");
                    txtDescripcion.setText("");
                    txtPrecio.setText("");
                    cargarCombosEnTabla(modeloTabla);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un numero valido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelForm.add(lblTitulo);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(crearEtiquetaForm("Nombre del combo"));
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtNombre);
        panelForm.add(Box.createVerticalStrut(12));
        panelForm.add(crearEtiquetaForm("Descripcion"));
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtDescripcion);
        panelForm.add(Box.createVerticalStrut(12));
        panelForm.add(crearEtiquetaForm("Precio (L.)"));
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtPrecio);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(btnAgregar);

        panel.add(scroll,    BorderLayout.CENTER);
        panel.add(panelForm, BorderLayout.EAST);
        return panel;
    }

    /**
     * Carga los combos en el modelo de la tabla.
     * @param modelo Modelo de la tabla
     */
    private void cargarCombosEnTabla(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Combo c : DatabaseConnection.getCombos()) {
            modelo.addRow(new Object[]{c.getId(), c.getNombre(),
                    c.getDescripcion(), "L. " + c.getPrecio()});
        }
    }

    // ================================================================
    // PESTAÑA 2 — REPARTIDORES
    // ================================================================

    /**
     * Crea el panel de gestion de repartidores.
     * @return Panel de repartidores
     */
    private JPanel crearPanelRepartidores() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] cols = {"ID", "Usuario", "Nombre completo"};
        DefaultTableModel modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        cargarRepartidoresEnTabla(modeloTabla);

        JTable tabla = new JTable(modeloTabla);
        estilizarTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_BORDE));

        // Formulario
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBackground(BLANCO);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panelForm.setPreferredSize(new Dimension(300, 0));

        JLabel lblTitulo = new JLabel("Agregar repartidor");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(AZUL_OSCURO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtNombre   = crearCampoForm();
        JTextField txtUsuario  = crearCampoForm();
        JPasswordField txtClave = new JPasswordField();
        txtClave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtClave.setFont(new Font("Arial", Font.PLAIN, 13));
        txtClave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        JButton btnAgregar = new JButton("Agregar repartidor");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAgregar.setBackground(VERDE);
        btnAgregar.setForeground(BLANCO);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnAgregar.addActionListener(e -> {
            String nombre  = txtNombre.getText().trim();
            String usuario = txtUsuario.getText().trim();
            String clave   = new String(txtClave.getPassword()).trim();

            if (nombre.isEmpty() || usuario.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.",
                        "Campos vacios", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Repartidor rep = new Repartidor(usuario, clave);
            rep.setNombreCompleto(nombre);
            if (DatabaseConnection.agregarRepartidor(rep)) {
                JOptionPane.showMessageDialog(this, "Repartidor agregado correctamente.",
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
                txtNombre.setText("");
                txtUsuario.setText("");
                txtClave.setText("");
                cargarRepartidoresEnTabla(modeloTabla);
            } else {
                JOptionPane.showMessageDialog(this, "El usuario ya existe.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelForm.add(lblTitulo);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(crearEtiquetaForm("Nombre completo"));
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtNombre);
        panelForm.add(Box.createVerticalStrut(12));
        panelForm.add(crearEtiquetaForm("Nombre de usuario"));
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtUsuario);
        panelForm.add(Box.createVerticalStrut(12));
        panelForm.add(crearEtiquetaForm("Clave"));
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtClave);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(btnAgregar);

        panel.add(scroll,    BorderLayout.CENTER);
        panel.add(panelForm, BorderLayout.EAST);
        return panel;
    }

    /**
     * Carga los repartidores en el modelo de la tabla.
     * @param modelo Modelo de la tabla
     */
    private void cargarRepartidoresEnTabla(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Usuario r : DatabaseConnection.getRepartidores()) {
            modelo.addRow(new Object[]{r.getId(),
                    r.getNombreUsuario(), r.getNombreCompleto()});
        }
    }

    // ================================================================
    // PESTAÑA 3 — PEDIDOS
    // ================================================================

    /**
     * Crea el panel de gestion de pedidos.
     * @return Panel de pedidos
     */
    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] cols = {"ID", "Cliente", "Combo", "Estado", "Repartidor", "Fecha"};
        DefaultTableModel modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        cargarPedidosEnTabla(modeloTabla);

        JTable tabla = new JTable(modeloTabla);
        estilizarTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(GRIS_BORDE));

        // Panel de acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelAcciones.setBackground(BLANCO);
        panelAcciones.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, GRIS_BORDE));

        JButton btnAsignar  = new JButton("Asignar repartidor");
        btnAsignar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAsignar.setBackground(NARANJA);
        btnAsignar.setForeground(BLANCO);
        btnAsignar.setBorderPainted(false);
        btnAsignar.setFocusPainted(false);
        btnAsignar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setFont(new Font("Arial", Font.PLAIN, 13));
        btnRefrescar.setBackground(GRIS_FONDO);
        btnRefrescar.setBorderPainted(true);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRefrescar.addActionListener(e -> cargarPedidosEnTabla(modeloTabla));

        btnAsignar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un pedido primero.",
                        "Sin seleccion", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idPedido = (int) modeloTabla.getValueAt(fila, 0);
            String estado = (String) modeloTabla.getValueAt(fila, 3);
            if (estado.equals("ENTREGADO")) {
                JOptionPane.showMessageDialog(this, "Este pedido ya fue entregado.",
                        "No permitido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Usuario> repartidores = DatabaseConnection.getRepartidores();
            if (repartidores.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay repartidores registrados.",
                        "Sin repartidores", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] opciones = repartidores.stream()
                    .map(r -> r.getId() + " - " + r.getNombreCompleto())
                    .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    this, "Selecciona un repartidor:",
                    "Asignar repartidor", JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]);

            if (seleccion != null) {
                int idRepartidor = Integer.parseInt(seleccion.split(" - ")[0]);
                if (DatabaseConnection.asignarRepartidor(idPedido, idRepartidor)) {
                    JOptionPane.showMessageDialog(this, "Repartidor asignado correctamente.",
                            "Exito", JOptionPane.INFORMATION_MESSAGE);
                    cargarPedidosEnTabla(modeloTabla);
                }
            }
        });

        panelAcciones.add(btnAsignar);
        panelAcciones.add(btnRefrescar);

        panel.add(scroll,        BorderLayout.CENTER);
        panel.add(panelAcciones, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Carga todos los pedidos en el modelo de la tabla.
     * @param modelo Modelo de la tabla
     */
    private void cargarPedidosEnTabla(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Pedido p : DatabaseConnection.getTodosLosPedidos()) {
            String fecha = p.getFechaPedido() != null
                    ? p.getFechaPedido().toString().substring(0, 16) : "—";
            String repartidor = p.getIdRepartidor() != null
                    ? "ID " + p.getIdRepartidor() : "Sin asignar";
            modelo.addRow(new Object[]{
                    p.getId(), p.getNombreCliente(), p.getNombreCombo(),
                    p.getEstado(), repartidor, fecha
            });
        }
    }

    // ================================================================
    // PESTAÑA 4 — ESTADISTICAS
    // ================================================================

    /**
     * Crea el panel de estadisticas del restaurante.
     * @return Panel de estadisticas
     */
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Pedido> todos  = DatabaseConnection.getTodosLosPedidos();
        List<Combo>  combos = DatabaseConnection.getCombos();

        long totalPedidos    = todos.size();
        long pedidosEntregados = todos.stream()
                .filter(p -> p.getEstado().equals("ENTREGADO")).count();
        long pedidosPendientes = totalPedidos - pedidosEntregados;

        double totalRecaudado = 0;
        Map<String, Integer> pedidosPorCombo = new HashMap<>();

        for (Pedido p : todos) {
            if (p.getEstado().equals("ENTREGADO")) {
                for (Combo c : combos) {
                    if (c.getId() == p.getIdCombo()) {
                        totalRecaudado += c.getPrecio();
                        break;
                    }
                }
            }
            pedidosPorCombo.merge(p.getNombreCombo(), 1, Integer::sum);
        }

        // Tarjetas KPI
        JPanel panelKPI = new JPanel(new GridLayout(1, 4, 15, 0));
        panelKPI.setBackground(BLANCO);
        panelKPI.add(crearKPI("Total pedidos",    String.valueOf(totalPedidos),    AZUL_PRINCIPAL));
        panelKPI.add(crearKPI("Entregados",        String.valueOf(pedidosEntregados), VERDE));
        panelKPI.add(crearKPI("Pendientes",        String.valueOf(pedidosPendientes), NARANJA));
        panelKPI.add(crearKPI("Total recaudado",
                "L. " + String.format("%.2f", totalRecaudado), new Color(142, 68, 173)));

        // Grafica de pedidos por combo
        PanelGraficaBarras grafica = new PanelGraficaBarras(pedidosPorCombo,
                "Pedidos por combo");
        grafica.setBackground(BLANCO);
        grafica.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        panel.add(panelKPI, BorderLayout.NORTH);
        panel.add(grafica,  BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea una tarjeta KPI con titulo, valor y color.
     * @param titulo Titulo del KPI
     * @param valor  Valor a mostrar
     * @param color  Color del valor
     * @return Panel de la tarjeta KPI
     */
    private JPanel crearKPI(String titulo, String valor, Color color) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTitulo.setForeground(GRIS_TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));
        lblValor.setForeground(color);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(lblValor);
        return tarjeta;
    }

    // ================================================================
    // PANEL DE GRAFICA INTERNA
    // ================================================================

    /**
     * Panel que dibuja una grafica de barras personalizada.
     */
    private static class PanelGraficaBarras extends JPanel {
        private final Map<String, Integer> datos;
        private final String titulo;

        public PanelGraficaBarras(Map<String, Integer> datos, String titulo) {
            this.datos  = datos;
            this.titulo = titulo;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (datos == null || datos.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int margenIzq = 30;
            int margenSup = 40;
            int margenInf = 70;
            int ancho     = getWidth()  - margenIzq - 30;
            int alto      = getHeight() - margenSup - margenInf;

            // Titulo
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.setColor(new Color(27, 54, 93));
            g2.drawString(titulo, margenIzq, 25);

            int maxVal     = datos.values().stream().max(Integer::compareTo).orElse(1);
            int anchoBarra = ancho / datos.size() - 12;

            Color[] colores = {
                    new Color(41, 128, 185), new Color(39, 174, 96),
                    new Color(230, 126, 34), new Color(142, 68, 173),
                    new Color(192, 57, 43),  new Color(22, 160, 133)
            };

            int i = 0;
            for (Map.Entry<String, Integer> e : datos.entrySet()) {
                int x         = margenIzq + i * (anchoBarra + 12);
                int altoBarra = (int) ((double) e.getValue() / maxVal * alto);
                int y         = margenSup + alto - altoBarra;

                g2.setColor(colores[i % colores.length]);
                g2.fillRoundRect(x, y, anchoBarra, altoBarra, 8, 8);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                String val = String.valueOf(e.getValue());
                int wVal = g2.getFontMetrics().stringWidth(val);
                g2.drawString(val, x + anchoBarra / 2 - wVal / 2, y - 6);

                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                String nombre = e.getKey().length() > 14
                        ? e.getKey().substring(0, 14) + "…" : e.getKey();
                int wNom = g2.getFontMetrics().stringWidth(nombre);
                g2.drawString(nombre, x + anchoBarra / 2 - wNom / 2,
                        margenSup + alto + 18);
                i++;
            }
        }
    }

    // ================================================================
    // UTILIDADES DE UI
    // ================================================================

    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(AZUL_OSCURO);
        tabla.getTableHeader().setForeground(BLANCO);
        tabla.setSelectionBackground(new Color(210, 230, 255));
        tabla.setGridColor(GRIS_BORDE);
        tabla.setShowHorizontalLines(true);
    }

    private JTextField crearCampoForm() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return campo;
    }

    private JLabel crearEtiquetaForm(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(GRIS_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

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
}
