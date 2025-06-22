package org.ajaxer.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Shakir Ansari
 * @since 2025-01-11
 */
@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class CommonService
{
	@Value("${firebase.collection-prefix}")
	private String collectionPrefix;

	@Value("${android.notification.channel-id}")
	private String dailyReminderChannelId;

	@Value("${GAYYA_IST_TIME}")
	private String istTime; //"%Y-%m-%d %H:%M:%S" || yyyy-MM-dd HH:mm:ss

	public String getPrefixedCollectionName(String collectionName)
	{
		return collectionPrefix + collectionName;
	}

	@ToString.Include
	public LocalDateTime getIstTimeDateTime()
	{
		log.info("istTime is {}", istTime);

		if (istTime == null || istTime.isEmpty())
			return LocalDateTime.now();

		// Define the formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Parse string to LocalDateTime
		return LocalDateTime.parse(istTime, formatter);
	}
}
