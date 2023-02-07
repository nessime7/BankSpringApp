package com.BankSpringApp;

import io.restassured.path.json.JsonPath;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public
class TestUtils {

	public static String getRequestBodyFromFile(String filename, String context) throws IOException {
		File file = ResourceUtils.getFile(String.format("classpath:%s/%s", context, filename));
		return new String(Files.readAllBytes(file.toPath()));
	}

	public static JsonPath getPath(String filename, String context) throws FileNotFoundException {
		return new JsonPath(ResourceUtils.getFile(String.format("classpath:%s/%s", context, filename)));
	}

}
