package org.ajaxer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Shakir Ansari
 * @since 2025-01-11
 */
@Getter
@Service
@RequiredArgsConstructor
public class CommonService
{
	@Value("${firebase.collection-prefix}")
	private String collectionPrefix;

	public String getPrefixedCollectionName(String collectionName)
	{
		return collectionPrefix + collectionName;
	}
}
