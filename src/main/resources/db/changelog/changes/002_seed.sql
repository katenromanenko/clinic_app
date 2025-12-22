--liquibase formatted sql

--changeset katya:002-seed-min
--comment: minimal seed to avoid empty changelog parse error
insert into specializations (spec_id, name, description, created_at, updated_at)
values
    ('11111111-1111-1111-1111-111111111111', 'Терапевт', 'Терапия', timestamp '2025-01-01 00:00:00', null),
    ('22222222-2222-2222-2222-222222222222', 'Невролог', 'Неврология', timestamp '2025-01-01 00:00:00', null);

-- ============================
-- USERS
-- password for all users: "123"
-- bcrypt hash:
-- $2a$10$8uLFU5..rZKt0onE0zmhIeT2CUAktOfjgoNwAhZw4BAQmfLd4036O
-- ============================
insert into users (
    user_id,
    login,
    password_hash,
    role,
    first_name,
    last_name,
    email,
    phone,
    birth_date,
    sex,
    specialization_id,
    office,
    work_hours,
    created_at,
    updated_at,
    is_active
)
values
    -- ADMIN
    (
        'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
        'admin',
        '$2a$10$8uLFU5..rZKt0onE0zmhIeT2CUAktOfjgoNwAhZw4BAQmfLd4036O',
        'ADMIN',
        'Admin',
        'User',
        'admin@gmail.com',
        null,
        null,
        null,
        null,
        null,
        null,
        timestamp '2025-01-01 00:00:00',
        null,
        true
    ),

    -- DOCTORS
    (
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        'doctor1',
        '$2a$10$8uLFU5..rZKt0onE0zmhIeT2CUAktOfjgoNwAhZw4BAQmfLd4036O',
        'DOCTOR',
        'Екатерина',
        'Романенко',
        'doctor1@gmail.com',
        '+375291111111',
        null,
        'FEMALE',
        '11111111-1111-1111-1111-111111111111',
        '101',
        '09:00-17:00',
        timestamp '2025-01-01 00:00:00',
        null,
        true
    ),
    (
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        'doctor2',
        '$2a$10$8uLFU5..rZKt0onE0zmhIeT2CUAktOfjgoNwAhZw4BAQmfLd4036O',
        'DOCTOR',
        'Игорь',
        'Бородачев',
        'doctor2@gmail.com',
        '+375292222222',
        null,
        'MALE',
        '22222222-2222-2222-2222-222222222222',
        '102',
        '10:00-18:00',
        timestamp '2025-01-01 00:00:00',
        null,
        true
    ),

    -- PATIENTS
    (
        'cccccccc-cccc-cccc-cccc-cccccccccccc',
        'patient1',
        '$2a$10$8uLFU5..rZKt0onE0zmhIeT2CUAktOfjgoNwAhZw4BAQmfLd4036O',
        'PATIENT',
        'Инна',
        'Мелешко',
        'patient1@gmail.com',
        '+375293333333',
        date '1990-01-10',
        'FEMALE',
        null,
        null,
        null,
        timestamp '2025-01-01 00:00:00',
        null,
        true
    ),
    (
        'dddddddd-dddd-dddd-dddd-dddddddddddd',
        'patient2',
        '$2a$10$8uLFU5..rZKt0onE0zmhIeT2CUAktOfjgoNwAhZw4BAQmfLd4036O',
        'PATIENT',
        'Виктор',
        'Наказнюк',
        'patient2@gmail.com',
        '+375294444444',
        date '1985-05-20',
        'MALE',
        null,
        null,
        null,
        timestamp '2025-01-01 00:00:00',
        null,
        true
    );

-- ============================
-- TIMESLOTS
-- ============================
insert into timeslots (
    slot_id,
    doctor_id,
    start_time,
    end_time,
    state,
    is_blocked,
    created_at,
    updated_at
)
values
    (
        '11111111-aaaa-aaaa-aaaa-111111111111',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        timestamp '2025-01-10 10:00:00',
        timestamp '2025-01-10 10:30:00',
        'AVAILABLE',
        false,
        timestamp '2025-01-01 00:00:00',
        null
    ),
    (
        '22222222-bbbb-bbbb-bbbb-222222222222',
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        timestamp '2025-01-10 11:00:00',
        timestamp '2025-01-10 11:30:00',
        'AVAILABLE',
        false,
        timestamp '2025-01-01 00:00:00',
        null
    );

-- ============================
-- APPOINTMENTS
-- ============================
insert into appointments (
    appointment_id,
    patient_id,
    doctor_id,
    slot_id,
    start_at,
    duration_min,
    status,
    description,
    cancellation_reason,
    notification_sent,
    created_at,
    updated_at,
    canceled_at
)
values
    (
        '99999999-9999-9999-9999-999999999999',
        'cccccccc-cccc-cccc-cccc-cccccccccccc',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        '11111111-aaaa-aaaa-aaaa-111111111111',
        timestamp '2025-01-10 10:00:00',
        30,
        'SCHEDULED',
        'Первичный приём',
        null,
        false,
        timestamp '2025-01-01 00:00:00',
        null,
        null
    ),
    (
        '88888888-8888-8888-8888-888888888888',
        'dddddddd-dddd-dddd-dddd-dddddddddddd',
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        '22222222-bbbb-bbbb-bbbb-222222222222',
        timestamp '2025-01-10 11:00:00',
        30,
        'SCHEDULED',
        'Консультация',
        null,
        false,
        timestamp '2025-01-01 00:00:00',
        null,
        null
    );

-- ============================
-- DIAGNOSIS_ATTACHMENTS
-- ============================
insert into diagnosis_attachments (
    attachment_id,
    appointment_id,
    record_id,
    text,
    created_at,
    updated_at
)
values
    (
        '12121212-1212-1212-1212-121212121212',
        '99999999-9999-9999-9999-999999999999',
        null,
        'Диагноз: ОРВИ. Рекомендации: покой, обильное питьё, симптоматическая терапия.',
        timestamp '2025-01-01 00:00:00',
        null
    ),
    (
        '34343434-3434-3434-3434-343434343434',
        '88888888-8888-8888-8888-888888888888',
        null,
        'Диагноз: Повышенное АД. Рекомендации: контроль давления, консультация терапевта.',
        timestamp '2025-01-01 00:00:00',
        null
    );
