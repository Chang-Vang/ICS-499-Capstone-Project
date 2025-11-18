-- Add a boolean flag to indicate if a user is a restaurant owner
-- Using BOOLEAN which maps to TINYINT(1) in MySQL
ALTER TABLE users
    ADD COLUMN restaurant_owner BOOLEAN NOT NULL DEFAULT FALSE;

-- Set owner flag values for initial users
UPDATE users SET restaurant_owner = TRUE  WHERE email = 'user1@test.com';
UPDATE users SET restaurant_owner = FALSE WHERE email = 'user2@test.com';
