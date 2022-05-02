package com.example.coursemc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.coursemc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preecherPagamentoComBoleto(PagamentoComBoleto pagto, Date InstanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(InstanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}

}
