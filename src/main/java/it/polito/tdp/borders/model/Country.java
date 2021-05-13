package it.polito.tdp.borders.model;

public class Country {
	private String stateAbb;
	private Integer ccode;
	private String stateName;
	
	public Country(String stateAbb, Integer ccode, String stateName) {
		this.stateAbb = stateAbb;
		this.ccode = ccode;
		this.stateName = stateName;
	}

	public String getStateAbb() {
		return stateAbb;
	}

	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	public Integer getCcode() {
		return ccode;
	}

	public void setCcode(Integer ccode) {
		this.ccode = ccode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccode == null) ? 0 : ccode.hashCode());
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
		Country other = (Country) obj;
		if (ccode == null) {
			if (other.ccode != null)
				return false;
		} else if (!ccode.equals(other.ccode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return stateName;
	}
	
	
	

}
