package iode.olz.reta.repo;

import iode.olz.reta.dao.OlzItem;

import java.util.Date;
import java.util.List;

public interface OlzItemRepository {
	List<OlzItem> getPageOfItems(Date fromDate);	
}