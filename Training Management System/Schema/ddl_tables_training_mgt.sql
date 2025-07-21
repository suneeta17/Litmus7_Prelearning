create database training_mgt;

USE training_mgt;

-- USER Table
CREATE TABLE USER (
    User_id INT PRIMARY KEY AUTO_INCREMENT,
    Password VARCHAR(45) NOT NULL,
    Role VARCHAR(15) NOT NULL,
    Name VARCHAR(45) NOT NULL,
    Email VARCHAR(45) NOT NULL,
    Phone_no VARCHAR(15),
    Is_active BOOLEAN DEFAULT TRUE,
    Start_date DATE NOT NULL,
    Updated_At DATE NOT NULL
);

-- COURSE Table
CREATE TABLE COURSE (
    Course_id INT PRIMARY KEY AUTO_INCREMENT,
    Course_name VARCHAR(45) NOT NULL
);

-- TOPICS Table
CREATE TABLE TOPICS (
    Topic_id INT PRIMARY KEY AUTO_INCREMENT,
    Topic_name VARCHAR(45),
    Course_id INT,
    FOREIGN KEY (Course_id) REFERENCES COURSE(Course_id)
);

-- BATCHES Table
CREATE TABLE BATCHES (
    Batch_id INT PRIMARY KEY AUTO_INCREMENT,
    Start_date DATE,
    End_date DATE,
    Course_id INT,
    Max_strength INT,
    FOREIGN KEY (Course_id) REFERENCES COURSE(Course_id)
);

-- ASSIGNMENTS Table
CREATE TABLE ASSIGNMENTS (
    Assignment_id INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(45),
    Description VARCHAR(45),
    Topic_id INT,
    Total_marks INT,
    Cut_off_marks INT,
    Created_At DATE NOT NULL,
    Updated_At DATE NOT NULL,
    FOREIGN KEY (Topic_id) REFERENCES TOPICS(Topic_id)
);

-- SUBMISSIONS Table
CREATE TABLE SUBMISSIONS (
    Submission_id INT PRIMARY KEY AUTO_INCREMENT,
    User_id INT,
    Assignment_id INT,
    Score INT,
    Pass_status VARCHAR(4),
    Submitted_date DATE,
    Max_attempts INT,
    No_of_attempts INT,
    Updated_At DATE NOT NULL,
    FOREIGN KEY (User_id) REFERENCES USER(User_id),
    FOREIGN KEY (Assignment_id) REFERENCES ASSIGNMENTS(Assignment_id)
);

-- USER_BATCH Table (Composite Key)
CREATE TABLE USER_BATCH (
    User_id INT,
    Batch_id INT,
    Status VARCHAR(15),
    Updated_At DATE NOT NULL,
    PRIMARY KEY (User_id, Batch_id),
    FOREIGN KEY (User_id) REFERENCES USER(User_id),
    FOREIGN KEY (Batch_id) REFERENCES BATCHES(Batch_id)
);

-- BATCH_ASSIGNMENT Table (Junction Table)
CREATE TABLE BATCH_ASSIGNMENT (
    Batch_code INT,
    Assignment_id INT,
    Due_Date DATE,
    Updated_At DATE NOT NULL,
    Updated_By INT,
    PRIMARY KEY (Batch_code, Assignment_id),
    FOREIGN KEY (Batch_code) REFERENCES BATCHES(Batch_id),
    FOREIGN KEY (Assignment_id) REFERENCES ASSIGNMENTS(Assignment_id),
    FOREIGN KEY (Updated_By) REFERENCES USER(User_id)
);

-- TRAINER_TOPIC Table (Junction Table)
CREATE TABLE TRAINER_TOPIC (
    Topic_id INT,
    User_id INT,
    PRIMARY KEY (Topic_id, User_id),
    FOREIGN KEY (Topic_id) REFERENCES TOPICS(Topic_id),
    FOREIGN KEY (User_id) REFERENCES USER(User_id)
);

-- EXIT_DETAILS Table
CREATE TABLE EXIT_DETAILS (
    Exit_id INT PRIMARY KEY AUTO_INCREMENT,
    User_id INT,
    Exit_date DATE NOT NULL,
    Reason TEXT,
    FOREIGN KEY (User_id) REFERENCES USER(User_id)
);
