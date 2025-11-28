package Interface;


import javax.swing.*;

import Montador.Montador;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.*;
import java.nio.file.Files;

//@SuppressWarnings("serial")
public class MontadorInterface extends JFrame {
    private final Montador montador;
    private JPanel headerPanel;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton montarButton;
    private JButton selectFileButton;
    private JLabel sicLabel;

    public MontadorInterface() {
        super("Montador SIC/XE");
        montador = new Montador();
        initComponents();
    }

    private void initComponents() {
        // Header Panel
        headerPanel = new JPanel();
        headerPanel.setBackground(ColorPalette.BG.getColor());
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 45, 10, 10));
        sicLabel = new JLabel();
        sicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sicLabel.setForeground(ColorPalette.TITLE.getColor());
        sicLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sicLabel.setText("Montador SIC/XE");
        headerPanel.add(sicLabel);

        inputArea = new JTextArea(500, 400);
        inputArea.setBackground(ColorPalette.BG_GRID.getColor());
        inputArea.setForeground(Color.white);
        JScrollPane inputPane = new JScrollPane(inputArea);


        outputArea = new JTextArea(500, 400);
        outputArea.setEditable(false);
        outputArea.setBackground(ColorPalette.BG_GRID.getColor());
        outputArea.setForeground(Color.white);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        montarButton = new JButton("Montar");
        montarButton.setBackground(ColorPalette.GRID.getColor());
        montarButton.setForeground(ColorPalette.TEXT.getColor());
        montarButton.addActionListener((ActionEvent e) -> {
            montarPrograma();
        });

        montarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        montarButton.setPreferredSize(new Dimension(150, 50));

        selectFileButton = new JButton("Selecionar");
        selectFileButton.setBackground(ColorPalette.GRID.getColor());
        selectFileButton.setForeground(ColorPalette.TEXT.getColor());
        selectFileButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            selectFileButtonActionPerformed(evt);
        });

        selectFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectFileButton.setPreferredSize(new Dimension(150, 50));

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel innerLeftPanel = new JPanel();
        innerLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        innerLeftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        innerLeftPanel.setBackground(ColorPalette.BG.getColor());

        JLabel inputLabel = new JLabel("Input");
        inputLabel.setFont(new Font("Arial", 0, 20));
        inputLabel.setForeground(ColorPalette.TEXT.getColor());

        innerLeftPanel.add(inputLabel);
        leftPanel.add(innerLeftPanel, BorderLayout.NORTH);
        leftPanel.add(inputPane, BorderLayout.CENTER);
        leftPanel.setPreferredSize(new Dimension(300, 600));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(15, 45, 30, 15));
        leftPanel.setBackground(ColorPalette.BG.getColor());

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerPanel.add(montarButton);
        centerPanel.add(selectFileButton);
        centerPanel.setPreferredSize(new Dimension(300, 600));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));
        centerPanel.setBackground(ColorPalette.BG.getColor());

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel innerRightPanel = new JPanel();
        innerRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        innerRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        innerRightPanel.setBackground(ColorPalette.BG.getColor());

        JLabel outputLabel = new JLabel("Output");
        outputLabel.setForeground(ColorPalette.TEXT.getColor());
        outputLabel.setFont(new Font("Arial", 0, 20));

        innerRightPanel.add(outputLabel);
        rightPanel.add(innerRightPanel, BorderLayout.NORTH);
        rightPanel.add(outputScrollPane, BorderLayout.CENTER);
        rightPanel.setPreferredSize(new Dimension(300, 600));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 30, 45));
        rightPanel.setBackground(ColorPalette.BG.getColor());

        // Body Panel
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setBackground(ColorPalette.BG.getColor());
        horizontalPanel.add(leftPanel);
        horizontalPanel.add(centerPanel);
        horizontalPanel.add(rightPanel);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20,37));
        footerPanel.setBackground(ColorPalette.BG.getColor());

        JButton executorButton = new JButton("Executor");
        executorButton.setBackground(ColorPalette.GRID.getColor());
        executorButton.setForeground(ColorPalette.TEXT.getColor());
        executorButton.setPreferredSize(new Dimension(92, 35));
        executorButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            chamaExecutor();
        });

        JButton limparButton = new JButton("Limpar");
        limparButton.setBackground(ColorPalette.GRID.getColor());
        limparButton.setForeground(ColorPalette.TEXT.getColor());
        limparButton.setPreferredSize(new Dimension(92, 35));
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
        setBackground(ColorPalette.BG.getColor());
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
