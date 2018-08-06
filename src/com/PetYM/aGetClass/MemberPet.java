package com.PetYM.aGetClass;

import java.io.Serializable;

import com.PetYM.Member.Member;
import com.PetYM.Pet.Pet;

public class MemberPet implements Serializable{
	   Member member;
	    Pet pet;
		public MemberPet(Member member, Pet pet) {
			super();
			this.member = member;
			this.pet = pet;
		}
		public MemberPet(){};
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
}
