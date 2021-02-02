CREATE DATABASE IF NOT EXISTS snake;
USE snake;
CREATE TABLE IF NOT EXISTS HighScore (
  PlayerName VARCHAR(50),
  Score      INT,
  PRIMARY KEY(PlayerName, Score)
);