package com.example.demo.service;

import com.example.demo.dto.CustomersDto;

public interface CustomersService {
	public CustomersDto registerCustomers(CustomersDto customersDto);

	public CustomersDto registerWithCredentials(CustomersDto customersDto);

	public CustomersDto registerwithCredentilasFileAttached(CustomersDto customersDto, String attachment);

	public CustomersDto registerWithPasswordBCryptCredentials(CustomersDto customersDto);

	public CustomersDto registCustomersDtoWithAllAbove(CustomersDto customersDto);

}
