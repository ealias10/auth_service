CREATE TABLE roles (
  id UUID NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_at BIGINT NOT NULL,
  modified_by VARCHAR(255) NOT NULL,
  modified_at BIGINT NOT NULL,
  name VARCHAR(255),
  CONSTRAINT pk_roles PRIMARY KEY (id)
);
ALTER TABLE roles ADD CONSTRAINT uc_roles_name UNIQUE (name);
CREATE TABLE users (
  id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_at BIGINT NOT NULL,
   modified_by VARCHAR(255) NOT NULL,
   modified_at BIGINT NOT NULL,
   username VARCHAR(255),
   password VARCHAR(255),
   refresh_token VARCHAR(255),
   active BOOLEAN,
   email VARCHAR(255),
   email_otp JSONB,
   password_otp JSONB,
   role_id UUID,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

INSERT INTO roles VALUES(gen_random_uuid(), 'system', extract(epoch from now()), 'system', extract(epoch from now()), 'USER');
INSERT INTO roles VALUES(gen_random_uuid(), 'system', extract(epoch from now()), 'system', extract(epoch from now()), 'ADMIN');