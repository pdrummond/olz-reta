package iode.olz.reta.dao;

import java.util.List;

public class ParsedTags {
	
	List<HashTag> hashTags;
	
	public ParsedTags(List<HashTag> hashTags) {
		this.hashTags = hashTags;
	}

	public List<HashTag> getHashTags() {
		return hashTags;
	}	
}
