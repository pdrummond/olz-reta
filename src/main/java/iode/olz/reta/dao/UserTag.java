package iode.olz.reta.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTag {
	
	private final String tag;
	
	@JsonCreator
	public UserTag(@JsonProperty("tag") String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}	
	
	@Override
	public boolean equals(Object o) {
	    if (o == this) return true;
	    if (! (o instanceof UserTag)) return false;
	    UserTag tag = (UserTag) o;
	    return getTag().equalsIgnoreCase(tag.getTag());
	}
	
	@Override
	public int hashCode() {
	    int result = 17;
	    result = 31 * result + characterwiseCaseNormalize(getTag()).hashCode();	    
	    return result;
	}

	private static String characterwiseCaseNormalize(String s) {
	    StringBuilder sb = new StringBuilder(s);
	    for(int i = 0; i < sb.length(); i++) {
	        sb.setCharAt(i,Character.toLowerCase(Character.toUpperCase(sb.charAt(i))));
	    }
	    return sb.toString();
	}
	

}
