
-- Table Schema For the Bank Database
-- Deleting the previous database if exist
drop database if exists Bank;
CREATE DATABASE Bank;

-- Using the new Database Bank
USE Bank;

-- Creating the table Contact Info
CREATE TABLE contact_info(
	contactId INT NOT NULL AUTO_INCREMENT,
	streetAdd VARCHAR(400),
	city VARCHAR(50) NOT NULL,
	state VARCHAR(50) NOT NULL,
	country VARCHAR(50) NOT NULL,
	postalCode INT NOT NULL,
	phone VARCHAR(15),
	mobile VARCHAR(15),
	email VARCHAR(50),
	PRIMARY KEY (contactId)
);

-- Creating the table Account Holder
CREATE TABLE account_holder(
	personID INT NOT NULL AUTO_INCREMENT,
	panNumber VARCHAR(15) NOT NULL UNIQUE,
	nameF VARCHAR(100)  NOT NULL,
	nameL VARCHAR(100)  NOT NULL,
	dob VARCHAR(10),
	contactId INT ,
	PRIMARY KEY (personID),
	FOREIGN KEY (contactId) REFERENCES contact_info(contactId)
	ON DELETE SET NULL ON UPDATE CASCADE
);

-- Creating the table Bank Account
CREATE TABLE bank_account(
	accountID INT NOT NULL AUTO_INCREMENT,
	openingDate VARCHAR(10) NOT NULL,
	closingDate VARCHAR(10),
	status VARCHAR(20),
	accType VARCHAR(20),
	cuurBalance NUMERIC(15,3),
	lastTasnsaction VARCHAR(10),
	accHolder INT,
	PRIMARY KEY (accountID),
	FOREIGN KEY (accHolder) REFERENCES account_holder(personID)
	ON DELETE SET NULL
);

-- Creating the table Transaction
CREATE TABLE transaction (
	transactionID INT NOT NULL AUTO_INCREMENT,
	type VARCHAR(50),
	tDate DATE,
	amount NUMERIC(15,3) NOT NULL,
	accountIDTo INT,
	accountIDFrom INT,
	category VARCHAR(50),
	remarks TEXT,
	PRIMARY KEY (transactionID),
	FOREIGN KEY (accountIDFrom) REFERENCES bank_account(accountID),
	FOREIGN KEY (accountIDTo) REFERENCES bank_account(accountID)
);