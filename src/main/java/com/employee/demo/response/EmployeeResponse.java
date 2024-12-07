package com.employee.demo.response;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class EmployeeResponse {

  private String name;
  private String email;
  private String phoneNumber;
  private AddressResponse addressResponse;

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setAddressResponse(AddressResponse addressResponse) {
    this.addressResponse = addressResponse;
  }
}
