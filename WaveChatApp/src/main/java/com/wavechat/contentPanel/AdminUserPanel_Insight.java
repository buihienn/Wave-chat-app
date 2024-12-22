package com.wavechat.contentPanel;

import com.wavechat.dao.LoginHistoryDAO;
import com.wavechat.dao.UserDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author buihi
 */
public class AdminUserPanel_Insight extends javax.swing.JPanel {

    /**
     * Creates new form AdminUserPanel_Insight
     */
    public AdminUserPanel_Insight() {
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
        activityButton = new javax.swing.JButton();
        jButtonRegister = new javax.swing.JButton();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanelChart = new javax.swing.JPanel();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        activityButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        activityButton.setText("Activity");
        activityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activityButtonActionPerformed(evt);
            }
        });

        jButtonRegister.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonRegister.setText("Register");
        jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegisterActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Year:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(activityButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jYearChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(activityButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        jPanelChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelChart, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegisterActionPerformed
        // TODO add your handling code here:
        int selectedYear = jYearChooser1.getYear();
        UserDAO userDAO = new UserDAO();
        int amountJan = userDAO.getNumberUserCreatedDate(selectedYear, 1);
        int amountFeb = userDAO.getNumberUserCreatedDate(selectedYear, 2);
        int amountMar = userDAO.getNumberUserCreatedDate(selectedYear, 3);
        int amountApr = userDAO.getNumberUserCreatedDate(selectedYear, 4);
        int amountMay = userDAO.getNumberUserCreatedDate(selectedYear, 5);
        int amountJun = userDAO.getNumberUserCreatedDate(selectedYear, 6);
        int amountJul = userDAO.getNumberUserCreatedDate(selectedYear, 7);
        int amountAug = userDAO.getNumberUserCreatedDate(selectedYear, 8);
        int amountSep = userDAO.getNumberUserCreatedDate(selectedYear, 9);
        int amountOct = userDAO.getNumberUserCreatedDate(selectedYear, 10);
        int amountNov = userDAO.getNumberUserCreatedDate(selectedYear, 11);
        int amountDec = userDAO.getNumberUserCreatedDate(selectedYear, 12);
        
        DefaultCategoryDataset barChartData = new DefaultCategoryDataset();
        barChartData.setValue(amountJan, "Amount", "Jan");
        barChartData.setValue(amountFeb, "Amount", "Feb");
        barChartData.setValue(amountMar, "Amount", "Mar");
        barChartData.setValue(amountApr, "Amount", "Apr");
        barChartData.setValue(amountMay, "Amount", " May,");
        barChartData.setValue(amountJun, "Amount", "Jun");
        barChartData.setValue(amountJul, "Amount", "Jul");
        barChartData.setValue(amountAug, "Amount", "Aug");
        barChartData.setValue(amountSep, "Amount", "Sep");
        barChartData.setValue(amountOct, "Amount", "Oct");
        barChartData.setValue(amountNov, "Amount", "Nov");
        barChartData.setValue(amountDec, "Amount", "Dec");
        
        JFreeChart barChart = ChartFactory.createBarChart("New register", "Monthly", "Number new registers", barChartData, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot barchrt = barChart.getCategoryPlot();
        barchrt.setRangeGridlinePaint(Color.GREEN);
        
        ChartPanel barPanel = new ChartPanel(barChart);
        jPanelChart.removeAll();
        jPanelChart.add(barPanel, BorderLayout.CENTER);
        jPanelChart.validate();
    }//GEN-LAST:event_jButtonRegisterActionPerformed

    private void activityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activityButtonActionPerformed
        // TODO add your handling code here:
        int selectedYear = jYearChooser1.getYear();
        LoginHistoryDAO loginHistoryDAO = new LoginHistoryDAO();
        int amountJan = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 1);
        int amountFeb = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 2);
        int amountMar = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 3);
        int amountApr = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 4);
        int amountMay = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 5);
        int amountJun = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 6);
        int amountJul = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 7);
        int amountAug = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 8);
        int amountSep = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 9);
        int amountOct = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 10);
        int amountNov = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 11);
        int amountDec = loginHistoryDAO.getNumberLoginsByYearAndMonth(selectedYear, 12);
        
        DefaultCategoryDataset barChartData = new DefaultCategoryDataset();
        barChartData.setValue(amountJan, "Amount", "Jan");
        barChartData.setValue(amountFeb, "Amount", "Feb");
        barChartData.setValue(amountMar, "Amount", "Mar");
        barChartData.setValue(amountApr, "Amount", "Apr");
        barChartData.setValue(amountMay, "Amount", " May,");
        barChartData.setValue(amountJun, "Amount", "Jun");
        barChartData.setValue(amountJul, "Amount", "Jul");
        barChartData.setValue(amountAug, "Amount", "Aug");
        barChartData.setValue(amountSep, "Amount", "Sep");
        barChartData.setValue(amountOct, "Amount", "Oct");
        barChartData.setValue(amountNov, "Amount", "Nov");
        barChartData.setValue(amountDec, "Amount", "Dec");
        
        JFreeChart barChart = ChartFactory.createBarChart("Activity", "Monthly", "Number activities", barChartData, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot barchrt = barChart.getCategoryPlot();
        barchrt.setRangeGridlinePaint(Color.GREEN);
        
        ChartPanel barPanel = new ChartPanel(barChart);
        jPanelChart.removeAll();
        jPanelChart.add(barPanel, BorderLayout.CENTER);
        jPanelChart.validate();
    }//GEN-LAST:event_activityButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton activityButton;
    private javax.swing.JButton jButtonRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelChart;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    // End of variables declaration//GEN-END:variables
}
