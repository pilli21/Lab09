package it.polito.tdp.borders.model;

public class Border {

	private Integer stato1;
	private Integer stato2;
	
	public Border(Integer stato1, Integer stato2) {
		this.stato1 = stato1;
		this.stato2 = stato2;
	}

	public Integer getStato1() {
		return stato1;
	}

	public void setStato1(Integer stato1) {
		this.stato1 = stato1;
	}

	public Integer getStato2() {
		return stato2;
	}

	public void setStato2(Integer stato2) {
		this.stato2 = stato2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stato1 == null) ? 0 : stato1.hashCode());
		result = prime * result + ((stato2 == null) ? 0 : stato2.hashCode());
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
		Border other = (Border) obj;
		if (stato1 == null) {
			if (other.stato1 != null)
				return false;
		} else if (!stato1.equals(other.stato1))
			return false;
		if (stato2 == null) {
			if (other.stato2 != null)
				return false;
		} else if (!stato2.equals(other.stato2))
			return false;
		return true;
	}
	
	
	
	
}
