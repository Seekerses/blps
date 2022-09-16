CREATE INDEX ts_idx ON essay USING GIN (ts);
INSERT INTO credentials VALUES (1, false, '$2a$10$k8RbFKaFVPa6DBzoLH25FeMX6CeFBNWO1VuMIZKrhccBZEk0pFUmO', 'admin');
INSERT INTO accounts VALUES (1, true, false, false, 'admin', 1);
