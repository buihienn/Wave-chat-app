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
    gender VARCHAR(6),
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
('U001', 'hienbui', 'password123', 'Bui Hien', '123 Nguyen Trai, District 1', '1990-01-01', 'male', 'hien.bui@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U002', 'vinhphu', 'securePass456', 'Phu Vinh', '456 Le Loi, District 5', '1992-03-15', 'male', 'vinh.phu@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U003', 'quangminh', 'easyPass789', 'Quang Minh', '78 Hai Ba Trung, District 3', '1993-07-22', 'male', 'minh.quang@example.com', '2024-11-26', TRUE, TRUE, FALSE),
('U004', 'lannguyen', 'password321', 'Nguyen Lan', '102 Tran Hung Dao, District 1', '1994-09-12', 'female', 'lan.nguyen@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U005', 'hoaviet', 'pass789456', 'Viet Hoa', '203 Vo Van Kiet, District 6', '1995-11-02', 'female', 'hoa.viet@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U006', 'ngocanh', 'ngocanh@123', 'Ngoc Anh', '56 Cach Mang Thang 8, District 10', NULL, 'male', 'ngoc.anh@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U007', 'admin1', 'admin@pass', 'Admin User', '1 Ton Duc Thang, District 1', '1985-05-05', 'male', 'admin@example.com', '2024-10-20', TRUE, FALSE, TRUE);