package fxalgoengine.marketdata;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Prices {

	@SerializedName("prices")
	@Expose
	private List<Price> prices = new ArrayList<Price>();

	/**
	 *
	 * @return The prices
	 */
	public List<Price> getPrices() {
		return prices;
	}

	/**
	 *
	 * @param prices
	 *            The prices
	 */
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

}