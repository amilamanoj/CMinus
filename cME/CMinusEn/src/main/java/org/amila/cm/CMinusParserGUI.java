package org.amila.cm;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;

/**
 * @author Amila Manoj
 */
public class CMinusParserGUI extends JFrame {

    private DefaultListModel listModel;
    private CMinusParser parser;
    private StyledDocument doc;
    private File sourceFile;


    public CMinusParserGUI() {
        this.setLocationRelativeTo(null);
        listModel = new DefaultListModel();
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }

        CMinusParserGUI gui = new CMinusParserGUI();
        gui.setVisible(true);
    }

    private void initComponents() {

        jScrollPane2 = new JScrollPane();
        List = new JList(listModel);
        parseButton = new JButton();
        browseButton = new JButton();
        jLabel2 = new JLabel();
        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        sourceTextArea = new JTextPane();
        doc = sourceTextArea.getStyledDocument();
        // Makes text red
        Style style = sourceTextArea.addStyle("Blue", null);
        StyleConstants.setForeground(style, Color.blue);
        Style style1 = sourceTextArea.addStyle("Green", null);
        StyleConstants.setForeground(style1, Color.green);
        Style style2 = sourceTextArea.addStyle("Red", null);
        StyleConstants.setBackground(style2, Color.red);
        Style style3 = sourceTextArea.addStyle("Usual", null);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("C-Minus Parser - 080475F - W.G.H.A.M. Silva");
        setResizable(false);

//        List.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(List);

//        parseButton.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        parseButton.setText("Parse");
        parseButton.setEnabled(false);
        parseButton.setIconTextGap(30);
        parseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parseButtonActionPerformed(evt);
            }
        });

//        browseButton.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        browseButton.setText("Browse...");
        browseButton.setIconTextGap(10);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

//        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Source:");

        jLabel1.setText("<select file>");
        jLabel1.setBorder(BorderFactory.createEtchedBorder());
        jLabel1.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
                browseButtonActionPerformed(null);
            }

            public void mouseReleased(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void mouseEntered(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void mouseExited(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });


//        sourceTextArea.setColumns(20);
//        sourceTextArea.setRows(5);
        sourceTextArea.setFont(new Font("Sagoe UI", 0, 14));
        jScrollPane1.setViewportView(sourceTextArea);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(browseButton, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(parseButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(browseButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(parseButton, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void parseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearFormatting();
        boolean scanSuccess = scan();
        if (scanSuccess) parse();
    }

    private boolean scan() {
        try {
            CMinusParserTokenManager lexer = new CMinusParserTokenManager(new JavaCharStream(new StringReader(sourceTextArea.getText())));
            listModel.clear();
            Token t;
            int i = 0;
            while (!(t = lexer.getNextToken()).image.equals("")) {
                i++;
                String item = "L" + t.beginLine + "C" + t.beginColumn + ": " + t.image;
                listModel.addElement(item);
                syntaxHighlight(t);
            }
            List.setSelectedIndex(0);
            List.ensureIndexIsVisible(0);
            return true;
        } catch (TokenMgrError e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "Lexical Analyzing failed due to unidentified token: \n" + e.getMessage(), "Scanning Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void parse() {
        parser = new CMinusParser(new StringReader(sourceTextArea.getText()));
        ParseResult result = parser.Parse();
        if (result.isSuccess()) {
            JOptionPane.showMessageDialog(this, "Parsing Process successfully completed", "Parsing Complete", JOptionPane.INFORMATION_MESSAGE);
        } else {
            ArrayList<ParseException> exceptionList = result.getExceptionList();
//
            String errors = "";
            String error;
            for (ParseException e : exceptionList) {
                error = e.getMessage();
                errorHighlight(e.currentToken);
                errors = errors + error + "\n";
            }
            JOptionPane.showMessageDialog(this, "Parsing Failed. Encountered " + exceptionList.size() + " error(s) while parsing: \n\n" + errors, "Parsing Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void errorHighlight(Token t) {
        try {
            int sOffset = getLineStartOffset(sourceTextArea, t.beginLine - 1);
            sOffset += t.beginColumn;
            System.out.println("Highlighting error: "+ t);
            doc.setCharacterAttributes(sOffset, t.image.length(), sourceTextArea.getStyle("Red"), true);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void syntaxHighlight(Token t) {
        if (
                t.kind == CMinusParserConstants.INT
                        || t.kind == CMinusParserConstants.IF
                        || t.kind == CMinusParserConstants.ELSE
                        || t.kind == CMinusParserConstants.WHILE
                        || t.kind == CMinusParserConstants.VOID
                        || t.kind == CMinusParserConstants.RETURN
                ) {
            try {
                int sOffset = getLineStartOffset(sourceTextArea, t.beginLine - 1);
                sOffset += t.beginColumn - 1;
                doc.setCharacterAttributes(sOffset, t.image.length(), sourceTextArea.getStyle("Blue"), true);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else if (t.kind == CMinusParserConstants.ID) {
            try {
                int sOffset = getLineStartOffset(sourceTextArea, t.beginLine - 1);
                sOffset += t.beginColumn - 1;
                doc.setCharacterAttributes(sOffset, t.image.length(), sourceTextArea.getStyle("Green"), true);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFormatting() {
        doc.setCharacterAttributes(0,doc.getLength(), sourceTextArea.getStyle("Usual"), true);
    }

    static int getLineStartOffset(JTextComponent comp, int line) throws BadLocationException {
        Element map = comp.getDocument().getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
//            System.out.println(lineElem);
            return lineElem.getStartOffset();
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
            parseButton.setEnabled(false);
            try {

                String fileContent = readFile(sFile.getAbsolutePath());
                if (fileContent != null && !fileContent.equals("")) {
                    sourceTextArea.setText(fileContent);
                    jLabel1.setText(sFile.getName());
                    sourceFile = sFile;
                    parseButton.setEnabled(true);
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
    private JList List;
    private JButton browseButton;
    private JButton parseButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextPane sourceTextArea;
    // End of variables declaration
}
