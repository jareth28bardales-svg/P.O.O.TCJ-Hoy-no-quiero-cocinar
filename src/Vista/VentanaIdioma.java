package Vista;

import Persistencia_requerida.GestorIdioma;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de seleccion de idioma.
 * Es la primera pantalla que ve el usuario al iniciar el sistema.
 *
 * @author TuNombre
 * @version 1.0
 */
public class VentanaIdioma extends JFrame {

    private static final Color AZUL_OSCURO = new Color(27, 54, 93);
    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color BLANCO = Color.WHITE;
    private static final Color GRIS_FONDO = new Color(245, 247, 250);
    private static final Color GRIS_TEXTO = new Color(130, 130, 130);

    /**
     * Constructor de la ventana de seleccion de idioma.
     */
    public VentanaIdioma() {
        construirVentana();
    }

    /**
     * Construye y organiza todos los componentes de la ventana.
     */
    private void construirVentana() {
        setTitle("Insert Into Hunger - Idioma / Language");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        add(crearOpcionIdioma(
                "Español",
                "🇭🇳",
                "Continuar en Español",
                "es"
        ));
        add(crearOpcionIdioma(
                "English",
                "🇺🇸",
                "Continue in English",
                "en"
        ));
    }

    /**
     * Crea el panel de una opcion de idioma.
     * @param nombre      Nombre del idioma
     * @param bandera     Emoji de la bandera
     * @param botonTexto  Texto del boton
     * @param codigo      Codigo del idioma
     * @return Panel de la opcion
     */
    private JPanel crearOpcionIdioma(String nombre, String bandera,
                                     String botonTexto, String codigo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(codigo.equals("es") ? AZUL_OSCURO : BLANCO);
        panel.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color colorTexto  = codigo.equals("es") ? BLANCO : AZUL_OSCURO;
        Color colorSub    = codigo.equals("es")
                ? new Color(180, 200, 220) : GRIS_TEXTO;
        Color colorBoton  = codigo.equals("es") ? AZUL_PRINCIPAL
                : AZUL_OSCURO;

        JLabel lblBandera = new JLabel(bandera, SwingConstants.CENTER);
        lblBandera.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        lblBandera.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 28));
        lblNombre.setForeground(colorTexto);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRestaurante = new JLabel("Insert Into Hunger", SwingConstants.CENTER);
        lblRestaurante.setFont(new Font("Arial", Font.PLAIN, 13));
        lblRestaurante.setForeground(colorSub);
        lblRestaurante.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(200, 1));
        sep.setForeground(colorSub);

        JButton btnSeleccionar = new JButton(botonTexto);
        btnSeleccionar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSeleccionar.setBackground(colorBoton);
        btnSeleccionar.setForeground(BLANCO);
        btnSeleccionar.setBorderPainted(false);
        btnSeleccionar.setFocusPainted(false);
        btnSeleccionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSeleccionar.setMaximumSize(new Dimension(220, 44));
        btnSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSeleccionar.addActionListener(e -> seleccionarIdioma(codigo));

        panel.add(Box.createVerticalGlue());
        panel.add(lblBandera);
        panel.add(Box.createVerticalStrut(15));
        panel.add(lblNombre);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblRestaurante);
        panel.add(Box.createVerticalStrut(25));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(25));
        panel.add(btnSeleccionar);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Carga el idioma seleccionado y abre el login.
     * @param codigo Codigo del idioma seleccionado
     */
    private void seleccionarIdioma(String codigo) {
        GestorIdioma.cargarIdioma(codigo);
        dispose();
        new VentanaLogin().setVisible(true);
    }
}