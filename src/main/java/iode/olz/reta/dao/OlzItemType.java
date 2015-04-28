package iode.olz.reta.dao;

public enum OlzItemType {
	COMMENT(1),
	TASK(2),
	CHANNEL(3); 
	
	private int typeId;

	OlzItemType(int type) {
		this.typeId = type;
	}
	
	public int getTypeId() {
		return typeId;
	}

	public static OlzItemType fromName(String text) {
		for (OlzItemType type : values()) {
			if (text.equals(type.name().toUpperCase())) {
				return type;
			}
		}
		return OlzItemType.COMMENT;
	}
	
	public static OlzItemType fromTypeId(int typeId) {
		OlzItemType[] types = OlzItemType.values();
		OlzItemType type = OlzItemType.COMMENT;
		for (int i=0; i < types.length; i++) {
			if (types[i].getTypeId() == typeId) {
				type = types[i];
				break;
			}
		}
		return type;
	}
}
