CREATE TABLE IF NOT EXISTS event (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    start TIMESTAMP NOT NULL,
    "end" TIMESTAMP NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    category INTEGER NOT NULL,
    create_user BIGINT,
    modify_user BIGINT
);

COMMENT ON COLUMN event.title IS '活動名稱';
COMMENT ON COLUMN event.start IS '開始時間';
COMMENT ON COLUMN event."end" IS '結束時間';
COMMENT ON COLUMN event.category IS '活動種類（0 = 出隊, 1 = 溯溪, 2 = 社課, 3 = 討論, 4 = 山防';
COMMENT ON COLUMN event.create_user IS '建立者 ID';
COMMENT ON COLUMN event.modify_user IS '更新者 ID';