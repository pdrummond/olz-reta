package iode.olz.reta.repo;

import iode.olz.reta.dao.LoopItem;

public interface LoopItemRepository {
	LoopItem getLoopItem(String id);
	LoopItem createLoopItem(LoopItem loopItem);	
}