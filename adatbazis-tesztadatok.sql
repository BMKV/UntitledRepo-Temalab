INSERT INTO users (user_id, email_address, password, can_deliver)
VALUES (42, 'test@test.com', 'VerySafePass', false);

INSERT INTO routes (route_id, start_location, destination)
VALUES ('1', 'from', 'to');

INSERT INTO jobs (job_id, delivery_route, status, size, payment, job_issued_date, deadline)
VALUES (42, '1', 'pending', 'medium', '420', 'ide', 'oda');

INSERT INTO package_sent (user_id, job_id)
VALUES (42, 42);