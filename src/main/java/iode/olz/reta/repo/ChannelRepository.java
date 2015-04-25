package iode.olz.reta.repo;

import iode.olz.reta.dao.Channel;

import java.util.Date;
import java.util.List;

public interface ChannelRepository {
	List<Channel> getPageOfChannels(Date fromDate);
	Channel getChannel(String id);
	Channel createChannel(Channel channel);
}
