
-- USERS AND TEAMS
    -- table for users
    -- should be safer and better later on
    CREATE TABLE users (
        id uuid PRIMARY KEY,
        name varchar(255) NOT NULL,
        email varchar(255) UNIQUE NOT NULL,
        phone_number varchar(13) UNIQUE NOT NULL,
        birthdate date NOT NULL,
        created_on timestamptz NOT NULL
    );
    
    
    -- table for teams
    -- will hold basic info and id
    CREATE TABLE teams (
        id uuid PRIMARY KEY,
        name varchar(255) NOT NULL
    );
    
    -- table for which users are coached for which teams
    CREATE TABLE team_coach_relations (
        team_id uuid NOT NULL,
        user_id uuid NOT NULL,
        CONSTRAINT fk_team
            FOREIGN KEY (team_id) REFERENCES teams(id),
        CONSTRAINT fk_coach
            FOREIGN KEY (user_id) REFERENCES users(id)
    );
    
    -- table for which teams gymnasts belong to
    CREATE TABLE team_gymnast_relations (
        team_id uuid NOT NULL,
        user_id uuid NOT NULL,
        CONSTRAINT fk_team
            FOREIGN KEY (team_id) REFERENCES teams(id),
        CONSTRAINT fk_gymnast
            FOREIGN KEY (user_id) REFERENCES users(id)
    );
    
-- USERS AND TEAMS

-- ELEMENTS
    -- categories
    CREATE TABLE categories (
        code varchar(20) PRIMARY KEY,
        name varchar(255) NOT NULL,
        description text,
        parent_code varchar(20) NULL,
        CONSTRAINT fk_parent
            FOREIGN KEY (parent_code) REFERENCES categories(code)
    );
    -- table for elements and their info
    CREATE TABLE elements (
        code varchar(20) PRIMARY KEY,
        name varchar(255) NOT NULL,
        description text,
        difficulty numeric(2,1),
        category_code varchar(20) NOT NULL,
        CONSTRAINT fk_category
            FOREIGN KEY (category_code) REFERENCES categories(code)
    );
-- ELEMENTS
