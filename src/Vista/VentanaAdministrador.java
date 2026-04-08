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
 * Ventana principal del modulo de Administrador.
 * Permite editar info del restaurante, agregar gerentes y ver estadisticas.
 *
 * @author TuNombre
 * @version 1.0
 */
public class VentanaAdministrador extends JFrame {

    private Usuario usuarioActual;

    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color AZUL_OSCURO    = new Color(27, 54, 93);
    private static final Color BLANCO         = Color.WHITE;
    private static final Color GRIS_FONDO     = new Color(245, 247, 250);
    private static final Color GRIS_BORDE     = new Color(220, 220, 220);
    private static final Color GRIS_TEXTO     = new Color(130, 130, 130);
    private static final Color VERDE          = new Color(39, 174, 96);
    private static final Color NARANJA        = new Color(230, 126, 34);
    private static final Color MORADO         = new Color(142, 68, 173);

    /**
     * Constructor de la ventana del administrador.
     * @param usuario Administrador que inicio sesion
     */
    public VentanaAdministrador(Usuario usuario) {
        this.usuarioActual = usuario;
        construirVentana();
    }

    /**
     * Construye y organiza todos los componentes de la ventana.
     */
    private void construirVentana() {
        setTitle("Insert Into Hunger - Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GRIS_FONDO);

        add(crearFranja(),    BorderLayout.NORTH);
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

        JLabel lblNombre = new JLabel("Insert Into Hunger  —  Administrador");
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

        JButton btnCerrar = crearBotonRojo("Cerrar sesion");
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

        pestanas.addTab("🏠  Restaurante",  crearPanelRestaurante());
        pestanas.addTab("👔  Gerentes",     crearPanelGerentes());
        pestanas.addTab("👥  Usuarios",     crearPanelUsuarios());
        pestanas.addTab("📊  Estadisticas", crearPanelEstadisticas());

        panel.add(pestanas, BorderLayout.CENTER);
        return panel;
    }

    // ================================================================
    // PESTAÑA 1 — INFO DEL RESTAURANTE
    // ================================================================

    /**
     * Crea el panel para editar la informacion del restaurante.
     * @return Panel del restaurante
     */
    private JPanel crearPanelRestaurante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        RestauranteInfo info = DatabaseConnection.getInfoRestaurante();

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel lblTitulo = new JLabel("Informacion del restaurante");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(AZUL_OSCURO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Edita los datos que se muestran publicamente");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSub.setForeground(GRIS_TEXTO);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(GRIS_BORDE);

        JTextField txtNombre      = crearCampo(info != null ? info.getNombre()     : "");
        JTextField txtDireccion   = crearCampo(info != null ? info.getDireccion()  : "");
        JTextField txtEslogan     = crearCampo(info != null ? info.getEslogan()    : "");

        JTextArea  txtDescripcion = new JTextArea(info != null ? info.getDescripcion() : "");
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnGuardar = new JButton("Guardar cambios");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(AZUL_PRINCIPAL);
        btnGuardar.setForeground(BLANCO);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setMaximumSize(new Dimension(250, 42));
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnGuardar.addActionListener(e -> {
            String nombre    = txtNombre.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String eslogan   = txtEslogan.getText().trim();
            String desc      = txtDescripcion.getText().trim();

            if (nombre.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nombre y direccion son obligatorios.",
                        "Campos vacios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            RestauranteInfo actualizada = new RestauranteInfo();
            actualizada.setId(info != null ? info.getId() : 1);
            actualizada.setNombre(nombre);
            actualizada.setDireccion(direccion);
            actualizada.setEslogan(eslogan);
            actualizada.setDescripcion(desc);

            if (DatabaseConnection.actualizarInfoRestaurante(actualizada)) {
                JOptionPane.showMessageDialog(this,
                        "Informacion actualizada correctamente.",
                        "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(lblSub);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(sep);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(crearEtiqueta("Nombre del restaurante"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtNombre);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(crearEtiqueta("Direccion"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtDireccion);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(crearEtiqueta("Eslogan"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtEslogan);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(crearEtiqueta("Descripcion"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(scrollDesc);
        tarjeta.add(Box.createVerticalStrut(25));
        tarjeta.add(btnGuardar);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets  = new Insets(0, 100, 0, 100);
        panel.add(tarjeta, gbc);

        return panel;
    }

    // ================================================================
    // PESTAÑA 2 — GERENTES
    // ================================================================

    /**
     * Crea el panel de gestion de gerentes.
     * @return Panel de gerentes
     */
    private JPanel crearPanelGerentes() {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] cols = {"ID", "Usuario", "Nombre completo"};
        DefaultTableModel modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        cargarGerentesEnTabla(modeloTabla);

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

        JLabel lblTitulo = new JLabel("Agregar gerente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(AZUL_OSCURO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField  txtNombre  = crearCampoForm();
        JTextField  txtUsuario = crearCampoForm();
        JPasswordField txtClave = new JPasswordField();
        txtClave.setFont(new Font("Arial", Font.PLAIN, 13));
        txtClave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtClave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        JButton btnAgregar = new JButton("Agregar gerente");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 13));
        btnAgregar.setBackground(MORADO);
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

            Gerente gerente = new Gerente(usuario, clave);
            gerente.setNombreCompleto(nombre);

            if (DatabaseConnection.agregarGerente(gerente)) {
                JOptionPane.showMessageDialog(this, "Gerente agregado correctamente.",
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
                txtNombre.setText("");
                txtUsuario.setText("");
                txtClave.setText("");
                cargarGerentesEnTabla(modeloTabla);
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
     * Carga los gerentes en el modelo de la tabla.
     * @param modelo Modelo de la tabla
     */
    private void cargarGerentesEnTabla(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Usuario g : DatabaseConnection.getGerentes()) {
            modelo.addRow(new Object[]{g.getId(),
                    g.getNombreUsuario(), g.getNombreCompleto()});
        }
    }

    // ================================================================
    // PESTAÑA 3 — USUARIOS
    // ================================================================

    /**
     * Crea el panel de consulta de usuarios del sistema.
     * @return Panel de usuarios
     */
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTabbedPane subPestanas = new JTabbedPane();
        subPestanas.setFont(new Font("Arial", Font.PLAIN, 13));

        // Sub-pestaña clientes
        String[] colsCliente = {"ID", "Usuario", "Nombre", "Direccion", "Telefono", "Email"};
        DefaultTableModel modeloClientes = new DefaultTableModel(colsCliente, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Usuario c : DatabaseConnection.getClientes()) {
            modeloClientes.addRow(new Object[]{
                    c.getId(), c.getNombreUsuario(), c.getNombreCompleto(),
                    c.getDireccion(), c.getTelefono(), c.getEmail()
            });
        }
        JTable tablaClientes = new JTable(modeloClientes);
        estilizarTabla(tablaClientes);
        JScrollPane scrollClientes = new JScrollPane(tablaClientes);
        scrollClientes.setBorder(BorderFactory.createLineBorder(GRIS_BORDE));

        // Sub-pestaña repartidores
        String[] colsRep = {"ID", "Usuario", "Nombre completo"};
        DefaultTableModel modeloRep = new DefaultTableModel(colsRep, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Usuario r : DatabaseConnection.getRepartidores()) {
            modeloRep.addRow(new Object[]{r.getId(),
                    r.getNombreUsuario(), r.getNombreCompleto()});
        }
        JTable tablaRep = new JTable(modeloRep);
        estilizarTabla(tablaRep);
        JScrollPane scrollRep = new JScrollPane(tablaRep);
        scrollRep.setBorder(BorderFactory.createLineBorder(GRIS_BORDE));

        subPestanas.addTab("👤  Clientes (" + modeloClientes.getRowCount() + ")",
                scrollClientes);
        subPestanas.addTab("🛵  Repartidores (" + modeloRep.getRowCount() + ")",
                scrollRep);

        panel.add(subPestanas, BorderLayout.CENTER);
        return panel;
    }

    // ================================================================
    // PESTAÑA 4 — ESTADISTICAS
    // ================================================================

    /**
     * Crea el panel de estadisticas generales del sistema.
     * @return Panel de estadisticas
     */
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Pedido> pedidos     = DatabaseConnection.getTodosLosPedidos();
        List<Combo>  combos      = DatabaseConnection.getCombos();
        List<Usuario> clientes   = DatabaseConnection.getClientes();
        List<Usuario> repartidores = DatabaseConnection.getRepartidores();

        long entregados  = pedidos.stream()
                .filter(p -> p.getEstado().equals("ENTREGADO")).count();
        long pendientes  = pedidos.size() - entregados;

        double totalRecaudado = 0;
        Map<String, Integer> pedidosPorCombo = new HashMap<>();
        Map<String, Integer> pedidosPorRep   = new HashMap<>();

        for (Pedido p : pedidos) {
            pedidosPorCombo.merge(p.getNombreCombo(), 1, Integer::sum);
            if (p.getIdRepartidor() != null) {
                for (Usuario r : repartidores) {
                    if (r.getId() == p.getIdRepartidor()) {
                        pedidosPorRep.merge(r.getNombreCompleto(), 1, Integer::sum);
                        break;
                    }
                }
            }
            if (p.getEstado().equals("ENTREGADO")) {
                for (Combo c : combos) {
                    if (c.getId() == p.getIdCombo()) {
                        totalRecaudado += c.getPrecio();
                        break;
                    }
                }
            }
        }

        // KPIs
        JPanel panelKPI = new JPanel(new GridLayout(1, 5, 10, 0));
        panelKPI.setBackground(BLANCO);
        panelKPI.add(crearKPI("Total pedidos",
                String.valueOf(pedidos.size()), AZUL_PRINCIPAL));
        panelKPI.add(crearKPI("Entregados",
                String.valueOf(entregados), VERDE));
        panelKPI.add(crearKPI("Pendientes",
                String.valueOf(pendientes), NARANJA));
        panelKPI.add(crearKPI("Clientes",
                String.valueOf(clientes.size()), MORADO));
        panelKPI.add(crearKPI("Recaudado",
                "L. " + String.format("%.2f", totalRecaudado),
                new Color(192, 57, 43)));

        // Graficas lado a lado
        JPanel panelGraficas = new JPanel(new GridLayout(1, 2, 15, 0));
        panelGraficas.setBackground(BLANCO);

        PanelGraficaBarras graficaCombos = new PanelGraficaBarras(
                pedidosPorCombo, "Pedidos por combo");
        graficaCombos.setBackground(BLANCO);
        graficaCombos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        PanelGraficaBarras graficaRep = new PanelGraficaBarras(
                pedidosPorRep.isEmpty()
                        ? Map.of("Sin datos", 0) : pedidosPorRep,
                "Entregas por repartidor");
        graficaRep.setBackground(BLANCO);
        graficaRep.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        panelGraficas.add(graficaCombos);
        panelGraficas.add(graficaRep);

        panel.add(panelKPI,     BorderLayout.NORTH);
        panel.add(panelGraficas, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea una tarjeta KPI con titulo, valor y color.
     * @param titulo Titulo del indicador
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
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTitulo.setForeground(GRIS_TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 22));
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

            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.setColor(AZUL_OSCURO);
            g2.drawString(titulo, margenIzq, 25);

            int maxVal     = datos.values().stream()
                    .max(Integer::compareTo).orElse(1);
            int anchoBarra = Math.max(20, ancho / datos.size() - 12);

            Color[] colores = {
                    new Color(41, 128, 185), new Color(39, 174, 96),
                    new Color(230, 126, 34), new Color(142, 68, 173),
                    new Color(192, 57, 43),  new Color(22, 160, 133)
            };

            int i = 0;
            for (Map.Entry<String, Integer> entry : datos.entrySet()) {
                int x         = margenIzq + i * (anchoBarra + 12);
                int altoBarra = maxVal == 0 ? 0
                        : (int) ((double) entry.getValue() / maxVal * alto);
                int y         = margenSup + alto - altoBarra;

                g2.setColor(colores[i % colores.length]);
                g2.fillRoundRect(x, y, anchoBarra, Math.max(altoBarra, 1), 8, 8);

                g2.setColor(Color.DARK_GRAY);
                g2.setFont(new Font("Arial", Font.BOLD, 11));
                String val  = String.valueOf(entry.getValue());
                int wVal    = g2.getFontMetrics().stringWidth(val);
                g2.drawString(val, x + anchoBarra / 2 - wVal / 2, y - 5);

                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                String nombre = entry.getKey().length() > 13
                        ? entry.getKey().substring(0, 13) + "…" : entry.getKey();
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

    private JTextField crearCampo(String valor) {
        JTextField campo = new JTextField(valor);
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return campo;
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

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(GRIS_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JLabel crearEtiquetaForm(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(GRIS_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

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