CREATE EXTENSION "uuid-ossp";

CREATE TABLE loopItems (
	id UUID NOT NULL DEFAULT uuid_generate_v4(),
	content TEXT,
	archived BOOLEAN NOT NULL DEFAULT false,
	createdAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	updatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	createdBy TEXT, 
	updatedBy TEXT,	
	CONSTRAINT loopPk PRIMARY KEY (id)
);