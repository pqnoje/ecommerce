package com.pqnoje.ecommerce.service;

import org.springframework.stereotype.Service;

import com.pqnoje.ecommerce.dao.ShelfDao;
import com.pqnoje.ecommerce.model.Shelf;

@Service
public class ShelfServiceImpl implements ShelfService {

	@Override
	public Shelf save(Shelf shelf) {
		ShelfDao shelfDao = new ShelfDao();
		Shelf savedShelf = shelfDao.save(shelf);
		return savedShelf;
	}

}
