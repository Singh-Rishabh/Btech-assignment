
-- Table Schema For the Bank Database
-- Deleting the previous database if exist
drop database if exists Team;
CREATE DATABASE Team;

-- Using the new Database Bank
USE Team;

-- Creating the table Contact Info
CREATE TABLE contact_info(
	contactID INT NOT NULL AUTO_INCREMENT,
	streetAdd VARCHAR(400),
	city VARCHAR(50) NOT NULL,
	state VARCHAR(50) NOT NULL,
	country VARCHAR(50) NOT NULL,
	postalCode INT NOT NULL,
	phone VARCHAR(15),
	mobile VARCHAR(15),
	email	 VARCHAR(50),
	PRIMARY KEY (contactID)
);

-- Creating the table Account Holder
CREATE TABLE person(
	personID INT NOT NULL AUTO_INCREMENT,
	nameF VARCHAR(100)  NOT NULL,
	nameL VARCHAR(100)  NOT NULL,
	dob VARCHAR(10),
	contactId INT ,
	PRIMARY KEY (personID),
	FOREIGN KEY (contactId) REFERENCES contact_info(contactID)
	ON DELETE SET NULL ON UPDATE CASCADE
);

-- Creating the table Bank Account
CREATE TABLE team(
	teamID INT NOT NULL AUTO_INCREMENT,
	creationDate VARCHAR(10),
	status VARCHAR(10),
	name VARCHAR(50),
	officeID INT,
	PRIMARY KEY (teamID),
	FOREIGN KEY (officeID) REFERENCES contact_info(contactID)
	ON DELETE SET NULL
);

-- Creating the table Transaction
CREATE TABLE team_member (
	memberID INT NOT NULL AUTO_INCREMENT,
	teamID INT,
	personID INT,
	salary NUMERIC(15,3),
	hireDate VARCHAR(10),
	role VARCHAR(20),
	remarks TEXT,
	PRIMARY KEY (memberID),
	FOREIGN KEY (teamID) REFERENCES team(teamID),
	FOREIGN KEY (personID) REFERENCES person(personID)
);