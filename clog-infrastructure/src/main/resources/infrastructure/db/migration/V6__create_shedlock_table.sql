CREATE TABLE IF NOT EXISTS shedlock (
                                        name VARCHAR(64) NOT NULL,
                                        lock_until DATETIME(3) NULL,
                                        locked_at DATETIME(3) NULL,
                                        locked_by VARCHAR(255) NOT NULL,
                                        PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
