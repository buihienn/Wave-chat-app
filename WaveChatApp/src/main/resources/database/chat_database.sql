-- Check if the database exists and drop it if it does
DROP DATABASE IF EXISTS CHATAPPLICATION;

-- Create the database
CREATE DATABASE CHATAPPLICATION;

-- Use the newly created database
USE CHATAPPLICATION;

CREATE TABLE User (
	userID CHAR(5),
    userName VARCHAR(50) UNIQUE,
    passWord VARCHAR(100),
    fullName NVARCHAR(50),
    address NVARCHAR(50),
    birthDay DATE NULL,
    gender VARCHAR(20),
    email VARCHAR (100) UNIQUE,
    createdDate DATE,
    status BOOL,   -- true = "Unlock" - false = "Locked"
    onlineStatus BOOL,
    isAdmin BOOL,

    CONSTRAINT PK_USER PRIMARY KEY (userID)
);

CREATE TABLE Friend_requests (
    requester_userID CHAR(5),     -- Người gửi yêu cầu kết bạn
    requested_userID CHAR(5),     -- Người nhận yêu cầu kết bạn
    status ENUM('pending', 'accepted', 'rejected') NOT NULL, -- Trạng thái yêu cầu kết bạn
    createdAt DATE, -- Thời gian gửi yêu cầu
    
    CONSTRAINT PK_FRIEND_REQUESTS PRIMARY KEY (requester_userID, requested_userID)
);


CREATE TABLE Friends (
    userID1 CHAR(5),
    userID2 CHAR(5),
    createdAt DATE,
    
    CONSTRAINT PK_FRIENDS PRIMARY KEY (userID1, userID2)
);

CREATE TABLE Blocks (
    userID CHAR(5),             -- thực hiện block
    blocked_userID CHAR(5),     -- bị block
    
    CONSTRAINT PK_BLOCKS PRIMARY KEY (userID, blocked_userID)
);

CREATE TABLE LoginHistory (
	id INT AUTO_INCREMENT,
    userID CHAR(5),
    loginTime DATETIME,
    
    CONSTRAINT PK_LOGINHISTORY PRIMARY KEY(id)
);

CREATE TABLE Chat (
	chatID INT,
    senderID CHAR(5),
    receiverID CHAR(5),
    groupID INT,
    message NVARCHAR(255),
    timeSend DATETIME,
    isRead BOOL,
    conversationID CHAR(5),
    
    CONSTRAINT PK_CHAT PRIMARY KEY (chatID)
);

CREATE TABLE GroupChat (
	groupID INT AUTO_INCREMENT,
    groupName NVARCHAR(50),
    createdBy CHAR(5),
    createdAt DATETIME,
    onlineStatus BOOL,
    
	CONSTRAINT PK_GROUPCHAT PRIMARY KEY (groupID)
);

CREATE TABLE GroupMembers (
    groupID INT AUTO_INCREMENT,
    userID CHAR(5),
    isAdmin BOOL,
    joinedDate DATETIME,
    
    CONSTRAINT PK_GROUPMEMBER PRIMARY KEY (groupID, userID)
);

CREATE TABLE SpamReport (
	reportID INT AUTO_INCREMENT,
    reporterID CHAR(5),
    reportedUserId CHAR(5),
    timeStamp DATETIME,
    
    CONSTRAINT PK_SPAMREPORT PRIMARY KEY (reportID)
);

CREATE TABLE Conversations (
    conversationID CHAR(5), 
    userID1 CHAR(5),  -- Owner
    userID2 CHAR(5),  -- Nếu là người, lưu userID
    groupID INT,  -- Nếu là nhóm, lưu groupID
    lastMessageTime DATETIME,
    status BOOL,  -- Có thể chỉ trạng thái cuộc trò chuyện (dùng sau)
    conversationType ENUM('friend', 'group', 'stranger'),  -- Type
    CONSTRAINT PK_CONVERSATIONS PRIMARY KEY (conversationID)

);


-- Friends
ALTER TABLE Friends 
ADD CONSTRAINT FOREIGN KEY (userID1) REFERENCES User(userID),
ADD CONSTRAINT FOREIGN KEY (userID2) REFERENCES User(userID);

-- Friends_request
ALTER TABLE Friend_requests 
ADD CONSTRAINT FK_REQUESTER FOREIGN KEY (requester_userID) REFERENCES User(userID),
ADD CONSTRAINT FK_REQUESTED FOREIGN KEY (requested_userID) REFERENCES User(userID);

-- Blocks
ALTER TABLE Blocks
ADD CONSTRAINT FK_BLOCKS_USERS FOREIGN KEY (userID) REFERENCES User(userID),
ADD CONSTRAINT FK_BLOCKED_USERS FOREIGN KEY (blocked_userID) REFERENCES User(userID);

-- LoginHistory
ALTER TABLE LoginHistory 
ADD CONSTRAINT FK_LoginHistory_User FOREIGN KEY (userID) REFERENCES User(userID);

-- Conversations
ALTER TABLE Conversations 
ADD CONSTRAINT FK_Conversations_User1 FOREIGN KEY (userID1) REFERENCES User(userID),
ADD CONSTRAINT FK_Conversations_User2 FOREIGN KEY (userID2) REFERENCES User(userID),
ADD CONSTRAINT FK_Conversations_GroupChat FOREIGN KEY (groupID) REFERENCES GroupChat(groupID);

-- Chat
ALTER TABLE Chat 
ADD CONSTRAINT FK_Chat_Conversations FOREIGN KEY (conversationID) REFERENCES Conversations(conversationID), 
ADD CONSTRAINT FK_Chat_Sender FOREIGN KEY (senderID) REFERENCES User(userID),
ADD CONSTRAINT FK_Chat_Receiver FOREIGN KEY (receiverID) REFERENCES User(userID),
ADD CONSTRAINT FK_Chat_GroupChat FOREIGN KEY (groupID) REFERENCES GroupChat(groupID);

-- GroupChat
ALTER TABLE GroupChat 
ADD CONSTRAINT FK_GroupChat_CreatedBy FOREIGN KEY (createdBy) REFERENCES User(userID);

-- GroupMembers
ALTER TABLE GroupMembers 
ADD CONSTRAINT FK_GroupMembers_User FOREIGN KEY (userID) REFERENCES User(userID),
ADD CONSTRAINT FK_GroupMembers_GroupCHat FOREIGN KEY (groupID) REFERENCES GroupChat (groupID);

-- SpamReport
ALTER TABLE SpamReport 
ADD CONSTRAINT FK_SpamReport_Reporter FOREIGN KEY (reporterID) REFERENCES User(userID),
ADD CONSTRAINT FK_SpamReport_ReportedUser FOREIGN KEY (reportedUserId) REFERENCES User(userID);

-- --------------------------------- Add data -----------------------------------
-- User
INSERT INTO User (userID, userName, passWord, fullName, address, birthDay, gender, email, createdDate, status, onlineStatus, isAdmin)
VALUES 
('U001', 'hienbui', '$2a$12$EZW2K.vlr6PTMd3eBxVtWur0ltNSVF6viSxWHPjr8DgJYmyOpbuQq', 'Bui Hien', '123 Nguyen Trai, District 1', '1990-01-01', 'male', 'hien.bui@example.com', '2024-11-26', TRUE, FALSE, FALSE), -- Buihien123
('U002', 'vinhphu', 'securePass456', 'Phu Vinh', '456 Le Loi, District 5', '1992-03-15', 'male', 'vinh.phu@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U003', 'quangminh', 'easyPass789', 'Quang Minh', '78 Hai Ba Trung, District 3', '1993-07-22', 'male', 'minh.quang@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U004', 'lannguyen', 'password321', 'Nguyen Lan', '102 Tran Hung Dao, District 1', '1994-09-12', 'female', 'lan.nguyen@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U005', 'admin3', 'pass789456', 'Viet Hoa', '203 Vo Van Kiet, District 6', '1995-11-02', 'female', 'hoa.viet@example.com', '2024-11-26', TRUE, FALSE, TRUE),
('U006', 'admin2', 'ngocanh@123', 'Ngoc Anh', '56 Cach Mang Thang 8, District 10', NULL, 'male', 'ngoc.anh@example.com', '2024-11-26', TRUE, FALSE, TRUE),
('U007', 'admin1', '$2a$12$f.FHP.EzG7M3yY3qNs7vPu.zE8x52uPY226TGBWi83kSb6LFKnkGK', 'Ho Hien', '1 Ton Duc Thang, District 1', '1985-05-05', 'female', 'admin@example.com', '2024-10-20', TRUE, FALSE, TRUE), -- Admin123
('U008', 'vinhhho', '$2a$12$7m52NAE1HSUkwO6vXLstKelNLBDjMkCmjBdMx1bA1vH/570C25kzS', 'Ho Phu Vinh', '1 Le Van Luong, District 7', '2004-05-05', 'male', 'vinhho2507@gmail.com', '2024-10-20', TRUE, FALSE, FALSE), -- Phuvinh123
('U009', 'thaison', '$2a$12$QiphfSLtIb2eQYORRuP6cup8V3E7QLlRtKUxHIh/sNHmKd/O892qO', 'Thai Son', '456 Le Loi, District 5', '1992-03-15', 'male', 'thaisonn8178@gmail.com', '2024-11-26', TRUE, FALSE, FALSE), -- Thaison123
('U010', 'baohuy', '$2a$12$BzO5cPjYhMm4n2RABAboeeC/s8c/IRyqBr6Cc852NXwcApYzP4Rqq', 'Bao Huy', '78 Hai Ba Trung, District 3', '1993-07-22', 'male', 'phbhuy22@clc.fitus.edu.vn', '2024-11-26', TRUE, FALSE, FALSE), -- Baohuy123
('U011', 'diuhuyen', '$2a$12$ilmcULu8ym.Lb5rQYy793OWkaU0y55JC0jRZSrFhtRzab8zrKW70m', 'Diu Huyen', '102 Tran Hung Dao, District 1', '1994-09-12', 'female', 'tdhuyen22@clc.fitus.edu.vn', '2024-11-26', TRUE, FALSE, FALSE), -- Diuhuyen123
('U012', 'hanhnguyen', 'securePass101', 'Nguyen Hanh', '11 Pasteur, District 1', '1990-06-10', 'female', 'hanh.nguyen@example.com', '2024-01-15', TRUE, FALSE, FALSE),
('U013', 'anhhoang', 'password202', 'Hoang Anh', '22 Vo Thi Sau, District 3', '1989-04-08', 'male', 'anh.hoang@example.com', '2024-02-20', TRUE, FALSE, FALSE),
('U014', 'linhtran', 'pass789101', 'Tran Linh', '33 Pham Van Dong, District 2', '1991-12-15', 'female', 'linh.tran@example.com', '2024-03-10', TRUE, FALSE, FALSE),
('U015', 'quocbao', 'easyPass303', 'Quoc Bao', '44 Dien Bien Phu, District 1', '1988-01-23', 'male', 'quoc.bao@example.com', '2024-04-05', TRUE, FALSE, FALSE),
('U016', 'khanhngoc', 'password404', 'Khanh Ngoc', '55 Hoang Dieu, District 4', '1996-07-17', 'female', 'khanh.ngoc@example.com', '2024-05-22', TRUE, FALSE, FALSE),
('U017', 'nguyenhung', 'securePass505', 'Nguyen Hung', '66 Ly Thuong Kiet, District 5', '1993-11-09', 'male', 'hung.nguyen@example.com', '2024-06-18', TRUE, FALSE, FALSE),
('U018', 'minhthu', 'password606', 'Minh Thu', '77 Bach Dang, District 2', '1995-03-22', 'female', 'thu.minh@example.com', '2024-07-07', TRUE, FALSE, FALSE),
('U019', 'phuonganh', 'pass202404', 'Phuong Anh', '88 Nguyen Thai Hoc, District 1', '1997-02-14', 'female', 'phuong.anh@example.com', '2024-08-13', TRUE, FALSE, FALSE),
('U020', 'dongquang', 'securePass707', 'Dong Quang', '99 Cong Hoa, District 4', '1998-10-05', 'male', 'quang.dong@example.com', '2024-09-09', TRUE, FALSE, FALSE),
('U021', 'quynhnga', 'password808', 'Quynh Nga', '100 Le Van Sy, District 3', '1992-08-03', 'female', 'nga.quynh@example.com', '2024-10-25', TRUE, FALSE, FALSE),
('U022', 'tuanle', 'pass303505', 'Tuan Le', '101 Ton That Tung, District 2', '1994-06-07', 'male', 'le.tuan@example.com', '2024-01-30', TRUE, FALSE, FALSE),
('U023', 'vannam', 'passpass101', 'Van Nam', '120 Nguyen Du, District 1', '1990-09-15', 'male', 'nam.van@example.com', '2024-02-14', TRUE, FALSE, FALSE),
('U024', 'nhungtran', 'passpass202', 'Nhung Tran', '23 Hoang Van Thu, District 2', '1992-10-19', 'female', 'nhung.tran@example.com', '2024-03-18', TRUE, FALSE, FALSE),
('U025', 'ducthang', 'securePass909', 'Duc Thang', '45 Nguyen Huu Tho, District 7', '1987-12-05', 'male', 'thang.duc@example.com', '2024-04-09', TRUE, FALSE, FALSE),
('U026', 'baoquyen', 'passpass303', 'Bao Quyen', '12 Tran Quoc Toan, District 3', '1993-05-15', 'female', 'quyen.bao@example.com', '2024-05-20', TRUE, FALSE, FALSE),
('U027', 'haiyen', 'passpass404', 'Hai Yen', '66 Hai Ba Trung, District 1', '1994-04-28', 'female', 'yen.hai@example.com', '2024-06-08', TRUE, FALSE, FALSE),
('U028', 'minhtam', 'passpass505', 'Minh Tam', '99 Tran Hung Dao, District 4', '1998-11-30', 'male', 'tam.minh@example.com', '2024-07-01', TRUE, FALSE, FALSE),
('U029', 'thanhson', 'securePass1010', 'Thanh Son', '44 Le Thanh Ton, District 1', '1995-08-22', 'male', 'son.thanh@example.com', '2024-08-18', TRUE, FALSE, FALSE),
('U030', 'trungkien', 'passpass606', 'Trung Kien', '78 Mac Dinh Chi, District 1', '1991-09-03', 'male', 'kien.trung@example.com', '2024-09-11', TRUE, FALSE, FALSE),
('U031', 'thuhien', 'securePass2020', 'Thu Hien', '33 Hoang Hoa Tham, District 2', '1990-07-12', 'female', 'hien.thu@example.com', '2024-10-05', TRUE, FALSE, FALSE),
('U032', 'manhhung', 'passpass707', 'Manh Hung', '45 Ly Chinh Thang, District 3', '1988-01-02', 'male', 'hung.manh@example.com', '2024-01-11', TRUE, FALSE, FALSE),
('U033', 'kimlan', 'securePass3030', 'Kim Lan', '66 Pham Ngu Lao, District 5', '1992-03-09', 'female', 'lan.kim@example.com', '2024-02-27', TRUE, FALSE, FALSE),
('U034', 'phucminh', 'passpass808', 'Phuc Minh', '12 Nguyen Thai Binh, District 4', '1997-12-17', 'male', 'minh.phuc@example.com', '2024-03-15', TRUE, FALSE, FALSE),
('U035', 'hoangloc', 'securePass4040', 'Hoang Loc', '44 Dinh Tien Hoang, District 1', '1993-08-25', 'male', 'loc.hoang@example.com', '2024-04-22', TRUE, FALSE, FALSE),
('U036', 'baongan', 'passpass909', 'Bao Ngan', '120 Nguyen Dinh Chieu, District 3', '1995-10-08', 'female', 'ngan.bao@example.com', '2024-05-19', TRUE, FALSE, FALSE),
('U037', 'lananh', 'securePass5050', 'Lan Anh', '88 Tran Cao Van, District 2', '1994-05-04', 'female', 'anh.lan@example.com', '2024-06-16', TRUE, FALSE, FALSE),
('U038', 'ngocphat', 'passpass1010', 'Ngoc Phat', '78 Truong Dinh, District 1', '1996-09-27', 'male', 'phat.ngoc@example.com', '2024-07-20', TRUE, FALSE, FALSE),
('U039', 'hongnhung', 'securePass6060', 'Hong Nhung', '55 Phan Chu Trinh, District 3', '1999-06-15', 'female', 'nhung.hong@example.com', '2024-08-05', TRUE, FALSE, FALSE),
('U040', 'vanbao', 'passpass2020', 'Van Bao', '33 Tran Duy Hung, District 2', '1987-03-12', 'male', 'bao.van@example.com', '2024-09-22', TRUE, FALSE, FALSE),
('U041', 'tienphat', 'securePass7070', 'Tien Phat', '22 Cao Thang, District 1', '1995-11-18', 'male', 'phat.tien@example.com', '2024-10-30', TRUE, FALSE, FALSE),
('U042', 'trangnguyen', 'passpass3030', 'Trang Nguyen', '101 Nguyen Hue, District 1', '1991-01-01', 'female', 'nguyen.trang@example.com', '2024-01-05', TRUE, FALSE, FALSE),
('U043', 'hienmai', 'securePass8080', 'Hien Mai', '120 Ngo Quyen, District 3', '1990-05-20', 'female', 'mai.hien@example.com', '2024-02-16', TRUE, FALSE, FALSE),
('U044', 'khanhvy', 'passpass4040', 'Khanh Vy', '88 Xo Viet Nghe Tinh, District 2', '1994-12-13', 'female', 'vy.khanh@example.com', '2024-03-10', TRUE, FALSE, FALSE),
('U045', 'vinhquang', 'securePass9090', 'Vinh Quang', '55 Dinh Bo Linh, District 4', '1989-06-06', 'male', 'quang.vinh@example.com', '2024-04-15', TRUE, FALSE, FALSE),
('U046', 'thanhtrung', 'passpass5050', 'Thanh Trung', '33 Nguyen Van Troi, District 5', '1992-11-22', 'male', 'trung.thanh@example.com', '2024-05-02', TRUE, FALSE, FALSE),
('U047', 'minhanh', 'securePass10101', 'Minh Anh', '78 Le Thanh Nghi, District 1', '1993-08-18', 'female', 'anh.minh@example.com', '2024-06-25', TRUE, FALSE, FALSE),
('U048', 'hoangminh', 'passpass6060', 'Hoang Minh', '120 Tran Quang Dieu, District 3', '1994-09-14', 'male', 'minh.hoang@example.com', '2024-07-13', TRUE, FALSE, FALSE),
('U049', 'kimcuong', 'securePass20202', 'Kim Cuong', '33 Hai Trieu, District 4', '1996-12-01', 'female', 'cuong.kim@example.com', '2024-08-21', TRUE, FALSE, FALSE),
('U050', 'ducanh', 'passpass7070', 'Duc Anh', '88 Ly Chieu Hoang, District 2', '1988-04-19', 'male', 'anh.duc@example.com', '2024-09-29', TRUE, FALSE, FALSE);

-- Friend_requests
INSERT INTO Friend_requests (requester_userID, requested_userID, status, createdAt)
VALUES
('U001', 'U008', 'pending', '2024-12-01'),
('U002', 'U008', 'pending', '2024-12-02'),
('U003', 'U008', 'pending', '2024-12-04'),
('U004', 'U008', 'pending', '2024-12-11'),
('U009', 'U008', 'accepted', '2024-12-02'),
('U010', 'U008', 'accepted', '2024-12-04'),
('U011', 'U008', 'accepted', '2024-12-11'),
('U005', 'U006', 'pending', '2024-12-03'),
('U007', 'U008', 'accepted', '2024-11-20'),
('U012', 'U013', 'pending', '2024-11-15'),
('U014', 'U015', 'accepted', '2024-11-12'),
('U016', 'U017', 'rejected', '2024-11-25'),
('U018', 'U019', 'pending', '2024-10-10'),
('U020', 'U021', 'accepted', '2024-10-01'),
('U022', 'U023', 'accepted', '2024-09-05'),
('U024', 'U025', 'pending', '2024-09-20'),
('U026', 'U027', 'rejected', '2024-08-15'),
('U028', 'U029', 'accepted', '2024-07-10'),
('U030', 'U031', 'accepted', '2024-06-25'),
('U032', 'U033', 'pending', '2024-06-01'),
('U034', 'U035', 'accepted', '2024-05-05'),
('U036', 'U037', 'rejected', '2024-04-15'),
('U038', 'U039', 'accepted', '2024-03-10'),
('U040', 'U041', 'pending', '2024-02-01'),
('U042', 'U043', 'accepted', '2024-01-15'),
('U044', 'U045', 'pending', '2024-01-20'),
('U046', 'U047', 'rejected', '2024-01-25'),
('U048', 'U049', 'accepted', '2024-01-30'),
('U050', 'U001', 'pending', '2024-12-05');

-- Friends
INSERT INTO Friends (userID1, userID2, createdAt)
VALUES
('U009', 'U008', '2024-12-02'),
('U010', 'U008', '2024-12-04'),
('U011', 'U008', '2024-12-11'),
('U007', 'U008', '2024-11-20'),
('U014', 'U015', '2024-11-12'),
('U020', 'U021', '2024-10-01'),
('U022', 'U023', '2024-09-05'),
('U028', 'U029', '2024-07-10'),
('U030', 'U031', '2024-06-25'),
('U034', 'U035', '2024-05-05'),
('U038', 'U039', '2024-03-10'),
('U042', 'U043', '2024-01-15'),
('U048', 'U049', '2024-01-30'),
('U003', 'U004', '2024-01-05'),
('U005', 'U006', '2024-01-10'),
('U018', 'U019', '2024-02-15'),
('U016', 'U017', '2024-03-20'),
('U012', 'U013', '2024-04-25'),
('U040', 'U041', '2024-05-30'),
('U044', 'U045', '2024-06-10'),
('U046', 'U047', '2024-07-15'),
('U050', 'U001', '2024-08-01');

-- Block
INSERT INTO Blocks (userID, blocked_userID)
VALUES
('U001', 'U002'),
('U003', 'U004'),
('U005', 'U001'),
('U006', 'U007'),
('U008', 'U009'),
('U010', 'U011'),
('U012', 'U013'),
('U014', 'U015'),
('U016', 'U017'),
('U018', 'U019'),
('U020', 'U021'),
('U022', 'U023'),
('U024', 'U025'),
('U026', 'U027'),
('U028', 'U029'),
('U030', 'U031'),
('U032', 'U033'),
('U034', 'U035'),
('U036', 'U037'),
('U038', 'U039'),
('U040', 'U041'),
('U042', 'U043'),
('U044', 'U045'),
('U046', 'U047'),
('U048', 'U049'),
('U050', 'U002');

-- LoginHistory
INSERT INTO LoginHistory (id, userID, loginTime)
VALUES
(1, 'U001', '2024-12-20 08:30:00'),
(2, 'U002', '2024-12-20 09:00:00'),
(3, 'U003', '2024-12-20 10:15:00'),
(4, 'U004', '2024-12-20 11:45:00'),
(5, 'U005', '2024-12-20 12:00:00'),
(6, 'U001', '2024-12-22 12:00:00'),
(7, 'U008', '2024-12-22 12:00:00'),
(8, 'U009', '2024-01-05 08:00:00'),
(9, 'U010', '2024-01-10 09:30:00'),
(10, 'U011', '2024-02-01 10:45:00'),
(11, 'U012', '2024-02-15 11:50:00'),
(12, 'U013', '2024-03-01 12:10:00'),
(13, 'U014', '2024-03-15 14:00:00'),
(14, 'U015', '2024-03-20 16:30:00'),
(15, 'U016', '2024-04-10 08:15:00'),
(16, 'U017', '2024-04-20 09:00:00'),
(17, 'U018', '2024-05-01 10:30:00'),
(18, 'U019', '2024-05-10 11:45:00'),
(19, 'U020', '2024-05-20 13:00:00'),
(20, 'U021', '2024-06-01 08:45:00'),
(21, 'U022', '2024-06-10 09:15:00'),
(22, 'U023', '2024-07-05 10:45:00'),
(23, 'U024', '2024-07-10 12:00:00'),
(24, 'U025', '2024-08-01 13:30:00'),
(25, 'U026', '2024-08-15 15:00:00'),
(26, 'U027', '2024-09-01 08:30:00'),
(27, 'U028', '2024-09-10 09:45:00'),
(28, 'U029', '2024-09-20 10:15:00'),
(29, 'U030', '2024-10-01 11:00:00'),
(30, 'U031', '2024-10-15 14:45:00'),
(31, 'U032', '2024-10-20 16:00:00'),
(32, 'U033', '2024-11-01 08:15:00'),
(33, 'U034', '2024-11-10 09:30:00'),
(34, 'U035', '2024-11-20 10:45:00'),
(35, 'U036', '2024-11-25 12:00:00'),
(36, 'U037', '2024-12-01 13:15:00'),
(37, 'U038', '2024-12-10 14:45:00'),
(38, 'U039', '2024-12-15 16:30:00'),
(39, 'U040', '2024-06-18 08:45:00'),
(40, 'U041', '2024-05-25 10:00:00'),
(41, 'U042', '2024-07-07 11:15:00'),
(42, 'U043', '2024-03-09 12:30:00'),
(43, 'U044', '2024-02-28 13:45:00'),
(44, 'U045', '2024-09-05 14:00:00'),
(45, 'U046', '2024-08-15 15:30:00'),
(46, 'U047', '2024-10-20 16:45:00'),
(47, 'U048', '2024-11-30 08:00:00'),
(48, 'U049', '2024-04-18 09:15:00'),
(49, 'U050', '2024-12-05 10:30:00'),
(50, 'U001', '2024-11-25 11:45:00');

-- Spam report
INSERT INTO SpamReport (reporterID, reportedUserId, timeStamp)
VALUES
('U001', 'U002', '2024-12-20 14:30:00'),
('U003', 'U004', '2024-12-20 15:00:00'),
('U005', 'U006', '2024-12-21 10:45:00'),
('U002', 'U003', '2024-12-21 11:20:00'),
('U004', 'U001', '2024-12-21 12:00:00'),
('U007', 'U008', '2024-11-20 09:15:00'),
('U009', 'U010', '2024-10-10 10:30:00'),
('U011', 'U012', '2024-09-15 08:45:00'),
('U013', 'U014', '2024-08-05 09:00:00'),
('U015', 'U016', '2024-07-25 14:00:00');

-- Group chat
INSERT INTO GroupChat (groupID, groupName, createdBy, createdAt, onlineStatus)
VALUES 
(1, 'Tech Enthusiasts', 'U001', '2024-12-13', 0),
(2, 'Anime Lovers', 'U003', '2024-12-13', 0),
(3, 'Sports Fans', 'U002', '2024-11-20', 1),
(4, 'Movie Buffs', 'U004', '2024-10-15', 0),
(5, 'Book Club', 'U005', '2024-09-10', 1),
(6, 'Gamers Unite', 'U006', '2024-08-25', 0),
(7, 'Music Lovers', 'U007', '2024-07-30', 1),
(8, 'Foodies', 'U008', '2024-06-15', 1),
(9, 'Travel Enthusiasts', 'U009', '2024-05-10', 0),
(10, 'Fitness Group', 'U010', '2024-04-05', 1);

-- Nhóm 1: Tech Enthusiasts
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(1, 'U001', 1, '2024-12-13'),
(1, 'U002', 0, '2024-12-14'),
(1, 'U003', 0, '2024-12-14'),
(1, 'U004', 0, '2024-12-15'),
(1, 'U005', 0, '2024-12-15'),
(1, 'U006', 0, '2024-12-16'),
(1, 'U007', 0, '2024-12-16'),
(1, 'U008', 0, '2024-12-17'),
(1, 'U009', 0, '2024-12-17'),
(1, 'U010', 0, '2024-12-18');

-- Nhóm 2: Anime Lovers
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(2, 'U008', 1, '2024-12-13'),
(2, 'U011', 0, '2024-12-14'),
(2, 'U012', 0, '2024-12-14'),
(2, 'U013', 0, '2024-12-15'),
(2, 'U014', 0, '2024-12-15'),
(2, 'U015', 0, '2024-12-16'),
(2, 'U016', 0, '2024-12-16'),
(2, 'U017', 0, '2024-12-17'),
(2, 'U018', 0, '2024-12-17'),
(2, 'U019', 0, '2024-12-18');

-- Nhóm 3: Sports Fans
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(3, 'U002', 1, '2024-11-20'),
(3, 'U020', 0, '2024-11-21'),
(3, 'U021', 0, '2024-11-21'),
(3, 'U022', 0, '2024-11-22'),
(3, 'U023', 0, '2024-11-22'),
(3, 'U024', 0, '2024-11-23'),
(3, 'U025', 0, '2024-11-23'),
(3, 'U026', 0, '2024-11-24'),
(3, 'U027', 0, '2024-11-24'),
(3, 'U028', 0, '2024-11-25');

-- Nhóm 4: Movie Buffs
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(4, 'U004', 1, '2024-10-15'),
(4, 'U029', 0, '2024-10-16'),
(4, 'U030', 0, '2024-10-16'),
(4, 'U031', 0, '2024-10-17'),
(4, 'U032', 0, '2024-10-17'),
(4, 'U033', 0, '2024-10-18'),
(4, 'U034', 0, '2024-10-18'),
(4, 'U035', 0, '2024-10-19'),
(4, 'U036', 0, '2024-10-19'),
(4, 'U037', 0, '2024-10-20');

-- Nhóm 5: Book Club
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(5, 'U005', 1, '2024-09-10'),
(5, 'U038', 0, '2024-09-11'),
(5, 'U039', 0, '2024-09-11'),
(5, 'U040', 0, '2024-09-12'),
(5, 'U041', 0, '2024-09-12'),
(5, 'U042', 0, '2024-09-13'),
(5, 'U043', 0, '2024-09-13'),
(5, 'U044', 0, '2024-09-14'),
(5, 'U045', 0, '2024-09-14'),
(5, 'U046', 0, '2024-09-15');

-- Nhóm 6: Gamers Unite
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(6, 'U006', 1, '2024-08-25'),
(6, 'U047', 0, '2024-08-26'),
(6, 'U048', 0, '2024-08-26'),
(6, 'U049', 0, '2024-08-27'),
(6, 'U050', 0, '2024-08-27'),
(6, 'U001', 0, '2024-08-28'),
(6, 'U002', 0, '2024-08-28'),
(6, 'U003', 0, '2024-08-29'),
(6, 'U004', 0, '2024-08-29'),
(6, 'U005', 0, '2024-08-30');

-- Nhóm 7: Music Lovers
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(7, 'U007', 1, '2024-07-30'),
(7, 'U006', 0, '2024-07-31'),
(7, 'U005', 0, '2024-07-31'),
(7, 'U004', 0, '2024-08-01'),
(7, 'U003', 0, '2024-08-01'),
(7, 'U002', 0, '2024-08-02'),
(7, 'U001', 0, '2024-08-02'),
(7, 'U050', 0, '2024-08-03'),
(7, 'U049', 0, '2024-08-03'),
(7, 'U048', 0, '2024-08-04');

-- Nhóm 8: Foodies
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(8, 'U008', 1, '2024-06-15'),
(8, 'U007', 0, '2024-06-16'),
(8, 'U006', 0, '2024-06-16'),
(8, 'U005', 0, '2024-06-17'),
(8, 'U004', 0, '2024-06-17'),
(8, 'U003', 0, '2024-06-18'),
(8, 'U002', 0, '2024-06-18'),
(8, 'U001', 0, '2024-06-19'),
(8, 'U050', 0, '2024-06-19'),
(8, 'U049', 0, '2024-06-20');

-- Nhóm 9: Travel Enthusiasts
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(9, 'U009', 1, '2024-05-10'),
(9, 'U008', 0, '2024-05-11'),
(9, 'U007', 0, '2024-05-11'),
(9, 'U006', 0, '2024-05-12'),
(9, 'U005', 0, '2024-05-12'),
(9, 'U004', 0, '2024-05-13'),
(9, 'U003', 0, '2024-05-13'),
(9, 'U002', 0, '2024-05-14'),
(9, 'U001', 0, '2024-05-14'),
(9, 'U050', 0, '2024-05-15');

-- Nhóm 10: Fitness Group
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(10, 'U010', 1, '2024-04-05'),
(10, 'U009', 0, '2024-04-06'),
(10, 'U008', 0, '2024-04-06'),
(10, 'U007', 0, '2024-04-07'),
(10, 'U006', 0, '2024-04-07'),
(10, 'U005', 0, '2024-04-08'),
(10, 'U004', 0, '2024-04-08'),
(10, 'U003', 0, '2024-04-09'),
(10, 'U002', 0, '2024-04-09'),
(10, 'U001', 0, '2024-04-10');


-- Tạo dữ liệu Conversation kiểu 'friend'
INSERT INTO Conversations (conversationID, userID1, userID2, groupID, lastMessageTime, status, conversationType)
VALUES
    ('1', 'U008', 'U001', NULL, NOW(), TRUE, 'friend'),
    ('2', 'U008', 'U002', NULL, NOW(), TRUE, 'friend'),
    ('3', 'U008', 'U003', NULL, NOW(), TRUE, 'friend');

-- Tạo dữ liệu Conversation kiểu 'group'
INSERT INTO Conversations (conversationID, userID1, userID2, groupID, lastMessageTime, status, conversationType)
VALUES
    ('4', 'U008', NULL, 2, NOW(), TRUE, 'group'),
    ('5', 'U008', NULL, 1, NOW(), TRUE, 'group');
    
-- Tạo dữ liệu Conversation kiểu 'stranger' giữa các người dùng
INSERT INTO Conversations (conversationID, userID1, userID2, groupID, lastMessageTime, status, conversationType)
VALUES
    ('6', 'U008', 'U005', NULL, NOW(), TRUE, 'stranger'),
    ('7', 'U008', 'U006', NULL, NOW(), TRUE, 'stranger');

-- test chat
-- Chèn dữ liệu cho conversationID '1' (U008 và U001)
INSERT INTO Chat (chatID, senderID, receiverID, groupID, message, timeSend, isRead, conversationID)
VALUES
    (1, 'U008', 'U001', NULL, 'Hi, how are you?', '2024-01-05 08:00:00', TRUE, '1'),
    (2, 'U001', 'U008', NULL, 'I am good, thank you!', '2024-01-05 08:30:00', TRUE, '1'),
    (3, 'U008', 'U001', NULL, 'What have you been up to?', '2024-02-10 09:00:00', TRUE, '1'),
    (4, 'U001', 'U008', NULL, 'Just working on some projects.', '2024-02-10 09:30:00', TRUE, '1'),
    (5, 'U008', 'U001', NULL, 'Sounds interesting!', '2024-03-15 10:00:00', TRUE, '1'),
    (6, 'U001', 'U008', NULL, 'Yeah, it’s quite exciting.', '2024-03-15 10:30:00', TRUE, '1'),
    (7, 'U008', 'U001', NULL, 'We should catch up soon.', '2024-04-20 11:00:00', TRUE, '1'),
    (8, 'U001', 'U008', NULL, 'Definitely!', '2024-04-20 11:30:00', TRUE, '1'),
    (9, 'U008', 'U001', NULL, 'Let’s plan for this weekend.', '2024-05-10 12:00:00', TRUE, '1'),
    (10, 'U001', 'U008', NULL, 'I’m free on Saturday afternoon.', '2024-05-10 12:30:00', TRUE, '1'),
    (11, 'U008', 'U001', NULL, 'Great! Let’s meet up then.', '2024-06-20 13:00:00', TRUE, '1'),
    (12, 'U001', 'U008', NULL, 'Looking forward to it.', '2024-06-20 13:30:00', TRUE, '1'),
    (13, 'U008', 'U001', NULL, 'Me too, it’s been too long!', '2024-07-25 14:00:00', TRUE, '1'),
    (14, 'U001', 'U008', NULL, 'Agreed! See you soon.', '2024-07-25 14:30:00', TRUE, '1'),
    (15, 'U008', 'U001', NULL, 'See you!', '2024-08-10 15:00:00', TRUE, '1'),
    (16, 'U001', 'U008', NULL, 'Take care!', '2024-08-10 15:30:00', TRUE, '1'),
    (17, 'U008', 'U001', NULL, 'Talk to you later.', '2024-09-20 16:00:00', TRUE, '1'),
    (18, 'U001', 'U008', NULL, 'Yes, see you soon!', '2024-09-20 16:30:00', TRUE, '1'),
    (19, 'U008', 'U001', NULL, 'Take care, buddy.', '2024-10-15 17:00:00', TRUE, '1'),
    (20, 'U001', 'U008', NULL, 'You too!', '2024-10-15 17:30:00', TRUE, '1');

-- Chèn dữ liệu cho conversationID '6' (U008 và U005)
INSERT INTO Chat (chatID, senderID, receiverID, groupID, message, timeSend, isRead, conversationID)
VALUES
    (21, 'U008', 'U005', NULL, 'Hi, how are you?', '2024-01-20 08:00:00', TRUE, '6'),
    (22, 'U005', 'U008', NULL, 'I am good, thank you!', '2024-01-20 08:30:00', TRUE, '6'),
    (23, 'U008', 'U005', NULL, 'What have you been up to?', '2024-03-05 09:00:00', TRUE, '6'),
    (24, 'U005', 'U008', NULL, 'Just working on some projects.', '2024-03-05 09:30:00', TRUE, '6'),
    (25, 'U008', 'U005', NULL, 'Sounds interesting!', '2024-05-15 10:00:00', TRUE, '6'),
    (26, 'U005', 'U008', NULL, 'Yeah, it’s quite exciting.', '2024-05-15 10:30:00', TRUE, '6'),
    (27, 'U008', 'U005', NULL, 'We should catch up soon.', '2024-07-01 11:00:00', TRUE, '6'),
    (28, 'U005', 'U008', NULL, 'Definitely!', '2024-07-01 11:30:00', TRUE, '6'),
    (29, 'U008', 'U005', NULL, 'Let’s plan for this weekend.', '2024-08-18 12:00:00', TRUE, '6'),
    (30, 'U005', 'U008', NULL, 'I’m free on Saturday afternoon.', '2024-08-18 12:30:00', TRUE, '6'),
    (31, 'U008', 'U005', NULL, 'Great! Let’s meet up then.', '2024-10-25 13:00:00', TRUE, '6'),
    (32, 'U005', 'U008', NULL, 'Looking forward to it.', '2024-10-25 13:30:00', TRUE, '6'),
    (33, 'U008', 'U005', NULL, 'Me too, it’s been too long!', '2024-11-10 14:00:00', TRUE, '6'),
    (34, 'U005', 'U008', NULL, 'Agreed! See you soon.', '2024-11-10 14:30:00', TRUE, '6'),
    (35, 'U008', 'U005', NULL, 'See you!', '2024-12-05 15:00:00', TRUE, '6'),
    (36, 'U005', 'U008', NULL, 'Take care!', '2024-12-05 15:30:00', TRUE, '6'),
    (37, 'U008', 'U005', NULL, 'Talk to you later.', '2024-12-20 16:00:00', TRUE, '6'),
    (38, 'U005', 'U008', NULL, 'Yes, see you soon!', '2024-12-20 16:30:00', TRUE, '6'),
    (39, 'U008', 'U005', NULL, 'Take care, buddy.', '2024-12-20 17:00:00', TRUE, '6'),
    (40, 'U005', 'U008', NULL, 'You too!', '2024-12-20 17:30:00', TRUE, '6');

-- Chèn dữ liệu cho conversationID '4' (Group 1)
INSERT INTO Chat (chatID, senderID, receiverID, groupID, message, timeSend, isRead, conversationID)
VALUES
    (41, 'U001', NULL, 1, 'Hello U002, how are you?', '2024-01-01 08:00:00', TRUE, '4'),
    (42, 'U002', NULL, 1, 'I am fine, thanks for asking!', '2024-01-01 08:05:00', TRUE, '4'),
    (43, 'U003', NULL, 1, 'Good to hear! What’s up U008?', '2024-02-01 08:10:00', TRUE, '4'),
    (44, 'U008', NULL, 1, 'I am just relaxing, what about you?', '2024-02-01 08:15:00', TRUE, '4'),
    (45, 'U001', NULL, 1, 'Nothing much, just chatting here.', '2024-03-01 08:20:00', TRUE, '4'),
    (46, 'U003', NULL, 1, 'Great, any plans for the weekend?', '2024-03-01 08:25:00', TRUE, '4'),
    (47, 'U008', NULL, 1, 'Planning to catch up with some friends.', '2024-04-01 08:30:00', TRUE, '4'),
    (48, 'U001', NULL, 1, 'That sounds fun! Let me know if you need company.', '2024-04-01 08:35:00', TRUE, '4'),
    (49, 'U002', NULL, 1, 'Sure! We’ll plan something soon.', '2024-05-01 08:40:00', TRUE, '4'),
    (50, 'U003', NULL, 1, 'Looking forward to it! Anyone else joining?', '2024-05-01 08:45:00', TRUE, '4');