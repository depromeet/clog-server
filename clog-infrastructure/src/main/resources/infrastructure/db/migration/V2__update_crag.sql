-- 1. lotNumberAddress 컬럼 추가
ALTER TABLE crag
    ADD COLUMN lot_number_address VARCHAR(255) NOT NULL COMMENT '지번 주소';

