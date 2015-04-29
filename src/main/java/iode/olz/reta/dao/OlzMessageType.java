package iode.olz.reta.dao;

public enum OlzMessageType {
	COMMENT(1),
	TASK(2),
	CHANNEL(3),
	DOCUMENT(4),
	
	MESSAGE_CONTENT_UPDATED(20), 
	PROMOTE_TO_TASK(21), 
	ARCHIVE_MESSAGE(22),
	
	UPDATE_MESSAGE_EVENT(50); 

	private int typeId;

	OlzMessageType(int type) {
		this.typeId = type;
	}
	
	public int getTypeId() {
		return typeId;
	}

	public static OlzMessageType fromName(String text) {
		for (OlzMessageType type : values()) {
			if (text.equals(type.name().toUpperCase())) {
				return type;
			}
		}
		return OlzMessageType.COMMENT;
	}
	
	public static OlzMessageType fromTypeId(int typeId) {
		OlzMessageType[] types = OlzMessageType.values();
		OlzMessageType type = OlzMessageType.COMMENT;
		for (int i=0; i < types.length; i++) {
			if (types[i].getTypeId() == typeId) {
				type = types[i];
				break;
			}
		}
		return type;
	}
}
