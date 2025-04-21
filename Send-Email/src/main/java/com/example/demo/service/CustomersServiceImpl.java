package com.example.demo.service;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomersDto;
import com.example.demo.entity.Customers;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomersRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CustomersServiceImpl implements CustomersService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private CustomersRepository customersRepository;
	@Autowired
	private MailProperties mailProperties;

	@Override
	public CustomersDto registerCustomers(CustomersDto customersDto) {

		if (customersDto != null) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom("ammrthegenious@gmail.com");

			simpleMailMessage.setTo(customersDto.getEmailId());
			simpleMailMessage.setText(
					"Dear Customer," + "\n" + "You are welcome as a Gold Memebr of our company." + "\n" + "Thank You");
			simpleMailMessage.setSubject("Regestered Successfully");
			javaMailSender.send(simpleMailMessage);
			Customers customers = new Customers();
			BeanUtils.copyProperties(customersDto, customers);
			customersRepository.save(customers);
			BeanUtils.copyProperties(customers, customersDto);
			return customersDto;
		}
		throw new CustomerNotFoundException("Details Unavailable");

	}

	@Override
	public CustomersDto registerWithCredentials(CustomersDto customersDto) {
		if (customersDto != null) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom("ammarthegenious@gmail.com");
			simpleMailMessage.setTo(customersDto.getEmailId());
			simpleMailMessage.setText("Dear Customer :" + customersDto.getCustomerName() + "\n"
					+ "Please find your Credentias" + "\n" + " Email Id:" + customersDto.getEmailId() + "\n"
					+ "Password:" + customersDto.getPassword() + "\n" + "Please Login with your Credentials." + "\n"
					+ "Thanks & Regards," + "\n" + "Teams");
			simpleMailMessage.setSubject("Registered Successfully");
			javaMailSender.send(simpleMailMessage);
			Customers customers = new Customers();
			BeanUtils.copyProperties(customersDto, customers);
			customersRepository.save(customers);
			BeanUtils.copyProperties(customers, customersDto);
			return customersDto;

		}
		throw new CustomerNotFoundException("Customer Not Found");
	}

	@Override
	public CustomersDto registerwithCredentilasFileAttached(CustomersDto customersDto, String attachment) {
		if (customersDto != null) {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			try {
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

				/*
				 * setFrom from the application. properties then we have to use
				 * mimeMessageHelper.setFrom(mailProperties.getUsername()); It helps avoid to
				 * write sender emailId into service logic.
				 */
				mimeMessageHelper.setFrom("ammarthegenious@gmail.com");
				mimeMessageHelper.setTo(customersDto.getEmailId());
				mimeMessageHelper.setText("Dear Customer :" + customersDto.getCustomerName() + "\n"
						+ "Please find your Credentias" + "\n" + " Email Id:" + customersDto.getEmailId() + "\n"
						+ "Password:" + customersDto.getPassword() + "\n" + "Please Login with your Credentials." + "\n"
						+ "Thanks & Regards," + "\n" + "Teams");
				mimeMessageHelper.setSubject("You are Welcome");
				FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
				mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
				javaMailSender.send(mimeMessage);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			Customers customers = new Customers();
			BeanUtils.copyProperties(customersDto, customers);
			customersRepository.save(customers);
			BeanUtils.copyProperties(customers, customersDto);
			return customersDto;
		}
		throw new CustomerNotFoundException("Customer Details Unavailable");
	}

	@Override
	public CustomersDto registerWithPasswordBCryptCredentials(CustomersDto customersDto) {
		if (customersDto != null) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom("ammarthegenious@gmail.com");
			simpleMailMessage.setTo(customersDto.getEmailId());
			simpleMailMessage.setText("Dear Customer :" + customersDto.getCustomerName() + "\n"
					+ "Please find your Credentias" + "\n" + " Email Id:" + customersDto.getEmailId() + "\n"
					+ "Password:" + customersDto.getPassword() + "\n" + "Please Login with your Credentials." + "\n"
					+ "Thanks & Regards," + "\n" + "Teams");
			simpleMailMessage.setSubject("Registered Successfully");
			javaMailSender.send(simpleMailMessage);
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encode = bCryptPasswordEncoder.encode(customersDto.getPassword());
			customersDto.setPassword(encode);
			Customers customers = new Customers();
			BeanUtils.copyProperties(customersDto, customers);
			customersRepository.save(customers);
			BeanUtils.copyProperties(customers, customersDto);
			return customersDto;

		}
		throw new CustomerNotFoundException("Registered Unsuccessfully");
	}

	@Override
	public CustomersDto registCustomersDtoWithAllAbove(CustomersDto customersDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
