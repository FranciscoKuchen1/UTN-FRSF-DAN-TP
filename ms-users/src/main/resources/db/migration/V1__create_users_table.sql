-- Create users table in usuarios schema
-- UUID generation: Using PostgreSQL uuid-ossp extension

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create users table
CREATE TABLE usuarios.users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('BUYER', 'ADMIN')),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Create index on email for faster lookups
CREATE INDEX idx_users_email ON usuarios.users (email);
CREATE INDEX idx_users_enabled ON usuarios.users (enabled);
CREATE INDEX idx_users_role ON usuarios.users (role);

-- Add comment to table
COMMENT ON TABLE usuarios.users IS 'Stores user account information for the WorldCupTicket platform';
COMMENT ON COLUMN usuarios.users.id IS 'Unique identifier generated as UUID v4';
COMMENT ON COLUMN usuarios.users.email IS 'User email, must be unique';
COMMENT ON COLUMN usuarios.users.password_hash IS 'BCrypt hashed password';
COMMENT ON COLUMN usuarios.users.first_name IS 'User first name';
COMMENT ON COLUMN usuarios.users.last_name IS 'User last name';
COMMENT ON COLUMN usuarios.users.role IS 'User role: BUYER or ADMIN';
COMMENT ON COLUMN usuarios.users.enabled IS 'Whether the user account is enabled';
COMMENT ON COLUMN usuarios.users.created_at IS 'Account creation timestamp';
COMMENT ON COLUMN usuarios.users.updated_at IS 'Last account update timestamp';