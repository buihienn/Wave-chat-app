package com.wavechat.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Message extends javax.swing.JLayeredPane {

    public Message() {
        initComponents();
        message.setEditable(false);
        message.setOpaque(false);
    }
    
    public void setMessage(String msg) {
        message.setText(msg);
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintComponent(grphcs);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        message = new com.wavechat.swing.JIMSendTextPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        message.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        message.setMinimumSize(new java.awt.Dimension(0, 0));
        add(message);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.wavechat.swing.JIMSendTextPane message;
    // End of variables declaration//GEN-END:variables
}
