package org.ajaxer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ajaxer.common.FirebaseCollectionName;
import org.ajaxer.dto.FcmTokenDto;
import org.ajaxer.dto.NotificationMessageDto;
import org.ajaxer.dto.NotificationStatusDto;
import org.ajaxer.service.CommonService;
import org.ajaxer.service.FirebaseNotificationService;
import org.ajaxer.service.FirestoreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Shakir Ansari
 * @since 2025-01-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationCommandLineRunner implements CommandLineRunner
{
	private final FirestoreService firestoreService;
	private final CommonService commonService;
	private final FirebaseNotificationService firebaseNotificationService;

	@Override
	public void run(String... args) throws Exception
	{
		sendDailyReminderNotification();
	}

	public void sendDailyReminderNotification() throws Exception
	{
		LocalDateTime localDateTime = commonService.getIstTimeDateTime();
		log.debug("localDateTime: {}", localDateTime);

		int quarter = getQuarter(localDateTime.getMinute());
		log.debug("quarter: {}", quarter);

		List<NotificationStatusDto> notificationStatusDtoList = firestoreService.fetchNotificationStatus(true,
		                                                                                                 localDateTime.getHour(),
		                                                                                                 quarter);

		log.debug("notificationStatusDtoList: {}", notificationStatusDtoList);

		if (CollectionUtils.isEmpty(notificationStatusDtoList))
		{
			log.error("Notification status list is empty");
			return;
		}

		String dailyReminderChannelId = commonService.getDailyReminderChannelId();
		log.debug("dailyReminderChannelId: {}", dailyReminderChannelId);

		String messageCollection = commonService.getPrefixedCollectionName(FirebaseCollectionName.NOTIFICATION_MESSAGES);
		log.debug("messageCollection: {}", messageCollection);

		String fcmTokenCollection = commonService.getPrefixedCollectionName(FirebaseCollectionName.FCM_TOKENS);
		log.debug("fcmTokenCollection: {}", fcmTokenCollection);

		var messageDto = firestoreService.fetchData(messageCollection, dailyReminderChannelId, NotificationMessageDto.class);
		log.debug("messageDto: {}", messageDto);

		for (NotificationStatusDto dto : notificationStatusDtoList)
		{
			FcmTokenDto fcmTokenDto = firestoreService.fetchData(fcmTokenCollection, dto.getUsername(), FcmTokenDto.class);
			log.debug("fcmTokenDto: {}", fcmTokenDto);

			if (!StringUtils.hasText(fcmTokenDto.getToken()))
			{
				log.error("fcmTokenDto.getToken() is empty for user: {}", fcmTokenDto.getUsername());
				continue;
			}

			firebaseNotificationService.sendNotification(fcmTokenDto.getToken(), dailyReminderChannelId, messageDto);
		}
	}

	public static boolean isMinuteMatched(int currentMinute, int savedMinute)
	{
		if (savedMinute == 0 && currentMinute < 15)
			return true;

		if (savedMinute == 15)
			if (currentMinute >= 15 && currentMinute < 30)
				return true;

		if (savedMinute == 30)
			if (currentMinute >= 30 && currentMinute < 45)
				return true;

		return savedMinute == 45 && currentMinute >= 45;
	}

	public static int getQuarter(int minute)
	{
		if (minute < 0 || minute > 59)
			minute = 0;

		if (minute < 15)
			return 0;

		if (minute < 30)
			return 1;

		if (minute < 45)
			return 2;

		return 3;
	}
}
