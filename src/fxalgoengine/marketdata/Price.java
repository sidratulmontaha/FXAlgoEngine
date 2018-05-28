package fxalgoengine.marketdata;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Price {

	@SerializedName("instrument")
	@Expose
	private String instrument;
	@SerializedName("time")
	@Expose
	private LocalDateTime time;
	@SerializedName("bid")
	@Expose
	private double bid;
	@SerializedName("ask")
	@Expose
	private double ask;
	@SerializedName("status")
	@Expose
	private String status;

	/**
	 *
	 * @return The instrument
	 */
	public String getInstrument() {
		return instrument;
	}

	/**
	 *
	 * @param instrument
	 *            The instrument
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

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
	 * @return The bid
	 */
	public double getBid() {
		return bid;
	}

	/**
	 *
	 * @param bid
	 *            The bid
	 */
	public void setBid(double bid) {
		this.bid = bid;
	}

	/**
	 *
	 * @return The ask
	 */
	public double getAsk() {
		return ask;
	}

	/**
	 *
	 * @param ask
	 *            The ask
	 */
	public void setAsk(double ask) {
		this.ask = ask;
	}

	/**
	 *
	 * @return The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 *
	 * @param status
	 *            The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}