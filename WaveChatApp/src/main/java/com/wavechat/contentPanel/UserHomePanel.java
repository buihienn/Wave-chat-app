package com.wavechat.contentPanel;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.ConversationBUS;
import com.wavechat.bus.GroupChatBUS;
import com.wavechat.bus.UserBUS;
import com.wavechat.component.ChatBody;
import com.wavechat.component.ChatFooter;
import com.wavechat.component.ChatHeader;
import com.wavechat.component.ConversationPanel;
import com.wavechat.dto.ConversationDTO;
import com.wavechat.dto.GroupChatDTO;
import com.wavechat.dto.UserDTO;
import java.util.List;

public class UserHomePanel extends javax.swing.JPanel {

    private ChatHeader header = new ChatHeader();    
    private ChatBody body = new ChatBody();
    private ChatFooter footer  = new ChatFooter(body);

    public UserHomePanel() {
    }
    
    public void open() {
        removeAll();
        initComponents();

        String userID = GlobalVariable.getUserID();

        ConversationBUS conversationBUS = new ConversationBUS();
        List<ConversationDTO> conversations = conversationBUS.getConversations(userID);

        for (ConversationDTO conversation : conversations) {
            if ("friend".equals(conversation.getConversationType())) {
                String friendID;
                UserDTO userDTO;

                if (userID.equals(conversation.getUserID1())) {
                    friendID = conversation.getUserID2();
                } else {
                    friendID = conversation.getUserID1();
                }

                try {
                    UserBUS userBUS = new UserBUS();
                    userDTO = userBUS.getUserByID(friendID);  

                    ConversationPanel conversationPanel = new ConversationPanel(userDTO);
                    conversationContainer.add(conversationPanel);
                    addConversationListener(conversationPanel, conversation);  

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else if ("group".equals(conversation.getConversationType())) {
                int groupID = Integer.parseInt(conversation.getGroupID());
                System.out.println(groupID);
                GroupChatDTO groupChatDTO;

                try {
                    GroupChatBUS groupChatBUS = new GroupChatBUS();
                    groupChatDTO = groupChatBUS.getGroupChatByID(groupID);  

                    ConversationPanel conversationPanel = new ConversationPanel(groupChatDTO);
                    conversationContainer.add(conversationPanel);
                    addConversationListenerGroupChat(conversationPanel, groupChatDTO, conversation);  

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else if ("stranger".equals(conversation.getConversationType())) {
                String strangerID;
                UserDTO userDTO;

                // Lấy ID bạn bè
                if (userID.equals(conversation.getUserID1())) {
                    strangerID = conversation.getUserID2();
                } else {
                    strangerID = conversation.getUserID1();
                }

                try {
                    UserBUS userBUS = new UserBUS();
                    userDTO = userBUS.getUserByID(strangerID); 

                    ConversationPanel conversationPanel = new ConversationPanel(userDTO);
                    conversationContainer.add(conversationPanel);
                    addConversationListener(conversationPanel, conversation); 

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
        chatContainer.add(header);
        chatContainer.add(body);
        chatContainer.add(footer);

        header.setVisible(false);
        body.setVisible(false);
        footer.setVisible(false);
    }
    
    // Thêm event click cho conversation
    private void addConversationListener(ConversationPanel conversationPanel, ConversationDTO conversation) {
        conversationPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openConversation(conversation);  // Gọi hàm openConversation với thông tin của bạn bè
            }
        });
    }
    
    private void addConversationListenerGroupChat(ConversationPanel conversationPanel, GroupChatDTO groupChat, ConversationDTO conversation) {
        conversationPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openConversationForGroupChat(groupChat, conversation);  // Gọi hàm openConversation với thông tin của bạn bè
            }
        });
    }
    
    public void openConversation(ConversationDTO conversation) { 
        UserBUS userBUS = new UserBUS();
        UserDTO friend;
        try {
            friend = userBUS.getUserByID(conversation.getUserID2());
            
            // Update header và footer
            header.setInfor(friend.getFullName(), friend.isOnlineStatus());
            footer.setMode("user");        
            footer.setReceiver(friend.getUserID());
            footer.setCurConversation(conversation);

            // Load tin nhắn
            body.removeChat();
            body.resetOffet();
            body.setMode("user");  
            body.loadMessages(friend);  
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        
        header.setVisible(true);
        body.setVisible(true);
        footer.setVisible(true);
    }

    
    public void openConversationForGroupChat(GroupChatDTO groupChat, ConversationDTO conversation) {     
        // Update header và footer cho nhóm chat
        header.setInfor(groupChat.getGroupName(), groupChat.isOnlineStatus());
        header.setUpChatPopupMenu(groupChat.getGroupID());
        footer.setMode("group");        
        footer.setGroupID(groupChat.getGroupID());
        footer.setCurConversation(conversation);

        // Load tin nhắn nhóm
        body.removeChat();
        body.resetOffet();
        body.setMode("group");  
        body.loadMessages(groupChat); 
        
        header.setVisible(true);
        body.setVisible(true);
        footer.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        navChatContainer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchBarContainer = new javax.swing.JPanel();
        searchInput = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        conversationScrollPane = new javax.swing.JScrollPane();
        conversationContainer = new javax.swing.JPanel();
        chatContainer = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(741, 600));
        setLayout(new java.awt.BorderLayout());

        navChatContainer.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        navChatContainer.setPreferredSize(new java.awt.Dimension(240, 600));

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 28)); // NOI18N
        jLabel1.setText("Chat");
        jLabel1.setPreferredSize(new java.awt.Dimension(214, 32));

        searchBarContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        searchBarContainer.setPreferredSize(new java.awt.Dimension(214, 32));
        searchBarContainer.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        searchInput.setText("Search");
        searchInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 0));
        searchInput.setPreferredSize(new java.awt.Dimension(160, 32));
        searchInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInputActionPerformed(evt);
            }
        });
        searchBarContainer.add(searchInput);

        searchButton.setBackground(new java.awt.Color(26, 41, 128));
        searchButton.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Search");
        searchButton.setBorder(null);
        searchButton.setPreferredSize(new java.awt.Dimension(52, 32));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        searchBarContainer.add(searchButton);

        conversationScrollPane.setBorder(null);
        conversationScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        conversationContainer.setLayout(new javax.swing.BoxLayout(conversationContainer, javax.swing.BoxLayout.Y_AXIS));
        conversationScrollPane.setViewportView(conversationContainer);

        javax.swing.GroupLayout navChatContainerLayout = new javax.swing.GroupLayout(navChatContainer);
        navChatContainer.setLayout(navChatContainerLayout);
        navChatContainerLayout.setHorizontalGroup(
            navChatContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navChatContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navChatContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(conversationScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        navChatContainerLayout.setVerticalGroup(
            navChatContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navChatContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conversationScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(navChatContainer, java.awt.BorderLayout.LINE_START);

        chatContainer.setPreferredSize(new java.awt.Dimension(500, 600));
        chatContainer.setLayout(new javax.swing.BoxLayout(chatContainer, javax.swing.BoxLayout.Y_AXIS));
        add(chatContainer, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void searchInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchInputActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatContainer;
    private javax.swing.JPanel conversationContainer;
    private javax.swing.JScrollPane conversationScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel navChatContainer;
    private javax.swing.JPanel searchBarContainer;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchInput;
    // End of variables declaration//GEN-END:variables
}
