-- Get all batches a candidate is enrolled in, with their status.
SELECT 
    b.batch_id,
    b.batch_code,
    b.start_date,
    b.end_date,
    c.course_name,
    bc.status
FROM BatchCandidates bc
JOIN Batches b ON bc.batch_id = b.batch_id
JOIN Courses c ON b.course_id = c.course_id
WHERE bc.candidate_id = '4';  -- Replace with candidate id

-- Get all trainers assigned to a batch.
SELECT 
    t.trainer_id,
    t.name,
    t.email,
    t.phone
FROM BatchTrainers bt
JOIN Trainers t ON bt.trainer_id = t.trainer_id
WHERE bt.batch_id = '3';  -- Replace with actual batch_id

--  Get all topics under a course
SELECT 
    t.topic_id,
    t.topic_name,
    t.topic_description
FROM Topics t
WHERE t.course_id = '1';  -- Replace with course_id

-- List assignment scores for a candidate in a batch.
SELECT 
    a.assignment_id,
    a.title,
    s.submission_date,
    s.score
FROM Submissions s
JOIN Assignments a ON s.assignment_id = a.assignment_id
JOIN BatchCandidates bc ON s.batch_candidate_id = bc.batch_candidate_id
WHERE bc.batch_id = '1'     -- Replace with actual batch_id
  AND bc.candidate_id = '1';  -- Replace with actual candidate_id
  
-- List candidates with status "Completed" in a given batch
  SELECT 
    c.candidate_id,
    c.name,
    c.email,
    c.phone
FROM BatchCandidates bc
JOIN Candidates c ON bc.candidate_id = c.candidate_id
WHERE bc.batch_id = '6'     -- Replace with actual batch_id
  AND bc.status = 'Completed';




