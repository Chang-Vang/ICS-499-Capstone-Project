
-- Insert sample data into 'restaurant'
INSERT INTO restaurant (name, location, category) VALUES
  ('Chick-Fil-A', '1770 Robert St S, West Saint Paul, MN 55118', 'American Fast Food'),
  ('Chipotle', '5901 94th Ave N Ste 101, Brooklyn Park, MN 55443', 'Mexican Fast Food'),
  ('McDonalds', '9695 Xenia Avenue North, Brooklyn Park, MN 55443', 'American Fast Food'),
  ('China Hope', 'Edinburgh Plaza, 1428 85th Ave. N., Brooklyn Park, MN 55444', 'Chinese');

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