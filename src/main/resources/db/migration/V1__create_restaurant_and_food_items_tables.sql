-- Drop tables if they already exist to avoid conflicts
DROP TABLE IF EXISTS food_items;
DROP TABLE IF EXISTS restaurant;

-- Create the 'restaurant' table
CREATE TABLE restaurant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL,
    location TEXT NOT NULL,
    category TEXT NOT NULL
)

-- Create the 'food_items' table
CREATE TABLE food_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    category TEXT NOT NULL,
    price FLOAT NOT NULL,
    restaurant_id INT,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
)
