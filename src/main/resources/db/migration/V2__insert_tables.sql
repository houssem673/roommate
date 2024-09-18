-- Beispiel Daten für die Tabelle "workspace"
INSERT INTO workspace (name, roomname)
VALUES ('Workspace A', 'Room 1'),
       ('Workspace B', 'Room 2'),
       ('Workspace C', 'Room 3');

-- Beispiel Daten für die Tabelle "equipment"
INSERT INTO equipment (workspace, description)
VALUES (1, 'Laptop'),
       (1, 'Beamer'),
       (2, 'Whiteboard'),
       (2, '2x Monitor'),
       (3, 'curvedMonitor'),
       (3, 'Mouse');

-- Beispiel Daten für die Tabelle "room"
-- INSERT INTO room (workspace, name)
-- VALUES (1, 'Meeting Room 1'),
--        (2, 'Conference Room'),
--        (3, 'Training Room');

/*
-- Beispiel Daten für die Tabelle "booking"
INSERT INTO booking (username, start_time, end_time, bookingDay, workspace_id)
VALUES ('hguelbol', '09:00:00', '12:00:00', '2024-02-10', 1),
       ('User2', '14:00:00', '16:00:00', '2024-02-11', 2),
       ('User3', '10:00:00', '12:00:00', '2024-02-12', 3);
*/