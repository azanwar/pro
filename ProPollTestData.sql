DROP DATABASE IF EXISTS ProPollData;
CREATE DATABASE ProPollData;
USE ProPollData;
CREATE TABLE Users (
	userID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    pw VARCHAR(20) NOT NULL
);

INSERT INTO Users (username,pw)
	VALUES ('Barack','Obama'),
		   ('GeorgeW', 'Bush'),
		   ('Donald','Trump');
           
CREATE TABLE Polls (
	pollID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pollName VARCHAR(20) NOT NULL,
    creatorID INT(11) NOT NULL, 
    private BIT(1) NOT NULL,
    active BIT(1) NOT NULL,
	FOREIGN KEY fk1(creatorID) REFERENCES Users(userID)
);

INSERT INTO Polls (pollName,creatorID,private,active)
	values ('Sample Public Poll',1,0,1),
		   ('poll 2',2,0,1);

CREATE TABLE AllowedUsers (
	allowedUserID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userID INT(11) NOT NULL,
    pollID INT(11) NOT NULL,
    FOREIGN KEY fk2(userID) REFERENCES Users(userID),
    FOREIGN KEY fk3(pollID) REFERENCES Polls(pollID)
);

CREATE TABLE Questions (
	questionID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    pollID INT(11) NOT NULL,
    question VARCHAR(50) NOT NULL,
	FOREIGN KEY fk4(pollID) REFERENCES Polls(pollID)
);

INSERT INTO Questions (pollID,question)
	VALUES (1,"What is your favorite CS Class?"),
	       (2,"What is your favorite color?");

CREATE TABLE Responses (
	responseID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    questionID INT(11) NOT NULL,
    response VARCHAR(40) NOT NULL,
    num INT(11) NOT NULL,
	FOREIGN KEY fk5(questionID) REFERENCES Questions(questionID)
);

INSERT INTO Responses (questionID,response,num)
	VALUES (1,"CS 103",0),
           (1,"CS 104",0),
           (1,"CS 170",1),
           (1,"CS 201",0),
           (2,"Red",1),
           (2,"Blue",1),
           (2,"Green",0),
           (2,"Yellow",0);
           

CREATE TABLE UserResponses (
	userResponseID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userID INT(11) NOT NULL,
    questionID INT(11) NOT NULL,
    responseID INT(11) NOT NULL,
    pollID INT(11) NOT NULL,
    FOREIGN KEY fk6(userID) REFERENCES Users(userID),
	FOREIGN KEY fk11(pollID) REFERENCES Polls(pollID),
    FOREIGN KEY fk7(questionID) REFERENCES Questions(questionID),
    FOREIGN KEY fk8(responseID) REFERENCES Responses(responseID)
);

INSERT INTO UserResponses (userID,questionID,responseID,pollID)
	VALUES (1,1,3,1),
	       (1,2,5,2),	
           (2,2,6,2);
	

CREATE TABLE UserPolls (
	userPollID INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userID INT(11) NOT NULL,
    pollID INT(11) NOT NULL,
    FOREIGN KEY fk9(userID) REFERENCES Users(userID),
    FOREIGN KEY fk10(pollID) REFERENCES Polls(pollID)
);

INSERT INTO UserPolls (userID, pollID)
	VALUE (1,1),
          (1,2),
          (2,2);
    

