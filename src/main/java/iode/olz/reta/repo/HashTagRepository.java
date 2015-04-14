package iode.olz.reta.repo;

import iode.olz.reta.dao.HashTag;

import java.util.List;

public interface HashTagRepository {
	List<HashTag> getHashTags(String loopId);
	HashTag getHashTag(String id);
	HashTag createHashTag(HashTag hashTag);
	HashTag updateHashTag(HashTag hashTag);
	void deleteHashTag(String hashTagId);
	void deleteLoopHashTags(String loopId);
	boolean tagExists(HashTag hashTag);
	void deleteAllUserEntries(String userId);
}
