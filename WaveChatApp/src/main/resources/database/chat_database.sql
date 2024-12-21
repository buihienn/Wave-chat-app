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


CREATE TABLE UserOnline (
	userID CHAR(5),
    lastSeen DATETIME,
    
    CONSTRAINT PK_USERONLINE PRIMARY KEY (userID)
);

CREATE TABLE UserOffline (
	userID CHAR(5),
    offlineTime DATETIME,
    duration INT,
    CONSTRAINT PK_USEROFFLINE PRIMARY KEY (userID, offlineTime)
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
	id INT,
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
	groupID INT,
    groupName NVARCHAR(50),
    createdBy CHAR(5),
    createdAt DATETIME,
    onlineStatus BOOL,
    
	CONSTRAINT PK_GROUPCHAT PRIMARY KEY (groupID)
);

CREATE TABLE GroupMembers (
    groupID INT,
    userID CHAR(5),
    isAdmin BOOL,
    joinedDate DATETIME,
    
    CONSTRAINT PK_GROUPMEMBER PRIMARY KEY (groupID, userID)
);

CREATE TABLE SpamReport (
	reportID INT,
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

-- UserOnline
ALTER TABLE UserOnline 
ADD CONSTRAINT FK_UserOnline_User FOREIGN KEY (userID) REFERENCES User(userID);

-- UserOffline
ALTER TABLE UserOffline 
ADD CONSTRAINT FK_UserOffline_User FOREIGN KEY (userID) REFERENCES User(userID);

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
INSERT INTO User (userID, userName, passWord, fullName, address, birthDay, gender, email, createdDate, status, onlineStatus, isAdmin)
VALUES 
('U001', 'hienbui', '$2a$12$EZW2K.vlr6PTMd3eBxVtWur0ltNSVF6viSxWHPjr8DgJYmyOpbuQq', 'Bui Hien', '123 Nguyen Trai, District 1', '1990-01-01', 'male', 'hien.bui@example.com', '2024-11-26', TRUE, FALSE, FALSE), -- Buihien123
('U002', 'vinhphu', 'securePass456', 'Phu Vinh', '456 Le Loi, District 5', '1992-03-15', 'male', 'vinh.phu@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U003', 'quangminh', 'easyPass789', 'Quang Minh', '78 Hai Ba Trung, District 3', '1993-07-22', 'male', 'minh.quang@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U004', 'lannguyen', 'password321', 'Nguyen Lan', '102 Tran Hung Dao, District 1', '1994-09-12', 'female', 'lan.nguyen@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U005', 'hoaviet', 'pass789456', 'Viet Hoa', '203 Vo Van Kiet, District 6', '1995-11-02', 'female', 'hoa.viet@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U006', 'ngocanh', 'ngocanh@123', 'Ngoc Anh', '56 Cach Mang Thang 8, District 10', NULL, 'male', 'ngoc.anh@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U007', 'admin1', '$2a$12$f.FHP.EzG7M3yY3qNs7vPu.zE8x52uPY226TGBWi83kSb6LFKnkGK', 'Ho Hien', '1 Ton Duc Thang, District 1', '1985-05-05', 'female', 'admin@example.com', '2024-10-20', TRUE, FALSE, TRUE), -- Admin123
('U008', 'vinhhho', '$2a$12$7m52NAE1HSUkwO6vXLstKelNLBDjMkCmjBdMx1bA1vH/570C25kzS', 'Ho Phu Vinh', '1 Le Van Luong, District 7', '2004-05-05', 'male', 'vinhho2507@gmail.com', '2024-10-20', TRUE, FALSE, FALSE), -- Phuvinh123
('U009', 'thaison', '$2a$12$QiphfSLtIb2eQYORRuP6cup8V3E7QLlRtKUxHIh/sNHmKd/O892qO', 'Thai Son', '456 Le Loi, District 5', '1992-03-15', 'male', 'thaisonn8178@gmail.com', '2024-11-26', TRUE, FALSE, FALSE), -- Thaison123
('U010', 'baohuy', '$2a$12$BzO5cPjYhMm4n2RABAboeeC/s8c/IRyqBr6Cc852NXwcApYzP4Rqq', 'Bao Huy', '78 Hai Ba Trung, District 3', '1993-07-22', 'male', 'phbhuy22@clc.fitus.edu.vn', '2024-11-26', TRUE, FALSE, FALSE), -- Baohuy123
('U011', 'diuhuyen', '$2a$12$ilmcULu8ym.Lb5rQYy793OWkaU0y55JC0jRZSrFhtRzab8zrKW70m', 'Diu Huyen', '102 Tran Hung Dao, District 1', '1994-09-12', 'female', 'tdhuyen22@clc.fitus.edu.vn', '2024-11-26', TRUE, FALSE, FALSE); -- Diuhuyen123

-- Thêm yêu cầu kết bạn cho U008
INSERT INTO Friend_requests (requester_userID, requested_userID, status, createdAt)
VALUES 
('U001', 'U008', 'pending', '2024-12-01'),
('U002', 'U008', 'pending', '2024-12-02'),
('U003', 'U008', 'pending', '2024-12-04'),
('U004', 'U008', 'pending', '2024-12-11'),
('U009', 'U008', 'accepted', '2024-12-02'),
('U010', 'U008', 'accepted', '2024-12-04'),
('U011', 'U008', 'accepted', '2024-12-11');

-- Thêm bạn bè cho U008
INSERT INTO Friends (userID1, userID2, createdAt)
VALUES
('U009', 'U008','2024-12-02'),
('U010', 'U008', '2024-12-04'),
('U011', 'U008', '2024-12-11');

-- Tạo Group chat
INSERT INTO GroupChat (groupID, groupName, createdBy, createdAt, onlineStatus)
VALUES 
(1, 'Tech Enthusiasts', 'U001', '2024-12-13', 0),
(2, 'Anime Lovers', 'U003', '2024-12-13', 0);

-- Thêm các thành viên khác
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(1, 'U001', 0, '2024-12-13'), 
(1, 'U002', 0, '2024-12-13'),
(1, 'U003', 0, '2024-12-13'), 
(1, 'U008', 1, '2024-12-13');  

-- Thêm các thành viên khác
INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES 
(2, 'U005', 0, '2024-12-13'), 
(2, 'U006', 0, '2024-12-13'),
(2, 'U007', 0, '2024-12-13'), 
(2, 'U008', 1, '2024-12-13');  

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
    (1, 'U008', 'U001', NULL, 'Hi, how are you?', '2024-12-10 08:00:00', TRUE, '1'),
    (2, 'U001', 'U008', NULL, 'I am good, thank you!', '2024-12-10 08:30:00', TRUE, '1'),
    (3, 'U008', 'U001', NULL, 'What have you been up to?', '2024-12-10 09:00:00', TRUE, '1'),
    (4, 'U001', 'U008', NULL, 'Just working on some projects.', '2024-12-10 09:30:00', TRUE, '1'),
    (5, 'U008', 'U001', NULL, 'Sounds interesting!', '2024-12-10 10:00:00', TRUE, '1'),
    (6, 'U001', 'U008', NULL, 'Yeah, it’s quite exciting.', '2024-12-10 10:30:00', TRUE, '1'),
    (7, 'U008', 'U001', NULL, 'We should catch up soon.', '2024-12-10 11:00:00', TRUE, '1'),
    (8, 'U001', 'U008', NULL, 'Definitely!', '2024-12-10 11:30:00', TRUE, '1'),
    (9, 'U008', 'U001', NULL, 'Let’s plan for this weekend.', '2024-12-10 12:00:00', TRUE, '1'),
    (10, 'U001', 'U008', NULL, 'I’m free on Saturday afternoon.', '2024-12-10 12:30:00', TRUE, '1'),
    (11, 'U008', 'U001', NULL, 'Great! Let’s meet up then.', '2024-12-10 13:00:00', TRUE, '1'),
    (12, 'U001', 'U008', NULL, 'Looking forward to it.', '2024-12-10 13:30:00', TRUE, '1'),
    (13, 'U008', 'U001', NULL, 'Me too, it’s been too long!', '2024-12-10 14:00:00', TRUE, '1'),
    (14, 'U001', 'U008', NULL, 'Agreed! See you soon.', '2024-12-10 14:30:00', TRUE, '1'),
    (15, 'U008', 'U001', NULL, 'See you!', '2024-12-10 15:00:00', TRUE, '1'),
    (16, 'U001', 'U008', NULL, 'Take care!', '2024-12-10 15:30:00', TRUE, '1'),
    (17, 'U008', 'U001', NULL, 'Talk to you later.', '2024-12-10 16:00:00', TRUE, '1'),
    (18, 'U001', 'U008', NULL, 'Yes, see you soon!', '2024-12-10 16:30:00', TRUE, '1'),
    (19, 'U008', 'U001', NULL, 'Take care, buddy.', '2024-12-10 17:00:00', TRUE, '1'),
    (20, 'U001', 'U008', NULL, 'You too!', '2024-12-10 17:30:00', TRUE, '1');

-- Chèn dữ liệu cho conversationID '6' (U008 và U005)
INSERT INTO Chat (chatID, senderID, receiverID, groupID, message, timeSend, isRead, conversationID)
VALUES
    (21, 'U008', 'U005', NULL, 'Hi, how are you?', '2024-12-10 08:00:00', TRUE, '6'),
    (22, 'U005', 'U008', NULL, 'I am good, thank you!', '2024-12-10 08:30:00', TRUE, '6'),
    (23, 'U008', 'U005', NULL, 'What have you been up to?', '2024-12-10 09:00:00', TRUE, '6'),
    (24, 'U005', 'U008', NULL, 'Just working on some projects.', '2024-12-10 09:30:00', TRUE, '6'),
    (25, 'U008', 'U005', NULL, 'Sounds interesting!', '2024-12-10 10:00:00', TRUE, '6'),
    (26, 'U005', 'U008', NULL, 'Yeah, it’s quite exciting.', '2024-12-10 10:30:00', TRUE, '6'),
    (27, 'U008', 'U005', NULL, 'We should catch up soon.', '2024-12-10 11:00:00', TRUE, '6'),
    (28, 'U005', 'U008', NULL, 'Definitely!', '2024-12-10 11:30:00', TRUE, '6'),
    (29, 'U008', 'U005', NULL, 'Let’s plan for this weekend.', '2024-12-10 12:00:00', TRUE, '6'),
    (30, 'U005', 'U008', NULL, 'I’m free on Saturday afternoon.', '2024-12-10 12:30:00', TRUE, '6'),
    (31, 'U008', 'U005', NULL, 'Great! Let’s meet up then.', '2024-12-10 13:00:00', TRUE, '6'),
    (32, 'U005', 'U008', NULL, 'Looking forward to it.', '2024-12-10 13:30:00', TRUE, '6'),
    (33, 'U008', 'U005', NULL, 'Me too, it’s been too long!', '2024-12-10 14:00:00', TRUE, '6'),
    (34, 'U005', 'U008', NULL, 'Agreed! See you soon.', '2024-12-10 14:30:00', TRUE, '6'),
    (35, 'U008', 'U005', NULL, 'See you!', '2024-12-10 15:00:00', TRUE, '6'),
    (36, 'U005', 'U008', NULL, 'Take care!', '2024-12-10 15:30:00', TRUE, '6'),
    (37, 'U008', 'U005', NULL, 'Talk to you later.', '2024-12-10 16:00:00', TRUE, '6'),
    (38, 'U005', 'U008', NULL, 'Yes, see you soon!', '2024-12-10 16:30:00', TRUE, '6'),
    (39, 'U008', 'U005', NULL, 'Take care, buddy.', '2024-12-10 17:00:00', TRUE, '6'),
    (40, 'U005', 'U008', NULL, 'You too!', '2024-12-10 17:30:00', TRUE, '6');

-- Chèn dữ liệu cho conversationID '4' (Group 1)
INSERT INTO Chat (chatID, senderID, receiverID, groupID, message, timeSend, isRead, conversationID)
VALUES
    (41, 'U001', NULL, 1, 'Hello U002, how are you?', '2024-12-10 08:00:00', TRUE, '4'),
    (42, 'U002', NULL, 1, 'I am fine, thanks for asking!', '2024-12-10 08:05:00', TRUE, '4'),
    (43, 'U003', NULL, 1, 'Good to hear! What’s up U008?', '2024-12-10 08:10:00', TRUE, '4'),
    (44, 'U008', NULL, 1, 'I am just relaxing, what about you?', '2024-12-10 08:15:00', TRUE, '4'),
    (45, 'U001', NULL, 1, 'Nothing much, just chatting here.', '2024-12-10 08:20:00', TRUE, '4'),
    (46, 'U003', NULL, 1, 'Great, any plans for the weekend?', '2024-12-10 08:25:00', TRUE, '4'),
    (47, 'U008', NULL, 1, 'Planning to catch up with some friends.', '2024-12-10 08:30:00', TRUE, '4'),
    (48, 'U001', NULL, 1, 'That sounds fun! Let me know if you need company.', '2024-12-10 08:35:00', TRUE, '4'),
    (49, 'U002', NULL, 1, 'Sure! We’ll plan something soon.', '2024-12-10 08:40:00', TRUE, '4'),
    (50, 'U003', NULL, 1, 'Looking forward to it! Anyone else joining?', '2024-12-10 08:45:00', TRUE, '4'),
    (51, 'U008', NULL, 1, 'I’ll check with some other friends too.', '2024-12-10 08:50:00', TRUE, '4'),
    (52, 'U001', NULL, 1, 'Sounds good. Keep me updated.', '2024-12-10 08:55:00', TRUE, '4'),
    (53, 'U002', NULL, 1, 'Will do! Let’s make it happen!', '2024-12-10 09:00:00', TRUE, '4'),
    (54, 'U003', NULL, 1, 'Awesome! Can’t wait!', '2024-12-10 09:05:00', TRUE, '4'),
    (55, 'U008', NULL, 1, 'Looking forward to a fun weekend!', '2024-12-10 09:10:00', TRUE, '4'),
    (56, 'U001', NULL, 1, 'Same here! Catch you later.', '2024-12-10 09:15:00', TRUE, '4'),
    (57, 'U003', NULL, 1, 'Have a good one!', '2024-12-10 09:20:00', TRUE, '4'),
    (58, 'U008', NULL, 1, 'You too! Take care!', '2024-12-10 09:25:00', TRUE, '4'),
    (59, 'U001', NULL, 1, 'Talk soon!', '2024-12-10 09:30:00', TRUE, '4'),
    (60, 'U002', NULL, 1, 'Definitely, bye!', '2024-12-10 09:35:00', TRUE, '4');