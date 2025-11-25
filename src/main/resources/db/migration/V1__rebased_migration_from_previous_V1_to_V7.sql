-- Rebased migration script combining previous V1 to V7 migrations

-- Drop tables if they already exist to avoid conflicts
-- This has to be done from the child to parent due to foreign key constraints
DROP TABLE IF EXISTS food_items;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS users;

-- Create 'users' table
CREATE TABLE users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   restaurant_owner BOOLEAN NOT NULL DEFAULT FALSE
);

-- Insert initial data into 'users'
INSERT INTO users (email, password, restaurant_owner) VALUES
  ('user1@test.com', 'password1', TRUE),
  ('user2@test.com', 'password2', FALSE);

-- Create 'address' table with foreign key to 'users'
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zipcode BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT address_users_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create 'restaurant' table with foreign key to 'users'
CREATE TABLE restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT restaurant_users_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create 'food_items' table with foreign key to 'restaurant'
CREATE TABLE food_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    category VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    CONSTRAINT foodItems_restaurant_id_fk FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

-- Insert sample data into 'restaurant'
INSERT INTO restaurant (name, location, category, user_id) VALUES
   ('Chick-Fil-A', '1770 Robert St S, West Saint Paul, MN 55118', 'American Fast Food', 1),
   ('Chipotle', '5901 94th Ave N Ste 101, Brooklyn Park, MN 55443', 'Mexican Fast Food', 1),
   ('McDonalds', '9695 Xenia Avenue North, Brooklyn Park, MN 55443', 'American Fast Food', 1),
   ('China Hope', 'Edinburgh Plaza, 1428 85th Ave. N., Brooklyn Park, MN 55444', 'Chinese', 1);

-- Insert sample data into 'food_items'
INSERT INTO food_items (name, description, category, price, restaurant_id) VALUES
   ('Chicken Sandwich', 'Original Chicken Sandwich, White Butter Bun Buttered, Pickles', 'Entrée', 5.09, 1),
   ('Chicken Sandwich Meal', 'Original Chicken Sandwich, White Butter Bun Buttered, Pickles with waffle fries with Medium Coke', 'Meal', 9.09, 1),
   ('Chicken Deluxe Sandwich', 'Original Chicken Deluxe Sandwich, White Butter Bun Buttered, Pickles Tomato, Lettuce', 'Entrée', 5.79, 1),
   ('Chicken Deluxe Sandwich Meal', 'Original Chicken Deluxe Sandwich, White Butter Bun Buttered, Pickles Tomato with waffle fries with Medium Coke, Lettuce', 'Meal', 9.79, 1),
   ('Chicken Strip Meal - 3ct', 'Chick-n-Strips, with waffle fries with Medium Coke, Lettuce', 'Entrée', 9.45, 1),
   ('Sweet Chili Chicken', 'Chicken breast stir-fried w/ onions, red & green pepper in a sweet, savory chili sauce.', 'Entrée', 13.50, 4),
   ('Black Pepper Steak', 'Sirloin steak, onions, red & green papper in a black pepper sauce.', 'Entrée', 16.50, 4),
   ('Chicken Wings (8)', 'Eight Chicken Wings', 'App', 8.50, 4),
   ('Chicken Fried Rice', 'Chicken, onions, beansprouts & egg.', 'Fried Rice', 5.90, 4),
   ('Chicken Lo Mein', 'Chicken Lo Mein', 'Lo Mein', 6.80, 4),
   ('Burrito - Steak', 'Steak Burrito with White Rice, Black Beans, Fresh Tomato Salsa, Sour Creame, Cheese and Romaine Lettuce', 'Entrée', 10.25, 2),
   ('Burrito - Chicken', 'Chicken Burrito with Brown Rice, Black Beans, Tomatillo-Green Chili Salsa, Sour Creame, Cheese and Romaine Lettuce', 'Entrée', 8.50, 2),
   ('Burrito Bowl - Chicken', 'Chicken Burrito with Brown Rice, Black Beans, Tomatillo-Red Chili Salsa, Sour Creame, Cheese and Romaine Lettuce', 'Entrée', 8.50, 2),
   ('Chips', 'Chips', 'Sides', 1.65, 2),
   ('Chips - Tomato Salsa', 'Chips with Fresh Tomato Salsa', 'Sides', 1.65, 2),
   ('Chips - Tomatillo-Green Chili', 'Chips with Tomatillo-Green Chili Salsa', 'Sides', 1.65, 2),
   ('Chips - Tomatillo-Red Chili', 'Chips with Tomatillo-Red Chili Salsa', 'Sides', 1.65, 2),
   ('Organic Watermelon Limeade - Med', 'Organic Watermelon Limeade', 'Drinks', 2.80, 2),
   ('Organic Limeade - Large', 'Organic Watermelon Limeade', 'Drinks', 3.10, 2),
   ('Bacon, Egg & Cheese', 'Bacon, Egg & Cheese', 'Breakfast', 0.00, 3),
   ('Sausage McMuffin with Egg', 'Sausage McMuffin with Egg', 'Breakfast', 1.25, 3),
   ('Sausage Biscuit', 'Sausage Biscuit', 'Breakfast', 1.30, 3),
   ('Sausage Biscuit with Egg', 'Sausage Biscuit with Egg', 'Breakfast', 1.50, 3),
   ('Sausage Biscuit with Egg', 'Sausage Biscuit with Egg', 'Breakfast', 1.75, 3),
   ('McNuggets - BBQ', 'Chicken McNuggets with BBQ Sauce', 'Lunch/Dinner', 2.00, 3),
   ('Fries - Large', 'Large French Fries', 'Lunch/Dinner', 1.35, 3),
   ('Dr Pepper - Large', 'Dr Pepper', 'Drink', 1.25, 3),
   ('Coca-Cola - Large', 'Coca Cola', 'Drink', 1.25, 3),
   ('Coca-Cola - Large', 'Iced Tea', 'Drink', 1.25, 3);