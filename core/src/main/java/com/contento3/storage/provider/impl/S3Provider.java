package com.contento3.storage.provider.impl;

import org.apache.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
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
		System.out.println("bucket size"+ s3.listBuckets().size());
		
		// The upload and download methods return immediately, while
		// TransferManager processes the transfer in the background thread pool
		Upload upload = tx.upload(storage.getBucket(), context.getFile().getName(), context.getFile());

		// While the transfer is processing, you can work with the transfer object
		while (upload.isDone() == false) {
			LOGGER.debug("Upload in progress for file with name "+ context.getFile().getName()+ upload.getProgress().getPercentTransferred());
		       System.out.println("Transfer: " + upload.getDescription());
		       System.out.println("  - State: " + upload.getState());
		       System.out.println("  - Progress: "
		                       + upload.getProgress().getBytesTransferred());

		}

		// Or you can block the current thread and wait for your transfer to
		// to complete. If the transfer fails, this method will throw an
		// AmazonClientException or AmazonServiceException detailing the reason.
		try {
			upload.waitForCompletion();
		} catch (AmazonServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AmazonClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		// After the upload is complete, call shutdownNow to release the resources.
		tx.shutdownNow();
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
