USE training_mgt;

-- Insert into USER
INSERT INTO USER (Password, Role, Name, Email, Phone_no, Start_date, Updated_At)
VALUES
('pass123', 'Admin', 'Alice John', 'alice@example.com', '9876543210', '2023-01-01', '2023-01-01'),
('pass456', 'Candidate', 'Bob Mathew', 'bob@example.com', '9876543211', '2023-02-10', '2023-03-01'),
('pass789', 'Trainer', 'Charlie Roy', 'charlie@example.com', '9876543212', '2023-01-15', '2023-02-10');

-- Insert into COURSE
INSERT INTO COURSE (Course_name)
VALUES ('Python Programming'), ('Data Science'), ('Web Development');

-- Insert into TOPICS
INSERT INTO TOPICS (Topic_name, Course_id)
VALUES
('Variables and Loops', 1),
('Functions and Modules', 1),
('Statistics Basics', 2),
('HTML & CSS', 3);

-- Insert into BATCHES
INSERT INTO BATCHES (Start_date, End_date, Course_id, Max_strength)
VALUES
('2023-04-01', '2023-07-01', 1, 30),
('2023-05-01', '2023-08-01', 2, 25);

-- Insert into ASSIGNMENTS
INSERT INTO ASSIGNMENTS (Title, Description, Topic_id, Total_marks, Cut_off_marks, Created_At, Updated_At)
VALUES
('Assignment 1', 'Basic Python', 1, 100, 40, '2023-04-05', '2023-04-06'),
('Assignment 2', 'Functions Practice', 2, 100, 50, '2023-04-10', '2023-04-11');

-- Insert into USER_BATCH
INSERT INTO USER_BATCH (User_id, Batch_id, Status, Updated_At)
VALUES
(2, 1, 'In Progress', '2023-05-01'),
(3, 2, 'In Progress', '2023-05-01');

-- Insert into BATCH_ASSIGNMENT
INSERT INTO BATCH_ASSIGNMENT (Batch_code, Assignment_id, Due_Date, Updated_At, Updated_By)
VALUES
(1, 1, '2023-06-01', '2023-05-15', 1),
(1, 2, '2023-06-10', '2023-05-20', 1);

-- Insert into TRAINER_TOPIC
INSERT INTO TRAINER_TOPIC (Topic_id, User_id)
VALUES
(1, 3),
(2, 3),
(3, 3);

-- Insert into SUBMISSIONS
INSERT INTO SUBMISSIONS (User_id, Assignment_id, Score, Pass_status, Submitted_date, Max_attempts, No_of_attempts, Updated_At)
VALUES
(2, 1, 85, 'Pass', '2023-06-01', 3, 1, '2023-06-01'),
(2, 2, 40, 'Fail', '2023-06-10', 3, 2, '2023-06-11');

-- Insert into EXIT_DETAILS
INSERT INTO EXIT_DETAILS (User_id, Exit_date, Reason)
VALUES
(2, '2024-01-10', 'Personal reasons');
