package fxalgoengine.marketdata;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Candle implements Comparable<Candle>{

	@SerializedName("time")
	@Expose
	private LocalDateTime time;
	@SerializedName("openMid")
	@Expose
	private double openMid;
	@SerializedName("highMid")
	@Expose
	private double highMid;
	@SerializedName("lowMid")
	@Expose
	private double lowMid;
	@SerializedName("closeMid")
	@Expose
	private double closeMid;
	@SerializedName("volume")
	@Expose
	private int volume;
	@SerializedName("complete")
	@Expose
	private boolean complete;

	/**
	 *
	 * @return The time
	 */
	public LocalDateTime getTime() {
		return time;
	}

	/**
	 *
	 * @param time
	 *            The time
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	/**
	 *
	 * @return The openMid
	 */
	public double getOpenMid() {
		return openMid;
	}

	/**
	 *
	 * @param openMid
	 *            The openMid
	 */
	public void setOpenMid(double openMid) {
		this.openMid = openMid;
	}

	/**
	 *
	 * @return The highMid
	 */
	public double getHighMid() {
		return highMid;
	}

	/**
	 *
	 * @param highMid
	 *            The highMid
	 */
	public void setHighMid(double highMid) {
		this.highMid = highMid;
	}

	/**
	 *
	 * @return The lowMid
	 */
	public double getLowMid() {
		return lowMid;
	}

	/**
	 *
	 * @param lowMid
	 *            The lowMid
	 */
	public void setLowMid(double lowMid) {
		this.lowMid = lowMid;
	}

	/**
	 *
	 * @return The closeMid
	 */
	public double getCloseMid() {
		return closeMid;
	}

	/**
	 *
	 * @param closeMid
	 *            The closeMid
	 */
	public void setCloseMid(double closeMid) {
		this.closeMid = closeMid;
	}

	/**
	 *
	 * @return The volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 *
	 * @param volume
	 *            The volume
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 *
	 * @return The complete
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 *
	 * @param complete
	 *            The complete
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public int compareTo(Candle o) {
		return time.compareTo(o.getTime());
	}

}