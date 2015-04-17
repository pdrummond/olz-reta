package iode.olz.reta.service;

import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.ParsedTags;

public interface TagParserService {

	public ParsedTags parseLoopItem(OlzMessage loopItem);
}
