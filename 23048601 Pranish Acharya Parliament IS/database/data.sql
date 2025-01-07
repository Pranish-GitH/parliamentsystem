CREATE DATABASE IF NOT EXISTS nepal_parliament;

USE nepal_parliament;

-- State table
CREATE TABLE State (
    stateId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    capital VARCHAR(255) NOT NULL,
    headOfState VARCHAR(255) NOT NULL
);

-- Parliament table
CREATE TABLE Parliament (
    parliamentId INT PRIMARY KEY AUTO_INCREMENT,
    stateId INT,
    houseName VARCHAR(255) NOT NULL,
    houseType ENUM('Unicameral') NOT NULL,
    FOREIGN KEY (stateId) REFERENCES State(stateId)
);

-- Government Changes table
CREATE TABLE GovernmentChanges (
    changeId INT PRIMARY KEY AUTO_INCREMENT,
    stateId INT,
    dateOfChange DATE NOT NULL,
    description TEXT NOT NULL,
    FOREIGN KEY (stateId) REFERENCES State(stateId)
);

-- Insert initial data
INSERT INTO State (name, capital, headOfState) VALUES
('Province 1', 'Biratnagar', 'Parmananda Subedi'),
('Madhesh', 'Janakpur', 'Hari Shankar Mishra'),
('Bagmati', 'Hetauda', 'Yadav Chandra Sharma'),
('Gandaki', 'Pokhara', 'Prithvi Subba Gurung'),
('Lumbini', 'Deukhuri', 'Dhurba Bahadur Shah'),
('Karnali', 'Birendranagar', 'Govinda Bahadur'),
('Sudurpashchim', 'Dhangadhi', 'Devendra Bahadur');

INSERT INTO Parliament (stateId, houseName, houseType) VALUES
(1, 'Provincial Assembly', 'Unicameral'),
(2, 'Provincial Assembly', 'Unicameral'),
(3, 'Provincial Assembly', 'Unicameral'),
(4, 'Provincial Assembly', 'Unicameral'),
(5, 'Provincial Assembly', 'Unicameral'),
(6, 'Provincial Assembly', 'Unicameral'),
(7, 'Provincial Assembly', 'Unicameral');

INSERT INTO GovernmentChanges (stateId, dateOfChange, description) VALUES
(3, '2020-05-15', 'Bagmati renamed its state capital from Kathmandu to Hetauda.'),
(5, '2021-01-10', 'Lumbini announced new development initiatives.');
