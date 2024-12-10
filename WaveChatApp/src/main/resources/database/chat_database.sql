-- Check if the database exists and drop it if it does
DROP DATABASE IF EXISTS CHATAPPLICATION;

-- Create the database
CREATE DATABASE CHATAPPLICATION;

-- Use the newly created database
USE CHATAPPLICATION;


CREATE TABLE User (
	userID CHAR(5),
    userName VARCHAR(50),
    passWord VARCHAR(50),
    fullName NVARCHAR(50),
    address NVARCHAR(50),
    birthDay DATE,
    gender VARCHAR(6),
    email VARCHAR (50),
    createdDate DATE,
    status BOOL,
    onlineStatus BOOL,
    totalFriend INT ,

    CONSTRAINT PK_USER PRIMARY KEY (userID)
);

CREATE TABLE AdminApp (
	userID CHAR(5),
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
ALTER TABLE Friends 
ADD CONSTRAINT FK_Friends_User1 FOREIGN KEY (userID1) REFERENCES User(userID),
ADD CONSTRAINT FK_Friends_User2 FOREIGN KEY (userID2) REFERENCES User(userID);

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

ALTER TABLE AdminApp
ADD CONSTRAINT FK_AdminApp_User FOREIGN KEY (userID) REFERENCES User(userID);


-- Add 

INSERT INTO User (userID, userName, passWord, fullName, address, birthDay, gender, email, createdDate, status, onlineStatus, totalFriend)
VALUES 
('U001', 'buihien', '123', 'B H', '123 Main St', '1990-01-01', 'male', 'bh@example.com', '2024-11-26', TRUE, TRUE, 2),
('U002', 'phuvinh', '123456', 'P Vinh', '456 Elm St', '1992-02-02', 'male', 'pv@example.com', '2024-11-26', TRUE, FALSE, 2),
('U003', 'someone', '123222', 'S one', '456 Elm St', '1992-02-02', 'male', 'pv@example.com', '2024-11-26', TRUE, TRUE, 2),
('U004', 'another', '123', 'Ano ther', '456 Elm St', '1992-02-02', 'female', 'pv@example.com', '2024-11-26', TRUE, FALSE, 0),
('U005', 'other', '123', 'Mor than', '456 Elm St', '1992-02-02', 'female', 'pv@example.com', '2024-11-26', TRUE, FALSE, 1),
('U006', 'others', '123', 'Other s', '456 Elm St', '1992-02-02', 'male', 'pv@example.com', '2024-11-26', TRUE, FALSE, 0);


INSERT INTO AdminApp (userID)
VALUES ('U001');


INSERT INTO UserOnline (userID, lastSeen)
VALUES ('U001', '2024-11-26 10:00:00');

INSERT INTO UserOffline (userID, offlineTime, duration)
VALUES ('U001', '2024-11-26 12:00:00', 120);

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

