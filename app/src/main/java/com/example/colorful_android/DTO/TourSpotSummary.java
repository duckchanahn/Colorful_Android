package com.example.colorful_android.DTO;


public class TourSpotSummary {

	int tourSpotId;
	String images;
	
	public TourSpotSummary() {
		super();
		
		this.tourSpotId = 0;
		this.images = "";
	}
	
	public TourSpotSummary(int tourSpotId, String images) {
		super();
		
		this.tourSpotId = tourSpotId;
		this.images = images;
	}


	public int getTourSpotId() {
		return tourSpotId;
	}

	public void setTourSpotId(int tourSpotId) {
		this.tourSpotId = tourSpotId;
	}


	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}


}
