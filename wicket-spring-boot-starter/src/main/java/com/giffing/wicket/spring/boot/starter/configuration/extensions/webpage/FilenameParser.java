package com.giffing.wicket.spring.boot.starter.configuration.extensions.webpage;

import org.springframework.util.StringUtils;

public class FilenameParser {

	private String bundlename;
	
	private String relativeFilePath;
	
	public FilenameParser(String filename, String relativeRootDirectory){
		
//		String normalizedFilename = FilenameUtils.normalize(filename);
//		String[] split = filename.split(relativeRootDirectory);
		String relativePath = filename;
		this.relativeFilePath = relativePath;
		String[] splittedRelativePath = relativePath.split("/");
		for (String splittedRelativePart : splittedRelativePath) {
			if(splittedRelativePart.startsWith("bundle")){
				String[] splittedBundle = splittedRelativePart.split("-");
				this.bundlename = splittedBundle[1];
				break;
			}
		}
		
	}
	
	boolean haseBundleName(){
		if(!StringUtils.isEmpty(bundlename)){
			return true;
		}
		return false;
	}

	public String getBundlename() {
		return bundlename;
	}

	public void setBundlename(String bundlename) {
		this.bundlename = bundlename;
	}

	public String getRelativeFilePath() {
		return relativeFilePath;
	}

	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}
	
}
