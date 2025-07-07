USE training_mgt;

-- 1. Inserting data to course table
INSERT INTO Courses (course_name, course_description) VALUES
('Python Programming', 'Beginner to advanced Python programming.'),
('Java Programming', 'Core and Advanced Java concepts.'),
('JavaScript Essentials', 'Learn front-end and backend JS.'),
('C Programming', 'Low-level programming using C.'),
('C++ Programming', 'Object-Oriented Programming with C++.'),
('SQL and Databases', 'Querying and designing databases using SQL.'),
('Go Programming', 'High-performance programming with Golang.');

SELECT * FROM Courses;

-- 2. Inserting Data to Topics Table
INSERT INTO Topics (course_id, topic_name, topic_description) VALUES
(1, 'Variables and Data Types', 'Basic syntax, types, and variables in Python'),
(1, 'Functions and Modules', 'Defining and using functions and modules'),
(2, 'OOP in Java', 'Java classes, objects, inheritance, and polymorphism'),
(2, 'Exception Handling', 'Try-catch, throws, and custom exceptions'),
(3, 'DOM Manipulation', 'JavaScript and the browser DOM'),
(3, 'Async Programming', 'Promises, async/await in JS'),
(4, 'Pointers and Memory', 'Pointer arithmetic and memory allocation in C'),
(5, 'STL in C++', 'Vectors, Maps, and other STL containers'),
(6, 'Joins and Queries', 'SQL joins and subqueries'),
(7, 'Concurrency in Go', 'Goroutines, Channels, and Go concurrency model');

SELECT * FROM Topics;

-- 3. Inserting data into Batch table
INSERT INTO Batches (course_id, batch_code, start_date, end_date) VALUES
(1, 'PY-101', '2024-06-01', '2024-07-15'),
(2, 'JA-202', '2024-06-05', '2024-07-20'),
(3, 'JS-303', '2024-06-10', '2024-07-25'),
(4, 'C-404',  '2024-06-15', '2024-07-30'),
(5, 'CPP-505', '2024-07-01', '2024-08-15'),
(6, 'SQL-606', '2024-07-10', '2024-08-20'),
(7, 'GO-707',  '2024-07-20', '2024-08-30');

SELECT * FROM Batches;

-- 4. Inserting Data to Trainers Table
INSERT INTO Trainers (name, email, phone) VALUES
('Alice Johnson', 'alice.j@trainers.com', '9991110001'),
('Bob Smith', 'bob.s@trainers.com', '9991110002'),
('Cathy Lee', 'cathy.l@trainers.com', '9991110003'),
('David Miller', 'david.m@trainers.com', '9991110004'),
('Emma Thomas', 'emma.t@trainers.com', '9991110005');

-- 5. Inserting Data to Batch-Trainers
INSERT INTO BatchTrainers (batch_id, trainer_id) VALUES
(1, 1), (2, 2), (3, 1), (4, 3),
(5, 4), (6, 2), (7, 5),
(2, 1), 
(3, 3);

SELECT * FROM BatchTrainers;

-- 6. Insert Data to Candidate Table
INSERT INTO Candidates (name, email, phone) VALUES
('Arjun R', 'arjun@candidates.com', '8882221001'),
('Beena M', 'beena@candidates.com', '8882221002'),
('Charles K', 'charles@candidates.com', '8882221003'),
('Deepa S', 'deepa@candidates.com', '8882221004'),
('Faizal N', 'faizal@candidates.com', '8882221005'),
('Geetha R', 'geetha@candidates.com', '8882221006'),
('Hari V', 'hari@candidates.com', '8882221007'),
('Indu K', 'indu@candidates.com', '8882221008'),
('John T', 'john@candidates.com', '8882221009'),
('Keerthi L', 'keerthi@candidates.com', '8882221010');

Select * From Candidates;

-- 7.Insert Data into BatchCandidates
INSERT INTO BatchCandidates (batch_id, candidate_id, status) VALUES
(1, 1, 'In Progress'),
(1, 2, 'Terminated'),
(2, 3, 'In Progress'),
(3, 4, 'In Progress'),
(3, 5, 'Completed'),
(4, 6, 'In Progress'),
(5, 7, 'In Progress'),
(6, 8, 'Completed'),
(7, 9, 'Terminated'),
(7, 10, 'In Progress');

Select * From BatchCandidates;

-- 8. Inserting Data into Assignments
INSERT INTO Assignments (batch_id, topic_id, title, description, due_date) VALUES
(1, 1, 'Python Basics Quiz', 'Test on variables and data types', '2024-06-15'),
(1, 2, 'Functions Practice', 'Practice problems on Python functions', '2024-06-20'),
(2, 3, 'OOP Assignment', 'Design a class structure in Java', '2024-06-22'),
(3, 5, 'DOM Project', 'Build a DOM-based app', '2024-06-25'),
(4, 7, 'Pointers Quiz', 'Pointer manipulation in C', '2024-07-01'),
(5, 8, 'STL Task', 'Use vectors and maps in C++', '2024-07-10'),
(6, 9, 'Join Challenge', 'Write queries using joins', '2024-07-15'),
(7, 10, 'Go Routine Task', 'Create a concurrent Go program', '2024-07-25');

Select * from Assignments;

-- Inserting Data into Submissions
-- For batch 1 assignments
INSERT INTO Submissions (assignment_id, batch_candidate_id, submission_date, score) VALUES
(1, 1, '2024-06-14', 85.0),
(1, 2, '2024-06-15', 88.5),
(2, 1, '2024-06-19', 90.0),
(2, 2, '2024-06-20', 87.0),

-- For batch 2 assignments
(3, 3, '2024-06-21', 75.5),

-- For batch 3 assignments
(4, 4, '2024-06-24', 92.0),
(4, 5, '2024-06-25', 85.0),

-- For batch 4 assignments
(5, 6, '2024-07-01', 78.0),

-- For batch 5 assignments
(6, 7, '2024-07-09', 80.5);

Select * from Submissions;

