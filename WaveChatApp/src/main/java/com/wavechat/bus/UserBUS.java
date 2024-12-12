package com.wavechat.bus;

import com.wavechat.dao.UserDAO;
import com.wavechat.dto.UserDTO;
import java.security.SecureRandom;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import org.mindrot.jbcrypt.BCrypt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;


public class UserBUS {
    private UserDAO userDAO;

    public UserBUS() {
        this.userDAO = new UserDAO();
    }
    
    // Ham getALl
    public List<UserDTO> getAll(){
        return userDAO.getAll();
    }

    // Hàm cập nhật thông tin người dùng
    public boolean editUser(UserDTO user) {
        return userDAO.updateUser(user); // Gọi phương thức từ DAO
    }
    
    // Hàm lấy thông tin người dùng
    public UserDTO getUserByID(String userID) throws Exception {
        return userDAO.getUserByID(userID); // Gọi phương thức từ DAO
    }
    
    // ---------------REGISTER---------------
    // Hash mật khẩu bằng BCrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));  // Sử dụng cost factor là 12
    }

    // Kiểm tra tên người dùng có hợp lệ không
    public boolean isUserNameExist(String userName) {
        // Kiểm tra trùng lặp tên người dùng trong cơ sở dữ liệu
        UserDAO userDAO = new UserDAO();
        return userDAO.checkUserNameExist(userName);  // Phương thức kiểm tra tồn tại của tên người dùng
    }

    // Kiểm tra email có hợp lệ không
    public boolean isEmailValid(String email) {
        // Kiểm tra email hợp lệ bằng regex
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches(); // Trả về true nếu email hợp lệ, ngược lại false
    }
    
    // Kiểm tra email có tồn tại trong cơ sở dữ liệu không
    public boolean isEmailExist(String email) {
        UserDAO userDAO = new UserDAO();
        return userDAO.checkEmailExist(email);  // Phương thức kiểm tra tồn tại của email trong cơ sở dữ liệu
    }

    // Kiểm tra mật khẩu có hợp lệ không
    public boolean isPasswordValid(String password) {
        // Kiểm tra mật khẩu có ít nhất 1 chữ cái viết hoa, 1 chữ cái viết thường và 1 chữ số
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";  // Mật khẩu dài ít nhất 8 ký tự
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Hàm thêm người dùng mới vào cơ sở dữ liệu
    public boolean addNewUser(String userName, String email, String password) {
        // Tạo userID mới
        UserDAO userDAO = new UserDAO();
        String userID = userDAO.generateUserID();
        if (userID == null) {
            return false; // Nếu không thể tạo userID thì trả về false
        }

        // Mã hóa mật khẩu trước khi thêm
        String hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            return false; // Nếu không thể hash password thì trả về false
        }

        // Thêm người dùng vào cơ sở dữ liệu
        return userDAO.addNewUser(userName, email, hashedPassword);
    }
    
    // ---------------LOGIN---------------  
    public boolean isAdmin(String emailOrUsername) {
        UserDAO userDAO = new UserDAO();
        return userDAO.getUserRole(emailOrUsername);  // Trả về true nếu là admin, false nếu là user
    }
    
    // Hàm check string có là email
    public boolean isEmail(String input) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return input.matches(emailRegex);
    }
    
    // Hàm check login
    public boolean validateUser(String emailOrUsername, String password) {
        UserDAO userDAO = new UserDAO();
        return userDAO.validateUser(emailOrUsername, password);  
    }
    
    // Lấy full name của user
    public String getFullNameByID(String userID) {
        UserDAO userDAO = new UserDAO();
        return userDAO.getFullNameByID(userID);
    }
    
    // ---------------PASSWORD---------------
    public boolean resetPassword(String email) {
        // Gọi phương thức trong DAO để kiểm tra email
        UserDAO userDAO = new UserDAO();
        String newPassword = generateNewPassword();  // Tạo mật khẩu mới
        String hashedPassword = hashPassword(newPassword);  // Hash mật khẩu mới

        boolean isUpdated = userDAO.updatePassword(email, hashedPassword);  // Cập nhật mật khẩu vào DB
        
        if (isUpdated) {
            // Gửi mật khẩu qua email
            sendPasswordToEmail(email, newPassword);
        }

        return isUpdated;
    }
    
    // Random password
    private static SecureRandom random = new SecureRandom();

    // Lấy ký tự ngẫu nhiên từ một chuỗi
    private static char getRandomCharacter(String characterSet) {
        int index = random.nextInt(characterSet.length());
        return characterSet.charAt(index);
    }

    // Trộn các ký tự trong chuỗi
    private static String shuffleString(String str) {
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            int j = random.nextInt(array.length); // Tạo chỉ số ngẫu nhiên
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new String(array);
    }

    // Tạo mật khẩu mới, đảm bảo có ít nhất một chữ in hoa, một chữ thường và một số
    public static String generateNewPassword() {
        String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String DIGITS = "0123456789";

        // Tạo ít nhất 1 chữ cái in hoa, 1 chữ cái thường và 1 chữ số
        StringBuilder password = new StringBuilder();
        password.append(getRandomCharacter(UPPERCASE)); // 1 chữ cái in hoa
        password.append(getRandomCharacter(LOWERCASE)); // 1 chữ cái thường
        password.append(getRandomCharacter(DIGITS)); // 1 chữ số

        // Tạo thêm các ký tự ngẫu nhiên từ tất cả các loại ký tự
        String allCharacters = LOWERCASE + UPPERCASE + DIGITS;
        for (int i = 3; i < 8; i++) { // Đảm bảo mật khẩu có đủ 8 ký tự
            password.append(getRandomCharacter(allCharacters));
        }

        // Trộn lại các ký tự để tạo mật khẩu ngẫu nhiên
        return shuffleString(password.toString());
    }
    
    // Hàm gửi email cho user
    public static void sendPasswordToEmail(String email, String password) {
        // Địa chỉ email người nhận 
        String recipient = email;

        // Địa chỉ email người gửi
        String sender = "wavee.noreply@gmail.com"; // Email sender

        // Cấu hình SMTP server (dùng Google SMTP hoặc SMTP server bạn đang dùng)
        String host = "smtp.gmail.com";  // Ví dụ với Gmail, có thể thay đổi nếu dùng SMTP khác

        // Cài đặt các thuộc tính cho email
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // Cổng của Gmail SMTP là 587 cho TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Tạo session để gửi email
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("wavee.noreply@gmail.com", "kvhy xywy rytq mpqo"); // Email và mật khẩu của sender
            }
        });

        try {
            // Tạo một đối tượng MimeMessage
            MimeMessage message = new MimeMessage(session);

            // Đặt địa chỉ email người gửi
            message.setFrom(new InternetAddress(sender));

            // Đặt địa chỉ email người nhận
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Đặt tiêu đề email
            message.setSubject("[Wave] Your new password");

            // Đặt nội dung email với mật khẩu
            message.setText("Dear user,"
                            + "\n\nYour new password is: " + password 
                            + "\n\nBest regards,\n"
                            + "Wave Team");

            // Gửi email
            Transport.send(message);
            System.out.println("Mail successfully sent to " + recipient);

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
        
    // Hàm thay đổi mật khẩu cho user
    public boolean changePassword(String userID, String currentPassword, String newPassword) {

        // Kiểm tra mật khẩu cũ
        UserDAO userDAO = new UserDAO();
        String storedHashedPassword = userDAO.getStoredPassword(userID);  // Lấy mật khẩu đã hash từ DB

        if (storedHashedPassword == null || !BCrypt.checkpw(currentPassword, storedHashedPassword)) {
            return false;  // Mật khẩu cũ không đúng hoặc không tìm thấy 
        }

        // Mật khẩu mới hợp lệ, hash mật khẩu mới và gọi DAO để cập nhật
        String hashedNewPassword = hashPassword(newPassword); // Hash mật khẩu mới
        return userDAO.updatePasswordByID(userID, hashedNewPassword);  // Cập nhật mật khẩu mới vào DB
    }
    
    // Admin insert new user
    public boolean insertUser(String userName, String fullname, String email){
        // Kiểm tra tính hợp lệ của dữ liệu đầu vào
        if (isUserNameExist(userName)) {
            System.out.println("Username already exists.");
            return false;
        }
        if (!isEmailValid(email)) {
            System.out.println("Invalid email format.");
            return false;
        }
        if (isEmailExist(email)) {
            System.out.println("Email already exists.");
            return false;
        }

        // Sinh mật khẩu ngẫu nhiên
        String rawPassword = generateNewPassword();
        // Hash mật khẩu
        String hashedPassword = hashPassword(rawPassword);
        
        boolean isInserted = userDAO.insertUser(userName, hashedPassword, fullname, email);

        if (isInserted) {
            // Gửi mật khẩu qua email
            sendPasswordToEmail(email, rawPassword);
            System.out.println("User added successfully!");
            return true;
        } else {
            System.out.println("Failed to add user.");
            return false;
        }
    }

}