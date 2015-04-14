package iode.olz.reta.dao;

public enum HashTagType {
	NORMAL(0);
	
	private int typeId;

	HashTagType(int type) {
		this.typeId = type;
	}
	
	public int getTypeId() {
		return typeId;
	}

	public static HashTagType fromName(String text) {
		for (HashTagType type : values()) {
			if (text.equals(type.name().toUpperCase())) {
				return type;
			}
		}
		return HashTagType.NORMAL;
	}
	
	public static HashTagType fromTypeId(int typeId) {
		HashTagType[] types = HashTagType.values();
		HashTagType type = HashTagType.NORMAL;
		for (int i=0; i < types.length; i++) {
			if (types[i].getTypeId() == typeId) {
				type = types[i];
				break;
			}
		}
		return type;
	}
}
