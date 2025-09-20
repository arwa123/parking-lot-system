-- Sample lot: 2 floors, slots per type
INSERT INTO parking_lot (id, location) VALUES (1, 'Downtown');
INSERT INTO parking_floor (id, level, parking_lot_id) VALUES (1, 1, 1);
INSERT INTO parking_floor (id, level, parking_lot_id) VALUES (2, 2, 1);

-- Floor 1
INSERT INTO parking_slot (id, type, floor_id, slot_number, status, version) VALUES (1, 'CAR', 1, 'F1-C1', 'FREE', 0);
INSERT INTO parking_slot (id, type, floor_id, slot_number, status, version) VALUES (2, 'CAR', 1, 'F1-C2', 'FREE', 0);
INSERT INTO parking_slot (id, type, floor_id, slot_number, status, version) VALUES (3, 'BIKE', 1, 'F1-B1', 'FREE', 0);
INSERT INTO parking_slot (id, type, floor_id, slot_number, status, version) VALUES (4, 'TRUCK', 1, 'F1-T1', 'FREE', 0);

-- Floor 2
INSERT INTO parking_slot (id, type, floor_id, slot_number, status, version) VALUES (5, 'CAR', 2, 'F2-C1', 'FREE', 0);
INSERT INTO parking_slot (id, type, floor_id, slot_number, status, version) VALUES (6, 'BIKE', 2, 'F2-B1', 'FREE', 0);

-- Pricing
INSERT INTO pricing_rule (id, type, free_minutes, hourly_rate) VALUES (1, 'BIKE', 30, 20.00);
INSERT INTO pricing_rule (id, type, free_minutes, hourly_rate) VALUES (2, 'CAR', 15, 50.00);
INSERT INTO pricing_rule (id, type, free_minutes, hourly_rate) VALUES (3, 'TRUCK', 0, 120.00);