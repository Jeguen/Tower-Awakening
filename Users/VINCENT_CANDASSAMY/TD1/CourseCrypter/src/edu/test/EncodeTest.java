package edu.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import edu.iut.Encode;
/**
 * 
 * @author swamynathan
 *
 */
public class EncodeTest {

	@Test
	public void test() {
		System.out.println("Test d'Encodage");
		String[] arguments = {"--input-file=textEncode.txt","--output-file=text.txt.crypt","--key=1234"};
		try {
			File test = new File("textEncode.txt"); test.createNewFile();
			Encode.main(arguments);
			FileOutputStream fileTestO = new FileOutputStream(test);
			fileTestO.write("Ceci est un test avec le code 1234".getBytes());
			
			File test2 = new File("text.txt.crypt");
			FileInputStream fileTestI = new FileInputStream(test2);
			while(fileTestI.available()>0) System.out.print((char)fileTestI.read());
			System.out.print('\n');
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
