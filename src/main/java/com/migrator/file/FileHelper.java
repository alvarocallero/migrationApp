package com.migrator.file;

import java.util.Base64;
import org.apache.log4j.Logger;
import com.migrator.service.DocumentServiceImpl;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;

public class FileHelper {

	final static Logger logger = Logger.getLogger(FileHelper.class);


	public static String getFileId(SObject file) {
		String result = (String) file.getField("Id");
		return result;
	}

	public static String getFileOwner(SObject file) {
		String result = (String) file.getField("AuthorId");
		return result;
	}

	public static String getFileFullName(SObject file) {
		String result = (String) file.getField("Name") + "." + file.getField("Type");
		return result;
	}

	public static byte[] fileDataAsByteArray(String fileData) {
		return (byte[]) Base64.getDecoder().decode(fileData);
	}

	public static SObject[] getFirst200Documents() {
		logger.info("Entering getFirst200Documents >>>");
		QueryResult result = null;
		try {
			result = DocumentServiceImpl.connection.query("SELECT Id, AuthorId, FolderId, Name, Type, BodyLength, IsDeleted FROM Document "
																	+ "order by id asc limit 200 ");
			logger.info("Leaving getFirst200Documents <<<");
		} catch (Exception e) {
			logger.error("Error on method getFirst200Documents: " + e);
			System.exit(0);
		}
		return result.getRecords();
	}
	
	public static SObject[] getnext200Documents(String documentId) {
		logger.info("Entering getnext200Documents >>>");
		QueryResult result = null;
		try {
			result = DocumentServiceImpl.connection.query("SELECT Id, AuthorId, FolderId, Name, Type, BodyLength, IsDeleted FROM Document "
					+ " where id > "+"'"+documentId+"'"+" order by id asc limit 200 ");
			logger.info("Leaving getnext200Documents <<<");
		} catch (Exception e) {
			logger.error("Error on method getnext200Documents: " + e);
			System.exit(0);
		}
		return result.getRecords();
	}

}
