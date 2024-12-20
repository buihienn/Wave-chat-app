package com.wavechat.component;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.ChatMessageBUS;
import com.wavechat.dto.ChatMessageDTO;
import com.wavechat.dto.ConversationDTO;
import com.wavechat.dto.GroupChatDTO;
import com.wavechat.dto.UserDTO;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class ChatBody extends javax.swing.JPanel {
    private int currentOffset = 0;  
    private int limit = 5; 
    private UserDTO currentFriend;
    private String lastSenderID = null; 
    private String newSenderID = null;  
    private GroupChatDTO currentGroup;
    private String mode;
    private ConversationDTO curConversation;

    public void setCurConversation(ConversationDTO curConversation) {
        this.curConversation = curConversation;
    }
    
    public void resetOffet() {
        this.currentOffset = 0;
        this.lastSenderID = null;
        this.newSenderID = null;
    }
    
    public void setMode(String mode) { this.mode = mode; }
    
    public ChatBody() {
        initComponents();
        body.setLayout(new MigLayout("fillx"));
    }
    
    public void addUsername(String text) {
        LeftMessage msg = new LeftMessage();
        msg.setUsername(text);
        body.add(msg, "wrap, w ::80%", 0);
        body.repaint();
        body.revalidate();
    }
    
    public void updateNewUsername(String text) {
        LeftMessage msg = new LeftMessage();
        msg.setUsername(text);
        body.add(msg, "wrap, w ::80%");
        body.repaint();
        body.revalidate();
    }
    
    public void addLeft(ChatMessageDTO message) {
        LeftMessage msg = new LeftMessage();
        msg.setLeftMessage(message.getMessage());
        msg.setChatID(message.getChatID());
        body.add(msg, "wrap, w ::80%" ,0);
        body.repaint();
        body.revalidate();
    }
    
    public void addRight(ChatMessageDTO message) {
        RightMessage msg = new RightMessage();
        msg.setRightMessage(message.getMessage());
        msg.setChatID(message.getChatID());
        body.add(msg, "wrap, al right, w ::80%", 0);
        body.repaint();
        body.revalidate();
    }
    
    public void addNew(ChatMessageDTO message) {
        RightMessage msg = new RightMessage();
        msg.setRightMessage(message.getMessage());
        msg.setChatID(message.getChatID());
        body.add(msg, "wrap, al right, w ::80%");
        body.repaint();
        body.revalidate();
    }
    
    public void updateNew(String text) {
        LeftMessage msg = new LeftMessage();
        msg.setLeftMessage(text);
        body.add(msg, "wrap, w ::80%");
        body.repaint();
        body.revalidate();
    }
    
    public void removeChat() {
        body.removeAll();
    }

    public void loadMessages(UserDTO friend) {
        this.currentFriend = friend;
        buttonContainer.add(loadMoreButton);  

        String curUserID = GlobalVariable.getUserID();
        String friendID = currentFriend.getUserID();

        ChatMessageBUS messageBUS = new ChatMessageBUS();
        List<ChatMessageDTO> messages = messageBUS.getMessagesBetweenUsers(curConversation.getConversationID(), currentOffset, limit);
           
        // Hiển thị tin nhắn
        displayMessages(messages);

        // Cập nhật lại offset cho lần tải sau
        currentOffset += limit;

        // Cập nhật giao diện
        body.revalidate();
        body.repaint();
    }
    
    private void displayMessages(List<ChatMessageDTO> messages) {
        String curUserID = GlobalVariable.getUserID();
        ChatMessageBUS messageBUS = new ChatMessageBUS();

        for (int i = 0; i < messages.size(); i++) {
            ChatMessageDTO message = messages.get(i);
            
            newSenderID = message.getSenderID();  

            // Kiểm tra nếu người gửi thay đổi 
            if (lastSenderID == null || !lastSenderID.equals(newSenderID)) {
                // Nếu người gửi cũ không phải là người hiện tại và lastSenderID khác null, thêm tên người gửi
                if (lastSenderID != null && !lastSenderID.equals(curUserID)) {
                    addUsername(messageBUS.getFullnameSender(lastSenderID));  
                }
            }

            // Thêm tin nhắn
            if (message.getSenderID().equals(curUserID)) {
                addRight(message); 
            } else {
                addLeft(message); 
            }

            // Cập nhật lastSenderID sau khi tin nhắn đã được thêm
            lastSenderID = newSenderID;
            
            // Kiểm tra xem nếu tin nhắn này là tin nhắn cuối cùng trong danh sách và là của người khác
            if (i == messages.size() - 1 && !newSenderID.equals(curUserID)) {
                addUsername(messageBUS.getFullnameSender(newSenderID));
            }
        }
    }

    public void loadMessages(GroupChatDTO groupChat) {
        this.currentGroup = groupChat;

        int groupID = currentGroup.getGroupID();

        // Lấy danh sách tin nhắn nhóm từ DB
        ChatMessageBUS messageBUS = new ChatMessageBUS();
        List<ChatMessageDTO> messages = messageBUS.getMessagesInGroup(curConversation.getConversationID(), currentOffset, limit);

        // Hiển thị tin nhắn nhóm
        displayMessages(messages);

        // Cập nhật lại offset cho lần tải sau
        currentOffset += limit;

        // Cập nhật giao diện
        body.revalidate();
        body.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loadMoreButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();
        buttonContainer = new javax.swing.JPanel();

        loadMoreButton.setBackground(new java.awt.Color(26, 41, 128));
        loadMoreButton.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        loadMoreButton.setForeground(new java.awt.Color(255, 255, 255));
        loadMoreButton.setText("Load more");
        loadMoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMoreButtonActionPerformed(evt);
            }
        });

        setPreferredSize(new java.awt.Dimension(500, 494));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setLayout(null);
        jScrollPane1.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(buttonContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loadMoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMoreButtonActionPerformed
        if (mode == "user") {
            this.lastSenderID = null;
            this.newSenderID = null;
            loadMessages(currentFriend);
        }
        else if (mode == "group") {
            this.lastSenderID = null;
            this.newSenderID = null;
            loadMessages(currentGroup);
        }
    }//GEN-LAST:event_loadMoreButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel buttonContainer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadMoreButton;
    // End of variables declaration//GEN-END:variables
}
