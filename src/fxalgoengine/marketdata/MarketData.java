package fxalgoengine.marketdata;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class MarketData {

	@SerializedName("instrument")
	@Expose
	private String instrument;
	@SerializedName("granularity")
	@Expose
	private String granularity;
	@SerializedName("candles")
	@Expose
	private SortedSet<Candle> candles = new TreeSet<Candle>();

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
	 * @return The granularity
	 */
	public String getGranularity() {
		return granularity;
	}

	/**
	 *
	 * @param granularity
	 *            The granularity
	 */
	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	/**
	 *
	 * @return The candles
	 */
	public SortedSet<Candle> getCandles() {
		return candles;
	}

	/**
	 *
	 * @param candles
	 *            The candles
	 */
	public void setCandles(SortedSet<Candle> candles) {
		this.candles = candles;
	}

}