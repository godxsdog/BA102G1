package com.PetYM.aGetClass;

import java.io.Serializable;

import com.PetYM.Member.Member;
import com.PetYM.Pet.Pet;
import com.PetYM.Restaurant.Restaurant;
import com.sun.istack.internal.Nullable;

/**
 * Created by k3nt on 2017/8/3.
 */

public class MemberPetResturant implements Serializable{

    public MemberPetResturant(Member member, Pet pet, @Nullable Restaurant restaurant) {
        this.member = member;
        this.pet = pet;
        this.restaurant = restaurant;
    }
    public MemberPetResturant(){};

    public MemberPetResturant(Member member, Pet pet) {
		super();
		this.member = member;
		this.pet = pet;
	}
	public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    Member member;
    Pet pet;
    Restaurant restaurant;
}
