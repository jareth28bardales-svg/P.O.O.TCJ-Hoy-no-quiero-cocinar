package Vista;

import MODELO.Cliente;
import Persistencia_requerida.GestorUsuarios;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de registro de nuevos clientes del sistema.
 * Se muestra en la misma ventana reemplazando el login.
 *
 * @author Wilmer Isai Amador Arteaga
 */
public class PantallaRegistro extends JPanel {

    private JTextField txtNombre;
    private JTextField txtUsuario;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmar;

    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color AZUL_OSCURO    = new Color(27, 54, 93);
    private static final Color BLANCO         = Color.WHITE;
    private static final Color GRIS_TEXTO     = new Color(130, 130, 130);
    private static final Color GRIS_BORDE     = new Color(220, 220, 220);

    /**
     * Constructor del panel de registro.
     */
    public PantallaRegistro() {
        setLayout(new GridLayout(1, 2));
        add(crearPanelFormulario());
        add(crearPanelDerecho());
    }

    /**
     * Crea el panel izquierdo con el formulario de registro.
     * @return Panel del formulario
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);

        JPanel card = new JPanel();
        card.setBackground(BLANCO);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(420, 620));
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel("Crear cuenta");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Completa tus datos para registrarte");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(GRIS_TEXTO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtNombre    = crearCampo();
        txtUsuario   = crearCampo();
        txtTelefono  = crearCampo();
        txtDireccion = crearCampo();

        txtContrasena = new JPasswordField();
        txtContrasena.setMaximumSize(new Dimension(260, 40));
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        txtContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtConfirmar = new JPasswordField();
        txtConfirmar.setMaximumSize(new Dimension(260, 40));
        txtConfirmar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtConfirmar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        txtConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonRegistrar = new JButton("Registrarse");
        botonRegistrar.setMaximumSize(new Dimension(260, 45));
        botonRegistrar.setBackground(AZUL_PRINCIPAL);
        botonRegistrar.setForeground(BLANCO);
        botonRegistrar.setFont(new Font("Arial", Font.BOLD, 15));
        botonRegistrar.setFocusPainted(false);
        botonRegistrar.setBorderPainted(false);
        botonRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrar.addActionListener(e -> accionRegistrar());

        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(260, 1));

        JButton botonVolver = new JButton("Ya tienes cuenta? Inicia sesion");
        botonVolver.setFont(new Font("Arial", Font.PLAIN, 13));
        botonVolver.setForeground(AZUL_PRINCIPAL);
        botonVolver.setBorderPainted(false);
        botonVolver.setFocusPainted(false);
        botonVolver.setBackground(BLANCO);
        botonVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonVolver.addActionListener(e -> volverAlLogin());

        card.add(titulo);
        card.add(Box.createVerticalStrut(5));
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(20));
        card.add(crearEtiqueta("Nombre completo"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtNombre);
        card.add(Box.createVerticalStrut(10));
        card.add(crearEtiqueta("Usuario"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtUsuario);
        card.add(Box.createVerticalStrut(10));
        card.add(crearEtiqueta("Telefono"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtTelefono);
        card.add(Box.createVerticalStrut(10));
        card.add(crearEtiqueta("Direccion"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtDireccion);
        card.add(Box.createVerticalStrut(10));
        card.add(crearEtiqueta("Contrasena"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtContrasena);
        card.add(Box.createVerticalStrut(10));
        card.add(crearEtiqueta("Confirmar contrasena"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtConfirmar);
        card.add(Box.createVerticalStrut(20));
        card.add(botonRegistrar);
        card.add(Box.createVerticalStrut(15));
        card.add(separador);
        card.add(Box.createVerticalStrut(10));
        card.add(botonVolver);

        panel.add(card);
        return panel;
    }

    /**
     * Crea el panel derecho decorativo igual que el login.
     * @return Panel derecho
     */
    private JPanel crearPanelDerecho() {
        JPanel panelExterior = new JPanel(new GridBagLayout());
        panelExterior.setBackground(BLANCO);

        JPanel tarjeta = new JPanel();
        tarjeta.setBackground(AZUL_OSCURO);
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        JLabel logo = new JLabel("🌮");
        logo.setFont(new Font("Dialog", Font.PLAIN, 85));
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

    /**
     * Crea un campo de texto con el estilo del sistema.
     * @return Campo de texto estilizado
     */
    private JTextField crearCampo() {
        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(260, 40));
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return campo;
    }

    /**
     * Crea una etiqueta con el estilo del sistema.
     * @param texto Texto de la etiqueta
     * @return Etiqueta estilizada
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 13));
        etiqueta.setForeground(GRIS_TEXTO);
        etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
        return etiqueta;
    }

    private void accionRegistrar() {
        String nombre     = txtNombre.getText().trim();
        String usuario    = txtUsuario.getText().trim();
        String telefono   = txtTelefono.getText().trim();
        String direccion  = txtDireccion.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();
        String confirmar  = new String(txtConfirmar.getPassword()).trim();

        if (nombre.isEmpty() || usuario.isEmpty() || telefono.isEmpty()
                || direccion.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor completa todos los campos.",
                    "Campos vacios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!contrasena.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                    "Las contrasenas no coinciden.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el objeto cliente y guardarlo en la base de datos
        MODELO.Cliente nuevoCliente = new MODELO.Cliente();
        nuevoCliente.setNombreUsuario(usuario);
        nuevoCliente.setContrasena(contrasena);
        nuevoCliente.setNombreCompleto(nombre);
        nuevoCliente.setDireccion(direccion);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setEmail("");

        boolean registrado = Persistencia_requerida.DatabaseConnection.registrarCliente(nuevoCliente);

        if (registrado) {
            JOptionPane.showMessageDialog(this,
                    "Cuenta creada exitosamente!",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            volverAlLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                    "El usuario ya existe, elige otro nombre.",
                    "Error de registro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Regresa a la ventana de login.
     */
    private void volverAlLogin() {
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        ventana.getContentPane().removeAll();
        ventana.getContentPane().setLayout(new GridLayout(1, 2));
        VentanaLogin login = new VentanaLogin();
        ventana.setContentPane(login.getContentPane());
        ventana.revalidate();
        ventana.repaint();
    }
}
