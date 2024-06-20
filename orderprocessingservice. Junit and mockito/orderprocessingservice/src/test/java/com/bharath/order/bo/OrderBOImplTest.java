package com.bharath.order.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bharath.order.bo.exception.BOException;
import com.bharath.order.dao.OrderDAO;
import com.bharath.order.dto.Order;

@ExtendWith(MockitoExtension.class)
class OrderBOImplTest {

	private static final int ORDER_ID = 123;
	private static final int ZERO = 0;
	private static final int ONE = 1;

	private OrderBOImpl bo;
	@Mock
	private OrderDAO dao;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		bo = new OrderBOImpl();
		bo.setDao(dao);
		;
	}

	@Test
	void placeOrderShouldCreateANOrder() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(order)).thenReturn(ONE);
		boolean placeOrder = bo.placeOrder(order);
		assertTrue(placeOrder);
		verify(dao).create(order);

	}

	@Test
	void placeOrderShouldCreateANOrderFalse() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(order)).thenReturn(ZERO);
		boolean placeOrder = bo.placeOrder(order);
		assertFalse(placeOrder);
		verify(dao).create(order);

	}

	@Test
	public void placeOrderShouldBOException() throws SQLException, BOException {
		Order order = new Order();
		when(dao.create(order)).thenThrow(SQLException.class);
		Assertions.assertThrows(BOException.class, () -> {
			bo.placeOrder(order);
		});
	}

	@Test
	void cancelOrderShouldCancelTheOrder() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenReturn(ORDER_ID);
		boolean cancelOrder = bo.cancelOrder(ORDER_ID);

		Assertions.assertTrue(cancelOrder);
		verify(dao).read(ORDER_ID);
		verify(dao).update(order);

	}

	@Test
	void cancelOrderShouldNotCancelTheOrder() throws SQLException, BOException {
		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenReturn(ZERO);
		boolean cancelOrder = bo.cancelOrder(ORDER_ID);

		Assertions.assertFalse(cancelOrder);
		verify(dao).read(ORDER_ID);
		verify(dao).update(order);

	}

	// Exception reader
	@Test
	void cancelOrderShouldThrowExceptionOnRead() throws SQLException, BOException {
		
		when(dao.read(ORDER_ID)).thenThrow(SQLException.class);
		Assertions.assertThrows(BOException.class, ()->{
			bo.cancelOrder(ORDER_ID);
		});
		

	}

	@Test
	void cancelOrderShouldThrowExceptionOnUpdate() throws SQLException, BOException {

		Order order = new Order();
		when(dao.read(ORDER_ID)).thenReturn(order);
		when(dao.update(order)).thenThrow(SQLException.class);
		Assertions.assertThrows(BOException.class, () -> {
			bo.cancelOrder(ORDER_ID);
		});

	}

	@Test
	void deleteOrder() throws SQLException, BOException {
		when(dao.delete(ORDER_ID)).thenReturn(1);
		boolean deleteOrder = bo.deleteOrder(ORDER_ID);
		Assertions.assertTrue(deleteOrder);
		verify(dao).delete(ORDER_ID);
	}
}
