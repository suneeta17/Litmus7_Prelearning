create database training_mgt;

USE training_mgt;

-- 1. Courses
CREATE TABLE Courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_description TEXT
);

-- 2. Topics
CREATE TABLE Topics (
    topic_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    topic_name VARCHAR(100) NOT NULL,
    topic_description TEXT,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

-- 3. Batches
CREATE TABLE Batches (
    batch_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    batch_code VARCHAR(50) UNIQUE NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

-- 4. Trainers
CREATE TABLE Trainers (
    trainer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20)
);

-- 5. BatchTrainers (Many-to-Many: Batches ↔ Trainers)
CREATE TABLE BatchTrainers (
    batch_id INT NOT NULL,
    trainer_id INT NOT NULL,
    PRIMARY KEY (batch_id, trainer_id),
    FOREIGN KEY (batch_id) REFERENCES Batches(batch_id),
    FOREIGN KEY (trainer_id) REFERENCES Trainers(trainer_id)
);

-- 6. Candidates
CREATE TABLE Candidates (
    candidate_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20)
);

-- 7. BatchCandidates (Many-to-Many: Batches ↔ Candidates with Status)
CREATE TABLE BatchCandidates (
    batch_candidate_id INT AUTO_INCREMENT PRIMARY KEY,
    batch_id INT NOT NULL,
    candidate_id INT NOT NULL,
    status ENUM('In Progress', 'Completed', 'Terminated') DEFAULT 'In Progress',
    FOREIGN KEY (batch_id) REFERENCES Batches(batch_id),
    FOREIGN KEY (candidate_id) REFERENCES Candidates(candidate_id)
);

-- 8. Assignments
CREATE TABLE Assignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    batch_id INT NOT NULL,
    topic_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    due_date DATE NOT NULL,
    FOREIGN KEY (batch_id) REFERENCES Batches(batch_id),
    FOREIGN KEY (topic_id) REFERENCES Topics(topic_id)
);

-- 9. Submissions
CREATE TABLE Submissions (
    submission_id INT AUTO_INCREMENT PRIMARY KEY,
    assignment_id INT NOT NULL,
    batch_candidate_id INT NOT NULL,
    submission_date DATE,
    score DECIMAL(5,2),
    FOREIGN KEY (assignment_id) REFERENCES Assignments(assignment_id),
    FOREIGN KEY (batch_candidate_id) REFERENCES BatchCandidates(batch_candidate_id)
);
