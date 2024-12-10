/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.wavechat.contentPanel;

/**
 *
 * @author buihi
 */
public class AdminUserPanel_NewRegister extends javax.swing.JPanel {

    /**
     * Creates new form AdminUserPanel_NewRegister
     */
    public AdminUserPanel_NewRegister() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        fromText = new javax.swing.JLabel();
        dayStart = new javax.swing.JTextField();
        toText = new javax.swing.JLabel();
        dayEnd = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        newRegisterTable = new javax.swing.JTable();

        fromText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        fromText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fromText.setText("FROM");

        dayStart.setText("12/09/2024");
        dayStart.setPreferredSize(new java.awt.Dimension(73, 25));
        dayStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayStartActionPerformed(evt);
            }
        });

        toText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        toText.setText("TO");

        dayEnd.setText("21/09/2024");
        dayEnd.setPreferredSize(new java.awt.Dimension(73, 25));
        dayEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayEndActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(fromText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dayStart, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(toText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dayEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(352, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dayStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromText)
                    .addComponent(toText)
                    .addComponent(dayEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        newRegisterTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"nguyenvana", "19/09/2024", "Lock"},
                {"nguyenvanb", "20/09/2024", "Unlock"},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Username", "Date created", "Lock/Unlock"
            }
        ));
        jScrollPane1.setViewportView(newRegisterTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dayStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayStartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dayStartActionPerformed

    private void dayEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayEndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dayEndActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dayEnd;
    private javax.swing.JTextField dayStart;
    private javax.swing.JLabel fromText;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable newRegisterTable;
    private javax.swing.JLabel toText;
    // End of variables declaration//GEN-END:variables
}
