package Vista;

import Persistencia_requerida.DatabaseConnection;
import MODELO.Combo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class VentanaMenuPublico extends JFrame {

    private JTextField txtBuscar;
    private JPanel panelCombos;

    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color AZUL_OSCURO    = new Color(27, 54, 93);
    private static final Color BLANCO         = Color.WHITE;
    private static final Color GRIS_FONDO     = new Color(245, 247, 250);
    private static final Color GRIS_BORDE     = new Color(220, 220, 220);
    private static final Color GRIS_TEXTO     = new Color(130, 130, 130);

    /**
     * Constructor de la ventana del menu publico.
     */
    public VentanaMenuPublico() {
        construirVentana();
        cargarCombos("");
    }

    /**
     * Construye y organiza todos los componentes de la ventana.
     */
    private void construirVentana() {
        setTitle("Insert Into Hunger - Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GRIS_FONDO);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(crearFranja(),        BorderLayout.NORTH);
        panelNorte.add(crearPanelBuscador(), BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);
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

        JPanel panelLogo = new JPanel();
        panelLogo.setLayout(new BoxLayout(panelLogo, BoxLayout.X_AXIS));
        panelLogo.setBackground(AZUL_OSCURO);

        JLabel lblLogo;
        try {
            ImageIcon icono = new ImageIcon("src/Recursos/LOG.png");
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

        JLabel lblEslogan = new JLabel("El sabor que nunca da error");
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

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setBackground(AZUL_OSCURO);

        JButton btnIniciarSesion = crearBotonFranjaAzul("Iniciar sesion");
        JButton btnRegistrarse   = crearBotonFranjaVerde("Registrarse");
        JButton btnVolver        = crearBotonFranjaRojo("Volver");

        btnIniciarSesion.addActionListener(e -> {
            dispose();
            new VentanaLogin().setVisible(true);
        });

        btnRegistrarse.addActionListener(e -> {
            dispose();
            VentanaLogin login = new VentanaLogin();
            login.setVisible(true);
            login.abrirRegistroDesdeAfuera();
        });

        btnVolver.addActionListener(e -> dispose());

        panelBotones.add(btnIniciarSesion);
        panelBotones.add(Box.createHorizontalStrut(5));
        panelBotones.add(btnRegistrarse);
        panelBotones.add(Box.createHorizontalStrut(5));
        panelBotones.add(btnVolver);

        franja.add(panelLogo,    BorderLayout.WEST);
        franja.add(panelBotones, BorderLayout.EAST);

        return franja;
    }

    /**
     * Crea boton azul para la franja.
     * @param texto Texto del boton
     * @return Boton estilizado
     */
    private JButton crearBotonFranjaAzul(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(BLANCO);
        btn.setBackground(AZUL_PRINCIPAL);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return btn;
    }

    /**
     * Crea boton verde para la franja.
     * @param texto Texto del boton
     * @return Boton estilizado
     */
    private JButton crearBotonFranjaVerde(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(BLANCO);
        btn.setBackground(new Color(39, 174, 96));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        return btn;
    }

    /**
     * Crea boton rojo para la franja.
     * @param texto Texto del boton
     * @return Boton estilizado
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
     * Crea el panel del buscador.
     * @return Panel del buscador
     */
    private JPanel crearPanelBuscador() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, GRIS_BORDE),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        JLabel lblLupa = new JLabel("🔍");
        lblLupa.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        lblLupa.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBuscar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        txtBuscar.setBackground(BLANCO);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarCombos(txtBuscar.getText());
            }
        });

        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.setBackground(BLANCO);
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panelBusqueda.add(lblLupa,   BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        panel.add(panelBusqueda, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel central con las tarjetas de combos.
     * @return Panel del catalogo
     */
    private JPanel crearPanelContenido() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRIS_FONDO);

        panelCombos = new JPanel(new GridLayout(0, 3, 15, 15));
        panelCombos.setBackground(GRIS_FONDO);
        panelCombos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel panelBanner = new JPanel(new BorderLayout());
        panelBanner.setBackground(GRIS_FONDO);
        panelBanner.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        try {
            ImageIcon iconoOriginal = new ImageIcon("src/Recursos/banner.png");
            int anchoOriginal = iconoOriginal.getIconWidth();
            int altoOriginal  = iconoOriginal.getIconHeight();

            // Calculamos el alto proporcional para un ancho de 1000px
            int anchoFinal = 1000;
            int altoFinal  = (int) ((double) altoOriginal / anchoOriginal * anchoFinal);

            Image img = iconoOriginal.getImage().getScaledInstance(
                    anchoFinal, altoFinal, Image.SCALE_AREA_AVERAGING
            );
            JLabel lblBanner = new JLabel(new ImageIcon(img));
            lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
            panelBanner.add(lblBanner, BorderLayout.CENTER);

        } catch (Exception e) {
            JLabel lblBanner = new JLabel("Registrate YA y obtén envio gratis!");
            lblBanner.setFont(new Font("Arial", Font.BOLD, 16));
            lblBanner.setForeground(AZUL_PRINCIPAL);
            lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
            panelBanner.add(lblBanner, BorderLayout.CENTER);
        }

        JPanel panelTodo = new JPanel();
        panelTodo.setLayout(new BoxLayout(panelTodo, BoxLayout.Y_AXIS));
        panelTodo.setBackground(GRIS_FONDO);
        panelTodo.add(panelCombos);
        panelTodo.add(panelBanner);

        JScrollPane scroll = new JScrollPane(panelTodo);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    /**
     * Carga los combos desde la base de datos.
     * @param texto Texto a buscar
     */
    private void cargarCombos(String texto) {
        panelCombos.removeAll();
        List<Combo> combos = DatabaseConnection.getCombos();
        for (Combo combo : combos) {
            if (combo.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                panelCombos.add(crearTarjetaCombo(combo));
            }
        }
        panelCombos.revalidate();
        panelCombos.repaint();
    }

    /**
     * Crea una tarjeta visual para un combo sin boton de pedir.
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

        JLabel lblIniciarSesion = new JLabel("Inicia sesion para pedir");
        lblIniciarSesion.setFont(new Font("Arial", Font.ITALIC, 11));
        lblIniciarSesion.setForeground(GRIS_TEXTO);
        lblIniciarSesion.setAlignmentX(Component.LEFT_ALIGNMENT);

        tarjeta.add(panelImagen);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblNombre);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(lblDescripcion);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(lblPrecio);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(lblIniciarSesion);

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
}
