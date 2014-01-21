package com.contento3.storage.provider.impl;

import org.apache.log4j.Logger;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.contento3.dam.content.storage.S3Storage;
import com.contento3.dam.content.storage.dao.S3StorageDao;
import com.contento3.storage.provider.StorageProvider;
import com.contento3.storage.provider.StorageProviderContext;

/**
 * Amazon S3 storage provider to
 * store content into S3 buckets
 * @author hamakhaa
 *
 */
public class S3Provider implements StorageProvider<S3Storage> {

	private static final Logger LOGGER = Logger.getLogger(S3Provider.class);

	private S3StorageDao storageDao;
	
	@Override
	public Boolean store(final StorageProviderContext context) {
		// Each instance of TransferManager maintains its own thread pool
		// where transfers are processed, so share an instance when possible

		S3Storage storage = fetchConfig(context.getAccountId());
		final AWSCredentials credentials = new BasicAWSCredentials(storage.getAccessKey(),storage.getSecretKey());
		final TransferManager tx = new TransferManager(credentials);
		AmazonS3 s3 = new AmazonS3Client(credentials);
		 
		
		// The upload and download methods return immediately, while
		// TransferManager processes the transfer in the background thread pool
		Upload upload = tx.upload(storage.getBucket(), context.getFile().getName(), context.getFile());

		// While the transfer is processing, you can work with the transfer object
		while (upload.isDone() == false) {
			LOGGER.debug("Upload in progress for file with name "+ context.getFile().getName()+ upload.getProgress().getPercentTransferred());
		}
		return upload.isDone();
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public S3Storage fetchConfig(final Integer accountId) {
		return storageDao.findByAccountId(accountId);
	}
	
	public void setStorageDao(final S3StorageDao storageDao){
		this.storageDao = storageDao;
	}
}
