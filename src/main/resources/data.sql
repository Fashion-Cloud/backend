WITH random_data AS (
    SELECT
        uuid_generate_v4() AS id,
        now() - (random() * interval '365 days') AS created_at,
        CASE WHEN random() < 0.8 THEN NULL ELSE now() - (random() * interval '365 days') END AS deleted_at,
        now() - (random() * interval '365 days') AS updated_at,
        'SampleData' AS image,
        'SampleData' AS name,
        floor(random() * 7) AS rainfall_type,
        CASE floor(random() * 3) WHEN 0 THEN 1 WHEN 1 THEN 3 ELSE 4 END AS sky_status,
        CASE floor(random() * 3) WHEN 0 THEN '추웠다' WHEN 1 THEN '괜찮았다' ELSE '더웠다' END AS review,
        uuid_generate_v4() AS user_id,
        -15 + random() * 35 AS wind_chill
    FROM generate_series(1, 1000000)
)
-- 더미 데이터를 post 테이블에 삽입
INSERT INTO post (id, created_at, deleted_at, updated_at, image, name, rainfall_type, sky_status, review, user_id, wind_chill)
SELECT
    id,
    created_at,
    deleted_at,
    updated_at,
    image,
    name,
    rainfall_type,
    sky_status,
    review,
    user_id,
    wind_chill
FROM random_data;