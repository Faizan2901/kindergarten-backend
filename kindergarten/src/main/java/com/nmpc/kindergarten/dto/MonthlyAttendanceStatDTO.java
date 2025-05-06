package com.nmpc.kindergarten.dto;

public class MonthlyAttendanceStatDTO {
	
	private String month;
	private int totalDays;
	private int presentDays;
	
	
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}
	public int getPresentDays() {
		return presentDays;
	}
	public void setPresentDays(int presentDays) {
		this.presentDays = presentDays;
	}
	

	public static class Builder {
		private String month;
		private Integer totalDays;
		private Integer presentDays;
		
		public Builder month(String month) {
			this.month=month;
			return this;
		}
		
		public Builder totalDays(Integer totalDays) {
			this.totalDays=totalDays;
			return this;
		}
		
		public Builder presentDays(Integer presentDays) {
			this.presentDays=presentDays;
			return this;
		}
		
	}


	@Override
	public String toString() {
		return "MonthlyAttendanceStatDTO [month=" + month + ", totalDays=" + totalDays + ", presentDays=" + presentDays
				+ "]";
	}

}
