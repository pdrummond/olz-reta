CREATE EXTENSION "uuid-ossp";

CREATE TABLE channels (
	id UUID NOT NULL DEFAULT uuid_generate_v4(),
	messageId UUID, 
	title TEXT,
	content TEXT,
	channelType INTEGER NOT NULL DEFAULT 1,		
	createdAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	updatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	createdBy TEXT, 
	updatedBy TEXT,	
	CONSTRAINT channelPk PRIMARY KEY (id)
);

CREATE TABLE messages (
	id UUID NOT NULL DEFAULT uuid_generate_v4(),
	messageType INTEGER NOT NULL DEFAULT 1,
	title TEXT,
	content TEXT,
	channelId UUID,
	archived BOOLEAN DEFAULT FALSE,
	status INTEGER,
	createdAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	updatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	createdBy TEXT, 
	updatedBy TEXT,	
	CONSTRAINT messagePk PRIMARY KEY (id)
);

CREATE TABLE hashTags (
	id UUID NOT NULL DEFAULT uuid_generate_v4(),
	messageId UUID,
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

CREATE TABLE userSettings (
    userId TEXT NOT NULL,
    filterQuery TEXT
);

CREATE TABLE users (
      userId TEXT NOT NULL,
      password TEXT,
      email TEXT NOT NULL, 
      firstName TEXT,
      surname TEXT, 
      enabled boolean NOT NULL,
      filter TEXT,
	  createdAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
	  updatedAt TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
      CONSTRAINT pkUsers PRIMARY KEY (userId)
);

create table authorities (
      userId TEXT NOT NULL,
      authority TEXT NOT NULL,
      CONSTRAINT fkAuthoritiesUsers FOREIGN KEY (userId) REFERENCES users(userId)  
);

CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);
