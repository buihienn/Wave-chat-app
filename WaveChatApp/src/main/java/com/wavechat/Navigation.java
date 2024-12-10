package com.wavechat;

import com.wavechat.form.*;
import javax.swing.JFrame;

public class Navigation {
    // Navigate đến trang login
    public void navigateToLogin(JFrame frame) { 
        frame.setVisible(false); 
        Login navFrame = new Login(); 
        navFrame.setVisible(true); 
    }
    
        // Navigate đến trang login
    public void navigateToRegister(JFrame frame) { 
        frame.setVisible(false); 
        Register navFrame = new Register(); 
        navFrame.setVisible(true); 
    }
    
    // Navigate đến trang chủ admin
    public void navigateToAdminHome(JFrame frame) { 
        frame.setVisible(false); 
        AdminHomeMain navFrame = new AdminHomeMain(); 
        navFrame.setVisible(true); 
    }
        
    // Navigate đến trang chủ user
    public void navigateToUserHome(JFrame frame) { 
        frame.setVisible(false); 
        UserHome navFrame = new UserHome(); 
        navFrame.setVisible(true); 
    }
    
    // Navigate đến trang user profile
    public void navigateToUserProfile(JFrame frame) { 
        frame.setVisible(false); 
        UserProfile navFrame = new UserProfile(); 
        navFrame.setVisible(true); 
    }
    
    // Navigate đến trang user friend list
    public void navigateToUserFriend(JFrame frame) { 
        frame.setVisible(false); 
        UserFriend navFrame = new UserFriend(); 
        navFrame.setVisible(true); 
    }
    
}
