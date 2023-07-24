CREATE TABLE category (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  created_on TIMESTAMP,
  last_updated_on TIMESTAMP,
  archive BOOLEAN
);