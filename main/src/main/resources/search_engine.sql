ALTER TABLE essay ADD COLUMN ts tsvector
    GENERATED ALWAYS AS (to_tsvector('english', text)) STORED;