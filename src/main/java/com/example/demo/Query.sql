Create database Comp303FinTech;

use Comp303FinTech;
-- Creating the Customer table
use Comp303FinTech;
SHOW COLUMNS FROM Customer;

CREATE TABLE Customer (
    customerId INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    customerName VARCHAR(50) NOT NULL,
    dob DATE,
    address TEXT,
    city VARCHAR(20),
    postalcode VARCHAR(9),
    emailId VARCHAR(50) ,
    phone VARCHAR(10) 
);

-- Creating the AccountType table
use Comp303FinTech;
CREATE TABLE AccountType (
    accountTypeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    accountTypeName VARCHAR(30) NOT NULL,
    accountTypeDesc TEXT,
    minimumBalanceAmount DECIMAL(15,2),
    hasOverDraft BOOLEAN
);

-- Creating the Account table
use Comp303FinTech;
CREATE TABLE Account (
    accountNumber INT PRIMARY KEY AUTO_INCREMENT,
    accountTypeId INT,
    customerId INT,
    balance DECIMAL(15,2),
    overDraftLimit DECIMAL(15,2),
    FOREIGN KEY (accountTypeId) REFERENCES AccountType(accountTypeId),
    FOREIGN KEY (customerId) REFERENCES Customer(customerId)
);

-- Inserting sample records into Customer
INSERT INTO Customer (customerId, username, password, customerName, dob, address, city, postalcode, emailId, phone) 
VALUES 
(101,'vannguyen', 'qwe123', 'Van Nguyen', '1994-06-14', '15 Dundas', 'London', 'N5B2N6', 'vannguyen@email.com', '5195548745'),
(103,'bot2', 'qwe123', 'Testing Bot 2', '2000-12-24', '5 Queen St', 'Toronto', 'M2K1N4', 'bot2@email.com', '6745219813'),
(102,'bot1', 'qwe123', 'Ethan Smith', '1985-07-15', '456 Park Ave', 'Toronto', 'M2K1N4', 'bot1@email.com', '5458743652');

-- Inserting sample records into AccountType
INSERT INTO AccountType (accountTypeName, accountTypeDesc, minimumBalanceAmount, hasOverDraft) 
VALUES 
('Savings', 'Savings Account', 15.00, FALSE),
('Checking', 'Checking Account1', 0.00, TRUE),
('Checking2', 'Checking Account 2', 20.00, TRUE);

-- Inserting sample records into Account
INSERT INTO Account (accountTypeId, customerId, balance, overDraftLimit) 
VALUES 
(1, 101, 1200.00, 0.00),
(2, 101, 900.00, 200.00),
(2, 102, 2000.00, 50.00);

Select * from Customer;
use Comp303FinTech;
desc Account;

-- Adding Constraint
use Comp303FinTech;
ALTER TABLE Account ADD CONSTRAINT check_overdraft 
CHECK (
    (SELECT hasOverDraft FROM AccountType WHERE AccountType.accountTypeId = Account.accountTypeId) 
    OR overDraftLimit = 0.00
);
