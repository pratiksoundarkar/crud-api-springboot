package com.employee.demo.response;

import lombok.Data;

@Data
public class AddressResponse {

  private int id;
  private String state;
  private String city;
  private int pinCode;
}
