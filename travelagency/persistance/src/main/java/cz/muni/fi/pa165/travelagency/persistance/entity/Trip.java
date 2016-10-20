package cz.muni.fi.pa165.travelagency.persistance.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Entity trip in travel agency
 * @author Katerina Caletkova
 */
@Entity
public class Trip {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id; 
   
   @NotNull
   private String name;
   
   @NotNull
   private Date dateFrom;
   
   @NotNull
   private Date dateTo;
   
   @NotNull
   private String destination;
   
   private String description;
   
   @NotNull
   private Integer capacity;
   
   @NotNull
   private BigDecimal price;
   
   @OneToMany(mappedBy="trip")
   private Set<Excursion> excursions = new HashSet<Excursion>();

    public Trip() {
    }
   
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    /**
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    /**
     * 
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }
    /**
     * 
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the excursions
     */
    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }
    /**
     * @param e the excursion to add
     */
    public void addExcursion(Excursion e) {
        excursions.add(e);
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + id + ", name=" + name + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", destination=" + destination + ", description=" + description + ", capacity=" + capacity + ", price=" + price + ", excursions=" + excursions + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.dateFrom);
        hash = 23 * hash + Objects.hashCode(this.dateTo);
        hash = 23 * hash + Objects.hashCode(this.destination);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trip other = (Trip) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        if (!Objects.equals(this.dateFrom, other.dateFrom)) {
            return false;
        }
        if (!Objects.equals(this.dateTo, other.dateTo)) {
            return false;
        }
        return true;
    }
}
