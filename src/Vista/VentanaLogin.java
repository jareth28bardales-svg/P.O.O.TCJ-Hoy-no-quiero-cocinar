package Vista;

import Persistencia_requerida.GestorSesion;
import Persistencia_requerida.GestorUsuarios;
import MODELO.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de inicio de sesión del sistema.
 * Diseño dividido: formulario a la izquierda, marca flotante a la derecha.
 *
 * @author Wilmer Isai Amador Arteaga
 */
public class VentanaLogin extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JCheckBox mostrarPassword;
    private GestorUsuarios gestorUsuarios;
    private GestorSesion gestorSesion;

    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color AZUL_OSCURO    = new Color(27, 54, 93);
    private static final Color BLANCO         = Color.WHITE;
    private static final Color GRIS_FONDO     = new Color(240, 244, 248);
    private static final Color GRIS_BORDE     = new Color(220, 220, 220);
    private static final Color GRIS_TEXTO     = new Color(130, 130, 130);

    public VentanaLogin() {
        gestorUsuarios = new GestorUsuarios();
        gestorSesion   = new GestorSesion();
        construirVentana();
    }

    private void construirVentana() {
        setTitle("INSERT INTO HUNGER - Iniciar Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridLayout(1, 2));
        add(crearPanelIzquierdo());
        add(crearPanelDerecho());
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);

        JPanel card = new JPanel();
        card.setBackground(BLANCO);
        card.setPreferredSize(new Dimension(420, 560));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Logo original con Emoji
        JLabel logo = new JLabel("🌮");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Bienvenido");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Ingresa tus credenciales para continuar");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(GRIS_TEXTO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel etiquetaUsuario = new JLabel("Usuario");
        etiquetaUsuario.setFont(new Font("Arial", Font.PLAIN, 13));
        etiquetaUsuario.setForeground(GRIS_TEXTO);
        etiquetaUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(260, 40));
        campoUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        campoUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        campoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel etiquetaContrasena = new JLabel("Contrasena");
        etiquetaContrasena.setFont(new Font("Arial", Font.PLAIN, 13));
        etiquetaContrasena.setForeground(GRIS_TEXTO);
        etiquetaContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoContrasena = new JPasswordField();
        campoContrasena.setMaximumSize(new Dimension(260, 40));
        campoContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        campoContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        campoContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);

        mostrarPassword = new JCheckBox("Mostrar contrasena");
        mostrarPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        mostrarPassword.setBackground(BLANCO);
        mostrarPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        mostrarPassword.addActionListener(e -> {
            if (mostrarPassword.isSelected())
                campoContrasena.setEchoChar((char) 0);
            else
                campoContrasena.setEchoChar('\u2022');
        });

        JButton botonIngresar = new JButton("Ingresar");
        botonIngresar.setMaximumSize(new Dimension(260, 45));
        botonIngresar.setBackground(AZUL_PRINCIPAL);
        botonIngresar.setForeground(BLANCO);
        botonIngresar.setFont(new Font("Arial", Font.BOLD, 15));
        botonIngresar.setFocusPainted(false);
        botonIngresar.setBorderPainted(false);
        botonIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIngresar.addActionListener(e -> accionIngresar());

        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(260, 1));

        JButton botonRegistrarse = new JButton("No tienes cuenta? Registrate");
        botonRegistrarse.setFont(new Font("Arial", Font.PLAIN, 13));
        botonRegistrarse.setForeground(AZUL_PRINCIPAL);
        botonRegistrarse.setBorderPainted(false);
        botonRegistrarse.setFocusPainted(false);
        botonRegistrarse.setBackground(BLANCO);
        botonRegistrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrarse.addActionListener(e -> abrirRegistro());

        JButton botonMenu = new JButton("Ver menu sin registrarse");
        botonMenu.setFont(new Font("Arial", Font.PLAIN, 13));
        botonMenu.setForeground(AZUL_PRINCIPAL);
        botonMenu.setBorderPainted(false);
        botonMenu.setFocusPainted(false);
        botonMenu.setBackground(BLANCO);
        botonMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonMenu.addActionListener(e -> abrirMenuPublico());

        card.add(logo);
        card.add(Box.createVerticalStrut(10));
        card.add(titulo);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(30));
        card.add(etiquetaUsuario);
        card.add(Box.createVerticalStrut(4));
        card.add(campoUsuario);
        card.add(Box.createVerticalStrut(12));
        card.add(etiquetaContrasena);
        card.add(Box.createVerticalStrut(4));
        card.add(campoContrasena);
        card.add(Box.createVerticalStrut(8));
        card.add(mostrarPassword);
        card.add(Box.createVerticalStrut(20));
        card.add(botonIngresar);
        card.add(Box.createVerticalStrut(20));
        card.add(separador);
        card.add(Box.createVerticalStrut(15));
        card.add(botonRegistrarse);
        card.add(Box.createVerticalStrut(5));
        card.add(botonMenu);

        panel.add(card);
        return panel;
    }

    private JPanel crearPanelDerecho() {
        JPanel panelExterior = new JPanel(new GridBagLayout());
        panelExterior.setBackground(GRIS_FONDO);
        panelExterior.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel tarjeta = new JPanel();
        tarjeta.setBackground(AZUL_OSCURO); // Restaurado a Azul Oscuro
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setPreferredSize(new Dimension(420, 520));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // Logo original con Emoji
        JLabel logo = new JLabel("🌮");
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 85));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nombre = new JLabel("INSERT INTO HUNGER");
        nombre.setFont(new Font("Arial", Font.BOLD, 28));
        nombre.setForeground(BLANCO);
        nombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel eslogan = new JLabel("El sabor que nunca da error");
        eslogan.setFont(new Font("Arial", Font.ITALIC, 14));
        eslogan.setForeground(new Color(180, 200, 220));
        eslogan.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel puntos = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        puntos.setBackground(AZUL_OSCURO);
        for (int i = 0; i < 3; i++) {
            JLabel punto = new JLabel("●");
            punto.setFont(new Font("Arial", Font.PLAIN, 12));
            punto.setForeground(i == 0 ? AZUL_PRINCIPAL : new Color(90, 90, 90));
            puntos.add(punto);
        }
        puntos.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(logo);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(nombre);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(eslogan);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(puntos);
        tarjeta.add(Box.createVerticalGlue());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(15, 15, 15, 15);

        panelExterior.add(tarjeta, gbc);
        return panelExterior;
    }

    private void accionIngresar() {
        String usuario    = campoUsuario.getText().trim();
        String contrasena = new String(campoContrasena.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor completa todos los campos.",
                    "Campos vacios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuarioValido = gestorUsuarios.validarCredenciales(usuario, contrasena);

        if (usuarioValido == null) {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contrasena incorrectos.",
                    "Error de acceso",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        gestorSesion.guardarSesion(usuarioValido.getNombreUsuario(), usuarioValido.getPerfil());
        redirigirSegunPerfil(usuarioValido);
    }

    private void redirigirSegunPerfil(Usuario usuario) {
        dispose();
        switch (usuario.getRol()) {
            case "CLIENTE":
                new VentanaMenu(usuario).setVisible(true);
                break;
            case "ADMIN":
                new VentanaAdministrador(usuario).setVisible(true);
                break;
            case "GERENTE":
                new VentanaGerente(usuario).setVisible(true);
                break;
            case "REPARTIDOR":
                new VentanaRepartidor(usuario).setVisible(true);
                break;
            default:
                new VentanaMenu(usuario).setVisible(true);
                break;
        }
    }

    private void abrirMenuPublico() {
        new VentanaMenuPublico().setVisible(true);
    }

    private void abrirRegistro() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new PantallaRegistro(), BorderLayout.CENTER);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    /**
     * Abre directamente la pantalla de registro.
     */
    public void abrirRegistroDesdeAfuera() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new PantallaRegistro(), BorderLayout.CENTER);
        getContentPane().revalidate();
        getContentPane().repaint();
    }
}
