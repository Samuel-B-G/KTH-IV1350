package integration;

import java.util.ArrayList;
import java.util.List;

import model.SaleDTO;

/**
 * This class represents the accounting system.
 */

public class AccountingSystem {
	private List<SaleDTO> saleDTOList;
	
	/**
	 * Creates a new instance, representing the details of the accounting system.
	 * This is temporary.
	 */
	
	public AccountingSystem() {
		this.saleDTOList = new ArrayList<SaleDTO>();
	}
	
	/**
	 * Adds the details of a finished sale to the accounting system
	 * This is temporary.
	 * 
	 * @param saleDTO
	 */
	
	public void updateAccounting(SaleDTO saleDTO) {
		saleDTOList.add(saleDTO);
	}
}
