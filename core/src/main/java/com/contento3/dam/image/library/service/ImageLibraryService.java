package com.contento3.dam.image.library.service;

import java.util.Collection;

import com.contento3.common.service.StorableService;
import com.contento3.dam.image.library.dto.ImageLibraryDto;

public interface ImageLibraryService extends StorableService<ImageLibraryDto> {
	/**
	 * Return collection of image libraries which has account id
	 * @param accountId
	 * @return
	 */
	Collection<ImageLibraryDto> findImageLibraryByAccountId(Integer accountId);
	
	/**
	 * find image library by id
	 * @param imageLibraryId
	 * @return
	 */
	ImageLibraryDto findImageLibraryById(Integer imageLibraryId);
}
