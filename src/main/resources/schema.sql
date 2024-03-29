DROP TABLE IF EXISTS movements_items;
DROP TABLE IF EXISTS movements;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS departments;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS organizations;

CREATE TABLE IF NOT EXISTS organizations (
  id                BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name              VARCHAR(100) NOT NULL,
  activated_code    VARCHAR(100) NOT NULL,
  activated boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
  id                BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name              VARCHAR(100) NOT NULL,
  organization_id   BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
  id                BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name              VARCHAR(100) NOT NULL,
  reporting         BOOLEAN NOT NULL,
  position          VARCHAR(100) NOT NULL,
  email             VARCHAR(100) NOT NULL UNIQUE,
  password          VARCHAR(100) NOT NULL,
  role              VARCHAR(10) NOT NULL,
  organization_id   BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS departments (
  id                BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name              VARCHAR(100) NOT NULL,
  address           VARCHAR(100) NOT NULL,
  organization_id   BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS items (
  id                BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  category_id       BIGINT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
  name              VARCHAR(100) NOT NULL,
  description       VARCHAR(1000) NOT NULL,
  serviceable       BOOLEAN NOT NULL,
  inv_number        VARCHAR(100),
  created           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  finished          TIMESTAMP WITHOUT TIME ZONE,
  client_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  owner_id          BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  department_id     BIGINT NOT NULL REFERENCES departments(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS movements (
  id                    BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  movement_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  from_owner_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  to_owner_id           BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  from_client_id        BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  to_client_id          BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  from_department_id    BIGINT NOT NULL REFERENCES departments(id) ON DELETE CASCADE,
  to_department_id      BIGINT NOT NULL REFERENCES departments(id) ON DELETE CASCADE,
  description           VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS movements_items (
  movement_id           BIGINT NOT NULL REFERENCES movements(id) ON DELETE CASCADE,
  item_id               BIGINT NOT NULL REFERENCES items(id) ON DELETE CASCADE
);
