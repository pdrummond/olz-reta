package iode.olz.reta.result;


public class OlzResult {
	private Boolean ok; 
	private String message;
	
	public Boolean isOk() {
		return ok;
	}

	public String getMessage() {
		return message;
	}

	public static class Builder {
		private Boolean ok;
		private String message;
		
		public Builder ok(Boolean ok) {
			this.ok = ok;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public OlzResult build() {
			return new OlzResult(this);
		}
	}
	
	public static OlzResult success() {
		return new OlzResult.Builder().ok(true).build();
	}

	private OlzResult(Builder b) {
		this.ok = b.ok;
		this.message = b.message;
	}

}
