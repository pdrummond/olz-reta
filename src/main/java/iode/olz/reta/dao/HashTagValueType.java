package iode.olz.reta.dao;

public enum HashTagValueType {
	NONE(0), 
	LONG(1),
	DOUBLE(2),
	TEXT(3);
	
	private int typeId;

	HashTagValueType(int type) {
		this.typeId = type;
	}
	
	public int getTypeId() {
		return typeId;
	}

	public static HashTagValueType fromName(String text) {
		for (HashTagValueType type : values()) {
			if (text.equals(type.name().toUpperCase())) {
				return type;
			}
		}
		return HashTagValueType.NONE;
	}
	
	public static HashTagValueType fromTypeId(int typeId) {
		HashTagValueType[] types = HashTagValueType.values();
		HashTagValueType type = HashTagValueType.NONE;
		for (int i=0; i < types.length; i++) {
			if (types[i].getTypeId() == typeId) {
				type = types[i];
				break;
			}
		}
		return type;
	}
}
