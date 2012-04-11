package com.contento3.cms.page.category.service;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.model.Category;
import com.contento3.common.assembler.Assembler;

public interface CategoryAssembler extends Assembler<CategoryDto,Category> {
	/*
	 * cleat stack in categoryAssmblerImpl class
	 */
	public void clearStack();
}
