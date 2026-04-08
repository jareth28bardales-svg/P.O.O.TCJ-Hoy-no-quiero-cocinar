package Vista;

import Persistencia_requerida.DatabaseConnection;
import MODELO.Usuario;
import MODELO.Combo;
import Persistencia_requerida.GestorIdioma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VentanaMenu extends JFrame {

    private JTextField txtBuscar;
    private JPanel panelCombos;
    private JLabel lblComboSeleccionado;
    private JLabel lblPrecioSeleccionado;
    private JButton btnConfirmar;
    private Combo comboSeleccionado;
    private Usuario usuarioActual;

    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color AZUL_OSCURO    = new Color(27, 54, 93);
    private static final Color BLANCO         = Color.WHITE;
    private static final Color GRIS_FONDO     = new Color(245, 247, 250);
    private static final Color GRIS_BORDE     = new Color(220, 220, 220);
    private static final Color GRIS_TEXTO     = new Color(130, 130, 130);

    /**
     * Constructor de la ventana del catalogo.
     * @param usuario Usuario que inicio sesion
     */
    public VentanaMenu(Usuario usuario) {
        this.usuarioActual = usuario;
        construirVentana();
        cargarCombos("");
    }

    /**
     * Construye y organiza todos los componentes de la ventana.
     */
    private void construirVentana() {
        setTitle("Insert Into Hunger - Catalogo de Combos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GRIS_FONDO);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(crearFranja(),        BorderLayout.NORTH);
        panelNorte.add(crearPanelBuscador(), BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(GRIS_FONDO);
        panelCentral.add(crearPanelCatalogo(), BorderLayout.CENTER);
        panelCentral.add(crearPanelDerecho(),  BorderLayout.EAST);
        add(panelCentral, BorderLayout.CENTER);
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

        JPanel panelLogo = new JPanel();
        panelLogo.setLayout(new BoxLayout(panelLogo, BoxLayout.X_AXIS));
        panelLogo.setBackground(AZUL_OSCURO);

        JLabel lblLogo;
        try {
            ImageIcon icono = new ImageIcon(
                    getClass().getResource("/Recursos/LOG.png")
            );

            Image imagen = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            lblLogo = new JLabel(new ImageIcon(imagen));

        } catch (Exception e) {
            lblLogo = new JLabel("🌮");
            lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        }

        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBackground(AZUL_OSCURO);

        JLabel lblNombre = new JLabel("Insert Into Hunger");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(BLANCO);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblEslogan = new JLabel("El sabor que nunca da error  ");
        lblEslogan.setFont(new Font("Arial", Font.ITALIC, 11));
        lblEslogan.setForeground(new Color(180, 200, 220));
        lblEslogan.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelTexto.add(Box.createVerticalGlue());
        panelTexto.add(lblNombre);
        panelTexto.add(lblEslogan);
        panelTexto.add(Box.createVerticalGlue());

        panelLogo.add(lblLogo);
        panelLogo.add(Box.createHorizontalStrut(10));
        panelLogo.add(panelTexto);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBackground(AZUL_OSCURO);

        JButton btnMenu      = crearBotonFranja("Menu");
        JButton btnHistorial = crearBotonFranja("Mis pedidos");
        JButton btnPerfil    = crearBotonFranja("Mi perfil");
        JButton btnCerrar    = crearBotonFranjaRojo("Cerrar sesion");

        btnHistorial.addActionListener(e -> verHistorial());
        btnPerfil.addActionListener(e -> verPerfil());
        btnCerrar.addActionListener(e -> cerrarSesion());

        panelBotones.add(Box.createVerticalGlue());
        panelBotones.add(btnMenu);
        panelBotones.add(Box.createHorizontalStrut(5));
        panelBotones.add(btnHistorial);
        panelBotones.add(Box.createHorizontalStrut(5));
        panelBotones.add(btnPerfil);
        panelBotones.add(Box.createHorizontalStrut(5));
        panelBotones.add(btnCerrar);
        panelBotones.add(Box.createVerticalGlue());

        franja.add(panelLogo,    BorderLayout.WEST);
        franja.add(panelBotones, BorderLayout.EAST);

        return franja;
    }

    /**
     * Crea un boton estilo franja de navegacion.
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
     * Crea un boton rojo para cerrar sesion.
     * @param texto Texto del boton
     * @return Boton rojo estilizado
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

    /**
     * Crea el panel del buscador superior.
     * @return Panel del buscador
     */
    private JPanel crearPanelBuscador() {
        // Panel contenedor principal (ocupa todo el ancho)
        JPanel panelContenedor = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        panelContenedor.setBackground(BLANCO);
        panelContenedor.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GRIS_BORDE));

        // La Lupa
        JLabel lblLupa = new JLabel("🔍");
        lblLupa.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        lblLupa.setForeground(GRIS_TEXTO);

        // El campo de texto
        txtBuscar = new JTextField();
        txtBuscar.setToolTipText("¿Qué se te antoja hoy?");
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBuscar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        txtBuscar.setBackground(BLANCO);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarCombos(txtBuscar.getText());
            }
        });

        // Panel redondeado (la "cápsula" del buscador)
        JPanel panelCapsula = new JPanel(new BorderLayout());
        panelCapsula.setBackground(BLANCO);
        // Le damos un tamaño preferido para que no se estire de más
        panelCapsula.setPreferredSize(new Dimension(500, 40));
        panelCapsula.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE, 1, true), // Borde redondeado
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        panelCapsula.add(lblLupa, BorderLayout.WEST);
        panelCapsula.add(txtBuscar, BorderLayout.CENTER);

        panelContenedor.add(panelCapsula);
        return panelContenedor;
    }

    /**
     * Crea el panel central con las tarjetas de combos.
     * @return Panel del catalogo
     */
    private JScrollPane crearPanelCatalogo() {
        panelCombos = new JPanel(new GridLayout(0, 3, 15, 15));
        panelCombos.setBackground(GRIS_FONDO);
        panelCombos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        JScrollPane scroll = new JScrollPane(panelCombos);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(GRIS_FONDO);
        return scroll;
    }

    /**
     * Crea el panel derecho con info del pedido.
     * @return Panel del pedido
     */
    /**
     * Crea el panel derecho con info del pedido y saludo al usuario.
     * @return Panel del pedido estilizado
     */
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AZUL_OSCURO);
        panel.setPreferredSize(new Dimension(280, 0));
        // Aumentamos el padding para que respire más el diseño (Regla 5)
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        // --- SECCIÓN DE BIENVENIDA ---
        JLabel lblBienvenida = new JLabel("Bienvenido,");
        // Usamos Liberation Serif como pide el documento en la Regla 21
        lblBienvenida.setFont(new Font("Liberation Serif", Font.PLAIN, 14));
        lblBienvenida.setForeground(new Color(180, 200, 220)); // Azul grisáceo suave
        lblBienvenida.setAlignmentX(Component.LEFT_ALIGNMENT);

        // El nombre del usuario ahora resalta más
        JLabel lblNombreCliente = new JLabel(usuarioActual.getNombreCompleto().toUpperCase());
        lblNombreCliente.setFont(new Font("Liberation Serif", Font.BOLD, 18));
        lblNombreCliente.setForeground(BLANCO);
        lblNombreCliente.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Un separador más sutil (transparente)
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(255, 255, 255, 40));

        // --- SECCIÓN TARJETA DE PEDIDO ---
        JPanel panelDetalle = new JPanel();
        panelDetalle.setLayout(new BoxLayout(panelDetalle, BoxLayout.Y_AXIS));
        panelDetalle.setBackground(BLANCO);
        // Bordes redondeados simulados con un borde compuesto
        panelDetalle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255,255,255,50), 1, true),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));
        panelDetalle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        JLabel lblTituloPedido = new JLabel("TU PEDIDO");
        lblTituloPedido.setFont(new Font("Liberation Serif", Font.BOLD, 12));
        lblTituloPedido.setForeground(GRIS_TEXTO);
        lblTituloPedido.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblComboSeleccionado = new JLabel("Selecciona un combo");
        lblComboSeleccionado.setFont(new Font("Liberation Serif", Font.ITALIC, 14));
        lblComboSeleccionado.setForeground(AZUL_OSCURO);
        lblComboSeleccionado.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblPrecioSeleccionado = new JLabel("");
        lblPrecioSeleccionado.setFont(new Font("Liberation Serif", Font.BOLD, 22));
        lblPrecioSeleccionado.setForeground(AZUL_PRINCIPAL);
        lblPrecioSeleccionado.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelDetalle.add(lblTituloPedido);
        panelDetalle.add(Box.createVerticalStrut(15));
        panelDetalle.add(lblComboSeleccionado);
        panelDetalle.add(Box.createVerticalStrut(10));
        panelDetalle.add(lblPrecioSeleccionado);

        // --- BOTÓN DE ACCIÓN ---
        btnConfirmar = new JButton("Confirmar y Pagar");
        btnConfirmar.setFont(new Font("Liberation Serif", Font.BOLD, 14));
        // Color naranja para que resalte y combine con el botón de "Cerrar Sesión" (Regla 5)
        btnConfirmar.setBackground(new Color(230, 126, 34));
        btnConfirmar.setForeground(BLANCO);
        btnConfirmar.setBorderPainted(false);
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(e -> pedirCombo());

        // Agregar todo al panel principal
        panel.add(lblBienvenida);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblNombreCliente);
        panel.add(Box.createVerticalStrut(20));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(25));
        panel.add(panelDetalle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnConfirmar);
        panel.add(Box.createVerticalGlue());

        return panel;
    }
    private JPanel crearBannerPromocional() {
        JPanel panelPromo = new JPanel(new BorderLayout());
        panelPromo.setBackground(Color.WHITE);
        panelPromo.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel lblImagen = new JLabel();
        try {
            // Cargamos la imagen del Combo Debugger Pro
            ImageIcon icono = new ImageIcon("src/Recursos/COMBO.jpeg");
            // Ajustamos el tamaño para que encaje en la celda del Grid
            Image img = icono.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImagen.setText("¡PROMOCIÓN: COMBO DEBUGGER PRO - L. 185.00!");
            lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        }

        panelPromo.add(lblImagen, BorderLayout.CENTER);
        return panelPromo;
    }

    /**
     * Carga los combos desde la base de datos y crea las tarjetas.
     * @param texto Texto a buscar
     */
    private void cargarCombos(String texto) {
        panelCombos.removeAll();
        List<Combo> combos = DatabaseConnection.getCombos();

        // 1. Agregar los combos normales
        for (Combo combo : combos) {
            if (combo.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                panelCombos.add(crearTarjetaCombo(combo));
            }
        }

        // 2. Rellenar el espacio vacío con la promoción
        // Si no hay búsqueda activa, añadimos el banner promocional
        if (texto.isEmpty()) {
            panelCombos.add(crearBannerPromocional());

            // Si quieres que llene ambos espacios vacíos (el azul y el verde),
            // puedes agregarlo dos veces o dejar uno con la promo y otro con un mensaje distinto.
            panelCombos.add(new JLabel(" ¡El sabor que nunca da error!", SwingConstants.CENTER));
        }

        panelCombos.revalidate();
        panelCombos.repaint();
    }
    /**
     * Crea una tarjeta visual para un combo.
     * @param combo Combo a mostrar
     * @return Panel de la tarjeta
     */
    private JPanel crearTarjetaCombo(Combo combo) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Panel de imagen
        JPanel panelImagen = new JPanel(new BorderLayout());
        panelImagen.setBackground(new Color(210, 230, 250));
        panelImagen.setPreferredSize(new Dimension(0, 140));
        panelImagen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        boolean imagenCargada = false;
        if (combo.getImagen() != null && !combo.getImagen().isEmpty()) {
            try {
                ImageIcon icono = new ImageIcon(
                        getClass().getResource("/Recursos/" + combo.getImagen()));
                Image img = icono.getImage()
                        .getScaledInstance(300, 140, Image.SCALE_SMOOTH);
                JLabel lblImagen = new JLabel(new ImageIcon(img));
                lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
                panelImagen.add(lblImagen, BorderLayout.CENTER);
                imagenCargada = true;
            } catch (Exception e) {
                System.out.println("Imagen no encontrada: " + combo.getImagen());
            }
        }

        if (!imagenCargada) {
            JLabel lblEmoji = new JLabel(getEmojiCombo(combo.getNombre()),
                    SwingConstants.CENTER);
            lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
            panelImagen.add(lblEmoji, BorderLayout.CENTER);
        }

        JLabel lblNombre = new JLabel(combo.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblNombre.setForeground(new Color(30, 30, 30));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel(
                "<html><p style='width:150px'>"
                        + combo.getDescripcion() + "</p></html>");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 11));
        lblDescripcion.setForeground(GRIS_TEXTO);
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPrecio = new JLabel("L. " + combo.getPrecio());
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 16));
        lblPrecio.setForeground(AZUL_PRINCIPAL);
        lblPrecio.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnSeleccionar = new JButton(
                GestorIdioma.get("seleccionar"));
        btnSeleccionar.setFont(new Font("Arial", Font.BOLD, 12));
        btnSeleccionar.setBackground(AZUL_PRINCIPAL);
        btnSeleccionar.setForeground(BLANCO);
        btnSeleccionar.setBorderPainted(false);
        btnSeleccionar.setFocusPainted(false);
        btnSeleccionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSeleccionar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSeleccionar.addActionListener(e -> seleccionarCombo(combo, tarjeta));

        tarjeta.add(panelImagen);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblNombre);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(lblDescripcion);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(lblPrecio);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(btnSeleccionar);

        return tarjeta;
    }

    /**
     * Devuelve un emoji segun el nombre del combo.
     * @param nombre Nombre del combo
     * @return Emoji correspondiente
     */
    private String getEmojiCombo(String nombre) {
        String n = nombre.toLowerCase();
        if (n.contains("burger") || n.contains("hambur") || n.contains("hello")) return "🍔";
        if (n.contains("pizza")  || n.contains("stack"))                          return "🍕";
        if (n.contains("taco")   || n.contains("debug"))                          return "🌮";
        if (n.contains("ensalada") || n.contains("clean"))                        return "🥗";
        if (n.contains("pollo"))                                                   return "🍗";
        return "🍽️";
    }

    /**
     * Selecciona un combo y actualiza el panel del pedido.
     * @param combo   Combo seleccionado
     * @param tarjeta Tarjeta seleccionada
     */
    private void seleccionarCombo(Combo combo, JPanel tarjeta) {
        comboSeleccionado = combo;
        lblComboSeleccionado.setText(combo.getNombre());
        lblPrecioSeleccionado.setText("L. " + combo.getPrecio());
        btnConfirmar.setEnabled(true);

        for (Component c : panelCombos.getComponents()) {
            c.setBackground(BLANCO);
        }
        tarjeta.setBackground(new Color(235, 245, 255));
    }

    /**
     * Realiza el pedido del combo seleccionado.
     */
    private void pedirCombo() {
        if (comboSeleccionado == null) return;

        int confirmar = JOptionPane.showConfirmDialog(this,
                "Confirmar pedido:\n" + comboSeleccionado.getNombre() +
                        "\nPrecio: L. " + comboSeleccionado.getPrecio(),
                "Confirmar pedido",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            boolean exito = DatabaseConnection.crearPedido(
                    usuarioActual.getId(),
                    comboSeleccionado.getId()
            );
            if (exito) {
                JOptionPane.showMessageDialog(this,
                        "Pedido realizado con exito!\nEstado: PENDIENTE",
                        "Pedido confirmado",
                        JOptionPane.INFORMATION_MESSAGE);
                comboSeleccionado = null;
                lblComboSeleccionado.setText("Selecciona un combo");
                lblPrecioSeleccionado.setText("");
                btnConfirmar.setEnabled(false);
                cargarCombos("");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al realizar el pedido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Abre la ventana del historial de pedidos.
     */
    private void verHistorial() {
        dispose();
        new VentanaHistorial(usuarioActual).setVisible(true);
    }

    /**
     * Cierra la sesion y regresa al login.
     */
    private void cerrarSesion() {
        dispose();
        new VentanaLogin().setVisible(true);
    }
    /**
     * Abre la ventana del perfil del cliente.
     */
    private void verPerfil() {
        dispose();
        new VentanaPerfil(usuarioActual).setVisible(true);
    }
}