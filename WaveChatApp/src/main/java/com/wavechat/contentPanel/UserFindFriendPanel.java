package com.wavechat.contentPanel;

import com.wavechat.GlobalVariable;
import com.wavechat.bus.UserBUS;
import com.wavechat.component.UserSearchPanel;
import com.wavechat.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class UserFindFriendPanel extends javax.swing.JPanel {
    private int offset = 0;
    private int limit = 2;
    
    public UserFindFriendPanel() {
        initComponents();
        resultPanel.setLayout(new MigLayout("fillx"));
    }

    public void resetOffet() {
        this.offset = 0;
    }
    
    private void handleSearch() {
        String query = searchInput.getText();
        String selectedChoice = (String) searchChoice.getSelectedItem();
        UserBUS userBUS = new UserBUS();

        List<UserDTO> findedUsers = new ArrayList<>();
        if ("Username".equals(selectedChoice)) {
            findedUsers = userBUS.findUserByUserName(query, offset, limit);
        } else if ("Fullname".equals(selectedChoice)) {
            findedUsers = userBUS.findUserByFullName(query, offset, limit);
        }
        
        // Hiển thị thêm người dùng
        for (UserDTO user : findedUsers) {
            UserSearchPanel userPanel = new UserSearchPanel(user);
            
            if (!userBUS.isFriend(GlobalVariable.getUserID(), user.getUserID())) {
                userPanel.addAddFriendButton();
            }
            
            resultPanel.add(userPanel, "wrap");
        }
        
        revalidate();
        repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        findFriendLabel = new javax.swing.JLabel();
        searchBarContainer = new javax.swing.JPanel();
        searchInput = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        searchChoice = new javax.swing.JComboBox<>();
        contentContainer = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        resultPanel = new javax.swing.JPanel();
        buttonContainer = new javax.swing.JPanel();
        loadMoreButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(741, 600));

        findFriendLabel.setFont(new java.awt.Font("Montserrat", 0, 28)); // NOI18N
        findFriendLabel.setText("Find Friend");
        findFriendLabel.setPreferredSize(new java.awt.Dimension(214, 32));

        searchBarContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        searchInput.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        searchInput.setText("Search username");
        searchInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 0, 0));
        searchInput.setPreferredSize(new java.awt.Dimension(642, 38));
        searchInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchInputMouseClicked(evt);
            }
        });

        searchButton.setBackground(new java.awt.Color(26, 41, 128));
        searchButton.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setText("Search");
        searchButton.setBorder(null);
        searchButton.setPreferredSize(new java.awt.Dimension(80, 38));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        searchChoice.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Username", "Fullname" }));

        javax.swing.GroupLayout searchBarContainerLayout = new javax.swing.GroupLayout(searchBarContainer);
        searchBarContainer.setLayout(searchBarContainerLayout);
        searchBarContainerLayout.setHorizontalGroup(
            searchBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchBarContainerLayout.createSequentialGroup()
                .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(searchChoice, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        searchBarContainerLayout.setVerticalGroup(
            searchBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(searchBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(searchChoice, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        contentContainer.setPreferredSize(new java.awt.Dimension(726, 700));
        contentContainer.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(726, 472));

        resultPanel.setPreferredSize(new java.awt.Dimension(686, 688));

        javax.swing.GroupLayout resultPanelLayout = new javax.swing.GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 710, Short.MAX_VALUE)
        );
        resultPanelLayout.setVerticalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(resultPanel);

        contentContainer.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        buttonContainer.setPreferredSize(new java.awt.Dimension(720, 30));

        loadMoreButton.setBackground(new java.awt.Color(26, 41, 128));
        loadMoreButton.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        loadMoreButton.setForeground(new java.awt.Color(255, 255, 255));
        loadMoreButton.setText("Find more");
        loadMoreButton.setPreferredSize(new java.awt.Dimension(97, 22));
        loadMoreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMoreButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonContainerLayout = new javax.swing.GroupLayout(buttonContainer);
        buttonContainer.setLayout(buttonContainerLayout);
        buttonContainerLayout.setHorizontalGroup(
            buttonContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonContainerLayout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(loadMoreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(305, Short.MAX_VALUE))
        );
        buttonContainerLayout.setVerticalGroup(
            buttonContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonContainerLayout.createSequentialGroup()
                .addComponent(loadMoreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        contentContainer.add(buttonContainer, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchBarContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contentContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(findFriendLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(findFriendLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchBarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contentContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        resultPanel.removeAll();
        resetOffet();
        handleSearch();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchInputMouseClicked
        searchInput.setText("");
    }//GEN-LAST:event_searchInputMouseClicked

    private void loadMoreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMoreButtonActionPerformed
        offset += limit;
        handleSearch();
    }//GEN-LAST:event_loadMoreButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonContainer;
    private javax.swing.JPanel contentContainer;
    private javax.swing.JLabel findFriendLabel;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton loadMoreButton;
    private javax.swing.JPanel resultPanel;
    private javax.swing.JPanel searchBarContainer;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> searchChoice;
    private javax.swing.JTextField searchInput;
    // End of variables declaration//GEN-END:variables
}
