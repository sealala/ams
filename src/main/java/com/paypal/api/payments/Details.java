package com.paypal.api.payments;

import com.paypal.core.rest.JSONFormatter;

public class Details  {

	/**
	 * Amount being charged for shipping.
	 */
	private String shipping;

	/**
	 * Sub-total (amount) of items being paid for.
	 */
	private String subtotal;

	/**
	 * Amount being charged as tax.
	 */
	private String tax;

	/**
	 * Fee charged by PayPal. In case of a refund, this is the fee amount refunded to the original receipient of the payment.
	 */
	private String fee;

	/**
	 * Amount being charged as handling fee.
	 */
	private String handlingFee;

	/**
	 * Amount being charged as gift wrap fee.
	 */
	private String giftWrap;

	/**
	 * Default Constructor
	 */
	public Details() {
	}


	/**
	 * Setter for shipping
	 */
	public Details setShipping(String shipping) {
		this.shipping = shipping;
		return this;
	}

	/**
	 * Getter for shipping
	 */
	public String getShipping() {
		return this.shipping;
	}


	/**
	 * Setter for subtotal
	 */
	public Details setSubtotal(String subtotal) {
		this.subtotal = subtotal;
		return this;
	}

	/**
	 * Getter for subtotal
	 */
	public String getSubtotal() {
		return this.subtotal;
	}


	/**
	 * Setter for tax
	 */
	public Details setTax(String tax) {
		this.tax = tax;
		return this;
	}

	/**
	 * Getter for tax
	 */
	public String getTax() {
		return this.tax;
	}


	/**
	 * Setter for fee
	 */
	public Details setFee(String fee) {
		this.fee = fee;
		return this;
	}

	/**
	 * Getter for fee
	 */
	public String getFee() {
		return this.fee;
	}


	/**
	 * Setter for handlingFee
	 */
	public Details setHandlingFee(String handlingFee) {
		this.handlingFee = handlingFee;
		return this;
	}

	/**
	 * Getter for handlingFee
	 */
	public String getHandlingFee() {
		return this.handlingFee;
	}


	/**
	 * Setter for giftWrap
	 */
	public Details setGiftWrap(String giftWrap) {
		this.giftWrap = giftWrap;
		return this;
	}

	/**
	 * Getter for giftWrap
	 */
	public String getGiftWrap() {
		return this.giftWrap;
	}

	/**
	 * Returns a JSON string corresponding to object state
	 *
	 * @return JSON representation
	 */
	public String toJSON() {
		return JSONFormatter.toJSON(this);
	}

	@Override
	public String toString() {
		return toJSON();
	}
}
