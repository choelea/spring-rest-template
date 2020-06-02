package tech.icoding.springrest.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3772117797527724933L;

	/**
     * define User id
     */
    @Id
    @Column(length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    @NotEmpty
    private String name;
    @Lob
    private String demoUrl;
    @Lob
    private String summary;
    @Lob
    private String detail;
    @Lob
    private String imageUrl;
    @Lob
    private String vedioCover;
    @Lob
    private String videoUrl;  
    @Lob
    private String whitePaperUrl;
    private Float rating = 5.0f;
    private Long ratingCount = 1L;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;
    private Long ranking = 0L;
    
    public String getRatingStr() {
    	return String.format("%.1f", getRating());
    }
    
	public Long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Long ratingCount) {
		this.ratingCount = ratingCount;
	}
	
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Long getRanking() {
		return ranking;
	}

	public void setRanking(Long ranking) {
		this.ranking = ranking;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDemoUrl() {
		return demoUrl;
	}

	public void setDemoUrl(String demoUrl) {
		this.demoUrl = demoUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getWhitePaperUrl() {
		return whitePaperUrl;
	}

	public void setWhitePaperUrl(String whitePaperUrl) {
		this.whitePaperUrl = whitePaperUrl;
	}

	public String getVedioCover() {
		return vedioCover;
	}

	public void setVedioCover(String vedioCover) {
		this.vedioCover = vedioCover;
	}
}
