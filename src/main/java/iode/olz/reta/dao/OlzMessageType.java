package iode.olz.reta.dao;

public enum OlzMessageType {
	MESSAGE(1),
	CHAT_MESSAGE(2),
	MESSAGE_CONTENT_UPDATED(3); 
	
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
		return OlzMessageType.MESSAGE;
	}
	
	public static OlzMessageType fromTypeId(int typeId) {
		OlzMessageType[] types = OlzMessageType.values();
		OlzMessageType type = OlzMessageType.MESSAGE;
		for (int i=0; i < types.length; i++) {
			if (types[i].getTypeId() == typeId) {
				type = types[i];
				break;
			}
		}
		return type;
	}
}
