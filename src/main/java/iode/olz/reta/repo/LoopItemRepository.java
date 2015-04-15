package iode.olz.reta.repo;

import java.util.Date;
import java.util.List;

import iode.olz.reta.dao.LoopItem;

public interface LoopItemRepository {
	List<LoopItem> getPageOfLoopItems(Date fromDate);
	LoopItem getLoopItem(String id);
	LoopItem createLoopItem(LoopItem loopItem);	
}