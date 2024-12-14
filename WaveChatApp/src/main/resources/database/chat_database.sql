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
    CONSTRAINT PK_FRIEND_REQUESTS PRIMARY KEY (requester_userID, requested_userID),
    CONSTRAINT FK_REQUESTER FOREIGN KEY (requester_userID) REFERENCES User(userID),
    CONSTRAINT FK_REQUESTED FOREIGN KEY (requested_userID) REFERENCES User(userID)
);


CREATE TABLE Friends (
    userID1 CHAR(5),
    userID2 CHAR(5),
    createdAt DATE,
    
    CONSTRAINT PK_FRIENDS PRIMARY KEY (userID1, userID2),
    CONSTRAINT FOREIGN KEY (userID1) REFERENCES User(userID),
    CONSTRAINT FOREIGN KEY (userID2) REFERENCES User(userID)
);

CREATE TABLE Blocks (
    userID CHAR(5),             -- thực hiện block
    blocked_userID CHAR(5),     -- bị block
    
    CONSTRAINT PK_BLOCKS PRIMARY KEY (userID, blocked_userID),
    CONSTRAINT FK_BLOCKS_USERS FOREIGN KEY (userID) REFERENCES User(userID),
    CONSTRAINT FK_BLOCKED_USERS FOREIGN KEY (blocked_userID) REFERENCES User(userID)
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

-- UserOnline
ALTER TABLE UserOnline 
ADD CONSTRAINT FK_UserOnline_User FOREIGN KEY (userID) REFERENCES User(userID);

-- UserOffline
ALTER TABLE UserOffline 
ADD CONSTRAINT FK_UserOffline_User FOREIGN KEY (userID) REFERENCES User(userID);

-- Friends
-- ALTER TABLE Friends 
-- ADD CONSTRAINT FK_Friends_User1 FOREIGN KEY (userID1) REFERENCES User(userID),
-- ADD CONSTRAINT FK_Friends_User2 FOREIGN KEY (userID2) REFERENCES User(userID);

-- LoginHistory
ALTER TABLE LoginHistory 
ADD CONSTRAINT FK_LoginHistory_User FOREIGN KEY (userID) REFERENCES User(userID);

-- Chat
ALTER TABLE Chat 
ADD CONSTRAINT FK_Chat_Sender FOREIGN KEY (senderID) REFERENCES User(userID),
ADD CONSTRAINT FK_Chat_Receiver FOREIGN KEY (receiverID) REFERENCES User(userID),
ADD CONSTRAINT FK_Chat_GroupChat FOREIGN KEY (groupID) REFERENCES GroupChat (groupID);

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

-- Add 

INSERT INTO User (userID, userName, passWord, fullName, address, birthDay, gender, email, createdDate, status, onlineStatus, isAdmin)
VALUES 
('U001', 'hienbui', 'password123', 'Bui Hien', '123 Nguyen Trai, District 1', '1990-01-01', 'male', 'hien.bui@example.com', '2024-11-26', TRUE, TRUE, FALSE),
('U002', 'vinhphu', 'securePass456', 'Phu Vinh', '456 Le Loi, District 5', '1992-03-15', 'male', 'vinh.phu@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U003', 'quangminh', 'easyPass789', 'Quang Minh', '78 Hai Ba Trung, District 3', '1993-07-22', 'male', 'minh.quang@example.com', '2024-11-26', TRUE, TRUE, FALSE),
('U004', 'lannguyen', 'password321', 'Nguyen Lan', '102 Tran Hung Dao, District 1', '1994-09-12', 'female', 'lan.nguyen@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U005', 'hoaviet', 'pass789456', 'Viet Hoa', '203 Vo Van Kiet, District 6', '1995-11-02', 'female', 'hoa.viet@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U006', 'ngocanh', 'ngocanh@123', 'Ngoc Anh', '56 Cach Mang Thang 8, District 10', NULL, 'male', 'ngoc.anh@example.com', '2024-11-26', TRUE, FALSE, FALSE),
('U007', 'admin1', 'admin@pass', 'Admin User', '1 Ton Duc Thang, District 1', '1985-05-05', 'male', 'admin@example.com', '2024-10-20', TRUE, TRUE, TRUE);


INSERT INTO UserOnline (userID, lastSeen)
VALUES ('U001', '2024-11-26 10:00:00');

INSERT INTO UserOffline (userID, offlineTime, duration)
VALUES ('U001', '2024-11-26 12:00:00', 120);

INSERT INTO Friend_requests (requester_userID, requested_userID, status, createdAt)
VALUES 
('U001', 'U002', 'accepted', '2024-11-26'),
('U001', 'U003', 'accepted', '2024-11-26'),
('U002', 'U004', 'accepted', '2024-11-26'),
('U003', 'U005', 'accepted', '2024-11-26');

INSERT INTO Friends (userID1, userID2, createdAt)
VALUES 
('U001', 'U002', '2024-11-26'),
('U001', 'U003', '2024-11-26'),
('U002', 'U003', '2024-11-26'),  
('U003', 'U005', '2024-11-26');


INSERT INTO LoginHistory (id, userID, loginTime)
VALUES (1, 'U001', '2024-11-26 09:00:00');

INSERT INTO Chat (chatID, senderID, receiverID, groupID, message, timeSend, isRead)
VALUES (1, 'U001', 'U002', NULL, 'Hello!', '2024-11-26 10:05:00', FALSE);

INSERT INTO GroupChat (groupID, groupName, createdBy, createdAt)
VALUES (1, 'Friends Group', 'U001', '2024-11-26 09:30:00');


INSERT INTO GroupMembers (groupID, userID, isAdmin, joinedDate)
VALUES (1, 'U001', TRUE, '2024-11-26 09:30:00');


INSERT INTO SpamReport (reportID, reporterID, reportedUserId, timeStamp)
VALUES (1, 'U001', 'U002', '2024-11-26 11:00:00');


