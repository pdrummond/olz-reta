package iode.olz.reta.repo;

import iode.olz.reta.dao.HashTag;

import java.util.List;

public interface HashTagRepository {
	List<HashTag> getHashTags(String messageId);
	HashTag getHashTag(String id);
	HashTag createHashTag(HashTag hashTag);
	HashTag updateHashTag(HashTag hashTag);
	void deleteHashTag(String hashTagId);
	void deleteLoopHashTags(String messageId);
	boolean tagExists(HashTag hashTag);
	void deleteAllUserEntries(String userId);
}
