-- V1__init.sql

-- 1. crag_color: 암장 색상 테이블
CREATE TABLE IF NOT EXISTS crag_color (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(50) NOT NULL,
                                          hex VARCHAR(7) NOT NULL,
                                          created_at DATETIME NOT NULL,
                                          modified_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. region: 지역 테이블
CREATE TABLE IF NOT EXISTS region (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      region_name ENUM(
                                          'SEOUL', 'BUSAN', 'DAEGU', 'INCHEON', 'GWANGJU', 'DAEJEON', 'ULSAN', 'SEJONG',
                                          'GYEONGGI', 'GANGWON', 'CHUNGBUK', 'CHUNGNAM', 'JEONBUK', 'JEONNAM', 'GYEONGBUK',
                                          'GYEONGNAM', 'JEJU'
                                          ) NOT NULL,
                                      district VARCHAR(255) NOT NULL,
                                      created_at DATETIME NOT NULL,
                                      modified_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. user: 사용자 테이블
CREATE TABLE IF NOT EXISTS `user` (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      login_id VARCHAR(255) NOT NULL UNIQUE,
                                      name VARCHAR(255),
                                      provider ENUM('KAKAO', 'APPLE', 'LOCAL') NOT NULL,
                                      is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                                      created_at DATETIME NOT NULL,
                                      modified_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. refresh_token: 리프레시 토큰 테이블
CREATE TABLE IF NOT EXISTS refresh_token (
                                             user_id BIGINT PRIMARY KEY,
                                             login_id VARCHAR(255),
                                             provider ENUM('KAKAO', 'APPLE', 'LOCAL') NOT NULL,
                                             token TEXT NOT NULL,
                                             created_at DATETIME NOT NULL,
                                             modified_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. thumbnail: 썸네일 테이블
CREATE TABLE IF NOT EXISTS thumbnail (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         file_url VARCHAR(255) NOT NULL,
                                         created_at DATETIME NOT NULL,
                                         modified_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. crag: 암장 테이블
CREATE TABLE IF NOT EXISTS crag (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    road_address VARCHAR(255) NOT NULL,
                                    location GEOMETRY NOT NULL,
                                    kakao_place_id BIGINT NOT NULL,
                                    created_at DATETIME NOT NULL,
                                    modified_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. grade: 등급 테이블
CREATE TABLE IF NOT EXISTS grade (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     color_id BIGINT NOT NULL,
                                     `order` INT,
                                     crag_id BIGINT NOT NULL,
                                     created_at DATETIME NOT NULL,
                                     modified_at DATETIME NOT NULL,
                                     CONSTRAINT fk_grade_color FOREIGN KEY (color_id) REFERENCES crag_color(id),
                                     CONSTRAINT fk_grade_crag FOREIGN KEY (crag_id) REFERENCES crag(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. story: 스토리 테이블
CREATE TABLE IF NOT EXISTS story (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     user_id BIGINT,
                                     crag_id BIGINT,
                                     memo VARCHAR(1024),
                                     date DATE NOT NULL,
                                     created_at DATETIME NOT NULL,
                                     modified_at DATETIME NOT NULL,
                                     CONSTRAINT fk_story_crag FOREIGN KEY (crag_id) REFERENCES crag(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. video: 비디오 테이블
CREATE TABLE IF NOT EXISTS video (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     local_path VARCHAR(255) NOT NULL,
                                     thumbnail_url VARCHAR(255) NOT NULL,
                                     duration_ms BIGINT NOT NULL,
                                     created_at DATETIME NOT NULL,
                                     modified_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. problem: 문제(프로블럼) 테이블
CREATE TABLE IF NOT EXISTS problem (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       story_id BIGINT NOT NULL,
                                       grade_id BIGINT,
                                       created_at DATETIME NOT NULL,
                                       modified_at DATETIME NOT NULL,
                                       CONSTRAINT fk_problem_story FOREIGN KEY (story_id) REFERENCES story(id),
                                       CONSTRAINT fk_problem_grade FOREIGN KEY (grade_id) REFERENCES grade(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. attempt: 시도(어텀프트) 테이블
CREATE TABLE IF NOT EXISTS attempt (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       problem_id BIGINT NOT NULL,
                                       video_id BIGINT NOT NULL,
                                       status ENUM('SUCCESS','FAILURE') NOT NULL,
                                       created_at DATETIME NOT NULL,
                                       modified_at DATETIME NOT NULL,
                                       CONSTRAINT fk_attempt_problem FOREIGN KEY (problem_id) REFERENCES problem(id),
                                       CONSTRAINT fk_attempt_video FOREIGN KEY (video_id) REFERENCES video(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 12. video_stamp: 비디오 스탬프 테이블
CREATE TABLE IF NOT EXISTS video_stamp (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           video_id BIGINT NOT NULL,
                                           time_ms BIGINT NOT NULL,
                                           created_at DATETIME NOT NULL,
                                           modified_at DATETIME NOT NULL,
                                           CONSTRAINT fk_video_stamp_video FOREIGN KEY (video_id) REFERENCES video(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 13. daily_report_statistic: 일별 리포트 통계 테이블
CREATE TABLE IF NOT EXISTS daily_report_statistic (
                                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                      user_id BIGINT NOT NULL,
                                                      stat_date DATE NOT NULL,
                                                      most_attempted_problem_crag VARCHAR(255) NOT NULL,
                                                      most_attempted_problem_grade VARCHAR(255) NOT NULL,
                                                      most_attempted_problem_attempt_count BIGINT NOT NULL,
                                                      most_attempted_problem_id BIGINT NOT NULL,
                                                      most_visited_crag_name VARCHAR(255) NOT NULL,
                                                      most_visited_crag_visit_count BIGINT NOT NULL,
                                                      created_at DATETIME NOT NULL,
                                                      modified_at DATETIME NOT NULL,
                                                      UNIQUE KEY uniq_user_stat_date (user_id, stat_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 14. 어드민 사용자 테이블

CREATE TABLE IF NOT EXISTS admin_user (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          login_id VARCHAR(255) NOT NULL COMMENT '어드민 로그인 아이디',
                                          password VARCHAR(255) NOT NULL COMMENT '어드민 로그인 비밀번호',
                                          created_at DATETIME NOT NULL,
                                          modified_at DATETIME NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;