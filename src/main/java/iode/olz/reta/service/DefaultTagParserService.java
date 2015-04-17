package iode.olz.reta.service;

import iode.olz.reta.dao.HashTag;
import iode.olz.reta.dao.OlzMessage;
import iode.olz.reta.dao.ParsedTags;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class DefaultTagParserService implements TagParserService {

	@Override
	public ParsedTags parseLoopItem(OlzMessage loopItem) {
		return new ParsedTags(extractHashTags(loopItem));
	}
	
	public List<HashTag> extractHashTags(OlzMessage loopItem) {
		List<HashTag> hashTags = new ArrayList<HashTag>();
		Pattern p = Pattern.compile("(#[\\w\\/-]+)");

		Matcher m = p.matcher(loopItem.getContent());
		while(m.find()) {
			String match = m.group(1);
			hashTags.add(new HashTag.Builder().loopItemId(loopItem.getId()).tag(match).build());			
		}
		return hashTags;
	}
}
