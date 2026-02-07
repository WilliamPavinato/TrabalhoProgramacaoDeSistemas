package TrabalhoPS;

import Executor.Executor;
import Ligador.Ligador;
import Montador.Montador;
import ProcessadorDeMacros.ProcessadorDeMacros;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SICXE extends javax.swing.JFrame {

    private ProcessadorDeMacros processadorDeMacrosPrimeiroModulo = new ProcessadorDeMacros();
    private ProcessadorDeMacros processadorDeMacrosSegundoModulo = new ProcessadorDeMacros();
    private Montador montadorPrimeiroModulo = new Montador();
    private Montador montadorSegundoModulo = new Montador();
    private Ligador ligador = new Ligador();
    private Executor executor = new Executor();

    // interface
    private javax.swing.JPanel backgroundPane;
    private javax.swing.JLabel titleLabel;

    private JTextArea txtMod1Source, txtMod1Output;
    private JTextArea txtMod2Source, txtMod2Output;
    private RoundedScrollPane scrollMod1Src, scrollMod1Out;
    private RoundedScrollPane scrollMod2Src, scrollMod2Out;
    private javax.swing.JLabel lblMod1, lblMod2;

    // memória e registradores
    private RoundedScrollPane memoryPane;
    private RoundedScrollPane registersPane;
    private javax.swing.JTable registerTable;
    private javax.swing.JList<String> memoryList;
    private javax.swing.JLabel lblMemory, lblRegisters;

    private RoundedTextField inputField;
    private RoundedTextField outputField;
    private javax.swing.JLabel lblInput, lblOutput;

    private RoundedButton btnMacros;
    private RoundedButton btnMontar;
    private RoundedButton btnLigar;
    private RoundedButton btnCarregar;
    private RoundedButton btnReiniciar;

    private RoundedButton btnStep;
    private RoundedButton btnRun;

    private final Color BG_PRIMARY       = new Color(255, 240, 245); // Rosa bebê bem claro
    private final Color BG_SECONDARY     = new Color(255, 228, 238); // Rosa bebê médio
    private final Color BG_CARD          = new Color(255, 218, 233); // Rosa bebê card
    private final Color ACCENT_PRIMARY   = new Color(236, 72, 153);  // Rosa vibrante
    private final Color ACCENT_SECONDARY = new Color(219, 39, 119);  // Rosa escuro
    private final Color ACCENT_SUCCESS   = new Color(34, 197, 94);   // Verde
    private final Color TEXT_PRIMARY     = new Color(30, 30, 30);    // Cinza escuro
    private final Color TEXT_SECONDARY   = new Color(100, 100, 100); // Cinza médio
    private final Color BORDER_COLOR     = new Color(244, 114, 182); // Rosa borda
    private final Color HIGHLIGHT        = new Color(255, 199, 0);   // Amarelo dourado

    public SICXE() {
        super("Ambiente SIC/XE");
        initComponents();
        attRegistradores();
        attMemoria(memoryList);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private static class RoundedScrollPane extends JScrollPane {
        private final int radius;
        private final Color borderColor;

        public RoundedScrollPane(Component view, int radius, Color borderColor) {
            super(view);
            this.radius = radius;
            this.borderColor = borderColor;
            setOpaque(false);
            getViewport().setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2d.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);
            g2d.dispose();
        }
    }

    private static class RoundedButton extends JButton {
        private final int radius;
        private Color hoverColor;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (isEnabled()) {
                        hoverColor = getBackground().brighter();
                        repaint();
                    }
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    hoverColor = null;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (!isEnabled()) {
                g2d.setColor(Color.GRAY);
            } else if (hoverColor != null) {
                g2d.setColor(hoverColor);
            } else {
                g2d.setColor(getBackground());
            }

            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            super.paintComponent(g);
            g2d.dispose();
        }
    }

    private static class RoundedTextField extends javax.swing.JTextField {
        private final int radius;
        private final Color borderColor;

        public RoundedTextField(int radius, Color borderColor) {
            this.radius = radius;
            this.borderColor = borderColor;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2d.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radius, radius);
            g2d.dispose();
        }
    }

    private class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = BORDER_COLOR;
            this.trackColor = BG_CARD;
        }
        @Override
        protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
        @Override
        protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new java.awt.Dimension(0, 0));
            return button;
        }
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(thumbColor);
            g2d.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4, 8, 8);
            g2d.dispose();
        }
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(trackColor);
            g2d.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 8, 8);
            g2d.dispose();
        }
    }

    //inicializa interface
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_PRIMARY);
        setResizable(false);
        setSize(1200, 800); // Aumentei um pouco pois tem mais coisa que o Executor

        backgroundPane = new javax.swing.JPanel();
        backgroundPane.setBackground(BG_PRIMARY);

        // titulo
        titleLabel = new javax.swing.JLabel("SIC/XE AMBIENTE INTEGRADO");
        titleLabel.setForeground(ACCENT_PRIMARY);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Font codeFont = new Font("Consolas", Font.PLAIN, 12);

        txtMod1Source = createTextArea(codeFont);
        txtMod1Output = createTextArea(codeFont);
        txtMod2Source = createTextArea(codeFont);
        txtMod2Output = createTextArea(codeFont);
        txtMod1Output.setEditable(false);
        txtMod2Output.setEditable(false);

        scrollMod1Src = new RoundedScrollPane(txtMod1Source, 15, ACCENT_SECONDARY);
        scrollMod1Out = new RoundedScrollPane(txtMod1Output, 15, ACCENT_SECONDARY);
        scrollMod2Src = new RoundedScrollPane(txtMod2Source, 15, ACCENT_SECONDARY);
        scrollMod2Out = new RoundedScrollPane(txtMod2Output, 15, ACCENT_SECONDARY);

        setupScrollBar(scrollMod1Src); setupScrollBar(scrollMod1Out);
        setupScrollBar(scrollMod2Src); setupScrollBar(scrollMod2Out);

        lblMod1 = createLabel("MÓDULO 1 (Fonte / Objeto)");
        lblMod2 = createLabel("MÓDULO 2 (Fonte / Objeto)");

        // tabela de registradors
        registerTable = new javax.swing.JTable();
        registerTable.setFont(new Font("Consolas", Font.PLAIN, 13));
        registerTable.setRowHeight(25);
        registerTable.setBackground(BG_SECONDARY);
        registerTable.setForeground(TEXT_PRIMARY);
        registerTable.setGridColor(BORDER_COLOR);
        registerTable.setShowVerticalLines(false);
        registerTable.setEnabled(false);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(ACCENT_PRIMARY);
        headerRenderer.setForeground(BG_PRIMARY); // Texto branco no header
        headerRenderer.setFont(new Font("Segoe UI", Font.BOLD, 12));
        registerTable.getTableHeader().setDefaultRenderer(headerRenderer);

        registersPane = new RoundedScrollPane(registerTable, 15, ACCENT_PRIMARY);
        setupScrollBar(registersPane);
        lblRegisters = createLabel("REGISTRADORES");

        // configuração da memoria
        memoryList = new JList<>();
        memoryList.setBackground(BG_SECONDARY);
        memoryList.setForeground(TEXT_PRIMARY);
        memoryList.setFont(new Font("Consolas", Font.PLAIN, 12));
        memoryList.setSelectionBackground(ACCENT_PRIMARY);
        memoryList.setSelectionForeground(BG_PRIMARY);

        memoryPane = new RoundedScrollPane(memoryList, 15, ACCENT_SECONDARY);
        setupScrollBar(memoryPane);
        lblMemory = createLabel("MEMÓRIA");

        lblInput = new javax.swing.JLabel("INPUT:");
        lblInput.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblInput.setForeground(TEXT_SECONDARY);

        inputField = new RoundedTextField(10, BORDER_COLOR);
        inputField.setBackground(BG_CARD);
        inputField.setEnabled(false);
        inputField.addActionListener(evt -> inputFieldActionPerformed(evt));

        lblOutput = new javax.swing.JLabel("OUTPUT:");
        lblOutput.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblOutput.setForeground(TEXT_SECONDARY);

        outputField = new RoundedTextField(10, BORDER_COLOR);
        outputField.setBackground(BG_CARD);
        outputField.setForeground(ACCENT_SUCCESS);
        outputField.setFont(new Font("Consolas", Font.BOLD, 14));
        outputField.setEditable(false);

        btnMacros = new RoundedButton("MACROS", 10);
        styleButton(btnMacros, ACCENT_SECONDARY);
        btnMacros.addActionListener(evt -> processarMacros());

        btnMontar = new RoundedButton("MONTAR", 10);
        styleButton(btnMontar, ACCENT_SECONDARY);
        btnMontar.setEnabled(false);
        btnMontar.addActionListener(evt -> montar());

        btnLigar = new RoundedButton("LIGAR", 10);
        styleButton(btnLigar, ACCENT_SECONDARY);
        btnLigar.setEnabled(false);
        btnLigar.addActionListener(evt -> ligar());

        btnCarregar = new RoundedButton("CARREGAR", 10);
        styleButton(btnCarregar, ACCENT_PRIMARY);
        btnCarregar.setEnabled(false);
        btnCarregar.addActionListener(evt -> carregar());

        btnReiniciar = new RoundedButton("REINICIAR", 10);
        styleButton(btnReiniciar, TEXT_SECONDARY);
        btnReiniciar.addActionListener(evt -> reiniciar());

        btnStep = new RoundedButton("STEP", 10);
        styleButton(btnStep, ACCENT_SECONDARY);
        btnStep.setEnabled(false);
        btnStep.addActionListener(evt -> stepButtonActionPerformed(evt));

        btnRun = new RoundedButton("RUN", 10);
        styleButton(btnRun, ACCENT_SUCCESS);
        btnRun.setEnabled(false);
        btnRun.addActionListener(evt -> runButtonActionPerformed(evt));

        // layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(backgroundPane);
        backgroundPane.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)

                                        // principal
                                        .addGroup(layout.createSequentialGroup()
                                                // modulos
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblMod1)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(scrollMod1Src, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(5, 5, 5)
                                                                .addComponent(scrollMod1Out, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))

                                                        .addComponent(lblMod2)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(scrollMod2Src, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(5, 5, 5)
                                                                .addComponent(scrollMod2Out, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))

                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnMacros, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(5, 5, 5)
                                                                .addComponent(btnMontar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(5, 5, 5)
                                                                .addComponent(btnLigar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(5, 5, 5)
                                                                .addComponent(btnCarregar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                )

                                                .addGap(20, 20, 20)

                                                // memoria
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblMemory)
                                                        .addComponent(memoryPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                )

                                                .addGap(20, 20, 20)

                                                // registradores
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblRegisters)
                                                        .addComponent(registersPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)

                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(0, 0, 0)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblInput)
                                                                        .addComponent(lblOutput))
                                                                .addGap(10, 10, 10)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        )

                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnStep, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(10, 10, 10)
                                                                .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))

                                                        .addComponent(btnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                )
                                        )
                                )
                                .addContainerGap(20, Short.MAX_VALUE)
                        )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(titleLabel)
                                .addGap(20, 20, 20)

                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblMod1)
                                                .addGap(5, 5, 5)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(scrollMod1Src, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(scrollMod1Out, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(15, 15, 15)
                                                .addComponent(lblMod2)
                                                .addGap(5, 5, 5)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(scrollMod2Src, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(scrollMod2Out, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnMacros, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnMontar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnLigar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnCarregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        )

                                        // memoria
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblMemory)
                                                .addGap(5, 5, 5)
                                                .addComponent(memoryPane, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        )

                                        // registradores
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblRegisters)
                                                .addGap(5, 5, 5)
                                                .addComponent(registersPane, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblInput)
                                                        .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(15, 15, 15)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblOutput)
                                                        .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(30, 30, 30)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnStep, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20)
                                                .addComponent(btnReiniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        )
                                )
                                .addContainerGap(20, Short.MAX_VALUE)
                        )
        );

        this.setContentPane(backgroundPane);
        pack();

        try {
            java.awt.image.BufferedImage emptyIcon = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            setIconImage(emptyIcon);
        } catch (Exception e) {}
    }

    private JTextArea createTextArea(Font font) {
        JTextArea txt = new JTextArea();
        txt.setBackground(BG_CARD);
        txt.setForeground(TEXT_PRIMARY);
        txt.setCaretColor(TEXT_PRIMARY);
        txt.setFont(font);
        txt.setMargin(new Insets(5,5,5,5));
        return txt;
    }

    private javax.swing.JLabel createLabel(String text) {
        javax.swing.JLabel lbl = new javax.swing.JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    private void setupScrollBar(JScrollPane pane) {
        pane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        pane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        pane.getVerticalScrollBar().setUnitIncrement(16);
        pane.setBackground(BG_SECONDARY);
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(BG_PRIMARY); // Texto branco/claro
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }


    public void processarMacros() {
        if (txtMod1Source.getText().isEmpty() && txtMod2Source.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha pelo menos um módulo", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!txtMod1Source.getText().isEmpty()) {
            processadorDeMacrosPrimeiroModulo.limpar();
            processadorDeMacrosPrimeiroModulo.setPrograma(txtMod1Source.getText());
            processadorDeMacrosPrimeiroModulo.macroProcessor("1");
            txtMod1Output.setText(processadorDeMacrosPrimeiroModulo.getOutput());
        }

        if (!txtMod2Source.getText().isEmpty()) {
            processadorDeMacrosSegundoModulo.limpar();
            processadorDeMacrosSegundoModulo.setPrograma(txtMod2Source.getText());
            processadorDeMacrosSegundoModulo.macroProcessor("2");
            txtMod2Output.setText(processadorDeMacrosSegundoModulo.getOutput());
        }

        btnMacros.setEnabled(false);
        btnMontar.setEnabled(true);
    }

    public void montar() {
        // verifica se tem output de macro
        if (txtMod1Output.getText().isEmpty() && txtMod2Output.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nada para montar (Processe as macros primeiro)", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!txtMod1Output.getText().isEmpty()) {
            montadorPrimeiroModulo.Montar(txtMod1Output.getText(), "1");
            txtMod1Output.setText(montadorPrimeiroModulo.getOutput().getMachineCodeAsString());

            if (!montadorPrimeiroModulo.getErrorMessage().isEmpty()) {
                resetLogic();
                return;
            }
        }

        if (!txtMod2Output.getText().isEmpty()) {
            montadorSegundoModulo.Montar(txtMod2Output.getText(), "2");
            txtMod2Output.setText(montadorSegundoModulo.getOutput().getMachineCodeAsString());

            if (!montadorSegundoModulo.getErrorMessage().isEmpty()) {
                resetLogic();
                return;
            }
        }

        btnMontar.setEnabled(false);
        btnLigar.setEnabled(true);
    }

    public void ligar() {
        if (txtMod1Output.getText().isEmpty() && txtMod2Output.getText().isEmpty()) {
            return;
        }

        if (!txtMod1Output.getText().isEmpty()) {
            ligador.adicionarPrograma(montadorPrimeiroModulo);
        }

        if (!txtMod2Output.getText().isEmpty()) {
            ligador.adicionarPrograma(montadorSegundoModulo);
        }

        String output = ligador.ligarProgramas();
        txtMod1Output.setText(output);
        txtMod2Output.setText(output);

        btnLigar.setEnabled(false);
        btnCarregar.setEnabled(true);
    }

    public void carregar() {
        executor.setOutput(-1);
        outputField.setText("");
        inputField.setEnabled(false);
        inputField.setBackground(BG_CARD);

        executor.setPrograma(txtMod1Output.getText());

        btnRun.setEnabled(true);
        btnStep.setEnabled(true);
        inputField.setText("");
        memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());

        attMemoria(memoryList);
        attRegistradores();

        btnCarregar.setEnabled(false);
    }

    public void reiniciar() {
        resetLogic();
        txtMod1Source.setText("");
        txtMod2Source.setText("");
        txtMod1Output.setText("");
        txtMod2Output.setText("");
        outputField.setText("");
        inputField.setText("");

        processadorDeMacrosPrimeiroModulo.limpar();
        processadorDeMacrosSegundoModulo.limpar();
        montadorPrimeiroModulo.limpaListas();
        montadorSegundoModulo.limpaListas();
        ligador.limpar();
        executor.limpar();

        attRegistradores();
        attMemoria(memoryList);
    }

    private void resetLogic() {
        btnMacros.setEnabled(true);
        btnMontar.setEnabled(false);
        btnCarregar.setEnabled(false);
        btnLigar.setEnabled(false);
        btnRun.setEnabled(false);
        btnStep.setEnabled(false);
    }

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt) {
        inputField.setText("");
        if ( !executor.executarPasso() ) {
            btnStep.setEnabled(false);
            btnRun.setEnabled(false);
        }
        updateAfterExec();
    }

    private void runButtonActionPerformed(ActionEvent evt) {
        btnStep.setEnabled(false);
        executor.executarPrograma();
        inputField.setText("");
        updateAfterExec();
        btnStep.setEnabled(false);
        btnRun.setEnabled(false);
        memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
    }

    private void updateAfterExec() {
        if( executor.getOutput() != -1 ) {
            outputField.setText(Integer.toString(executor.getOutput()));
            executor.setOutput(-1);
        }
        if (executor.getStop()){
            btnStep.setEnabled(false);
            btnRun.setEnabled(false);
            inputField.setEnabled(true);
            inputField.setBackground(HIGHLIGHT);
            inputField.setForeground(TEXT_PRIMARY);
        }
        attRegistradores();
        attMemoria(memoryList);
        try {
            memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
        } catch(Exception e){}
    }

    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {
        String enteredText = inputField.getText();
        try {
            int value = Integer.parseInt(enteredText);
            if ( value >= 0 && value <= 255 ) {
                executor.getRegistradores().getRegistradorPorNome("A").setValorInt(value);
                attRegistradores();
                btnStep.setEnabled(true);
                btnRun.setEnabled(true);
                inputField.setEnabled(false);
                inputField.setBackground(BG_CARD);
                inputField.setForeground(TEXT_PRIMARY);
            } else {
                JOptionPane.showMessageDialog(null, "Não é um inteiro válido!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Não é um inteiro válido!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // atualiza dados

    private void attRegistradores() {
        registerTable.setModel(new DefaultTableModel(
                new Object [][] {
                        {"PC", executor.getRegistradores().getValorPC()},
                        {"A", executor.getRegistradores().getRegistradorPorNome("A").getValorIntSigned()},
                        {"X", executor.getRegistradores().getRegistradorPorNome("X").getValorIntSigned()},
                        {"L", executor.getRegistradores().getRegistradorPorNome("L").getValorIntSigned()},
                        {"B", executor.getRegistradores().getRegistradorPorNome("B").getValorIntSigned()},
                        {"S", executor.getRegistradores().getRegistradorPorNome("S").getValorIntSigned()},
                        {"T", executor.getRegistradores().getRegistradorPorNome("T").getValorIntSigned()},
                        {"SW", executor.getRegistradores().getRegistradorPorNome("SW").getValorIntSigned()}
                },
                new String [] { "Nome", "Valor" }
        ));
    }

    private void attMemoria(JList<String> memory) {
        memory.setModel(new javax.swing.AbstractListModel<String>() {
            byte[] bytes = executor.getMemoria().getMemoria();
            @Override
            public int getSize() { return bytes.length; }
            @Override
            public String getElementAt(int i) {
                return "(" + String.format("%04d", i) + ")    " +  String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
            }
        });
    }
}