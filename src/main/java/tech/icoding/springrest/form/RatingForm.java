package tech.icoding.springrest.form;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class RatingForm {
	private Long productId;
	
	@NotNull
    @DecimalMin("0")
    @DecimalMax("5")
	private BigDecimal rating;
	@NotNull
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public BigDecimal getRating() {
		return rating;
	}
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
}
