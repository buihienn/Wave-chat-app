package com.wavechat.component;

public class LeftMessage extends javax.swing.JLayeredPane {

    public LeftMessage() {
        initComponents();
    }
    
    public void setLeftMessage(String msg) {
        message.setMessage(msg);
        message.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    public void setUsername(String username) {
        message.setMessage(username);
        message.setBackground(new java.awt.Color(242, 242, 242));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        message = new com.wavechat.component.Message();

        setBackground(new java.awt.Color(153, 255, 153));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        message.setBackground(new java.awt.Color(204, 255, 255));
        add(message);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.wavechat.component.Message message;
    // End of variables declaration//GEN-END:variables
}
