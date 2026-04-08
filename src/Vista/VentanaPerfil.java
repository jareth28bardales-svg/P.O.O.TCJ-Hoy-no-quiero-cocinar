package Vista;

import Persistencia_requerida.DatabaseConnection;
import MODELO.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana de perfil del cliente.
 * Permite ver y editar los datos personales del usuario.
 *
 * @author TuNombre
 * @version 1.0
 */
public class VentanaPerfil extends JFrame {

    private Usuario usuarioActual;

    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JPasswordField txtContrasenaActual;
    private JPasswordField txtNuevaContrasena;
    private JPasswordField txtConfirmarContrasena;

    private static final Color AZUL_PRINCIPAL = new Color(41, 128, 185);
    private static final Color AZUL_OSCURO    = new Color(27, 54, 93);
    private static final Color BLANCO         = Color.WHITE;
    private static final Color GRIS_FONDO     = new Color(245, 247, 250);
    private static final Color GRIS_BORDE     = new Color(220, 220, 220);
    private static final Color GRIS_TEXTO     = new Color(130, 130, 130);
    private static final Color VERDE          = new Color(39, 174, 96);

    /**
     * Constructor de la ventana de perfil.
     * @param usuario Usuario activo en sesion
     */
    public VentanaPerfil(Usuario usuario) {
        this.usuarioActual = usuario;
        construirVentana();
    }

    /**
     * Construye y organiza todos los componentes de la ventana.
     */
    private void construirVentana() {
        setTitle("Insert Into Hunger - Mi Perfil");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JButton btnMenu      = crearBotonFranja("Menu");
        JButton btnHistorial = crearBotonFranja("Mis pedidos");
        JButton btnCerrar    = crearBotonFranjaRojo("Cerrar sesion");

        btnMenu.addActionListener(e -> {
            dispose();
            new VentanaMenu(usuarioActual).setVisible(true);
        });
        btnHistorial.addActionListener(e -> {
            dispose();
            new VentanaHistorial(usuarioActual).setVisible(true);
        });
        btnCerrar.addActionListener(e -> {
            dispose();
            new VentanaLogin().setVisible(true);
        });

        panelBotones.add(btnMenu);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnCerrar);

        franja.add(panelLogo,    BorderLayout.WEST);
        franja.add(panelBotones, BorderLayout.EAST);
        return franja;
    }

    /**
     * Crea el contenido principal dividido en dos tarjetas.
     * @return Panel del contenido
     */
    private JPanel crearContenido() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.insets  = new Insets(0, 0, 0, 20);

        // Tarjeta izquierda: datos personales
        gbc.gridx   = 0;
        gbc.weightx = 0.55;
        panel.add(crearTarjetaDatos(), gbc);

        // Tarjeta derecha: cambio de contrasena
        gbc.gridx   = 1;
        gbc.weightx = 0.45;
        gbc.insets  = new Insets(0, 0, 0, 0);
        panel.add(crearTarjetaContrasena(), gbc);

        return panel;
    }

    /**
     * Crea la tarjeta de datos personales del usuario.
     * @return Panel de datos personales
     */
    private JPanel crearTarjetaDatos() {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));

        // Avatar con inicial
        JPanel panelAvatar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAvatar.setBackground(BLANCO);

        JPanel avatar = new JPanel(new GridBagLayout());
        avatar.setBackground(AZUL_OSCURO);
        avatar.setPreferredSize(new Dimension(70, 70));
        avatar.setMaximumSize(new Dimension(70, 70));
        String inicial = usuarioActual.getNombreCompleto() != null
                && !usuarioActual.getNombreCompleto().isEmpty()
                ? String.valueOf(usuarioActual.getNombreCompleto().charAt(0)).toUpperCase()
                : "?";
        JLabel lblInicial = new JLabel(inicial);
        lblInicial.setFont(new Font("Arial", Font.BOLD, 32));
        lblInicial.setForeground(BLANCO);
        avatar.add(lblInicial);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(BLANCO);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        JLabel lblTitulo = new JLabel("Datos personales");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(AZUL_OSCURO);

        JLabel lblUsuario = new JLabel("@" + usuarioActual.getNombreUsuario());
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 13));
        lblUsuario.setForeground(GRIS_TEXTO);

        panelInfo.add(lblTitulo);
        panelInfo.add(Box.createVerticalStrut(3));
        panelInfo.add(lblUsuario);

        panelAvatar.add(avatar);
        panelAvatar.add(panelInfo);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(GRIS_BORDE);

        // Campos
        txtNombre    = crearCampo(usuarioActual.getNombreCompleto());
        txtDireccion = crearCampo(usuarioActual.getDireccion() != null
                ? usuarioActual.getDireccion() : "");
        txtTelefono  = crearCampo(usuarioActual.getTelefono() != null
                ? usuarioActual.getTelefono() : "");
        txtEmail     = crearCampo(usuarioActual.getEmail() != null
                ? usuarioActual.getEmail() : "");

        JButton btnGuardar = new JButton("Guardar cambios");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(AZUL_PRINCIPAL);
        btnGuardar.setForeground(BLANCO);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnGuardar.addActionListener(e -> guardarDatos());

        tarjeta.add(panelAvatar);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(sep);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(crearEtiqueta("Nombre completo"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtNombre);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(crearEtiqueta("Direccion de entrega"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtDireccion);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(crearEtiqueta("Telefono"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtTelefono);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(crearEtiqueta("Correo electronico"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtEmail);
        tarjeta.add(Box.createVerticalStrut(25));
        tarjeta.add(btnGuardar);

        return tarjeta;
    }

    /**
     * Crea la tarjeta de cambio de contrasena.
     * @return Panel de contrasena
     */
    private JPanel crearTarjetaContrasena() {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(BLANCO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));

        JLabel lblTitulo = new JLabel("Cambiar contrasena");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(AZUL_OSCURO);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Por seguridad, confirma tu contrasena actual");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSub.setForeground(GRIS_TEXTO);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(GRIS_BORDE);

        txtContrasenaActual    = crearCampoPassword();
        txtNuevaContrasena     = crearCampoPassword();
        txtConfirmarContrasena = crearCampoPassword();

        JButton btnCambiar = new JButton("Actualizar contrasena");
        btnCambiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCambiar.setBackground(VERDE);
        btnCambiar.setForeground(BLANCO);
        btnCambiar.setBorderPainted(false);
        btnCambiar.setFocusPainted(false);
        btnCambiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnCambiar.addActionListener(e -> cambiarContrasena());

        // Informacion de cuenta (solo lectura)
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(new Color(245, 247, 250));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        panelInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel lblInfoTitulo = new JLabel("Informacion de cuenta");
        lblInfoTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblInfoTitulo.setForeground(GRIS_TEXTO);

        JLabel lblRol = new JLabel("Rol: " + usuarioActual.getRol());
        lblRol.setFont(new Font("Arial", Font.PLAIN, 13));
        lblRol.setForeground(AZUL_OSCURO);

        JLabel lblUser = new JLabel("Usuario: " + usuarioActual.getNombreUsuario());
        lblUser.setFont(new Font("Arial", Font.PLAIN, 13));
        lblUser.setForeground(AZUL_OSCURO);

        panelInfo.add(lblInfoTitulo);
        panelInfo.add(Box.createVerticalStrut(6));
        panelInfo.add(lblRol);
        panelInfo.add(Box.createVerticalStrut(3));
        panelInfo.add(lblUser);

        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(lblSub);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(sep);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(crearEtiqueta("Contrasena actual"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtContrasenaActual);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(crearEtiqueta("Nueva contrasena"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtNuevaContrasena);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(crearEtiqueta("Confirmar nueva contrasena"));
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtConfirmarContrasena);
        tarjeta.add(Box.createVerticalStrut(25));
        tarjeta.add(btnCambiar);
        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(panelInfo);

        return tarjeta;
    }

    /**
     * Guarda los datos personales actualizados.
     */
    private void guardarDatos() {
        String nombre    = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono  = txtTelefono.getText().trim();
        String email     = txtEmail.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nombre, direccion y telefono son obligatorios.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarioActual.setNombreCompleto(nombre);
        usuarioActual.setDireccion(direccion);
        usuarioActual.setTelefono(telefono);
        usuarioActual.setEmail(email);

        boolean ok = DatabaseConnection.actualizarPerfil(usuarioActual);
        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Datos actualizados correctamente.",
                    "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar los datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Valida y actualiza la contrasena del usuario.
     */
    private void cambiarContrasena() {
        String actual    = new String(txtContrasenaActual.getPassword()).trim();
        String nueva     = new String(txtNuevaContrasena.getPassword()).trim();
        String confirmar = new String(txtConfirmarContrasena.getPassword()).trim();

        if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Completa todos los campos de contrasena.",
                    "Campos vacios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!actual.equals(usuarioActual.getContrasena())) {
            JOptionPane.showMessageDialog(this,
                    "La contrasena actual no es correcta.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nueva.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                    "La nueva contrasena y la confirmacion no coinciden.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nueva.length() < 6) {
            JOptionPane.showMessageDialog(this,
                    "La nueva contrasena debe tener al menos 6 caracteres.",
                    "Contrasena debil", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarioActual.setContrasena(nueva);
        boolean ok = DatabaseConnection.actualizarPerfil(usuarioActual);
        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Contrasena actualizada correctamente.",
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
            txtContrasenaActual.setText("");
            txtNuevaContrasena.setText("");
            txtConfirmarContrasena.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar la contrasena.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea un campo de texto estilizado con valor inicial.
     * @param valor Valor inicial del campo
     * @return Campo de texto
     */
    private JTextField crearCampo(String valor) {
        JTextField campo = new JTextField(valor);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    /**
     * Crea un campo de contrasena estilizado.
     * @return Campo de contrasena
     */
    private JPasswordField crearCampoPassword() {
        JPasswordField campo = new JPasswordField();
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    /**
     * Crea una etiqueta con el estilo del sistema.
     * @param texto Texto de la etiqueta
     * @return Etiqueta estilizada
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(GRIS_TEXTO);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
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
}
