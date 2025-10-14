CREATE TABLE IF NOT EXISTS record (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    category SMALLINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT NOT NULL,
    create_user BIGINT,
    modify_user BIGINT
);

COMMENT ON COLUMN record.name IS '路線名稱';
COMMENT ON COLUMN record.image IS '圖片路徑';
COMMENT ON COLUMN record.content IS '紀錄內容';
COMMENT ON COLUMN record.category IS '種類（0 = 中級山, 1 = 高山, 2 = 溯溪）';
COMMENT ON COLUMN record.start_date IS '開始日期';
COMMENT ON COLUMN record.end_date IS '結束日期';
COMMENT ON COLUMN record.description IS '路線簡介';
COMMENT ON COLUMN record.create_user IS '建立者 ID';
COMMENT ON COLUMN record.modify_user IS '更新者 ID';