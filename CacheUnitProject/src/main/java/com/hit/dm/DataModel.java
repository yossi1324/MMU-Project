package com.hit.dm;

public class DataModel<T> extends java.lang.Object implements java.io.Serializable {
	private T content;
	private Long dataModelId;

	public DataModel(java.lang.Long dataModelId, T content) {
		this.content = content;
		this.dataModelId = dataModelId;
	}

	private static final long serialVersionUID = -616017516154926601L;

	public DataModel() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((dataModelId == null) ? 0 : dataModelId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		DataModel<T> other = (DataModel<T>) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dataModelId == null) {
			if (other.dataModelId != null)
				return false;
		} else if (!dataModelId.equals(other.dataModelId))
			return false;
		return true;
	}

	@Override
	public java.lang.String toString() {
		return ("Page Id:" + dataModelId + ", Content:" + content);

	}

	public java.lang.Long getDataModelId() {
		return dataModelId;

	}

	public void setDataModelId(java.lang.Long dataModelId) {
		this.dataModelId = dataModelId;
	}

	public T getContent() {
		return content;

	}

	public void setContent(T content) {
		this.content = content;
	}
}
