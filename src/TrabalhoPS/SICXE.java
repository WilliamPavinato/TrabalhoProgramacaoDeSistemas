package TrabalhoPS;

import Interface.ColorPalette;
import Ligador.Ligador;
import Montador.Montador;
import ProcessadorDeMacros.ProcessadorDeMacros;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Executor.Executor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Graziele
 */
public class SICXE extends javax.swing.JFrame{
    private javax.swing.JButton carregarButton;
    private javax.swing.JTextField input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton ligarButton;
    private javax.swing.JList<String> memory;
    private javax.swing.JButton montarButton;
    private javax.swing.JTextField output;
    private javax.swing.JTextArea primeiroModulo;
    private javax.swing.JLabel primeiroModuloLabel;
    private javax.swing.JTextArea primeiroModuloOutput;
    private javax.swing.JButton processarMacrosButton;
    private javax.swing.JButton reiniciarButton;
    private javax.swing.JTable registers;
    private javax.swing.JButton runButton;
    private javax.swing.JTextArea segundoModulo;
    private javax.swing.JLabel segundoModuloLabel;
    private javax.swing.JLabel segundoModuloLabel1;
    private javax.swing.JLabel segundoModuloLabel2;
    private javax.swing.JLabel segundoModuloLabel3;
    private javax.swing.JLabel segundoModuloLabel4;
    private javax.swing.JTextArea segundoModuloOutput;
    private javax.swing.JButton stepButton;


    private ProcessadorDeMacros processadorDeMacrosPrimeiroModulo = new ProcessadorDeMacros();
    private ProcessadorDeMacros processadorDeMacrosSegundoModulo = new ProcessadorDeMacros();

    private Montador montadorPrimeiroModulo = new Montador();
    private Montador montadorSegundoModulo = new Montador();

    private Ligador ligador = new Ligador();
    private Executor executor  = new Executor();

    public SICXE() {
        super("SIC/XE");
        initComponents();
        attRegistradores();
        attMemoria(memory);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void attRegistradores() {
        registers.setModel(new DefaultTableModel(
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

    private void attMemoria(JList<String> memory) {
        memory.setModel(new javax.swing.AbstractListModel<String>() {
            byte[] bytes = executor.getMemoria().getMemoria();

            @Override
            public int getSize() { return bytes.length; }

            @Override
            public String getElementAt(int i) {
                return "(" + String.format("%04d", i) + ")   " +  String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0');
            }
        });
    }

    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt, JList<String> memory) {
        input.setText("");
        if ( !executor.executarPasso() ) {
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
        }

        if( executor.getOutput() != -1 ) {
            output.setText(Integer.toString(executor.getOutput()));
            executor.setOutput(-1);
        }
        if (executor.getStop()){
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            input.setEnabled(true);
            input.setBackground(ColorPalette.TITLE.getColor());
            input.setForeground(Color.black);
        }
        attRegistradores();
        attMemoria(memory);
        memory.setSelectedIndex(executor.getRegistradores().getValorPC());
    }

    private void runButtonActionPerformed(ActionEvent evt, JList<String> memoryList) {
        stepButton.setEnabled(false);

        executor.executarPrograma();

        input.setText("");

        if (executor.getStop()){
            stepButton.setEnabled(false);
            runButton.setEnabled(false);
            input.setEnabled(true);
            input.setBackground(ColorPalette.TITLE.getColor());
            input.setForeground(Color.black);
        }
        attRegistradores();
        attMemoria(memoryList);
        if( executor.getOutput() != -1 ) {
            output.setText(Integer.toString(executor.getOutput()));
            executor.setOutput(-1);
        }

        stepButton.setEnabled(false);
        runButton.setEnabled(false);
        memoryList.setSelectedIndex(executor.getRegistradores().getValorPC());
    }

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {
        String enteredText = input.getText();
        try {
            int value = Integer.parseInt(enteredText);
            if ( value >= 0 && value <= 255 ) {
                executor.getRegistradores().getRegistradorPorNome("A").setValorInt(value);
                attRegistradores();
                stepButton.setEnabled(true);
                runButton.setEnabled(true);
                input.setEnabled(false);
                input.setBackground(new java.awt.Color(36, 37, 38));
                input.setForeground(new java.awt.Color(228, 230, 235));
            } else {
                JOptionPane.showMessageDialog(null, "Não é um inteiro válido!", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Não é um inteiro válido!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void processarMacros() {
        if (primeiroModulo.getText().isEmpty() || segundoModulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha os dois módulos!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        processadorDeMacrosPrimeiroModulo.limpar();
        processadorDeMacrosPrimeiroModulo.setPrograma(primeiroModulo.getText());
        processadorDeMacrosPrimeiroModulo.macroProcessor("1");
        primeiroModuloOutput.setText(processadorDeMacrosPrimeiroModulo.getOutput());

        processadorDeMacrosSegundoModulo.limpar();
        processadorDeMacrosSegundoModulo.setPrograma(segundoModulo.getText());
        processadorDeMacrosSegundoModulo.macroProcessor("2");
        segundoModuloOutput.setText(processadorDeMacrosSegundoModulo.getOutput());

        processarMacrosButton.setEnabled(false);
        montarButton.setEnabled(true);
    }

    public void montar() {
        if (primeiroModuloOutput.getText().isEmpty() || segundoModuloOutput.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primeiro processe as macros!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        montadorPrimeiroModulo.Montar(primeiroModuloOutput.getText(), "1");
        primeiroModuloOutput.setText(montadorPrimeiroModulo.getOutput().getMachineCodeAsString());

        if (!montadorPrimeiroModulo.getErrorMessage().isEmpty()) {
            processarMacrosButton.setEnabled(true);
            montarButton.setEnabled(false);
            primeiroModuloOutput.setText("");
            segundoModuloOutput.setText("");
            return;
        }


        montadorSegundoModulo.Montar(segundoModuloOutput.getText(), "2");
        segundoModuloOutput.setText(montadorSegundoModulo.getOutput().getMachineCodeAsString());

        if (!montadorSegundoModulo.getErrorMessage().isEmpty()) {
            processarMacrosButton.setEnabled(true);
            montarButton.setEnabled(false);
            primeiroModuloOutput.setText("");
            segundoModuloOutput.setText("");
            return;
        }

        montarButton.setEnabled(false);
        ligarButton.setEnabled(true);
    }

    public void carregar() {
        executor.setOutput(-1);
        output.setText("");
        input.setEnabled(false);

        executor.setPrograma(primeiroModuloOutput.getText());
        runButton.setEnabled(true);
        stepButton.setEnabled(true);
        input.setText("");
        memory.setSelectedIndex(executor.getRegistradores().getValorPC());
        attMemoria(memory);
        attRegistradores();

        carregarButton.setEnabled(false);
        ligarButton.setEnabled(false);
    }

    public void ligar() {
        ligador.adicionarPrograma(montadorPrimeiroModulo);
        ligador.adicionarPrograma(montadorSegundoModulo);
        String output = ligador.ligarProgramas();
        primeiroModuloOutput.setText(output);
        segundoModuloOutput.setText(output);

        ligarButton.setEnabled(false);
        carregarButton.setEnabled(true);
    }

    public void reiniciar() {
        processarMacrosButton.setEnabled(true);
        montarButton.setEnabled(false);
        carregarButton.setEnabled(false);
        ligarButton.setEnabled(false);
        runButton.setEnabled(false);
        stepButton.setEnabled(false);

        primeiroModulo.setText("");
        segundoModulo.setText("");
        primeiroModuloOutput.setText("");
        segundoModuloOutput.setText("");

        output.setText("");
        input.setText("");

        processadorDeMacrosPrimeiroModulo.limpar();
        processadorDeMacrosSegundoModulo.limpar();
        montadorPrimeiroModulo.limpaListas();
        montadorSegundoModulo.limpaListas();
        ligador.limpar();
        executor.limpar();

        attRegistradores();
        attMemoria(memory);
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        primeiroModuloLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        primeiroModuloOutput = new javax.swing.JTextArea();
        segundoModuloLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        segundoModulo = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        primeiroModulo = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        segundoModuloOutput = new javax.swing.JTextArea();
        montarButton = new javax.swing.JButton();
        runButton = new javax.swing.JButton();
        carregarButton = new javax.swing.JButton();
        ligarButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        segundoModuloLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        memory = new javax.swing.JList<>();
        segundoModuloLabel2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        registers = new javax.swing.JTable();
        segundoModuloLabel3 = new javax.swing.JLabel();
        input = new javax.swing.JTextField();
        segundoModuloLabel4 = new javax.swing.JLabel();
        output = new javax.swing.JTextField();
        processarMacrosButton = new javax.swing.JButton();
        reiniciarButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(24, 25, 26));

        jPanel1.setBackground(new java.awt.Color(24, 25, 26));

        primeiroModuloLabel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        primeiroModuloLabel.setForeground(new java.awt.Color(228, 230, 235));
        primeiroModuloLabel.setText("Primeiro Módulo");

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        primeiroModuloOutput.setBackground(new java.awt.Color(36, 37, 38));
        primeiroModuloOutput.setColumns(20);
        primeiroModuloOutput.setForeground(new java.awt.Color(228, 230, 235));
        primeiroModuloOutput.setRows(5);
        primeiroModuloOutput.setEditable(false);
        jScrollPane1.setViewportView(primeiroModuloOutput);

        segundoModuloLabel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        segundoModuloLabel.setForeground(new java.awt.Color(228, 230, 235));
        segundoModuloLabel.setText("Segundo Módulo");

        jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        segundoModulo.setBackground(new java.awt.Color(36, 37, 38));
        segundoModulo.setColumns(20);
        segundoModulo.setForeground(new java.awt.Color(228, 230, 235));
        segundoModulo.setRows(5);
        jScrollPane2.setViewportView(segundoModulo);

        jScrollPane3.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane3.setForeground(new java.awt.Color(255, 255, 255));

        primeiroModulo.setBackground(new java.awt.Color(36, 37, 38));
        primeiroModulo.setColumns(20);
        primeiroModulo.setForeground(new java.awt.Color(228, 230, 235));
        primeiroModulo.setRows(5);
        jScrollPane3.setViewportView(primeiroModulo);

        jScrollPane4.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane4.setForeground(new java.awt.Color(255, 255, 255));

        segundoModuloOutput.setBackground(new java.awt.Color(36, 37, 38));
        segundoModuloOutput.setColumns(20);
        segundoModuloOutput.setForeground(new java.awt.Color(228, 230, 235));
        segundoModuloOutput.setRows(5);
        segundoModuloOutput.setEditable(false);
        jScrollPane4.setViewportView(segundoModuloOutput);

        montarButton.setBackground(new java.awt.Color(36, 37, 38));
        montarButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        montarButton.setForeground(new java.awt.Color(228, 230, 235));
        montarButton.setText("Montar");
        montarButton.setEnabled(false);
        montarButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            montar();
        });

        runButton.setBackground(new java.awt.Color(36, 37, 38));
        runButton.setForeground(new java.awt.Color(228, 230, 235));
        runButton.setText("Run");
        runButton.setEnabled(false);
        runButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            runButtonActionPerformed(evt, memory);
        });

        carregarButton.setBackground(new java.awt.Color(36, 37, 38));
        carregarButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        carregarButton.setForeground(new java.awt.Color(228, 230, 235));
        carregarButton.setText("Carregar");
        carregarButton.setEnabled(false);
        carregarButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            carregar();
        });

        ligarButton.setBackground(new java.awt.Color(36, 37, 38));
        ligarButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ligarButton.setForeground(new java.awt.Color(228, 230, 235));
        ligarButton.setText("Ligar");
        ligarButton.setEnabled(false);
        ligarButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            ligar();
        });

        jLabel1.setBackground(new java.awt.Color(255, 217, 102));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 217, 102));
        jLabel1.setText("SIC/XE");
        jLabel1.setToolTipText("");

        segundoModuloLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        segundoModuloLabel1.setForeground(new java.awt.Color(228, 230, 235));
        segundoModuloLabel1.setText("Memória");

        memory.setBackground(new java.awt.Color(36, 37, 38));

        memory.setForeground(new java.awt.Color(228, 230, 235));
        memory.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        memory.setSelectionBackground(new java.awt.Color(51, 51, 51));
        memory.setSelectionForeground(new java.awt.Color(228, 230, 235));
        memory.setFixedCellHeight(25);
        jScrollPane5.setViewportView(memory);

        segundoModuloLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        segundoModuloLabel2.setForeground(new java.awt.Color(228, 230, 235));
        segundoModuloLabel2.setText("Registradores");

        registers.setBackground(new java.awt.Color(36, 37, 38));
        registers.setForeground(new java.awt.Color(228, 230, 235));
        registers.setGridColor(ColorPalette.GRID.getColor());
        registers.setFillsViewportHeight(true);
        registers.setBackground(ColorPalette.BG_GRID.getColor());
        registers.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {"a", null},
                        {null, null},
                        {null, null},
                        {null, null}
                },
                new String [] {
                        "Nome", "Valor"
                }
        ));
        registers.setEnabled(false);
        registers.setRowHeight(20);
        registers.setSelectionBackground(new java.awt.Color(36, 37, 38));
        registers.setShowGrid(false);
        registers.setShowVerticalLines(false);
        jScrollPane6.setViewportView(registers);

        segundoModuloLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        segundoModuloLabel3.setForeground(new java.awt.Color(228, 230, 235));
        segundoModuloLabel3.setText("Input:");

        input.setBackground(new java.awt.Color(36, 37, 38));
        input.setForeground(new java.awt.Color(228, 230, 235));
        input.setEnabled(false);
        input.addActionListener((java.awt.event.ActionEvent evt) -> {
            inputActionPerformed(evt);
        });

        segundoModuloLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        segundoModuloLabel4.setForeground(new java.awt.Color(228, 230, 235));
        segundoModuloLabel4.setText("Output:");

        output.setBackground(new java.awt.Color(36, 37, 38));
        output.setForeground(new java.awt.Color(228, 230, 235));
        output.setEnabled(false);

        processarMacrosButton.setBackground(new java.awt.Color(36, 37, 38));
        processarMacrosButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        processarMacrosButton.setForeground(new java.awt.Color(228, 230, 235));
        processarMacrosButton.setText("Processar Macros");
        processarMacrosButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            processarMacros();
        });

        reiniciarButton.setBackground(new java.awt.Color(36, 37, 38));
        reiniciarButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        reiniciarButton.setForeground(new java.awt.Color(228, 230, 235));
        reiniciarButton.setText("Reiniciar");
        reiniciarButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            reiniciar();
        });

        stepButton.setBackground(new java.awt.Color(36, 37, 38));
        stepButton.setForeground(new java.awt.Color(228, 230, 235));
        stepButton.setText("Step");
        stepButton.setEnabled(false);
        stepButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            stepButtonActionPerformed(evt, memory);
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(69, 69, 69)
                                                                .addComponent(primeiroModuloLabel)
                                                                .addGap(146, 146, 146)
                                                                .addComponent(segundoModuloLabel))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jScrollPane4))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(reiniciarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(montarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(carregarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(ligarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(processarMacrosButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                )))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(94, 94, 94)
                                                                .addComponent(segundoModuloLabel1)))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(88, 88, 88)
                                                                .addComponent(segundoModuloLabel2))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(33, 33, 33)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(segundoModuloLabel4)
                                                                                        .addComponent(segundoModuloLabel3))
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(input)
                                                                                        .addComponent(output)))
                                                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                        .addComponent(stepButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(1, 1, 1)
                                                        .addComponent(segundoModuloLabel1)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(segundoModuloLabel2)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(37, 37, 37)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(segundoModuloLabel3)
                                                                .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(segundoModuloLabel4)
                                                                .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(34, 34, 34)
                                                        .addComponent(stepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(22, 22, 22)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                .addComponent(segundoModuloLabel)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(primeiroModuloLabel)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(reiniciarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(80, 80, 80)
                                                                .addComponent(processarMacrosButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(montarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(19, 19, 19)
                                                                .addComponent(ligarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(carregarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }
}