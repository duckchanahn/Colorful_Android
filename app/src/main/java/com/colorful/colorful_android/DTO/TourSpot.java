package com.colorful.colorful_android.DTO;

import java.io.Serializable;


public class TourSpot implements Serializable {

	int tourSpotId;
	String name;
	String area;
	String position_x;
	String position_y;	
	String address;
	String personalColor;
	String psyColor;
	String hours;
	String homepage;
	String parking;
	String content;
	String images;
	String telephone;
	String tags;
	
	public TourSpot() {
		super();
		
		this.tourSpotId = 0;
		this.name = "";
		this.area = "";
		this.position_x = "";
		this.position_y = "";
		this.address = "";
		this.personalColor = "";
		this.psyColor = "";
		this.hours = "";
		this.homepage = "";
		this.parking = "";
		this.content = "";
		this.images = "";
		this.telephone = "";
		this.tags = "";
	}
	
	public TourSpot(int tourSpotId,
			String name,
			String area,
			String position_x,
			String position_y,
			String address,
			String personalColor,
			String psyColor,
			String hours,
			String homepage,
			String parking,
			String content,
			String images,
			String telephone,
			String tags
			) {
		super();
		
		this.tourSpotId = tourSpotId;
		this.name = name;
		this.area = area;
		this.position_x = position_x;
		this.position_y = position_y;
		this.address = address;
		this.personalColor = personalColor;
		this.psyColor = psyColor;
		this.hours = hours;
		this.homepage = homepage;
		this.parking = parking;
		this.content = content;
		this.images = images;
		this.telephone = telephone;
		this.tags = tags;
	}



	public int getTourSpotId() {
		return tourSpotId;
	}

	public void setTourSpotId(int tourSpotId) {
		this.tourSpotId = tourSpotId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPosition_x() {
		return position_x;
	}

	public void setPosition_x(String position_x) {
		this.position_x = position_x;
	}

	public String getPosition_y() {
		return position_y;
	}

	public void setPosition_y(String position_y) {
		this.position_y = position_y;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getPersonalColor() {
		return personalColor;
	}

	public void setPersonalColor(String personalColor) {
		this.personalColor = personalColor;
	}

	public String getPsyColor() {
		return psyColor;
	}

	public void setPsyColor(String psyColor) {
		this.psyColor = psyColor;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	


}
