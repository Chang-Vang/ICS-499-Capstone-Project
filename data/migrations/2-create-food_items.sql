CREATE TABLE food_items (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  description TEXT,
  category TEXT,
  price FLOAT,
  resturant_id INTEGER,
  FOREIGN KEY(resturant_id ) REFERENCES resturant(id)
 );