-- 1. 기존 NULL 값 처리: UNKNOWN으로 치환
UPDATE attempt
SET status = 'UNKNOWN'
WHERE status IS NULL;

-- 2. DEFAULT 값 설정
ALTER TABLE attempt
    ALTER COLUMN status SET DEFAULT 'UNKNOWN';
