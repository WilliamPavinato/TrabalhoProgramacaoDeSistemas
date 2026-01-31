package Interface;

import javax.swing.*;
import Montador.Montador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import javax.swing.border.AbstractBorder;

public class MontadorInterface extends JFrame {
    private final Montador montador;
    private JPanel headerPanel;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton montarButton;
    private JButton selectFileButton;
    private JLabel sicLabel;

    // Paleta de cores moderna (mesma do ExecutorInterface)
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

    public MontadorInterface() {
        super("Montador SIC/XE");
        montador = new Montador();
        initComponents();
    }

    // Classe para borda arredondada
    private static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int radius;

        public RoundedBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawRoundRect(x + thickness/2, y + thickness/2,
                    width - thickness, height - thickness, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness + 2, thickness + 2, thickness + 2, thickness + 2);
        }
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

    private void initComponents() {
        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(BG_PRIMARY);
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 45, 10, 10));

        sicLabel = new JLabel();
        sicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sicLabel.setForeground(ACCENT_PRIMARY);
        sicLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        sicLabel.setText("MONTADOR SIC/XE");
        headerPanel.add(sicLabel);

        // Input Area
        inputArea = new JTextArea(500, 400);
        inputArea.setBackground(BG_SECONDARY);
        inputArea.setForeground(TEXT_PRIMARY);
        inputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        inputArea.setCaretColor(TEXT_PRIMARY);
        inputArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        RoundedScrollPane inputPane = new RoundedScrollPane(inputArea, 15, ACCENT_PRIMARY);
        inputPane.setBackground(BG_SECONDARY);
        inputPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        inputPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        inputPane.getVerticalScrollBar().setUnitIncrement(16);
        inputPane.getHorizontalScrollBar().setUnitIncrement(16);

        // Output Area
        outputArea = new JTextArea(500, 400);
        outputArea.setEditable(false);
        outputArea.setBackground(BG_SECONDARY);
        outputArea.setForeground(ACCENT_SUCCESS);
        outputArea.setFont(new Font("Consolas", Font.BOLD, 13));
        outputArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        RoundedScrollPane outputScrollPane = new RoundedScrollPane(outputArea, 15, ACCENT_SECONDARY);
        outputScrollPane.setBackground(BG_SECONDARY);
        outputScrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        outputScrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        outputScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        outputScrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        // Montar Button
        montarButton = new RoundedButton("MONTAR", 12);
        montarButton.setBackground(ACCENT_SUCCESS);
        montarButton.setForeground(TEXT_PRIMARY);
        montarButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        montarButton.addActionListener((ActionEvent e) -> {
            montarPrograma();
        });
        montarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        montarButton.setPreferredSize(new Dimension(150, 45));

        // Select File Button
        selectFileButton = new RoundedButton("SELECIONAR", 12);
        selectFileButton.setBackground(ACCENT_SECONDARY);
        selectFileButton.setForeground(TEXT_PRIMARY);
        selectFileButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        selectFileButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            selectFileButtonActionPerformed(evt);
        });
        selectFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectFileButton.setPreferredSize(new Dimension(150, 45));

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel innerLeftPanel = new JPanel();
        innerLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        innerLeftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        innerLeftPanel.setBackground(BG_PRIMARY);

        JLabel inputLabel = new JLabel("INPUT");
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        inputLabel.setForeground(TEXT_PRIMARY);

        innerLeftPanel.add(inputLabel);
        leftPanel.add(innerLeftPanel, BorderLayout.NORTH);
        leftPanel.add(inputPane, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(300, 600));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(15, 45, 30, 15));
        leftPanel.setBackground(BG_PRIMARY);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerPanel.add(montarButton);
        centerPanel.add(selectFileButton);
        centerPanel.setPreferredSize(new Dimension(300, 600));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));
        centerPanel.setBackground(BG_PRIMARY);

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel innerRightPanel = new JPanel();
        innerRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        innerRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        innerRightPanel.setBackground(BG_PRIMARY);

        JLabel outputLabel = new JLabel("OUTPUT");
        outputLabel.setForeground(TEXT_PRIMARY);
        outputLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        innerRightPanel.add(outputLabel);
        rightPanel.add(innerRightPanel, BorderLayout.NORTH);
        rightPanel.add(outputScrollPane, BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(300, 600));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 30, 45));
        rightPanel.setBackground(BG_PRIMARY);

        // Body Panel
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setBackground(BG_PRIMARY);
        horizontalPanel.add(leftPanel);
        horizontalPanel.add(centerPanel);
        horizontalPanel.add(rightPanel);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 37));
        footerPanel.setBackground(BG_PRIMARY);

        JButton executorButton = new RoundedButton("EXECUTOR", 10);
        executorButton.setBackground(BG_CARD);
        executorButton.setForeground(TEXT_PRIMARY);
        executorButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        executorButton.setPreferredSize(new Dimension(110, 40));
        executorButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            chamaExecutor();
        });

        JButton limparButton = new RoundedButton("LIMPAR", 10);
        limparButton.setBackground(BG_CARD);
        limparButton.setForeground(TEXT_PRIMARY);
        limparButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        limparButton.setPreferredSize(new Dimension(110, 40));
        limparButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            limparButtonActionPerformed(evt);
        });

        footerPanel.add(executorButton);
        footerPanel.add(limparButton);

        // Main Panel
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(horizontalPanel, BorderLayout.CENTER);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);

        pack();

        // Frame settings
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(BG_PRIMARY);
        getContentPane().setBackground(BG_PRIMARY);
        setSize(800, 550);
        setResizable(false);
        setLocationRelativeTo(null);

        // Remove o ícone da janela criando uma imagem transparente
        try {
            java.awt.image.BufferedImage emptyIcon = new java.awt.image.BufferedImage(1, 1, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            setIconImage(emptyIcon);
        } catch (Exception e) {
            // Se falhar, apenas ignora
        }

        setVisible(true);
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

    private void chamaExecutor() {
        setVisible(false);
        new ExecutorInterface();
    }

    private void limparButtonActionPerformed(ActionEvent evt) {
        outputArea.setText("");
        inputArea.setText("");
        montador.limpaListas();
    }

    // ActionListeners
    private void selectFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(selectedFile.toPath()));
                inputArea.setText(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void montarPrograma() {
        outputArea.setText("");
        String input = inputArea.getText();
        try {
            String out = montador.Montar(input, "1");
            outputArea.setText(out);
        } catch (Exception e) {
            outputArea.setText("Erro ao montar o programa: " + e.getMessage());
        }
    }
}