/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visao.teste;

import doutorado.tese.controle.mb.testelaboratorioMB.LoggerMB;
import doutorado.tese.controle.mb.testelaboratorioMB.PerguntaMB;
import doutorado.tese.controle.negocio.teste.ManipuladorLog;
import doutorado.tese.util.ColunasLog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.time.LocalDateTime;

/**
 *
 * @author Anderson Soares
 */
public class MainScreenLog extends javax.swing.JFrame {

    int lastX, lastY;
    private PerguntaMB perguntaMB;
    private int contQuestaoTreinamento = 0;
    private int contQuestaoTeste = 0;
//    private LoggerMB loggerMB;
    private String[] colunaLog;

    public MainScreenLog() {
        initComponents();
        perguntaMB = new PerguntaMB();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoDIreitoMousePopupMenu = new javax.swing.JPopupMenu();
        fecharMenuItem = new javax.swing.JMenuItem();
        buttonGroup = new javax.swing.ButtonGroup();
        fundoPanel = new javax.swing.JPanel();
        textoPergunta = new javax.swing.JLabel();
        start_Button = new javax.swing.JButton();
        next_Button = new javax.swing.JButton();
        submit_Button = new javax.swing.JButton();
        treinamentoRadioButton = new javax.swing.JRadioButton();
        testeRadioButton = new javax.swing.JRadioButton();

        fecharMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-close-window-17.png"))); // NOI18N
        fecharMenuItem.setText("Close");
        fecharMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fecharMenuItemActionPerformed(evt);
            }
        });
        botaoDIreitoMousePopupMenu.add(fecharMenuItem);

        buttonGroup.add(treinamentoRadioButton);
        buttonGroup.add(testeRadioButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Questions Test");
        setAlwaysOnTop(true);
        setLocation(new java.awt.Point(150, 0));
        setUndecorated(true);
        setResizable(false);

        fundoPanel.setBackground(new java.awt.Color(255, 255, 255));
        fundoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Questions Test", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 9))); // NOI18N
        fundoPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                fundoPanelMouseDragged(evt);
            }
        });
        fundoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                fundoPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fundoPanelMouseReleased(evt);
            }
        });

        textoPergunta.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        textoPergunta.setText("....");

        javax.swing.GroupLayout fundoPanelLayout = new javax.swing.GroupLayout(fundoPanel);
        fundoPanel.setLayout(fundoPanelLayout);
        fundoPanelLayout.setHorizontalGroup(
            fundoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textoPergunta, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
        );
        fundoPanelLayout.setVerticalGroup(
            fundoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textoPergunta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        start_Button.setText("Start training");
        start_Button.setEnabled(false);
        start_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_ButtonActionPerformed(evt);
            }
        });

        next_Button.setText("Next");
        next_Button.setEnabled(false);
        next_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next_ButtonActionPerformed(evt);
            }
        });

        submit_Button.setText("Submit");
        submit_Button.setEnabled(false);
        submit_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit_ButtonActionPerformed(evt);
            }
        });

        treinamentoRadioButton.setText("Training");
        treinamentoRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                treinamentoRadioButtonActionPerformed(evt);
            }
        });

        testeRadioButton.setText("Test");
        testeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testeRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fundoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submit_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(next_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(start_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(treinamentoRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(testeRadioButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(submit_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(next_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(start_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fundoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(treinamentoRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(testeRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fundoPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundoPanelMousePressed
        lastX = evt.getX();
        lastY = evt.getY();
    }//GEN-LAST:event_fundoPanelMousePressed

    private void fecharMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fecharMenuItemActionPerformed
        ManipuladorLog.setTesteAcontecendo(false);
        this.dispose();
    }//GEN-LAST:event_fecharMenuItemActionPerformed

    private void fundoPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundoPanelMouseReleased
        if (SwingUtilities.isRightMouseButton(evt)) {
            if (evt.isPopupTrigger()) {
                botaoDIreitoMousePopupMenu.show(this, evt.getX(), evt.getY());
            }
        }
    }//GEN-LAST:event_fundoPanelMouseReleased

    private void fundoPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundoPanelMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - lastX, y - lastY);
    }//GEN-LAST:event_fundoPanelMouseDragged

    private void start_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_ButtonActionPerformed
        if (treinamentoRadioButton.isSelected()) {
            perguntaMB.managerPerguntasTreinamento();
            textoPergunta.setText(perguntaMB.getPerguntasTreinamento()[contQuestaoTreinamento].getTexto());
            updateStartLog(contQuestaoTreinamento);
            contQuestaoTreinamento++;
        } else {
            perguntaMB.managerPerguntasTeste();
            textoPergunta.setText(perguntaMB.getPerguntasTeste()[contQuestaoTeste].getTexto());
            updateStartLog(contQuestaoTeste);
            contQuestaoTeste++;
        }
        submit_Button.setEnabled(true);
        start_Button.setEnabled(false);
    }//GEN-LAST:event_start_ButtonActionPerformed

    private void updateStartLog(int contQuestao) {
        ManipuladorLog.carregarGabarito();
        LoggerMB.startLog();
        LoggerMB.getColunaLog()[ColunasLog.ID_TAREFA.getId()] = (contQuestao + 1) + "";
        LoggerMB.getColunaLog()[ColunasLog.TEMPO_INICIO.getId()] = System.currentTimeMillis() + "";
        LoggerMB.getColunaLog()[ColunasLog.TIMESTAMP_INICIO.getId()] = LocalDateTime.now() + "";
        LoggerMB.getColunaLog()[ColunasLog.QUESTAO_CORRETA.getId()] = "null";
        LoggerMB.addNewLineLog();
    }

    private void treinamentoRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_treinamentoRadioButtonActionPerformed
        start_Button.setText("Start Training");
        start_Button.setEnabled(true);
        submit_Button.setEnabled(false);
        ManipuladorLog.setArquivoGabarito("respostasTreinamento.tsv");
        ManipuladorLog.setTreinamentoAcontecendo(true);
    }//GEN-LAST:event_treinamentoRadioButtonActionPerformed

    private void testeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testeRadioButtonActionPerformed
        start_Button.setText("Start Test");
        start_Button.setEnabled(true);
        next_Button.setEnabled(false);
        submit_Button.setEnabled(false);
        ManipuladorLog.setArquivoGabarito("respostas.tsv");
        ManipuladorLog.setTreinamentoAcontecendo(false);
    }//GEN-LAST:event_testeRadioButtonActionPerformed

    private void next_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next_ButtonActionPerformed
        if (treinamentoRadioButton.isSelected()) {
            updateNextLog(contQuestaoTreinamento);
            textoPergunta.setText(perguntaMB.getPerguntasTreinamento()[contQuestaoTreinamento].getTexto());
            contQuestaoTreinamento++;
        } else {
            updateNextLog(contQuestaoTeste);
            textoPergunta.setText(perguntaMB.getPerguntasTeste()[contQuestaoTeste].getTexto());
            contQuestaoTeste++;
        }
        next_Button.setEnabled(false);
        submit_Button.setEnabled(true);
    }//GEN-LAST:event_next_ButtonActionPerformed

    private void updateNextLog(int contQuestao) {
        LoggerMB.getColunaLog()[ColunasLog.ID_TAREFA.getId()] = (contQuestao + 1) + "";
        LoggerMB.getColunaLog()[ColunasLog.TEMPO_INICIO.getId()] = System.currentTimeMillis() + "";
        LoggerMB.getColunaLog()[ColunasLog.TIMESTAMP_INICIO.getId()] = LocalDateTime.now() + "";
        LoggerMB.getColunaLog()[ColunasLog.TEMPO_FINAL.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.TEMPO_QUANDO_CLICOU.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.TEMPO_FINAL_CALCULADO.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.TIMESTAMP_QUANDO_CLICOU.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.TIMESTAMP_FIM.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.ID_TREEMAP_ITEM.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.SELECIONADO.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.TREEMAP_LABEL.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.QUESTAO_CORRETA.getId()] = "null";
        LoggerMB.addNewLineLog();
    }

    private void submit_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit_ButtonActionPerformed
        textoPergunta.setText("");
        submit_Button.setEnabled(false);
        if (treinamentoRadioButton.isSelected()) {
            if (contQuestaoTreinamento > 9) {
                updateSubmitLog();
                int resposta = JOptionPane.showConfirmDialog(this, "Você gostaria de continuar?");
                if (resposta == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, "Que bom, vamos iniciar os testes!");
                } else {
                    next_Button.setEnabled(false);
                    textoPergunta.setText("Fim do teste. Obrigado!");
                }
            } else {
                updateSubmitLog();
                next_Button.setEnabled(true);
            }
        } else {
            updateSubmitLog();
            if (contQuestaoTeste > 11) {
                next_Button.setEnabled(false);
                LoggerMB.salvarLog();
                textoPergunta.setText("Fim do teste. Obrigado!");
            } else {
                next_Button.setEnabled(true);
            }
        }
    }//GEN-LAST:event_submit_ButtonActionPerformed

    private void updateSubmitLog() {
        LoggerMB.getColunaLog()[ColunasLog.TIMESTAMP_FIM.getId()] = LocalDateTime.now() + "";
        LoggerMB.getColunaLog()[ColunasLog.TEMPO_FINAL.getId()] = System.currentTimeMillis() + "";
        LoggerMB.getColunaLog()[ColunasLog.TEMPO_FINAL_CALCULADO.getId()]
                = (Long.parseLong(LoggerMB.getColunaLog()[ColunasLog.TEMPO_FINAL.getId()])
                - Long.parseLong(LoggerMB.getColunaLog()[ColunasLog.TEMPO_INICIO.getId()])) + "";
        LoggerMB.getColunaLog()[ColunasLog.RESPOSTA_CORRETA.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.TEMPO_QUANDO_CLICOU.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.TIMESTAMP_QUANDO_CLICOU.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.ID_TREEMAP_ITEM.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.SELECIONADO.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.TREEMAP_LABEL.getId()] = "null";
        LoggerMB.getColunaLog()[ColunasLog.QUESTAO_CORRETA.getId()] = ManipuladorLog.verificaQuestaoCorreta(
                Integer.parseInt(LoggerMB.getColunaLog()[ColunasLog.ID_TAREFA.getId()])) + "";
        ManipuladorLog.getRespostaUsuarioTemp().clear();
        LoggerMB.addNewLineLog();
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
            java.util.logging.Logger.getLogger(MainScreenLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreenLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreenLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreenLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreenLog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu botaoDIreitoMousePopupMenu;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JMenuItem fecharMenuItem;
    private javax.swing.JPanel fundoPanel;
    private javax.swing.JButton next_Button;
    private javax.swing.JButton start_Button;
    private javax.swing.JButton submit_Button;
    private javax.swing.JRadioButton testeRadioButton;
    private javax.swing.JLabel textoPergunta;
    private javax.swing.JRadioButton treinamentoRadioButton;
    // End of variables declaration//GEN-END:variables
}
