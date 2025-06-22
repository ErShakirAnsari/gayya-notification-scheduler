package org.ajaxer.common;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Shakir Ansari
 * @since 2025-06-22
 */
public class Utils
{
	public static String encode(String plainText)
	{
		return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
	}

	public static String decode(String base64Text)
	{
		byte[] decodedBytes = Base64.getDecoder().decode(base64Text);
		return new String(decodedBytes, StandardCharsets.UTF_8);
	}
}
