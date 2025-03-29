-- 1. lotNumberAddress 컬럼 추가
ALTER TABLE crag
    ADD COLUMN status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE' COMMENT '암장의 현재 상태' AFTER name;

