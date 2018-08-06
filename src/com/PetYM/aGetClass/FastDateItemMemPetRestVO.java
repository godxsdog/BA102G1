package com.PetYM.aGetClass;



import java.io.Serializable;
import java.util.ArrayList;

import com.PetYM.Member.Member;
import com.PetYM.Pet.Pet;
import com.PetYM.Restaurant.Restaurant;
import com.PetYM.puDateItem.DateItemVO;

/**
 * Created by k3nt on 2017/8/3.
 */

public class FastDateItemMemPetRestVO implements Serializable {

    public FastDateItemMemPetRestVO(ArrayList<DateItemVO> dateItemList, ArrayList<Member> membersList,
			ArrayList<Pet> petList, ArrayList<Restaurant> restaurantList) {
		super();
		this.dateItemList = dateItemList;
		this.membersList = membersList;
		this.petList = petList;
		this.restaurantList = restaurantList;
	}
	public ArrayList<DateItemVO> getDateItemList() {
		return dateItemList;
	}
	public void setDateItemList(ArrayList<DateItemVO> dateItemList) {
		this.dateItemList = dateItemList;
	}
	public ArrayList<Member> getMembersList() {
		return membersList;
	}
	public void setMembersList(ArrayList<Member> membersList) {
		this.membersList = membersList;
	}
	public ArrayList<Pet> getPetList() {
		return petList;
	}
	public void setPetList(ArrayList<Pet> petList) {
		this.petList = petList;
	}
	public ArrayList<Restaurant> getRestaurantList() {
		return restaurantList;
	}
	public void setRestaurantList(ArrayList<Restaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}
	ArrayList<DateItemVO> dateItemList;
    ArrayList<Member> membersList;
    ArrayList<Pet> petList;
    ArrayList<Restaurant> restaurantList;

}
