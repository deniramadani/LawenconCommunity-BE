package com.lawencon.community.service;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.constant.ProductTypeConst;
import com.lawencon.community.constant.ResponseConst;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.PaymentDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Payment;
import com.lawencon.community.model.Product;
import com.lawencon.community.model.User;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PaymentService extends BaseCoreService {

	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private UserDao userDao;
	
	public String generateTrx(final String code) {
		final Date date = new Date();
		final Format month = new SimpleDateFormat("MMM");
		final String strMonth = month.format(date);
		final Format year = new SimpleDateFormat("yyyy");
		final String yearNow = year.format(date);
		final Long trxCount = paymentDao.countAllType(code) + 1L;
		String trxString = trxCount.toString();
		if(trxString.length() <= 1) {
			trxString = "0000"+trxString;
		} else if(trxString.length() <= 2) {
			trxString = "000"+trxString;
		} else if(trxString.length() <= 3) {
			trxString = "00"+trxString;
		} else {
			trxString = "0"+trxString;
		}
		final StringBuilder transactionCode = new StringBuilder()
				.append(trxString).append("/")
				.append(code).append("/")
				.append(strMonth).append("/")
				.append(yearNow);
		return transactionCode.toString();
	}
	
	public ResponseDto insert(final Payment data, final String code)  {
		final ResponseDto response = new ResponseDto();
		try {
			begin();
			final Payment insertOne = new Payment();
			final String trxCode = generateTrx(code);
			insertOne.setTransactionCode(trxCode);
			insertOne.setApproval(false);
			final User user = new User();
			user.setId(principalService.getAuthPrincipal());
			insertOne.setUser(user);
			final Product product = new Product();
			product.setId(data.getProduct().getId());
			insertOne.setProduct(product);
			File file = new File();
			file.setFileEncode(data.getFile().getFileEncode());
			file.setFileExtensions(data.getFile().getFileExtensions());
			file = fileDao.save(file);
			insertOne.setFile(file);
			paymentDao.save(insertOne);			
			commit();
			response.setMessage(ResponseConst.CREATED.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			response.setMessage(ResponseConst.FAILED.getResponse());
		}
		return response;
	}
	
	public ResponseDto paymentAccepted(final String id)  {
		final ResponseDto response = new ResponseDto();
		final Payment result = paymentDao.getByIdAndDetach(Payment.class, id);
		final Optional<Payment> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			Payment updateOne = optional.get();
			try {
				begin();
				final User user = userDao.getByIdAndDetach(User.class, updateOne.getProduct().getOwnerId().getId());
				final Optional<User> userOpt = Optional.ofNullable(user);
				if(userOpt.isPresent()) {
					User updateUsr = userOpt.get();
					BigDecimal newBallance = new BigDecimal("0");
					newBallance = newBallance.add(updateUsr.getBallance());
					if(updateOne.getProduct().getProductType().getProductTypeCode()
							.equalsIgnoreCase(ProductTypeConst.SUBSCRIBE.getProductTypeCodeEnum())) {
						newBallance = newBallance.add(updateOne.getProduct().getPrice());
						updateUsr.setBallance(newBallance);
					} else {
						
						BigDecimal rateOwner = new BigDecimal("0.9");
						newBallance = newBallance.add(rateOwner.multiply(updateOne.getProduct().getPrice()));
						updateUsr.setBallance(newBallance);
						
						BigDecimal systemBallance = new BigDecimal("0");
						final String systemId = "4ba262b9-258b-4ae3-b879-ee286c1db783";
						final User system = userDao.getByIdAndDetach(User.class, systemId);
						final Optional<User> systemOpt = Optional.ofNullable(system);
						final User updateSystem = systemOpt.get();
						BigDecimal rateSystem = new BigDecimal("0.1");
						systemBallance = systemBallance.add(rateSystem.multiply(updateOne.getProduct().getPrice()));
						systemBallance = systemBallance.add(updateSystem.getBallance());
						updateSystem.setBallance(systemBallance);
						userDao.saveAndFlush(updateSystem);
					}
					userDao.saveAndFlush(updateUsr);
				} else {
					throw new RuntimeException(ResponseConst.USER_NOT_FOUND.getResponse());
				}
				updateOne.setApproval(true);
				updateOne.setIsActive(true);
				updateOne = paymentDao.saveAndFlush(updateOne);		
				commit();
				response.setMessage(ResponseConst.PAYMENT_ACCEPTED.getResponse());
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
	public ResponseDto paymentRejected(final String id)  {
		final ResponseDto response = new ResponseDto();
		final Payment result = paymentDao.getByIdAndDetach(Payment.class, id);
		final Optional<Payment> optional = Optional.ofNullable(result);
		if(optional.isPresent()) {
			Payment updateOne = optional.get();
			try {
				begin();
				updateOne.setApproval(false);
				updateOne.setIsActive(false);
				updateOne = paymentDao.saveAndFlush(updateOne);		
				commit();
				response.setMessage(ResponseConst.PAYMENT_REJECTED.getResponse());
			} catch (Exception e) {
				e.printStackTrace();
				rollback();
				response.setMessage(ResponseConst.FAILED.getResponse());
			}			
		}
		return response;
	}
	
}
