package Interface;

import javax.swing.*;
import ProcessadorDeMacros.ProcessadorDeMacros;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import javax.swing.border.AbstractBorder;

public class ProcessadorMacrosInterface extends JFrame{
    private final Color BG_PRIMARY = new Color(255, 240, 245); // rosa bebê bem claro
    private final Color BG_SECONDARY     = new Color(255, 228, 238); // rosa bebê médio
    private final Color BG_CARD          = new Color(255, 218, 233); // rosa bebê card
    private final Color ACCENT_PRIMARY   = new Color(236, 72, 153);  // rosa vibrante
    private final Color ACCENT_SECONDARY = new Color(219, 39, 119);  // rosa escuro
    private final Color ACCENT_SUCCESS   = new Color(34, 197, 94);   // verde
    private final Color TEXT_PRIMARY     = new Color(30, 30, 30);    // cinza escuro
    private final Color TEXT_SECONDARY   = new Color(100, 100, 100); // cinza médio
    private final Color BORDER_COLOR     = new Color(244, 114, 182); // rosa borda
    private final Color HIGHLIGHT        = new Color(255, 199, 0);   // amarelo dourado

    private final ProcessadorDeMacros processadorDeMacros;
    private String content;

    private JTextArea codeArea;
    private JTextArea macrosArea;
    private JButton converterButton;
    private JButton selectFileButton;
    private JButton montadorButton;
    private JButton limparButton;

    public ProcessadorMacrosInterface() {
        super("Processador de Macros SIC/XE");
        processadorDeMacros = new ProcessadorDeMacros();
        initComponents();
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

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    hoverColor = getBackground().brighter();
                    repaint();
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

            if (hoverColor != null) {
                g2d.setColor(hoverColor);
            } else {
                g2d.setColor(getBackground());
            }

            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2d.dispose();
            super.paintComponent(g);
        }
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
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
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

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BG_PRIMARY);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 45, 10, 10));

        JLabel titleLabel = new JLabel("PROCESSADOR DE MACROS SIC/XE");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(ACCENT_PRIMARY);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        panel.add(titleLabel);
        return panel;
    }

    private void setupTextAreas() {
        // area de codigo
        codeArea = new JTextArea(500, 400);
        codeArea.setBackground(BG_SECONDARY);
        codeArea.setForeground(TEXT_PRIMARY);
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        codeArea.setCaretColor(TEXT_PRIMARY);
        codeArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // area de macros
        macrosArea = new JTextArea(500, 400);
        macrosArea.setEditable(false);
        macrosArea.setBackground(BG_SECONDARY);
        macrosArea.setForeground(ACCENT_SUCCESS);
        macrosArea.setFont(new Font("Consolas", Font.BOLD, 13));
        macrosArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void setupButtons() {
        // botao selecionar arquivo
        selectFileButton = new RoundedButton("SELECIONAR", 12);
        selectFileButton.setBackground(ACCENT_SECONDARY);
        selectFileButton.setForeground(TEXT_PRIMARY);
        selectFileButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        selectFileButton.setPreferredSize(new Dimension(150, 45));
        selectFileButton.addActionListener(evt -> selectFileButtonActionPerformed(evt));

        // botao converter
        converterButton = new RoundedButton("CONVERTER", 12);
        converterButton.setEnabled(false);
        converterButton.setBackground(ACCENT_SUCCESS);
        converterButton.setForeground(TEXT_PRIMARY);
        converterButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        converterButton.setPreferredSize(new Dimension(150, 45));
        converterButton.addActionListener(e -> converter());

        // botao montador
        montadorButton = new RoundedButton("MONTADOR", 10);
        montadorButton.setBackground(BG_CARD);
        montadorButton.setForeground(TEXT_PRIMARY);
        montadorButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        montadorButton.setPreferredSize(new Dimension(110, 40));
        montadorButton.addActionListener(evt -> chamaMontador());

        // botao limpar
        limparButton = new RoundedButton("LIMPAR", 10);
        limparButton.setBackground(BG_CARD);
        limparButton.setForeground(TEXT_PRIMARY);
        limparButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        limparButton.setPreferredSize(new Dimension(110, 40));
        limparButton.addActionListener(evt -> limpar());
    }

    private JPanel createLeftPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_PRIMARY);
        mainPanel.setPreferredSize(new Dimension(300, 600));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 45, 30, 15));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelPanel.setBackground(BG_PRIMARY);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel label = new JLabel("CÓDIGO");
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(TEXT_PRIMARY);
        labelPanel.add(label);

        RoundedScrollPane scrollPane = new RoundedScrollPane(codeArea, 15, ACCENT_PRIMARY);
        scrollPane.setBackground(BG_SECONDARY);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        mainPanel.add(labelPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setBackground(BG_PRIMARY);
        panel.setPreferredSize(new Dimension(300, 600));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        panel.add(converterButton);
        panel.add(selectFileButton);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_PRIMARY);
        mainPanel.setPreferredSize(new Dimension(300, 600));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 30, 45));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelPanel.setBackground(BG_PRIMARY);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel label = new JLabel("MACROS");
        label.setForeground(TEXT_PRIMARY);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelPanel.add(label);

        RoundedScrollPane scrollPane = new RoundedScrollPane(macrosArea, 15, ACCENT_SECONDARY);
        scrollPane.setBackground(BG_SECONDARY);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        mainPanel.add(labelPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(BG_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 37));

        panel.add(montadorButton);
        panel.add(limparButton);

        return panel;
    }

    private void initComponents() {
        setupTextAreas();
        setupButtons();

        // criar paineis
        JPanel headerPanel = createHeaderPanel();
        JPanel leftPanel = createLeftPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel rightPanel = createRightPanel();
        JPanel footerPanel = createFooterPanel();

        // painel
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.X_AXIS));
        bodyPanel.setBackground(BG_PRIMARY);
        bodyPanel.add(leftPanel);
        bodyPanel.add(centerPanel);
        bodyPanel.add(rightPanel);

        // layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(bodyPanel, BorderLayout.CENTER);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_PRIMARY);
        getContentPane().setBackground(BG_PRIMARY);
        setSize(800, 550);
        setResizable(false);
        setLocationRelativeTo(null);

        // remove icone da janela
        try {
            java.awt.image.BufferedImage emptyIcon = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            setIconImage(emptyIcon);
        } catch (Exception e) {
            // ignora se falhar
        }

        pack();
        setVisible(true);
    }

    private void converter() {
        String programaCompleto = macrosArea.getText() + "\n" + codeArea.getText();
        processadorDeMacros.limpar();
        processadorDeMacros.setPrograma(programaCompleto);
        processadorDeMacros.macroProcessor("1");
        codeArea.setText(processadorDeMacros.getOutput());
        converterButton.setEnabled(false);
    }

    private void chamaMontador() {
        setVisible(false);
        new MontadorInterface();
    }

    private void limpar() {
        codeArea.setText("");
        macrosArea.setText("");
        content = "";
        processadorDeMacros.limpar();
    }

    private void separaString() {
        String macros = "";
        String programa = "START";
        String[] separada = content.split("START");

        if (separada.length > 1) {
            macros = separada[0];
            programa += separada[1];
        } else {
            programa += separada[0];
        }

        codeArea.setText(programa);
        macrosArea.setText(macros);
    }

    private void selectFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        limpar();
        converterButton.setEnabled(true);

        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                StringBuilder contentBuilder = new StringBuilder();
                for (String line : lines) {
                    contentBuilder.append(line).append("\n");
                }
                content = contentBuilder.toString();
                separaString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}