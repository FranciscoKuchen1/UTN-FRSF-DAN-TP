-- Create trigger to automatically update updated_at on row update
CREATE OR REPLACE FUNCTION usuarios.update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Apply trigger to users table
DROP TRIGGER IF EXISTS update_users_updated_at ON usuarios.users;

CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON usuarios.users
    FOR EACH ROW
    EXECUTE FUNCTION usuarios.update_updated_at_column();

COMMENT ON FUNCTION usuarios.update_updated_at_column() IS 'Automatically updates the updated_at timestamp on row updates';