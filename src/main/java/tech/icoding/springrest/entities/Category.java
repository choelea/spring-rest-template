package tech.icoding.springrest.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8065556628429735630L;
	@Id
    @Column(length = 20)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(length = 100, nullable = false)
	private String name;
	
	

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

	
    
}
