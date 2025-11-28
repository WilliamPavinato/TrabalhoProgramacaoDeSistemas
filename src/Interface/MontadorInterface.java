package Interface;

import javax.swing.*;
import Montador.Montador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;

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
        inputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane inputPane = new JScrollPane(inputArea);
        inputPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_PRIMARY, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        inputPane.setBackground(BG_SECONDARY);

        // Output Area
        outputArea = new JTextArea(500, 400);
        outputArea.setEditable(false);
        outputArea.setBackground(BG_SECONDARY);
        outputArea.setForeground(ACCENT_SUCCESS);
        outputArea.setFont(new Font("Consolas", Font.BOLD, 13));
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_SECONDARY, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        outputScrollPane.setBackground(BG_SECONDARY);

        // Montar Button
        montarButton = new JButton("MONTAR");
        montarButton.setBackground(ACCENT_SUCCESS);
        montarButton.setForeground(TEXT_PRIMARY);
        montarButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        montarButton.setFocusPainted(false);
        montarButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_SUCCESS, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        montarButton.addActionListener((ActionEvent e) -> {
            montarPrograma();
        });
        montarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        montarButton.setPreferredSize(new Dimension(150, 45));

        // Select File Button
        selectFileButton = new JButton("SELECIONAR");
        selectFileButton.setBackground(ACCENT_SECONDARY);
        selectFileButton.setForeground(TEXT_PRIMARY);
        selectFileButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        selectFileButton.setFocusPainted(false);
        selectFileButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_SECONDARY, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
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

        JButton executorButton = new JButton("EXECUTOR");
        executorButton.setBackground(BG_CARD);
        executorButton.setForeground(TEXT_PRIMARY);
        executorButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        executorButton.setFocusPainted(false);
        executorButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        executorButton.setPreferredSize(new Dimension(110, 40));
        executorButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            chamaExecutor();
        });

        JButton limparButton = new JButton("LIMPAR");
        limparButton.setBackground(BG_CARD);
        limparButton.setForeground(TEXT_PRIMARY);
        limparButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        limparButton.setFocusPainted(false);
        limparButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
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
        setVisible(true);
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
            String out = montador.Montar(input);
            outputArea.setText(out);
        } catch (Exception e) {
            outputArea.setText("Erro ao montar o programa: " + e.getMessage());
        }
    }
}