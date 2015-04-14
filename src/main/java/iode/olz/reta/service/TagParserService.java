package iode.olz.reta.service;

import iode.olz.reta.dao.LoopItem;
import iode.olz.reta.dao.ParsedTags;

public interface TagParserService {

	public ParsedTags parseLoopItem(LoopItem loopItem);
}
