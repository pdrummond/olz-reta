ALTER table messages ADD COLUMN status INTEGER;
ALTER table messages DROP COLUMN archived;
ALTER table messages ADD COLUMN milestoneId UUID;