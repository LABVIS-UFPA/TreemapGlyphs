/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visao.teste;

import doutorado.tese.controle.mb.testelaboratorioMB.LoggerMB;
import doutorado.tese.controle.negocio.teste.ManipuladorLog;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Anderson Soares
 */
public class ConsoleTest extends javax.swing.JFrame {

    Thread t1;

    /**
     * Creates new form ConsoleTest
     */
    public ConsoleTest() {
        initComponents();
        DefaultCaret caret = (DefaultCaret) saidaTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fundoConsole = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        saidaTextArea = new javax.swing.JTextArea();
        monitorarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Console - TreemapGlyphs");
        setAlwaysOnTop(true);

        fundoConsole.setBorder(javax.swing.BorderFactory.createTitledBorder("Console - Saídas teste"));

        jScrollPane1.setAutoscrolls(true);

        saidaTextArea.setEditable(false);
        saidaTextArea.setBackground(new java.awt.Color(51, 51, 51));
        saidaTextArea.setColumns(20);
        saidaTextArea.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        saidaTextArea.setForeground(new java.awt.Color(255, 255, 255));
        saidaTextArea.setRows(5);
        jScrollPane1.setViewportView(saidaTextArea);

        monitorarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/turnOff.png"))); // NOI18N
        monitorarButton.setText("Iniciar monitoramento");
        monitorarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monitorarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fundoConsoleLayout = new javax.swing.GroupLayout(fundoConsole);
        fundoConsole.setLayout(fundoConsoleLayout);
        fundoConsoleLayout.setHorizontalGroup(
            fundoConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1346, Short.MAX_VALUE)
            .addGroup(fundoConsoleLayout.createSequentialGroup()
                .addComponent(monitorarButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        fundoConsoleLayout.setVerticalGroup(
            fundoConsoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fundoConsoleLayout.createSequentialGroup()
                .addComponent(monitorarButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fundoConsole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fundoConsole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void monitorarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monitorarButtonActionPerformed
        monitorarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/turnOn.png")));
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (t1) {
                    try {
                        while (ManipuladorLog.isTesteAcontecendo()) {
                            updateConsole();
                            Thread.sleep(3000);
                        }
                        monitorarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/turnOff.png")));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConsoleTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        t1.start();
        if (!ManipuladorLog.isTesteAcontecendo()) {
            saidaTextArea.setText("Inicie o teste.");
        }
    }//GEN-LAST:event_monitorarButtonActionPerformed

    public void updateConsole() {
        saidaTextArea.setText("");
        if (LoggerMB.getBuffer() != null) {
            saidaTextArea.setText(LoggerMB.getBuffer().toString());
        } else {
            saidaTextArea.setText("Log vazio.");

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConsoleTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsoleTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsoleTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsoleTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsoleTest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel fundoConsole;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton monitorarButton;
    private javax.swing.JTextArea saidaTextArea;
    // End of variables declaration//GEN-END:variables
}
