CREATE TABLE subcategory (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  category_id INTEGER ,
  created_on TIMESTAMP,
  last_updated_on TIMESTAMP,
  archive BOOLEAN,
  FOREIGN KEY (category_id) REFERENCES category (categoryId)
);