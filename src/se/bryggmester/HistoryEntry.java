package se.bryggmester;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import se.bryggmester.HistoryData.Type;

/**
 * @author jorgen.smas@entercash.com
 */
public class HistoryEntry {

	private long id;
	private Date date;
	private String programName;
	private List<HistoryData> data = new LinkedList<>();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public List<HistoryData> getData() {
		return data;
	}

	public void setData(List<HistoryData> data) {
		this.data = data;
	}

	public Collection<Type> getDataTypes() {
		HashSet<Type> types = new HashSet<>();
		for (HistoryData d : data) {
			types.add(d.getType());
		}
		return types;
	}

	public String getDataString(Type type) {
		StringBuilder s = new StringBuilder();
		s.append("[");
		for (HistoryData d : data) {
			if (d.getType() == type) {
				if (s.length() > 1)
					s.append(",");
				s.append("[");
				s.append(d.getDate().getTime());
				s.append(",");
				if (d.getType() == Type.PUMP) {
					if (PumpState.valueOf(d.getValue()) == PumpState.ON) {
						s.append("10");
					} else {
						s.append("0");
					}
				} else if (d.getType() == Type.HEAT) {
					if (HeatState.valueOf(d.getValue()) == HeatState.ON) {
						s.append("20");
					} else {
						s.append("0");
					}
				} else {
					s.append(d.getValue());
				}
				s.append("]");
			}
		}
		s.append("]");
		return s.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
