-- Insert sample data into the 'deals'
INSERT INTO deals (title, description, applicable_item_ids,discount_value, discount_type, end_date, start_date, restaurant_id) VALUES
    ('Christmas Sale!', 'Get 25% Off', 'all', 25,'PERCENT', '2023-12-26', '2023-12-20', 1),
    ('New Year Special', 'Buy One Get One Free on all Burgers', 'burger1,burger2,burger3', 100, 'PERCENT', '2026-01-15', '2025-12-15', 3),
    ('Super Bowl Snack Pack', '20% Off on Snack Combos', 'snack1,snack2,snack3', 20,'PERCENT', '2024-02-05', '2024-01-28', 3),
    ('Valentine\'s Day Delight', 'Free Dessert with any Main Course', 'all', 100,'PERCENT', '2024-02-15', '2024-02-10', 3),
    ('Spring Fling', '15% Off on all Salads', 'salad1,salad2,salad3', 15,'PERCENT', '2025-05-31', '2025-05-01', 2);