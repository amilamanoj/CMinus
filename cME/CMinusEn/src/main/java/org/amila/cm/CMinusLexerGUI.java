package org.amila.cm;

import javax.swing.*;
import java.io.*;

/**
 * @author Amila Manoj
 */
public class CMinusLexerGUI extends javax.swing.JDialog {

    private DefaultListModel listModel;
    private CMinusParser parser;
    private File sourceFile;

    public CMinusLexerGUI() {
        this.setLocationRelativeTo(null);
        listModel = new DefaultListModel();
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        CMinusLexerGUI gui = new CMinusLexerGUI();
        gui.setVisible(true);
    }

    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        List = new javax.swing.JList(listModel);
        scanButton = new javax.swing.JButton();
        browseButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sourceTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("C-Minus Lexical Analyzer - 080475F - W.G.H.A.M. Silva");
        setResizable(false);

        List.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        List.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(List);

        scanButton.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        scanButton.setText("Scan");
        scanButton.setEnabled(false);
        scanButton.setIconTextGap(30);
        scanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanButtonActionPerformed(evt);
            }
        });

        browseButton.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        browseButton.setText("Browse...");
        browseButton.setIconTextGap(10);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Source:");

        jLabel1.setText("<select file>");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sourceTextArea.setColumns(20);
        sourceTextArea.setRows(5);
        jScrollPane1.setViewportView(sourceTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(browseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(scanButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(scanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void scanButtonActionPerformed(java.awt.event.ActionEvent evt) {
        scan();
    }

    private void scan() {
        try {
            parser = new CMinusParser(new StringReader(sourceTextArea.getText()));
            listModel.clear();
            String t;
            int i = 0;
            while (!(t = parser.getNextToken().image).equals("")) {
                i++;
                String item = i + ": " + t;
                listModel.addElement(item);
            }
            List.setSelectedIndex(0);
            List.ensureIndexIsVisible(0);
            JOptionPane.showMessageDialog(this, "Lexical analysis successfully completed", "Scan Complete", JOptionPane.INFORMATION_MESSAGE);
        } catch (TokenMgrError e) {
            JOptionPane.showMessageDialog(this, "Found invalid tokens in the source file", "Scan Failed", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        processFile();
    }

    private void processFile() {
        final JFileChooser fc = new JFileChooser(".");
        fc.showOpenDialog(this);
        File sFile = fc.getSelectedFile();
        if (sFile != null) {
            listModel.clear();
            sourceTextArea.setText("");
            scanButton.setEnabled(false);
            try {

                String fileContent = readFile(sFile.getAbsolutePath());
                if (fileContent != null && !fileContent.equals("")) {
                    sourceTextArea.setText(fileContent);
                    jLabel1.setText(sFile.getName());
                    sourceFile = sFile;
                    scanButton.setEnabled(true);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("File not found");
            }

        }


    }

    private String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        return stringBuilder.toString();
    }

    // Variables declaration - do not modify
    private javax.swing.JList List;
    private javax.swing.JButton browseButton;
    private javax.swing.JButton scanButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea sourceTextArea;
    // End of variables declaration
}
