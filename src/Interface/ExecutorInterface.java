package Interface;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JList;
import javax.swing.JOptionPane;

import Executor.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ExecutorInterface extends javax.swing.JFrame {
    private Executor executor;
    private javax.swing.JPanel backgroundPane;
    private javax.swing.JLabel sicLabel;
    private javax.swing.JTextField inputField;
    private javax.swing.JTextField outputField;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton stepButton;
    private javax.swing.JButton runButton;
    private javax.swing.JLabel executeLabel;
    private javax.swing.JLabel inputLabel;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JLabel registersLabel;
    private javax.swing.JLabel memoryLabel;
    private javax.swing.JScrollPane memoryPane;
    private javax.swing.JScrollPane registersPane;
    private javax.swing.JTable registerTable;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JList<String> memoryList;

    // Paleta de cores moderna
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

    public ExecutorInterface() {
        super("Executor SIC/XE");
        initComponents();
    }

    // Classe para JScrollPane com cantos arredondados
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

    // Classe para JButton com cantos arredondados
    private static class RoundedButton extends JButton {
        private final int radius;
        private Color hoverColor;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);

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
                g2d.setColor(getBackground().darker());
            } else if (hoverColor != null) {
                g2d.setColor(hoverColor);
            } else {
                g2d.setColor(getBackground());
            }

            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2d.dispose();
            super.paintComponent(g);
        }
    }

    // Classe para TextField com cantos arredondados
    private static class RoundedTextField extends javax.swing.JTextField {
        private final int radius;
        private final Color borderColor;

        public RoundedTextField(int radius, Color borderColor) {
            this.radius = radius;
            this.borderColor = borderColor;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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

    // ScrollBar moderna e minimalista
    private class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = BORDER_COLOR;
            this.trackColor = BG_CARD;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new java.awt.Dimension(0, 0));
            button.setMinimumSize(new java.awt.Dimension(0, 0));
            button.setMaximumSize(new java.awt.Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(thumbColor);
            g2d.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2,
                    thumbBounds.width - 4, thumbBounds.height - 4, 8, 8);
            g2d.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(trackColor);
            g2d.fillRoundRect(trackBounds.x, trackBounds.y,
                    trackBounds.width, trackBounds.height, 8, 8);
            g2d.dispose();
        }
    }

    private void initComponents() {
        executor       = new Executor();
        backgroundPane = new javax.swing.JPanel();
        sicLabel       = new javax.swing.JLabel();

        registersPane = new RoundedScrollPane(null, 15, ACCENT_PRIMARY);
        memoryPane    = new RoundedScrollPane(null, 15, ACCENT_SECONDARY);

        registerTable  = new javax.swing.JTable();
        registersLabel = new javax.swing.JLabel();

        executeLabel = new javax.swing.JLabel();
        executeLabel.setText("teste");

        inputField  = new RoundedTextField(10, BORDER_COLOR);
        outputField = new RoundedTextField(10, BORDER_COLOR);
        inputLabel  = new javax.swing.JLabel();
        outputLabel = new javax.swing.JLabel();

        memoryList  = new javax.swing.JList<>();
        memoryLabel = new javax.swing.JLabel();

        loadButton = new RoundedButton("CARREGAR PROGRAMA", 10);
        stepButton = new RoundedButton("STEP", 10);
        runButton  = new RoundedButton("RUN", 10);

        fileChooser = new javax.swing.JFileChooser();

        //Background
        backgroundPane.setBackground(BG_PRIMARY);
        sicLabel.setForeground(ACCENT_PRIMARY);
        sicLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        sicLabel.setText("SIC/XE EXECUTOR");

        // Registradores
        attRegistradores();

        registerTable.setForeground(TEXT_PRIMARY);
        registerTable.setAlignmentY(1.0F);
        registerTable.setEnabled(false);
        registerTable.setRowHeight(35);
        registerTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        registerTable.setShowGrid(true);
        registerTable.setShowVerticalLines(false);
        registerTable.setGridColor(BORDER_COLOR);
        registerTable.setFillsViewportHeight(true);
        registerTable.setBackground(BG_SECONDARY);
        registerTable.setFont(new Font("Consolas", Font.PLAIN, 13));

        DefaultTableCellRenderer MyHeaderRender = new DefaultTableCellRenderer();
        MyHeaderRender.setBackground(ACCENT_PRIMARY);
        MyHeaderRender.setForeground(TEXT_PRIMARY);
        MyHeaderRender.setFont(new Font("Segoe UI", Font.BOLD, 12));
        registerTable.getTableHeader().getColumnModel().getColumn(0).setHeaderRenderer(MyHeaderRender);
        registerTable.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(MyHeaderRender);

        registersPane.setViewportView(registerTable);
        registersPane.setBackground(BG_SECONDARY);
        registersPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        registersPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        registersPane.getVerticalScrollBar().setUnitIncrement(16);

        if (registerTable.getColumnModel().getColumnCount() > 0) {
            registerTable.getColumnModel().getColumn(0).setMinWidth(60);
            registerTable.getColumnModel().getColumn(0).setPreferredWidth(60);
            registerTable.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        registersLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        registersLabel.setText("REGISTRADORES");
        registersLabel.setForeground(TEXT_PRIMARY);

        // Memória
        attMemoria(memoryList);
        memoryList.setBackground(BG_SECONDARY);
        memoryList.setForeground(TEXT_PRIMARY);
        memoryList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        memoryList.setSelectionBackground(ACCENT_PRIMARY);
        memoryList.setSelectionForeground(TEXT_PRIMARY);
        memoryList.setFixedCellHeight(28);
        memoryList.setFont(new Font("Consolas", Font.PLAIN, 12));
        memoryPane.setViewportView(memoryList);
        memoryPane.setBackground(BG_SECONDARY);
        memoryPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        memoryPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        memoryPane.getVerticalScrollBar().setUnitIncrement(16);

        memoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        memoryLabel.setText("MEMÓRIA");
        memoryLabel.setForeground(TEXT_PRIMARY);

        // Input e Output
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inputLabel.setText("INPUT:");
        inputLabel.setForeground(TEXT_SECONDARY);
        inputField.setText("");
        inputField.setEnabled(false);
        inputField.setBackground(BG_CARD);
        inputField.setForeground(TEXT_PRIMARY);
        inputField.setCaretColor(TEXT_PRIMARY);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 13));
        inputField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        inputField.addActionListener((java.awt.event.ActionEvent evt) -> {
            inputFieldActionPerformed(evt);
        });

        outputLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        outputLabel.setText("OUTPUT:");
        outputLabel.setForeground(TEXT_SECONDARY);
        outputField.setText("");
        outputField.setEditable(false);
        outputField.setBackground(BG_CARD);
        outputField.setForeground(ACCENT_SUCCESS);
        outputField.setFont(new Font("Consolas", Font.BOLD, 13));
        outputField.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        // Botoes
        loadButton.setBackground(BG_CARD);
        loadButton.setForeground(TEXT_PRIMARY);
        loadButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loadButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            loadButtonActionPerformed(evt, memoryList);
        });

        runButton.setEnabled(false);
        runButton.setBackground(ACCENT_SUCCESS);
        runButton.setForeground(TEXT_PRIMARY);
        runButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        runButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            runButtonActionPerformed(evt, memoryList);
        });

        stepButton.setEnabled(false);
        stepButton.setBackground(ACCENT_SECONDARY);
        stepButton.setForeground(TEXT_PRIMARY);
        stepButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        stepButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            stepButtonActionPerformed(evt, memoryList);
        });

        // Layout
        javax.swing.GroupLayout backgroundPaneLayout = new javax.swing.GroupLayout(backgroundPane);
        backgroundPane.setLayout(backgroundPaneLayout);
        backgroundPaneLayout.setHorizontalGroup(
                backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundPaneLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(sicLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(backgroundPaneLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(memoryPane, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(memoryLabel))
                                .addGap(58, 58, 58)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(registersPane, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(registersLabel))
                                .addGap(68, 68, 68)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(backgroundPaneLayout.createSequentialGroup()
                                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(outputLabel)
                                                        .addComponent(inputLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(44, Short.MAX_VALUE))
                        .addGroup(backgroundPaneLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(stepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        backgroundPaneLayout.setVerticalGroup(
                backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundPaneLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(sicLabel)
                                .addGap(28, 28, 28)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(memoryLabel)
                                        .addComponent(registersLabel)
                                        .addComponent(inputLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(memoryPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(registersPane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(backgroundPaneLayout.createSequentialGroup()
                                                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(outputLabel))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(backgroundPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(stepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_PRIMARY);
        setSize(800,550);
        setResizable(false);

        // Remove o ícone da janela (Duke) criando uma imagem transparente
        try {
            java.awt.image.BufferedImage emptyIcon = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            setIconImage(emptyIcon);
        } catch (Exception e) {
            // Se falhar, apenas ignora
        }
    }


    // Action Listeners
    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {
        String enteredText = inputField.getText();
        try {
            int value = Integer.parseInt(enteredText);
            if ( value >= 0 && value <= 255 ) {
                executor.getRegistradores().getRegistradorPorNome("A").setValorInt(value);
                attRegistradores();
                stepButton.setEnabled(true);
                runButton.setEnabled(true);
                loadButton.setEnabled(true);
                inputField.setEnabled(false);
                inputField.setBackground(Color.white);
                inputField.setForeground(Color.black);
            } else {
                JOptionPane.showMessageDialog(null, "Não é um inteiro válido!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Não é um inteiro válido!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void runButtonActionPerformed(ActionEvent evt, JList<String> memoryList) {
        stepButton.setEnabled(false);
        loadButton.setEnabled(false);

        executor.executarPrograma();

        inputField.setText("");

        if (executor.getStop()){
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            loadButton.setEnabled(true);
            inputField.setEnabled(true);
            inputField.setBackground(HIGHLIGHT);
            inputField.setForeground(BG_PRIMARY);
        }
        attRegistradores();
        attMemoria(memoryList);
        if( executor.getOutput() != -1 ) {
            outputField.setText(Integer.toString(executor.getOutput()));
            executor.setOutput(-1);
        }

        stepButton.setEnabled(false);
        runButton.setEnabled(false);
        loadButton.setEnabled(true);
        memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
    }

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt, JList<String> memoryList) {
        loadButton.setEnabled(false);
        inputField.setText("");
        if ( !executor.executarPasso() ) {
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            loadButton.setEnabled(true);
        }

        if( executor.getOutput() != -1 ) {
            outputField.setText(Integer.toString(executor.getOutput()));
            executor.setOutput(-1);
        }
        if (executor.getStop()){
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            loadButton.setEnabled(true);
            inputField.setEnabled(true);
            inputField.setBackground(HIGHLIGHT);
            inputField.setForeground(BG_PRIMARY);
        }
        attRegistradores();
        attMemoria(memoryList);
        memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
    }

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt, JList<String> memoryList) {
        executor.setOutput(-1);
        outputField.setText("");
        inputField.setEnabled(false);
        inputField.setBackground(BG_CARD);
        inputField.setForeground(TEXT_PRIMARY);

        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        try {
            if( fileChooser.showSaveDialog(rootPane) == javax.swing.JFileChooser.APPROVE_OPTION ) {
                File selectedFile = fileChooser.getSelectedFile();
                executor.setPrograma(selectedFile.getAbsolutePath());
                runButton.setEnabled(true);
                stepButton.setEnabled(true);
                inputField.setText("");
                memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
                attMemoria(memoryList);
                attRegistradores();
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }


    // Auxiliary Functions
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
                new String [] {
                        "Nome", "Valor"
                }
        ));
    }

    private void attMemoria(JList<String> memoryList) {
        memoryList.setModel(new javax.swing.AbstractListModel<String>() {
            byte[] bytes = executor.getMemoria().getMemoria();

            @Override
            public int getSize() { return bytes.length; }

            @Override
            public String getElementAt(int i) {
                return "(" + String.format("%04d", i) + ")   " +  String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
            }
        });
    }

}