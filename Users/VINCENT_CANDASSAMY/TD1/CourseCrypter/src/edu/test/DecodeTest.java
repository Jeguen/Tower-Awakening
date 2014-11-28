package edu.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import edu.iut.Decode;

/**
 * 
 * @author swamynathan
 *
 */

public class DecodeTest {

	@Test
	public void test() {
		new EncodeTest().test();
		System.out.println("Test de Decodage");
		String[] arguments = {"--input-file=text.txt.crypt","--output-file=textDecode.txt","--key=1234"};

		try {
			Decode.main(arguments);		
			File actual = new File("textDecode.txt");
			File expected = new File("textEncode.txt");
			FileInputStream fileActualI = new FileInputStream(actual);
			FileInputStream fileExpectedI = new FileInputStream(expected);
			byte[] expectedData = new byte[fileActualI.available()], actualData= new byte[fileExpectedI.available()];
			fileActualI.read(actualData); fileExpectedI.read(expectedData);
			Assert.assertArrayEquals(expectedData, actualData);
			System.out.print('\n');
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
