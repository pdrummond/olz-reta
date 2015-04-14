CREATE EXTENSION "uuid-ossp";

CREATE TABLE loopItems (
	id UUID NOT NULL DEFAULT uuid_generate_v4(),
	itemType INTEGER NOT NULL DEFAULT 1,
	content TEXT,
	archived BOOLEAN NOT NULL DEFAULT false,
	createdAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	updatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	createdBy TEXT, 
	updatedBy TEXT,	
	CONSTRAINT loopPk PRIMARY KEY (id)
);

CREATE TABLE hashTags (
	id UUID NOT NULL DEFAULT uuid_generate_v4(),
	loopItemId UUID,
	tag TEXT,
	tagName TEXT,
	longValue BIGINT,
	doubleValue REAL,
	textValue TEXT,
	color TEXT,
	hashTagType INTEGER DEFAULT 1 NOT NULL,
	valueType INTEGER DEFAULT 1 NOT NULL,
	createdAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	updatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	createdBy TEXT, 
	updatedBy TEXT
);

CREATE TABLE users (
      userId TEXT NOT NULL,
      password TEXT NOT NULL,
      email TEXT NOT NULL, 
      firstName TEXT,
      surname TEXT,      
      enabled boolean NOT NULL,
	  createdAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	  updatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
      CONSTRAINT pkUsers PRIMARY KEY (userId)
);

create table authorities (
      userId TEXT NOT NULL,
      authority TEXT NOT NULL,
      CONSTRAINT fkAuthoritiesUsers FOREIGN KEY (userId) REFERENCES users(userId)  
);