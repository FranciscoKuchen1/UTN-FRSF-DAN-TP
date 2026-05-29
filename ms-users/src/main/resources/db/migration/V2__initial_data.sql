-- Insert initial roles/admin user
-- Password: 'admin123' (bcrypt hashed)
-- You should replace this with proper initialization

INSERT INTO usuarios.users (
    email,
    password_hash,
    first_name,
    last_name,
    role,
    enabled,
    created_at,
    updated_at
) VALUES (
    'admin@worldcupticket.com',
    '$2a$10$SlVZrJ5XwCPqROQv.V7PNuWQ4.PJmqQv5VRbVJP8PHN1xZ5K5H4jC',
    'Admin',
    'User',
    'ADMIN',
    TRUE,
    NOW(),
    NOW()
) ON CONFLICT (email) DO NOTHING;