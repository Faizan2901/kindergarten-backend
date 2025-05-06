package com.nmpc.kindergarten.dto;

public class MonthlyAttendanceStatDTO {
	
	private String month;
	private Integer totalDays;
	private Integer presentDays;

	private Integer absentDays;

	public MonthlyAttendanceStatDTO(Builder builder) {
		this.month=builder.month;
		this.totalDays=builder.totalDays;
		this.absentDays=builder.absentDays;
		this.presentDays=builder.presentDays;
	}


	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}
	public Integer getPresentDays() {
		return presentDays;
	}
	public void setPresentDays(Integer presentDays) {
		this.presentDays = presentDays;
	}

	public Integer getAbsentDays() {
		return absentDays;
	}

	public void setAbsentDays(Integer absentDays) {
		this.absentDays = absentDays;
	}

	public static class Builder {
		private String month;
		private Integer totalDays;
		private Integer presentDays;

		private Integer absentDays;
		
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

		public Builder absentDays(Integer absentDays) {
			this.absentDays=absentDays;
			return this;
		}

		public MonthlyAttendanceStatDTO build() {
			return new MonthlyAttendanceStatDTO(this);
		}
		
	}


	@Override
	public String toString() {
		return "MonthlyAttendanceStatDTO{" +
				"month='" + month + '\'' +
				", totalDays=" + totalDays +
				", presentDays=" + presentDays +
				", absentDays=" + absentDays +
				'}';
	}
}
